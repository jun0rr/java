package net.bytebuddy.dynamic.scaffold;

import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.description.method.MethodList;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.ModifierResolver;
import net.bytebuddy.implementation.Implementation;
import net.bytebuddy.implementation.LoadedTypeInitializer;
import net.bytebuddy.implementation.attribute.MethodAttributeAppender;
import net.bytebuddy.implementation.bytecode.ByteCodeAppender;
import net.bytebuddy.matcher.ElementMatcher;
import net.bytebuddy.matcher.LatentMethodMatcher;

import java.util.*;

import static net.bytebuddy.description.method.MethodDescription.Latent.typeInitializerOf;
import static net.bytebuddy.matcher.ElementMatchers.*;
import static net.bytebuddy.utility.ByteBuddyCommons.join;

/**
 * A method registry is responsible for storing information on how a method is intercepted.
 */
public interface MethodRegistry {

    /**
     * Prepends the given method definition to this method registry, i.e. this configuration is applied first.
     *
     * @param methodMatcher            A matcher to identify all entries that are to be matched.
     * @param handler                  The handler to instrument any matched method.
     * @param attributeAppenderFactory A method attribute appender to apply to any matched method.
     * @return A mutated version of this method registry.
     */
    MethodRegistry prepend(LatentMethodMatcher methodMatcher,
                           Handler handler,
                           MethodAttributeAppender.Factory attributeAppenderFactory);

    /**
     * Appends the given method definition to this method registry, i.e. this configuration is applied last.
     *
     * @param methodMatcher            A matcher to identify all entries that are to be matched.
     * @param handler                  The handler to instrument any matched method.
     * @param attributeAppenderFactory A method attribute appender to apply to any matched method.
     * @return A mutated version of this method registry.
     */
    MethodRegistry append(LatentMethodMatcher methodMatcher,
                          Handler handler,
                          MethodAttributeAppender.Factory attributeAppenderFactory);

    /**
     * Prepares this method registry.
     *
     * @param instrumentedType   The instrumented type that should be created.
     * @param methodLookupEngine A method lookup engine to analyze the fully prepared type.
     * @param methodFilter       A filter that only matches methods that should be instrumented.
     * @return A prepared version of this method registry.
     */
    Prepared prepare(InstrumentedType instrumentedType,
                     MethodLookupEngine methodLookupEngine,
                     LatentMethodMatcher methodFilter);

    /**
     * A handler for implementing a method.
     */
    interface Handler {

        /**
         * Prepares the instrumented type for this handler.
         *
         * @param instrumentedType The instrumented type to prepare.
         * @return The prepared instrumented type.
         */
        InstrumentedType prepare(InstrumentedType instrumentedType);

        /**
         * Compiles this handler.
         *
         * @param implementationTarget The implementation target to compile this handler for.
         * @return A compiled handler.
         */
        Handler.Compiled compile(Implementation.Target implementationTarget);

        /**
         * A handler for defining an abstract or native method.
         */
        class ForAbstractMethod implements Handler, Compiled {

            /**
             * The transformer to apply to the modifier of this method.
             */
            private final ModifierResolver modifierResolver;

            /**
             * Creates a new handler for defining an abstract method.
             *
             * @param modifierResolver The transformer to apply to the modifier of this method.
             */
            public ForAbstractMethod(ModifierResolver modifierResolver) {
                this.modifierResolver = modifierResolver;
            }

            @Override
            public InstrumentedType prepare(InstrumentedType instrumentedType) {
                return instrumentedType;
            }

            @Override
            public Compiled compile(Implementation.Target implementationTarget) {
                return this;
            }

            @Override
            public TypeWriter.MethodPool.Entry assemble(MethodAttributeAppender attributeAppender) {
                return new TypeWriter.MethodPool.Entry.ForAbstractMethod(attributeAppender, modifierResolver);
            }

            @Override
            public boolean equals(Object other) {
                return this == other || !(other == null || getClass() != other.getClass())
                        && modifierResolver.equals(((ForAbstractMethod) other).modifierResolver);
            }

            @Override
            public int hashCode() {
                return modifierResolver.hashCode();
            }

            @Override
            public String toString() {
                return "MethodRegistry.Handler.ForAbstractMethod{" +
                        "modifierResolver=" + modifierResolver +
                        '}';
            }
        }

