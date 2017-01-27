package com.jsoniter;

import com.jsoniter.any.Any;

import java.io.IOException;

class IterImplForStreaming {

    public static final int readObjectFieldAsHash(JsonIterator iter) throws IOException {
        if (nextToken(iter) != '"') {
            throw iter.reportError("readObjectFieldAsHash", "expect \"");
        }
        long hash = 0x811c9dc5;
        for (; ; ) {
            byte c = 0;
            int i = iter.head;
            for (; i < iter.tail; i++) {
                c = iter.buf[i];
                if (c == '"') {
                    break;
                }
                hash ^= c;
                hash *= 0x1000193;
            }
            if (c == '"') {
                iter.head = i + 1;
                if (nextToken(iter) != ':') {
                    throw iter.reportError("readObjectFieldAsHash", "expect :");
                }
                return (int) hash;
            }
            if (!loadMore(iter)) {
                throw iter.reportError("readObjectFieldAsHash", "unmatched quote");
            }
        }
    }

    public static final Slice readObjectFieldAsSlice(JsonIterator iter) throws IOException {
        if (nextToken(iter) != '"') {
            throw iter.reportError("readObjectFieldAsSlice", "expect \"");
        }
        Slice field = readSlice(iter);
        boolean notCopied = field != null;
        if (CodegenAccess.skipWhitespacesWithoutLoadMore(iter)) {
            if (notCopied) {
                int len = field.tail() - field.head();
                byte[] newBuf = new byte[len];
                System.arraycopy(field.data(), field.head(), newBuf, 0, len);
                field.reset(newBuf, 0, newBuf.length);
            }
            if (!loadMore(iter)) {
                throw iter.reportError("readObjectFieldAsSlice", "expect : after object field");
            }
        }
        if (iter.buf[iter.head] != ':') {
            throw iter.reportError("readObjectFieldAsSlice", "expect : after object field");
        }
        iter.head++;
        return field;
    }

    final static void skipArray(JsonIterator iter) throws IOException {
        int level = 1;
        for (; ; ) {
            for (int i = iter.head; i < iter.tail; i++) {
                switch (iter.buf[i]) {
                    case '"': // If inside string, skip it
                        iter.head = i + 1;
                        skipString(iter);
                        i = iter.head - 1; // it will be i++ soon
                        break;
                    case '[': // If open symbol, increase level
                        level++;
                        break;
                    case ']': // If close symbol, increase level
                        level--;

                        // If we have returned to the original level, we're done
                        if (level == 0) {
                            iter.head = i + 1;
                            return;
                        }
                        break;
                }
            }
            if (!loadMore(iter)) {
                return;
            }
        }
    }

    final static void skipObject(JsonIterator iter) throws IOException {
        int level = 1;
        for (; ; ) {
            for (int i = iter.head; i < iter.tail; i++) {
                switch (iter.buf[i]) {
                    case '"': // If inside string, skip it
                        iter.head = i + 1;
                        skipString(iter);
                        i = iter.head - 1; // it will be i++ soon
                        break;
                    case '{': // If open symbol, increase level
                        level++;
                        break;
                    case '}': // If close symbol, increase level
                        level--;

                        // If we have returned to the original level, we're done
                        if (level == 0) {
                            iter.head = i + 1;
                            return;
                        }
                        break;
                }
            }
            if (!loadMore(iter)) {
                return;
            }
        }
    }

    final static void skipString(JsonIterator iter) throws IOException {
        for (; ; ) {
            int end = IterImplSkip.findStringEnd(iter);
            if (end == -1) {
                int j = iter.tail - 1;
                boolean escaped = true;
                // can not just look the last byte is \
                // because it could be \\ or \\\
                for (; ; ) {
                    // walk backward until head
                    if (j < iter.head || iter.buf[j] != '\\') {
                        // even number of backslashes
                        // either end of buffer, or " found
                        escaped = false;
                        break;
                    }
                    j--;
                    if (j < iter.head || iter.buf[j] != '\\') {
                        // odd number of backslashes
                        // it is \" or \\\"
                        break;
                    }
                    j--;

                }
                if (!loadMore(iter)) {
                    throw iter.reportError("skipString", "incomplete string");
                }
                if (escaped) {
                    iter.head = 1; // skip the first char as last char is \
                }
            } else {
                iter.head = end;
                return;
            }
        }
    }

