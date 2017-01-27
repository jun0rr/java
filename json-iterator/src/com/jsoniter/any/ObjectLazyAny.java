package com.jsoniter.any;

import com.jsoniter.*;
import com.jsoniter.output.JsonStream;
import com.jsoniter.spi.JsonException;
import com.jsoniter.spi.TypeLiteral;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

class ObjectLazyAny extends LazyAny {

    private final static TypeLiteral<Map<String, Any>> typeLiteral = new TypeLiteral<Map<String, Any>>(){};
    private Map<Object, Any> cache;
    private int lastParsedPos;

    public ObjectLazyAny(byte[] data, int head, int tail) {
        super(data, head, tail);
        lastParsedPos = head;
    }

    @Override
    public ValueType valueType() {
        return ValueType.OBJECT;
    }

    @Override
    public Object object() {
        fillCache();
        return cache;
    }

    @Override
    public boolean toBoolean() {
        try {
            return CodegenAccess.readObjectStart(parse());
        } catch (IOException e) {
            throw new JsonException(e);
        }
    }

    @Override
    public int size() {
        fillCache();
        return cache.size();
    }

    @Override
    public Set<String> keys() {
        fillCache();
        return (Set) cache.keySet();
    }

    @Override
    public Any get(Object key) {
        Any element = fillCache(key);
        if (element == null) {
            return new NotFoundAny(key, object());
        }
        return element;
    }

    @Override
    public Any get(Object[] keys, int idx) {
        if (idx == keys.length) {
            return this;
        }
        Object key = keys[idx];
        if (isWildcard(key)) {
            fillCache();
            HashMap<String, Any> result = new HashMap<String, Any>();
            for (Map.Entry<Object, Any> entry : cache.entrySet()) {
                Any mapped = entry.getValue().get(keys, idx + 1);
                if (mapped.valueType() != ValueType.INVALID) {
                    result.put((String) entry.getKey(), mapped);
                }
            }
            return Any.wrapAnyMap(result);
        }
        Any child = fillCache(key);
        if (child == null) {
            return new NotFoundAny(keys, idx, object());
        }
        return child.get(keys, idx+1);
    }

    private Any fillCache(Object target) {
        if (lastParsedPos == tail) {
            return cache.get(target);
        }
        if (cache == null) {
            cache = new HashMap<Object, Any>(4);
        }
        Any value = cache.get(target);
        if (value != null) {
            return value;
        }
        try {
            JsonIterator iter = JsonIterator.tlsIter.get();
            iter.reset(data, lastParsedPos, tail);
            if (lastParsedPos == head) {
                if (!CodegenAccess.readObjectStart(iter)) {
                    lastParsedPos = tail;
                    return null;
                }
                String field = CodegenAccess.readObjectFieldAsString(iter);
                value = iter.readAny();
                cache.put(field, value);
                if (field.hashCode() == target.hashCode() && field.equals(target)) {
                    lastParsedPos = CodegenAccess.head(iter);
                    return value;
                }
            }
            while (CodegenAccess.nextToken(iter) == ',') {
                String field = CodegenAccess.readObjectFieldAsString(iter);
                value = iter.readAny();
                cache.put(field, value);
                if (field.hashCode() == target.hashCode() && field.equals(target)) {
                    lastParsedPos = CodegenAccess.head(iter);
                    return value;
                }
            }
            lastParsedPos = tail;
            return null;
        } catch (IOException e) {
            throw new JsonException(e);
        }
    }

    private void fillCache() {
        if (lastParsedPos == tail) {
            return;
        }
        if (cache == null) {
            cache = new HashMap<Object, Any>(4);
        }
        try {
            JsonIterator iter = JsonIterator.tlsIter.get();
            iter.reset(data, lastParsedPos, tail);
            if (lastParsedPos == head) {
                if (!CodegenAccess.readObjectStart(iter)) {
                    lastParsedPos = tail;
                    return;
                }
                String field = CodegenAccess.readObjectFieldAsString(iter);
                cache.put(field, iter.readAny());
            }
            while (CodegenAccess.nextToken(iter) == ',') {
                String field = CodegenAccess.readObjectFieldAsString(iter);
                cache.put(field, iter.readAny());
            }
            lastParsedPos = tail;
        } catch (IOException e) {
            throw new JsonException(e);
        }
    }

    @Override
    public EntryIterator entries() {
        return new LazyIterator();
    }


    // TODO: lastParsedPos can not share with underlying Any, as it might be changed during iteration
    private class LazyIterator implements EntryIterator {

        private Iterator<Map.Entry<Object, Any>> mapIter;
        private String key;
        private Any value;

        public LazyIterator() {
            if (cache == null) {
                cache = new HashMap<Object, Any>();
            }
            mapIter = cache.entrySet().iterator();
            try {
                if (lastParsedPos == head) {
                    JsonIterator iter = JsonIterator.tlsIter.get();
                    iter.reset(data, lastParsedPos, tail);
                    if (!CodegenAccess.readObjectStart(iter)) {
                        lastParsedPos = tail;
                    } else {
                        lastParsedPos = CodegenAccess.head(iter);
                    }
                }
            } catch (IOException e) {
                throw new JsonException(e);
            }
        }

        @Override
        public boolean next() {
            if (lastParsedPos == tail) {
                return false;
            }
            if (mapIter != null) {
                if (mapIter.hasNext()) {
                    Map.Entry<Object, Any> entry = mapIter.next();
                    key = (String) entry.getKey();
                    value = entry.getValue();
                    return true;
                } else {
                    mapIter = null;
                }
            }
            try {
                JsonIterator iter = JsonIterator.tlsIter.get();
                iter.reset(data, lastParsedPos, tail);
                key = CodegenAccess.readObjectFieldAsString(iter);
                value = iter.readAny();
                cache.put(key, value);
                if (CodegenAccess.nextToken(iter) == ',') {
                    lastParsedPos = CodegenAccess.head(iter);
                } else {
                    lastParsedPos = tail;
                }
            } catch (IOException e) {
                throw new JsonException(e);
            }
            return true;
        }

        @Override
        public String key() {
            return key;
        }

        @Override
        public Any value() {
            return value;
        }
    }

    @Override
    public void writeTo(JsonStream stream) throws IOException {
        if (lastParsedPos == head) {
            super.writeTo(stream);
        } else {
            // there might be modification
            fillCache();
            stream.writeVal(typeLiteral, (Map) cache);
        }
    }
}