        /**
         * A compiled handler for implementing a method.
         */
        interface Compiled {

            /**
             * Assembles this compiled entry with a method attribute appender.
             *
             * @param attributeAppender The method attribute appender to apply together with this handler.
             * @return A method pool entry representing this handler and the given attribute appender.
             */
            TypeWriter.MethodPool.Entry assemble(MethodAttributeAppender attributeAppender);
        }

        /**
         * A handler for a method that is implemented as byte code.
         */
        class ForImplementation implements Handler {

            /**
             * The implementation to apply.
             */
            private final Implementation implementation;

            /**
             * The transformer to apply to the modifier of this method.
             */
            private final ModifierResolver modifierResolver;

            /**
             * Creates a new handler for implementing a method with byte code.
             *
             * @param implementation   The implementation to apply.
             * @param modifierResolver The transformer to apply to the modifier of this method.
             */
            public ForImplementation(Implementation implementation, ModifierResolver modifierResolver) {
                this.implementation = implementation;
                this.modifierResolver = modifierResolver;
            }

            @Override
            public InstrumentedType prepare(InstrumentedType instrumentedType) {
                return implementation.prepare(instrumentedType);
            }

            @Override
            public Compiled compile(Implementation.Target implementationTarget) {
                return new Compiled(implementation.appender(implementationTarget), modifierResolver);
            }

            @Override
            public boolean equals(Object other) {
                return this == other || !(other == null || getClass() != other.getClass())
                        && implementation.equals(((ForImplementation) other).implementation)
                        && modifierResolver.equals(((ForImplementation) other).modifierResolver);
            }

            @Override
            public int hashCode() {
                return implementation.hashCode() + 31 * modifierResolver.hashCode();
            }

            @Override
            public String toString() {
                return "MethodRegistry.Handler.ForImplementation{" +
                        "implementation=" + implementation +
                        ", modifierResolver=" + modifierResolver +
                        '}';
            }

            /**
             * A compiled handler for implementing a method.
             */
            protected static class Compiled implements Handler.Compiled {

                /**
                 * The byte code appender to apply.
                 */
                private final ByteCodeAppender byteCodeAppender;

                /**
                 * The transformer to apply to the modifier of this method.
                 */
                private final ModifierResolver modifierResolver;

                /**
                 * Creates a new compiled handler for a method implementation.
                 *
                 * @param byteCodeAppender The byte code appender to apply.
                 * @param modifierResolver The transformer to apply to the modifier of this method.
                 */
                protected Compiled(ByteCodeAppender byteCodeAppender, ModifierResolver modifierResolver) {
                    this.byteCodeAppender = byteCodeAppender;
                    this.modifierResolver = modifierResolver;
                }

                @Override
                public TypeWriter.MethodPool.Entry assemble(MethodAttributeAppender attributeAppender) {
                    return new TypeWriter.MethodPool.Entry.ForImplementation(byteCodeAppender, attributeAppender, modifierResolver);
                }

                @Override
                public boolean equals(Object other) {
                    return this == other || !(other == null || getClass() != other.getClass())
                            && byteCodeAppender.equals(((Compiled) other).byteCodeAppender)
                            && modifierResolver.equals(((Compiled) other).modifierResolver);
                }

                @Override
                public int hashCode() {
                    return byteCodeAppender.hashCode() + 31 * modifierResolver.hashCode();
                }

                @Override
                public String toString() {
                    return "MethodRegistry.Handler.ForImplementation.Compiled{" +
                            "byteCodeAppender=" + byteCodeAppender +
                            ", modifierResolver=" + modifierResolver +
                            '}';
                }
            }
        }

        /**
         * A handler for defining a default annotation value for a method.
         */
        class ForAnnotationValue implements Handler, Compiled {

            /**
             * Represents the given value as an annotation default value handler after validating its suitability.
             *
             * @param annotationValue  The annotation value to represent.
             * @param modifierResolver The transformer to apply to the modifier of this method.
             * @return A handler for setting the given value as a default value for instrumented methods.
             */
            public static Handler of(Object annotationValue, ModifierResolver modifierResolver) {
                TypeDescription typeDescription = new TypeDescription.ForLoadedType(annotationValue.getClass());
                if (!typeDescription.isAnnotationValue() && !typeDescription.isPrimitiveWrapper()) {
                    throw new IllegalArgumentException("Does not describe an annotation value: " + annotationValue);
                }
                return new ForAnnotationValue(annotationValue, modifierResolver);
            }