    final static void skipUntilBreak(JsonIterator iter) throws IOException {
        // true, false, null, number
        for (; ; ) {
            for (int i = iter.head; i < iter.tail; i++) {
                byte c = iter.buf[i];
                if (IterImplSkip.breaks[c]) {
                    iter.head = i;
                    return;
                }
            }
            if (!loadMore(iter)) {
                iter.head = iter.tail;
                return;
            }
        }
    }

    final static boolean skipNumber(JsonIterator iter) throws IOException {
        // true, false, null, number
        boolean dotFound = false;
        for (; ; ) {
            for (int i = iter.head; i < iter.tail; i++) {
                byte c = iter.buf[i];
                if (c == '.') {
                    dotFound = true;
                    continue;
                }
                if (IterImplSkip.breaks[c]) {
                    iter.head = i;
                    return dotFound;
                }
            }
            if (!loadMore(iter)) {
                iter.head = iter.tail;
                return dotFound;
            }
        }
    }

    // read the bytes between " "
    final static Slice readSlice(JsonIterator iter) throws IOException {
        if (IterImpl.nextToken(iter) != '"') {
            throw iter.reportError("readSlice", "expect \" for string");
        }
        int end = IterImplString.findSliceEnd(iter);
        if (end != -1) {
            // reuse current buffer
            iter.reusableSlice.reset(iter.buf, iter.head, end - 1);
            iter.head = end;
            return iter.reusableSlice;
        }
        // TODO: avoid small memory allocation
        byte[] part1 = new byte[iter.tail - iter.head];
        System.arraycopy(iter.buf, iter.head, part1, 0, part1.length);
        for (; ; ) {
            if (!loadMore(iter)) {
                throw iter.reportError("readSlice", "unmatched quote");
            }
            end = IterImplString.findSliceEnd(iter);
            if (end == -1) {
                byte[] part2 = new byte[part1.length + iter.buf.length];
                System.arraycopy(part1, 0, part2, 0, part1.length);
                System.arraycopy(iter.buf, 0, part2, part1.length, iter.buf.length);
                part1 = part2;
            } else {
                byte[] part2 = new byte[part1.length + end - 1];
                System.arraycopy(part1, 0, part2, 0, part1.length);
                System.arraycopy(iter.buf, 0, part2, part1.length, end - 1);
                iter.head = end;
                iter.reusableSlice.reset(part2, 0, part2.length);
                return iter.reusableSlice;
            }
        }
    }

    final static byte nextToken(JsonIterator iter) throws IOException {
        for (; ; ) {
            for (int i = iter.head; i < iter.tail; i++) {
                byte c = iter.buf[i];
                switch (c) {
                    case ' ':
                    case '\n':
                    case '\t':
                    case '\r':
                        continue;
                    default:
                        iter.head = i + 1;
                        return c;
                }
            }
            if (!loadMore(iter)) {
                return 0;
            }
        }
    }

    public final static boolean loadMore(JsonIterator iter) throws IOException {
        if (iter.in == null) {
            return false;
        }
        if (iter.skipStartedAt != -1) {
            return keepSkippedBytesThenRead(iter);
        }
        int n = iter.in.read(iter.buf);
        if (n < 1) {
            if (n == -1) {
                return false;
            } else {
                throw iter.reportError("loadMore", "read from input stream returned " + n);
            }
        } else {
            iter.head = 0;
            iter.tail = n;
        }
        return true;
    }

    private static boolean keepSkippedBytesThenRead(JsonIterator iter) throws IOException {
        int n;
        int offset;
        if (iter.skipStartedAt == 0 || iter.skipStartedAt < iter.tail / 2) {
            byte[] newBuf = new byte[iter.buf.length * 2];
            offset = iter.tail - iter.skipStartedAt;
            System.arraycopy(iter.buf, iter.skipStartedAt, newBuf, 0, offset);
            iter.buf = newBuf;
            n = iter.in.read(iter.buf, offset, iter.buf.length - offset);
        } else {
            offset = iter.tail - iter.skipStartedAt;
            System.arraycopy(iter.buf, iter.skipStartedAt, iter.buf, 0, offset);
            n = iter.in.read(iter.buf, offset, iter.buf.length - offset);
        }
        iter.skipStartedAt = 0;
        if (n < 1) {
            if (n == -1) {
                return false;
            } else {
                throw iter.reportError("loadMore", "read from input stream returned " + n);
            }
        } else {
            iter.head = offset;
            iter.tail = offset + n;
        }
        return true;
    }

    final static byte readByte(JsonIterator iter) throws IOException {
        if (iter.head == iter.tail) {
            if (!loadMore(iter)) {
                throw iter.reportError("readByte", "no more to read");
            }
        }
        return iter.buf[iter.head++];
    }