            /**
             * The annotation value to set as a default value.
             */
            private final Object annotationValue;

            /**
             * The transformer to apply to the modifier of this method.
             */
            private final ModifierResolver modifierResolver;

            /**
             * Creates a handler for defining a default annotation value for a method.
             *
             * @param annotationValue  The annotation value to set as a default value.
             * @param modifierResolver The transformer to apply to the modifier of this method.
             */
            protected ForAnnotationValue(Object annotationValue, ModifierResolver modifierResolver) {
                this.annotationValue = annotationValue;
                this.modifierResolver = modifierResolver;
            }

            @Override
            public InstrumentedType prepare(InstrumentedType instrumentedType) {
                return instrumentedType;
            }

            @Override
            public Compiled compile(Implementation.Target implementationTarget) {
                return this;
            }

            @Override
            public TypeWriter.MethodPool.Entry assemble(MethodAttributeAppender attributeAppender) {
                return new TypeWriter.MethodPool.Entry.ForAnnotationDefaultValue(annotationValue, attributeAppender, modifierResolver);
            }

            @Override
            public boolean equals(Object other) {
                return this == other || !(other == null || getClass() != other.getClass())
                        && annotationValue.equals(((ForAnnotationValue) other).annotationValue)
                        && modifierResolver.equals(((ForAnnotationValue) other).modifierResolver);
            }

            @Override
            public int hashCode() {
                return annotationValue.hashCode() + 31 * modifierResolver.hashCode();
            }

            @Override
            public String toString() {
                return "MethodRegistry.Handler.ForAnnotationValue{" +
                        "annotationValue=" + annotationValue +
                        ", modifierResolver=" + modifierResolver +
                        '}';
            }
        }
    }

    /**
     * A method registry that fully prepared the instrumented type.
     */
    interface Prepared {

        /**
         * Returns the fully prepared instrumented type.
         *
         * @return The fully prepared instrumented type.
         */
        TypeDescription getInstrumentedType();

        /**
         * Returns a list of all methods that should be instrumented.
         *
         * @return A list of all methods that should be instrumented.
         */
        MethodList getInstrumentedMethods();

        /**
         * Returns the loaded type initializer of the instrumented type.
         *
         * @return The loaded type initializer of the instrumented type.
         */
        LoadedTypeInitializer getLoadedTypeInitializer();

        /**
         * The type initializer of the instrumented type.
         *
         * @return The type initializer of the instrumented type.
         */
        InstrumentedType.TypeInitializer getTypeInitializer();

        /**
         * Compiles this prepared method registry.
         *
         * @param implementationTargetFactory A factory for creating an implementation target.
         * @return A factory for creating an implementation target.
         */
        Compiled compile(Implementation.Target.Factory implementationTargetFactory);
    }

    /**
     * A compiled version of a method registry.
     */
    interface Compiled extends TypeWriter.MethodPool {

        /**
         * Returns the instrumented type that is to be created.
         *
         * @return The instrumented type that is to be created.
         */
        TypeDescription getInstrumentedType();

        /**
         * Returns a list of all methods that should be instrumented.
         *
         * @return A list of all methods that should be instrumented.
         */
        MethodList getInstrumentedMethods();

        /**
         * Returns the loaded type initializer of the instrumented type.
         *
         * @return The loaded type initializer of the instrumented type.
         */
        LoadedTypeInitializer getLoadedTypeInitializer();

        /**
         * The type initializer of the instrumented type.
         *
         * @return The type initializer of the instrumented type.
         */
        InstrumentedType.TypeInitializer getTypeInitializer();
    }

    /**
     * A default implementation of a method registry.
     */
    class Default implements MethodRegistry {

        /**
         * The list of currently registered entries in their application order.
         */
        private final List<Entry> entries;

        /**
         * Creates a new default method registry without entries.
         */
        public Default() {
            entries = Collections.emptyList();
        }

        /**
         * Creates a new default method registry.
         *
         * @param entries The currently registered entries.
         */
        private Default(List<Entry> entries) {
            this.entries = entries;
        }

        @Override
        public MethodRegistry prepend(LatentMethodMatcher methodMatcher,
                                      Handler handler,
                                      MethodAttributeAppender.Factory attributeAppenderFactory) {
            return new Default(join(new Entry(methodMatcher, handler, attributeAppenderFactory), entries));
        }

        @Override
        public MethodRegistry append(LatentMethodMatcher methodMatcher,
                                     Handler handler,
                                     MethodAttributeAppender.Factory attributeAppenderFactory) {
            return new Default(join(entries, new Entry(methodMatcher, handler, attributeAppenderFactory)));
        }

        @Override
        public MethodRegistry.Prepared prepare(InstrumentedType instrumentedType,
                                               MethodLookupEngine methodLookupEngine,
                                               LatentMethodMatcher methodFilter) {
            Map<MethodDescription, Entry> implementations = new HashMap<MethodDescription, Entry>();
            Set<Handler> handlers = new HashSet<Handler>(entries.size());
            int helperMethodIndex = instrumentedType.getDeclaredMethods().size();
            for (Entry entry : entries) {
                if (handlers.add(entry.getHandler())) {
                    instrumentedType = entry.getHandler().prepare(instrumentedType);
                    MethodList helperMethods = instrumentedType.getDeclaredMethods();
                    for (MethodDescription helperMethod : helperMethods.subList(helperMethodIndex, helperMethods.size())) {
                        implementations.put(helperMethod, entry);
                    }
                    helperMethodIndex = helperMethods.size();
                }
            }
            MethodLookupEngine.Finding finding = methodLookupEngine.process(instrumentedType);
            ElementMatcher<? super MethodDescription> instrumented = (ElementMatcher<? super MethodDescription>) not(anyOf(implementations.keySet()))
                    .and(methodFilter.resolve(instrumentedType));
            List<MethodDescription> methodDescriptions = join(typeInitializerOf(instrumentedType), finding.getInvokableMethods().filter(instrumented));
            for (MethodDescription methodDescription : methodDescriptions) {
                for (Entry entry : entries) {
                    if (entry.resolve(instrumentedType).matches(methodDescription)) {
                        implementations.put(methodDescription, entry);
                        break;
                    }
                }
            }
            return new Prepared(implementations,
                    instrumentedType.getLoadedTypeInitializer(),
                    instrumentedType.getTypeInitializer(),
                    finding);
        }

        @Override
        public boolean equals(Object other) {
            return this == other || !(other == null || getClass() != other.getClass())
                    && entries.equals(((Default) other).entries);

        }

        @Override
        public int hashCode() {
            return entries.hashCode();
        }

        @Override
        public String toString() {
            return "MethodRegistry.Default{" +
                    "entries=" + entries +
                    '}';
        }

        /**
         * An entry of a default method registry.
         */
        protected static class Entry implements LatentMethodMatcher {

            /**
             * The latent method matcher that this entry represents.
             */
            private final LatentMethodMatcher methodMatcher;

            /**
             * The handler to apply to all matched entries.
             */
            private final Handler handler;

            /**
             * A method attribute appender factory to apply to all entries.
             */
            private final MethodAttributeAppender.Factory attributeAppenderFactory;

            /**
             * Creates a new entry.
             *
             * @param methodMatcher            The latent method matcher that this entry represents.
             * @param handler                  The handler to apply to all matched entries.
             * @param attributeAppenderFactory A method attribute appender factory to apply to all entries.
             */
            protected Entry(LatentMethodMatcher methodMatcher,
                            Handler handler,
                            MethodAttributeAppender.Factory attributeAppenderFactory) {
                this.methodMatcher = methodMatcher;
                this.handler = handler;
                this.attributeAppenderFactory = attributeAppenderFactory;
            }

            /**
             * Returns the handler of this entry.
             *
             * @return The handler of this entry.
             */
            protected Handler getHandler() {
                return handler;
            }

            /**
             * Returns the attribute appender factory of this entry.
             *
             * @return The attribute appender factory of this entry.
             */
            protected MethodAttributeAppender.Factory getAppenderFactory() {
                return attributeAppenderFactory;
            }

            @Override
            public ElementMatcher<? super MethodDescription> resolve(TypeDescription instrumentedType) {
                return methodMatcher.resolve(instrumentedType);
            }