    public static Any readAny(JsonIterator iter) throws IOException {
        // TODO: avoid small memory allocation
        iter.skipStartedAt = iter.head;
        byte c = nextToken(iter);
        switch (c) {
            case '"':
                skipString(iter);
                byte[] copied = copySkippedBytes(iter);
                return Any.lazyString(copied, 0, copied.length);
            case 't':
                skipFixedBytes(iter, 3);
                iter.skipStartedAt = -1;
                return Any.wrap(true);
            case 'f':
                skipFixedBytes(iter, 4);
                iter.skipStartedAt = -1;
                return Any.wrap(false);
            case 'n':
                skipFixedBytes(iter, 3);
                iter.skipStartedAt = -1;
                return Any.wrap((Object) null);
            case '[':
                skipArray(iter);
                copied = copySkippedBytes(iter);
                return Any.lazyArray(copied, 0, copied.length);
            case '{':
                skipObject(iter);
                copied = copySkippedBytes(iter);
                return Any.lazyObject(copied, 0, copied.length);
            default:
                if (skipNumber(iter)) {
                    copied = copySkippedBytes(iter);
                    return Any.lazyDouble(copied, 0, copied.length);
                } else {
                    copied = copySkippedBytes(iter);
                    return Any.lazyLong(copied, 0, copied.length);
                }
        }
    }

    private static byte[] copySkippedBytes(JsonIterator iter) {
        int start = iter.skipStartedAt;
        iter.skipStartedAt = -1;
        int end = iter.head;
        byte[] bytes = new byte[end - start];
        System.arraycopy(iter.buf, start, bytes, 0, bytes.length);
        return bytes;
    }

    public static void skipFixedBytes(JsonIterator iter, int n) throws IOException {
        iter.head += n;
        if (iter.head >= iter.tail) {
            int more = iter.head - iter.tail;
            if (!loadMore(iter)) {
                throw iter.reportError("skipFixedBytes", "unexpected end");
            }
            iter.head += more;
        }
    }

    public final static String readStringSlowPath(JsonIterator iter, int j) throws IOException {
        for (;;) {
            int bc = readByte(iter);
            if (bc == '"') {
                return new String(iter.reusableChars, 0, j);
            }
            if (bc == '\\') {
                bc = readByte(iter);
                switch (bc) {
                    case 'b':
                        bc = '\b';
                        break;
                    case 't':
                        bc = '\t';
                        break;
                    case 'n':
                        bc = '\n';
                        break;
                    case 'f':
                        bc = '\f';
                        break;
                    case 'r':
                        bc = '\r';
                        break;
                    case '"':
                    case '/':
                    case '\\':
                        break;
                    case 'u':
                        bc = (IterImplString.translateHex(readByte(iter)) << 12) +
                                (IterImplString.translateHex(readByte(iter)) << 8) +
                                (IterImplString.translateHex(readByte(iter)) << 4) +
                                IterImplString.translateHex(readByte(iter));
                        break;

                    default:
                        throw iter.reportError("readStringSlowPath", "invalid escape character: " + bc);
                }
            } else if ((bc & 0x80) != 0) {
                final int u2 = readByte(iter);
                if ((bc & 0xE0) == 0xC0) {
                    bc = ((bc & 0x1F) << 6) + (u2 & 0x3F);
                } else {
                    final int u3 = readByte(iter);
                    if ((bc & 0xF0) == 0xE0) {
                        bc = ((bc & 0x0F) << 12) + ((u2 & 0x3F) << 6) + (u3 & 0x3F);
                    } else {
                        final int u4 = readByte(iter);
                        if ((bc & 0xF8) == 0xF0) {
                            bc = ((bc & 0x07) << 18) + ((u2 & 0x3F) << 12) + ((u3 & 0x3F) << 6) + (u4 & 0x3F);
                        } else {
                            throw iter.reportError("readStringSlowPath", "invalid unicode character");
                        }

                        if (bc >= 0x10000) {
                            // check if valid unicode
                            if (bc >= 0x110000)
                                throw iter.reportError("readStringSlowPath", "invalid unicode character");

                            // split surrogates
                            final int sup = bc - 0x10000;
                            iter.reusableChars[j++] = (char) ((sup >>> 10) + 0xd800);
                            iter.reusableChars[j++] = (char) ((sup & 0x3ff) + 0xdc00);
                            continue;
                        }
                    }
                }
            }
            iter.reusableChars[j++] = (char) bc;
        }
    }

}