            @Override
            public boolean equals(Object other) {
                if (this == other) return true;
                if (other == null || getClass() != other.getClass()) return false;
                Entry entry = (Entry) other;
                return methodMatcher.equals(entry.methodMatcher)
                        && handler.equals(entry.handler)
                        && attributeAppenderFactory.equals(entry.attributeAppenderFactory);
            }

            @Override
            public int hashCode() {
                int result = methodMatcher.hashCode();
                result = 31 * result + handler.hashCode();
                result = 31 * result + attributeAppenderFactory.hashCode();
                return result;
            }

            @Override
            public String toString() {
                return "MethodRegistry.Default.Entry{" +
                        "methodMatcher=" + methodMatcher +
                        ", handler=" + handler +
                        ", attributeAppenderFactory=" + attributeAppenderFactory +
                        '}';
            }
        }

        /**
         * A prepared version of a default method registry.
         */
        protected static class Prepared implements MethodRegistry.Prepared {

            /**
             * A map of all method descriptions mapped to their handling entries.
             */
            private final Map<MethodDescription, Entry> implementations;

            /**
             * The loaded type initializer of the instrumented type.
             */
            private final LoadedTypeInitializer loadedTypeInitializer;

            /**
             * The type initializer of the instrumented type.
             */
            private final InstrumentedType.TypeInitializer typeInitializer;

            /**
             * The analyzed instrumented type.
             */
            private final MethodLookupEngine.Finding finding;

            /**
             * Creates a prepared version of a default method registry.
             *
             * @param implementations       A map of all method descriptions mapped to their handling entries.
             * @param loadedTypeInitializer The loaded type initializer of the instrumented type.
             * @param typeInitializer       The type initializer of the instrumented type.
             * @param finding               The analyzed instrumented type.
             */
            public Prepared(Map<MethodDescription, Entry> implementations,
                            LoadedTypeInitializer loadedTypeInitializer,
                            InstrumentedType.TypeInitializer typeInitializer,
                            MethodLookupEngine.Finding finding) {
                this.implementations = implementations;
                this.loadedTypeInitializer = loadedTypeInitializer;
                this.typeInitializer = typeInitializer;
                this.finding = finding;
            }

            @Override
            public TypeDescription getInstrumentedType() {
                return finding.getTypeDescription();
            }

            @Override
            public LoadedTypeInitializer getLoadedTypeInitializer() {
                return loadedTypeInitializer;
            }

            @Override
            public InstrumentedType.TypeInitializer getTypeInitializer() {
                return typeInitializer;
            }

            @Override
            public MethodList getInstrumentedMethods() {
                return new MethodList.Explicit(new ArrayList<MethodDescription>(implementations.keySet())).filter(not(isTypeInitializer()));
            }

            @Override
            public MethodRegistry.Compiled compile(Implementation.Target.Factory implementationTargetFactory) {
                Map<Handler, Handler.Compiled> compilationCache = new HashMap<Handler, Handler.Compiled>(implementations.size());
                Map<MethodAttributeAppender.Factory, MethodAttributeAppender> attributeAppenderCache = new HashMap<MethodAttributeAppender.Factory, MethodAttributeAppender>(implementations.size());
                Map<MethodDescription, TypeWriter.MethodPool.Entry> entries = new HashMap<MethodDescription, TypeWriter.MethodPool.Entry>(implementations.size());
                Implementation.Target implementationTarget = implementationTargetFactory.make(finding, getInstrumentedMethods());
                for (Map.Entry<MethodDescription, Entry> entry : implementations.entrySet()) {
                    Handler.Compiled cachedEntry = compilationCache.get(entry.getValue().getHandler());
                    if (cachedEntry == null) {
                        cachedEntry = entry.getValue().getHandler().compile(implementationTarget);
                        compilationCache.put(entry.getValue().getHandler(), cachedEntry);
                    }
                    MethodAttributeAppender cachedAttributeAppender = attributeAppenderCache.get(entry.getValue().getAppenderFactory());
                    if (cachedAttributeAppender == null) {
                        cachedAttributeAppender = entry.getValue().getAppenderFactory().make(finding.getTypeDescription());
                        attributeAppenderCache.put(entry.getValue().getAppenderFactory(), cachedAttributeAppender);
                    }
                    entries.put(entry.getKey(), cachedEntry.assemble(cachedAttributeAppender));
                }
                return new Compiled(finding.getTypeDescription(), loadedTypeInitializer, typeInitializer, entries);
            }

            @Override
            public boolean equals(Object other) {
                if (this == other) return true;
                if (other == null || getClass() != other.getClass()) return false;
                Prepared prepared = (Prepared) other;
                return implementations.equals(prepared.implementations)
                        && loadedTypeInitializer.equals(prepared.loadedTypeInitializer)
                        && typeInitializer.equals(prepared.typeInitializer)
                        && finding.equals(prepared.finding);
            }

            @Override
            public int hashCode() {
                int result = implementations.hashCode();
                result = 31 * result + loadedTypeInitializer.hashCode();
                result = 31 * result + typeInitializer.hashCode();
                result = 31 * result + finding.hashCode();
                return result;
            }

            @Override
            public String toString() {
                return "MethodRegistry.Default.Prepared{" +
                        "implementations=" + implementations +
                        ", loadedTypeInitializer=" + loadedTypeInitializer +
                        ", typeInitializer=" + typeInitializer +
                        ", finding=" + finding +
                        '}';
            }
        }

        /**
         * A compiled version of a default method registry.
         */
        protected static class Compiled implements MethodRegistry.Compiled {

            /**
             * The instrumented type.
             */
            private final TypeDescription instrumentedType;

            /**
             * The loaded type initializer of the instrumented type.
             */
            private final LoadedTypeInitializer loadedTypeInitializer;

            /**
             * The type initializer of the instrumented type.
             */
            private final InstrumentedType.TypeInitializer typeInitializer;

            /**
             * A map of all method descriptions mapped to their handling entries.
             */
            private final Map<MethodDescription, Entry> implementations;

            /**
             * Creates a new compiled version of a default method registry.
             *
             * @param instrumentedType      The instrumented type.
             * @param loadedTypeInitializer The loaded type initializer of the instrumented type.
             * @param typeInitializer       The type initializer of the instrumented type.
             * @param implementations       A map of all method descriptions mapped to their handling entries.
             */
            public Compiled(TypeDescription instrumentedType,
                            LoadedTypeInitializer loadedTypeInitializer,
                            InstrumentedType.TypeInitializer typeInitializer,
                            Map<MethodDescription, Entry> implementations) {
                this.instrumentedType = instrumentedType;
                this.loadedTypeInitializer = loadedTypeInitializer;
                this.typeInitializer = typeInitializer;
                this.implementations = implementations;
            }

            @Override
            public TypeDescription getInstrumentedType() {
                return instrumentedType;
            }

            @Override
            public LoadedTypeInitializer getLoadedTypeInitializer() {
                return loadedTypeInitializer;
            }

            @Override
            public InstrumentedType.TypeInitializer getTypeInitializer() {
                return typeInitializer;
            }

            @Override
            public MethodList getInstrumentedMethods() {
                return new MethodList.Explicit(new ArrayList<MethodDescription>(implementations.keySet())).filter(not(isTypeInitializer()));
            }

            @Override
            public Entry target(MethodDescription methodDescription) {
                Entry entry = implementations.get(methodDescription);
                return entry == null
                        ? Entry.ForSkippedMethod.INSTANCE
                        : entry;
            }

            @Override
            public boolean equals(Object other) {
                if (this == other) return true;
                if (other == null || getClass() != other.getClass()) return false;
                Compiled compiled = (Compiled) other;
                return instrumentedType.equals(compiled.instrumentedType)
                        && loadedTypeInitializer.equals(compiled.loadedTypeInitializer)
                        && typeInitializer.equals(compiled.typeInitializer)
                        && implementations.equals(compiled.implementations);
            }

            @Override
            public int hashCode() {
                int result = instrumentedType.hashCode();
                result = 31 * result + loadedTypeInitializer.hashCode();
                result = 31 * result + typeInitializer.hashCode();
                result = 31 * result + implementations.hashCode();
                return result;
            }

            @Override
            public String toString() {
                return "MethodRegistry.Default.Compiled{" +
                        "instrumentedType=" + instrumentedType +
                        ", loadedTypeInitializer=" + loadedTypeInitializer +
                        ", typeInitializer=" + typeInitializer +
                        ", implementations=" + implementations +
                        '}';
            }
        }
    }
}
