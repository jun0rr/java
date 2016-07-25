package net.bytebuddy.implementation;

import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.description.method.MethodList;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.scaffold.InstrumentedType;
import net.bytebuddy.dynamic.scaffold.MethodLookupEngine;
import net.bytebuddy.implementation.bind.*;
import net.bytebuddy.implementation.bind.annotation.*;
import net.bytebuddy.implementation.bytecode.ByteCodeAppender;
import net.bytebuddy.implementation.bytecode.Duplication;
import net.bytebuddy.implementation.bytecode.StackManipulation;
import net.bytebuddy.implementation.bytecode.TypeCreation;
import net.bytebuddy.implementation.bytecode.assign.Assigner;
import net.bytebuddy.implementation.bytecode.member.FieldAccess;
import net.bytebuddy.implementation.bytecode.member.MethodVariableAccess;
import net.bytebuddy.matcher.ElementMatcher;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.util.Arrays;
import java.util.List;

import static net.bytebuddy.matcher.ElementMatchers.*;
import static net.bytebuddy.utility.ByteBuddyCommons.*;

/**
 * This implementation delegates an method call to another method which can either be {@code static} by providing
 * a reference to a {@link java.lang.Class} or an instance method when another object is provided. The potential
 * targets of the method delegation can further be filtered by applying a filter. The method delegation can be
 * customized by invoking the {@code MethodDelegation}'s several builder methods.
 * <h3>Without any customization, the method delegation will work as follows:</h3>
 * <span style="text-decoration: underline">Binding an instrumented method to a given delegate method</span>
 * <p>&nbsp;</p>
 * A method will be bound parameter by parameter. Considering a method {@code Foo#bar} being bound to a method
 * {@code Qux#baz}, the method delegation will be decided on basis of the following annotations:
 * <ul>
 * <li>{@link net.bytebuddy.implementation.bind.annotation.Argument}:
 * This annotation will bind the {@code n}-th parameter of {@code Foo#bar} to that parameter of {@code Qux#baz}that
 * is annotated with this annotation where {@code n} is the obligatory argument of the {@code @Argument} annotation.</li>
 * <li>{@link net.bytebuddy.implementation.bind.annotation.AllArguments}:
 * This annotation will assign a collection of all parameters of {@code Foo#bar} to that parameter of {@code Qux#baz}
 * that is annotated with {@code AllArguments}.</li>
 * <li>{@link net.bytebuddy.implementation.bind.annotation.This}: A parameter
 * of {@code Qux#baz} that is annotated with {@code This} will be assigned the instance that is instrumented for
 * a non-static method.</li>
 * <li>{@link net.bytebuddy.implementation.bind.annotation.Super}: A parameter that is annotated with
 * this annotation is assigned a proxy that allows calling an instrumented type's super methods.</li>
 * <li>{@link net.bytebuddy.implementation.bind.annotation.Default}: A parameter that is annotated with
 * this annotation is assigned a proxy that allows calling an instrumented type's directly implemented interfaces'
 * default methods.</li>
 * <li>{@link net.bytebuddy.implementation.bind.annotation.SuperCall}: A parameter
 * of {@code Qux#baz} that is annotated with {@code SuperCall} will be assigned an instance of a type implementing both
 * {@link java.lang.Runnable} and {@link java.util.concurrent.Callable} which will invoke the instrumented method on the
 * invocation of either interface's method. The call is made using the original arguments of the method invocation.
 * The return value is only emitted for the {@link java.util.concurrent.Callable#call()} method which additionally
 * requires to catch any unchecked exceptions that might be thrown by the original method's implementation. If a
 * source method is abstract, using this annotation excludes the method with this parameter annotation from being bound
 * to this source method.
 * </li>
 * <li>{@link net.bytebuddy.implementation.bind.annotation.DefaultCall}:
 * This annotation is similar to the {@link net.bytebuddy.implementation.bind.annotation.SuperCall}
 * annotation but it invokes a default method that is compatible to this method. If a source method does not represent
 * a default method, using this annotation excludes the method with this parameter annotation from being bound to this
 * source method.</li>
 * <li>{@link net.bytebuddy.implementation.bind.annotation.Origin}: A parameter of
 * {@code Qux#baz} that is annotated with {@code Origin} is assigned a reference to either a {@link java.lang.reflect.Method}
 * or a {@link java.lang.Class} instance. A {@code Method}-typed parameter is assigned a reference to the original method that
 * is overriden. A {@code Class}-typed parameter is assigned the type of the caller.</li>
 * <li>{@link net.bytebuddy.implementation.bind.annotation.StubValue}: Assigns the (boxed) default value of the
 * intercepted method's return type to the parameter. If the return type is {@code void}, {@code null} is assigned.</li>
 * <li>{@link net.bytebuddy.implementation.bind.annotation.Empty}: Assigns the parameter type's
 * default value, i.e. {@code null} for a reference type or zero for primitive types. This is an opportunity to
 * ignore a parameter.</li>
 * <li>{@link net.bytebuddy.implementation.bind.annotation.Pipe}: A parameter that is annotated
 * with this annotation is assigned a proxy for forwarding the source method invocation to another instance of the
 * same type as the declaring type of the intercepted method. <b>This annotation needs to be installed and explicitly
 * registered before it can be used.</b> See the {@link net.bytebuddy.implementation.bind.annotation.Pipe}
 * annotation's documentation for further information on how this can be done.</li>
 * <li>{@link net.bytebuddy.implementation.bind.annotation.Morph}: The morph annotation is similar to
 * the {@link net.bytebuddy.implementation.bind.annotation.SuperCall} annotation but allows to
 * explicitly define and therewith alter the arguments that are handed to the super method. <b>This annotation needs
 * to be installed and explicitly registered before it can be used.</b> See the documentation to the annotation for
 * further information.</li>
 * <li>{@link net.bytebuddy.implementation.bind.annotation.FieldValue}: Allows to access a field's value at the time
 * of the method invocation. The field's value is directly assigned to the annotated parameter.</li>
 * <li>{@link net.bytebuddy.implementation.bind.annotation.FieldProxy}: Allows to access fields via getter
 * and setter proxies. <b>This annotation needs to be installed and explicitly registered before it can be used.</b>
 * Note that any field access requires boxing such that a use of {@link net.bytebuddy.implementation.FieldAccessor} in
 * combination with {@link net.bytebuddy.implementation.MethodDelegation#andThen(Implementation)} might be a more
 * performant alternative for implementing field getters and setters.</li>
 * </ul>
 * If a method is not annotated with any of the above methods, it will be treated as if it was annotated
 * {@link net.bytebuddy.implementation.bind.annotation.Argument} using the next
 * unbound parameter index of the source method as its parameter. This means that a method
 * {@code Qux#baz(@Argument(2) Object p1, Object p2, @Argument(0) Object p3} would be treated as if {@code p2} was annotated
 * with {@code @Argument(1)}.
 * <p>&nbsp;</p>
 * In addition, the {@link net.bytebuddy.implementation.bind.annotation.RuntimeType}
 * annotation can instruct a parameter to be bound by a
 * {@link net.bytebuddy.implementation.bytecode.assign.Assigner} with considering the
 * runtime type of the parameter.
 * <p>&nbsp;</p>
 * <span style="text-decoration: underline">Selecting among different methods that can be used for binding a method
 * of the instrumented type</span>
 * <p>&nbsp;</p>
 * When deciding between two methods {@code Foo#bar} and {@code Foo#qux} that could both be used to delegating a
 * method call, the following consideration is applied in the given order:
 * <ol>
 * <li>{@link net.bytebuddy.implementation.bind.annotation.BindingPriority}:
 * A method that is annotated with this annotation is given a specific priority where the default priority is set
 * to {@link net.bytebuddy.implementation.bind.annotation.BindingPriority#DEFAULT}
 * for non-annotated method. A method with a higher priority is considered a better target for delegation.</li>
 * <li>{@link net.bytebuddy.implementation.bind.DeclaringTypeResolver}:
 * If a target method is declared by a more specific type than another method, the method with the most specific
 * type is bound.</li>
 * <li>{@link net.bytebuddy.implementation.bind.MethodNameEqualityResolver}:
 * If a source method {@code Baz#qux} is the source method, it will rather be assigned to {@code Foo#qux} because
 * of their equal names. Similar names and case-insensitive equality are not considered.</li>
 * <li>{@link net.bytebuddy.implementation.bind.ArgumentTypeResolver}:
 * The most specific type resolver will consider all bindings that are using the
 * {@link net.bytebuddy.implementation.bind.annotation.Argument}
 * annotation for resolving a binding conflict. In this context, the resolution will equal the most-specific
 * type resolution that is performed by the Java compiler. This means that a source method {@code Bar#baz(String)}
 * will rather be bound to a method {@code Foo#bar(String)} than {@code Foo#qux(Object)} because the {@code String}
 * type is more specific than the {@code Object} type. If two methods are equally adequate by their parameter types,
 * then the method with the higher numbers of {@code @Argument} annotated parameters is considered as the better
 * delegation target.</li>
 * <li>{@link net.bytebuddy.implementation.bind.ParameterLengthResolver}:
 * If a target methods has a higher number of total parameters that were successfully bound, the method with
 * the higher number will be considered as the better delegation target.</li>
 * </ol>
 * Additionally, if a method is annotated by
 * {@link net.bytebuddy.implementation.bind.annotation.IgnoreForBinding},
 * it is never considered as a target for a method delegation.
 */
public class MethodDelegation implements Implementation {

    /**
     * The error message for the exception to be thrown if no method for delegation can be identified.
     */
    private static final String NO_METHODS_ERROR_MESSAGE = "The target type does not define any methods for delegation";

    /**
     * The implementation delegate for this method delegation.
     */
    private final ImplementationDelegate implementationDelegate;

    /**
     * A list of {@link net.bytebuddy.implementation.bind.annotation.TargetMethodAnnotationDrivenBinder.ParameterBinder}s
     * to be used by this method delegation.
     */
    private final List<TargetMethodAnnotationDrivenBinder.ParameterBinder<?>> parameterBinders;

    /**
     * The {@link net.bytebuddy.implementation.bind.annotation.TargetMethodAnnotationDrivenBinder.DefaultsProvider}
     * to be used by this method delegation.
     */
    private final TargetMethodAnnotationDrivenBinder.DefaultsProvider defaultsProvider;

    /**
     * The termination handler to apply.
     */
    private final TargetMethodAnnotationDrivenBinder.TerminationHandler terminationHandler;

    /**
     * The {@link net.bytebuddy.implementation.bind.MethodDelegationBinder.AmbiguityResolver}
     * to be used by this method delegation.
     */
    private final MethodDelegationBinder.AmbiguityResolver ambiguityResolver;

    /**
     * The {@link net.bytebuddy.implementation.bytecode.assign.Assigner} to be used by this method delegation.
     */
    private final Assigner assigner;

    /**
     * A list of methods to be considered as target by this method delegation.
     */
    private final MethodList targetCandidates;

    /**
     * Creates a new method delegation.
     *
     * @param implementationDelegate The implementation delegate to use by this method delegator.
     * @param parameterBinders       The parameter binders to use by this method delegator.
     * @param defaultsProvider       The defaults provider to use by this method delegator.
     * @param terminationHandler     The termination handler to apply.
     * @param ambiguityResolver      The ambiguity resolver to use by this method delegator.
     * @param assigner               The assigner to be supplied by this method delegator.
     * @param targetCandidates A list of methods that should be considered as possible binding targets by
     *                               this method delegator.
     */
    protected MethodDelegation(ImplementationDelegate implementationDelegate,
                               List<TargetMethodAnnotationDrivenBinder.ParameterBinder<?>> parameterBinders,
                               TargetMethodAnnotationDrivenBinder.DefaultsProvider defaultsProvider,
                               TargetMethodAnnotationDrivenBinder.TerminationHandler terminationHandler,
                               MethodDelegationBinder.AmbiguityResolver ambiguityResolver,
                               Assigner assigner,
                               MethodList targetCandidates) {
        this.implementationDelegate = implementationDelegate;
        this.parameterBinders = parameterBinders;
        this.defaultsProvider = defaultsProvider;
        this.terminationHandler = terminationHandler;
        this.ambiguityResolver = ambiguityResolver;
        this.assigner = assigner;
        this.targetCandidates = isNotEmpty(targetCandidates, NO_METHODS_ERROR_MESSAGE);
    }

    /**
     * Creates an implementation where only {@code static} methods of the given type are considered as binding targets.
     *
     * @param type The type containing the {@code static} methods for binding.
     * @return A method delegation implementation to the given {@code static} methods.
     */
    public static MethodDelegation to(Class<?> type) {
        return to(new TypeDescription.ForLoadedType(nonNull(type)));
    }

    /**
     * Creates an implementation where only {@code static} methods of the given type are considered as binding targets.
     *
     * @param typeDescription The type containing the {@code static} methods for binding.
     * @return A method delegation implementation to the given {@code static} methods.
     */
    public static MethodDelegation to(TypeDescription typeDescription) {
        if (typeDescription.isArray()) {
            throw new IllegalArgumentException("Cannot delegate to array " + typeDescription);
        } else if (typeDescription.isPrimitive()) {
            throw new IllegalArgumentException("Cannot delegate to primitive " + typeDescription);
        }
        return new MethodDelegation(ImplementationDelegate.ForStaticMethod.INSTANCE,
                defaultParameterBinders(),
                defaultDefaultsProvider(),
                TargetMethodAnnotationDrivenBinder.TerminationHandler.Returning.INSTANCE,
                defaultAmbiguityResolver(),
                Assigner.DEFAULT,
                typeDescription.getDeclaredMethods().filter(isStatic().and(not(isPrivate()))));
    }

    /**
     * Creates an implementation where only instance methods of the given object are considered as binding targets.
     * This method will never bind to constructors but will consider methods that are defined in super types. Note
     * that this includes methods that were defined by the {@link java.lang.Object} class. You can narrow this default
     * selection by explicitly selecting methods with calling the
     * {@link net.bytebuddy.implementation.MethodDelegation#filter(net.bytebuddy.matcher.ElementMatcher)}
     * method on the returned method delegation as for example:
     * <pre>MethodDelegation.to(new Foo()).filter(MethodMatchers.not(isDeclaredBy(Object.class)));</pre>
     * which will result in a delegation to <code>Foo</code> where no methods of {@link java.lang.Object} are considered
     * for delegation.
     *
     * @param delegate A delegate instance which will be injected by a
     *                 {@link net.bytebuddy.implementation.LoadedTypeInitializer}. All intercepted method calls are
     *                 then delegated to this instance.
     * @return A method delegation implementation to the given instance methods.
     */
    public static MethodDelegation to(Object delegate) {
        return to(nonNull(delegate), MethodLookupEngine.Default.DEFAULT_LOOKUP_DISABLED);
    }

    /**
     * Identical to {@link net.bytebuddy.implementation.MethodDelegation#to(Object)} but uses an explicit
     * {@link MethodLookupEngine}.
     *
     * @param delegate           A delegate instance which will be injected by a
     *                           {@link net.bytebuddy.implementation.LoadedTypeInitializer}. All intercepted method
     *                           calls are then delegated to this instance.
     * @param methodLookupEngine The method lookup engine to use.
     * @return A method delegation implementation to the given instance methods.
     */
    public static MethodDelegation to(Object delegate, MethodLookupEngine methodLookupEngine) {
        return new MethodDelegation(new ImplementationDelegate.ForStaticField(nonNull(delegate)),
                defaultParameterBinders(),
                defaultDefaultsProvider(),
                TargetMethodAnnotationDrivenBinder.TerminationHandler.Returning.INSTANCE,
                defaultAmbiguityResolver(),
                Assigner.DEFAULT,
                methodLookupEngine.process(new TypeDescription.ForLoadedType(delegate.getClass()))
                        .getInvokableMethods()
                        .filter(not(isStatic().or(isPrivate()).or(isConstructor()))));
    }

    /**
     * Creates an implementation where only instance methods of the given object are considered as binding targets.
     * This method will never bind to constructors but will consider methods that are defined in super types. Note
     * that this includes methods that were defined by the {@link java.lang.Object} class. You can narrow this default
     * selection by explicitly selecting methods with calling the
     * {@link net.bytebuddy.implementation.MethodDelegation#filter(net.bytebuddy.matcher.ElementMatcher)}
     * method on the returned method delegation as for example:
     * <pre>MethodDelegation.to(new Foo()).filter(MethodMatchers.not(isDeclaredBy(Object.class)));</pre>
     * which will result in a delegation to <code>Foo</code> where no methods of {@link java.lang.Object} are considered
     * for delegation.
     *
     * @param delegate  A delegate instance which will be injected by a
     *                  {@link net.bytebuddy.implementation.LoadedTypeInitializer}. All intercepted method calls are
     *                  then delegated to this instance.
     * @param fieldName The name of the field for storing the delegate instance.
     * @return A method delegation implementation to the given {@code static} methods.
     */
    public static MethodDelegation to(Object delegate, String fieldName) {
        return to(delegate, fieldName, MethodLookupEngine.Default.DEFAULT_LOOKUP_DISABLED);
    }

    /**
     * Identical to {@link net.bytebuddy.implementation.MethodDelegation#to(Object, java.lang.String)} but uses an
     * explicit {@link MethodLookupEngine}.
     *
     * @param delegate           A delegate instance which will be injected by a type initializer and to which all intercepted
     *                           method calls are delegated to.
     * @param fieldName          The name of the field for storing the delegate instance.
     * @param methodLookupEngine The method lookup engine to use.
     * @return A method delegation implementation to the given {@code static} methods.
     */
    public static MethodDelegation to(Object delegate, String fieldName, MethodLookupEngine methodLookupEngine) {
        return new MethodDelegation(
                new ImplementationDelegate.ForStaticField(nonNull(delegate), isValidIdentifier(fieldName)),
                defaultParameterBinders(),
                defaultDefaultsProvider(),
                TargetMethodAnnotationDrivenBinder.TerminationHandler.Returning.INSTANCE,
                defaultAmbiguityResolver(),
                Assigner.DEFAULT,
                methodLookupEngine.process(new TypeDescription.ForLoadedType(delegate.getClass()))
                        .getInvokableMethods()
                        .filter(not(isStatic().or(isPrivate()).or(isConstructor()))));
    }

    /**
     * Creates an implementation where method calls are delegated to an instance that is manually stored in a field
     * {@code fieldName} that is defined for the instrumented type. The field belongs to any instance of the instrumented
     * type and must be set manually by the user of the instrumented class. Note that this prevents interception of
     * method calls within the constructor of the instrumented class which will instead result in a
     * {@link java.lang.NullPointerException}. Note that this includes methods that were defined by the
     * {@link java.lang.Object} class. You can narrow this default selection by explicitly selecting methods with
     * calling the
     * {@link net.bytebuddy.implementation.MethodDelegation#filter(net.bytebuddy.matcher.ElementMatcher)}
     * method on the returned method delegation as for example:
     * <pre>MethodDelegation.to(new Foo()).filter(MethodMatchers.not(isDeclaredBy(Object.class)));</pre>
     * which will result in a delegation to <code>Foo</code> where no methods of {@link java.lang.Object} are considered
     * for delegation.
     * <p>&nbsp;</p>
     * The field is typically accessed by reflection or by defining an accessor on the instrumented type.
     *
     * @param type      The type of the delegate and the field.
     * @param fieldName The name of the field.
     * @return A method delegation that intercepts method calls by delegating to method calls on the given instance.
     */
    public static MethodDelegation toInstanceField(Class<?> type, String fieldName) {
        return toInstanceField(new TypeDescription.ForLoadedType(nonNull(type)), fieldName);
    }

    /**
     * Creates an implementation where method calls are delegated to an instance that is manually stored in a field
     * {@code fieldName} that is defined for the instrumented type. The field belongs to any instance of the instrumented
     * type and must be set manually by the user of the instrumented class. Note that this prevents interception of
     * method calls within the constructor of the instrumented class which will instead result in a
     * {@link java.lang.NullPointerException}. Note that this includes methods that were defined by the
     * {@link java.lang.Object} class. You can narrow this default selection by explicitly selecting methods with
     * calling the
     * {@link net.bytebuddy.implementation.MethodDelegation#filter(net.bytebuddy.matcher.ElementMatcher)}
     * method on the returned method delegation as for example:
     * <pre>MethodDelegation.to(new Foo()).filter(MethodMatchers.not(isDeclaredBy(Object.class)));</pre>
     * which will result in a delegation to <code>Foo</code> where no methods of {@link java.lang.Object} are considered
     * for delegation.
     * <p>&nbsp;</p>
     * The field is typically accessed by reflection or by defining an accessor on the instrumented type.
     *
     * @param typeDescription The type of the delegate and the field.
     * @param fieldName       The name of the field.
     * @return A method delegation that intercepts method calls by delegating to method calls on the given instance.
     */
    public static MethodDelegation toInstanceField(TypeDescription typeDescription, String fieldName) {
        return toInstanceField(nonNull(typeDescription), isValidIdentifier(fieldName), MethodLookupEngine.Default.DEFAULT_LOOKUP_DISABLED);
    }

    /**
     * Identical to {@link net.bytebuddy.implementation.MethodDelegation#toInstanceField(Class, String)} but uses an
     * explicit {@link MethodLookupEngine}.
     *
     * @param type               The type of the delegate and the field.
     * @param fieldName          The name of the field.
     * @param methodLookupEngine The method lookup engine to use.
     * @return A method delegation that intercepts method calls by delegating to method calls on the given instance.
     */
    public static MethodDelegation toInstanceField(Class<?> type, String fieldName, MethodLookupEngine methodLookupEngine) {
        return toInstanceField(new TypeDescription.ForLoadedType(nonNull(type)), fieldName, methodLookupEngine);
    }

    /**
     * Identical to {@link net.bytebuddy.implementation.MethodDelegation#toInstanceField(Class, String)} but uses an
     * explicit {@link MethodLookupEngine}.
     *
     * @param typeDescription    The type of the delegate and the field.
     * @param fieldName          The name of the field.
     * @param methodLookupEngine The method lookup engine to use.
     * @return A method delegation that intercepts method calls by delegating to method calls on the given instance.
     */
    public static MethodDelegation toInstanceField(TypeDescription typeDescription, String fieldName, MethodLookupEngine methodLookupEngine) {
        return new MethodDelegation(
                new ImplementationDelegate.ForInstanceField(nonNull(typeDescription), isValidIdentifier(fieldName)),
                defaultParameterBinders(),
                defaultDefaultsProvider(),
                TargetMethodAnnotationDrivenBinder.TerminationHandler.Returning.INSTANCE,
                defaultAmbiguityResolver(),
                Assigner.DEFAULT,
                methodLookupEngine.process(typeDescription)
                        .getInvokableMethods()
                        .filter(not(isStatic().or(isPrivate()).or(isConstructor()))));
    }

    /**
     * Creates an implementation where method calls are delegated to constructor calls on the given type. As a result,
     * the return values of all instrumented methods must be assignable to
     *
     * @param type The type that should be constructed by the instrumented methods.
     * @return An implementation that creates instances of the given type as its result.
     */
    public static MethodDelegation toConstructor(Class<?> type) {
        return toConstructor(new TypeDescription.ForLoadedType(nonNull(type)));
    }

    /**
     * Creates an implementation where method calls are delegated to constructor calls on the given type. As a result,
     * the return values of all instrumented methods must be assignable to
     *
     * @param typeDescription The type that should be constructed by the instrumented methods.
     * @return An implementation that creates instances of the given type as its result.
     */
    public static MethodDelegation toConstructor(TypeDescription typeDescription) {
        return new MethodDelegation(new ImplementationDelegate.ForConstruction(nonNull(typeDescription)),
                defaultParameterBinders(),
                defaultDefaultsProvider(),
                TargetMethodAnnotationDrivenBinder.TerminationHandler.Returning.INSTANCE,
                defaultAmbiguityResolver(),
                Assigner.DEFAULT,
                typeDescription.getDeclaredMethods().filter(isConstructor()));
    }

    /**
     * Returns the default parameter binders to be used if not explicitly specified.
     *
     * @return The default parameter binders to be used if not explicitly specified.
     */
    private static List<TargetMethodAnnotationDrivenBinder.ParameterBinder<?>> defaultParameterBinders() {
        return Arrays.<TargetMethodAnnotationDrivenBinder.ParameterBinder<?>>asList(Argument.Binder.INSTANCE,
                AllArguments.Binder.INSTANCE,
                Origin.Binder.INSTANCE,
                This.Binder.INSTANCE,
                Super.Binder.INSTANCE,
                Default.Binder.INSTANCE,
                SuperCall.Binder.INSTANCE,
                DefaultCall.Binder.INSTANCE,
                FieldValue.Binder.INSTANCE,
                StubValue.Binder.INSTANCE,
                Empty.Binder.INSTANCE);
    }

    /**
     * Returns the defaults provider that is to be used if no other is specified explicitly.
     *
     * @return The defaults provider that is to be used if no other is specified explicitly.
     */
    private static TargetMethodAnnotationDrivenBinder.DefaultsProvider defaultDefaultsProvider() {
        return Argument.NextUnboundAsDefaultsProvider.INSTANCE;
    }

    /**
     * Returns the ambiguity resolver that is to be used if no other is specified explicitly.
     *
     * @return The ambiguity resolver that is to be used if no other is specified explicitly.
     */
    private static MethodDelegationBinder.AmbiguityResolver defaultAmbiguityResolver() {
        return MethodDelegationBinder.AmbiguityResolver.Chain.of(BindingPriority.Resolver.INSTANCE,
                DeclaringTypeResolver.INSTANCE,
                ArgumentTypeResolver.INSTANCE,
                MethodNameEqualityResolver.INSTANCE,
                ParameterLengthResolver.INSTANCE);
    }

    /**
     * Defines an parameter binder to be appended to the already defined parameter binders.
     *
     * @param parameterBinder The parameter binder to append to the already defined parameter binders.
     * @return A method delegation implementation that makes use of the given parameter binder.
     */
    public MethodDelegation appendParameterBinder(TargetMethodAnnotationDrivenBinder.ParameterBinder<?> parameterBinder) {
        return new MethodDelegation(implementationDelegate,
                join(parameterBinders, nonNull(parameterBinder)),
                defaultsProvider,
                terminationHandler,
                ambiguityResolver,
                assigner,
                targetCandidates);
    }

    /**
     * Defines a number of parameter binders to be appended to be used by this method delegation.
     *
     * @param parameterBinder The parameter binders to use by this parameter binders.
     * @return A method delegation implementation that makes use of the given parameter binders.
     */
    public MethodDelegation defineParameterBinder(TargetMethodAnnotationDrivenBinder.ParameterBinder<?>... parameterBinder) {
        return new MethodDelegation(implementationDelegate,
                Arrays.asList(nonNull(parameterBinder)),
                defaultsProvider,
                terminationHandler,
                ambiguityResolver,
                assigner,
                targetCandidates);
    }

    /**
     * A provider for annotation instances on values that are not explicitly annotated.
     *
     * @param defaultsProvider The defaults provider to use.
     * @return A method delegation implementation that makes use of the given defaults provider.
     */
    public MethodDelegation withDefaultsProvider(TargetMethodAnnotationDrivenBinder.DefaultsProvider defaultsProvider) {
        return new MethodDelegation(implementationDelegate,
                parameterBinders,
                nonNull(defaultsProvider),
                terminationHandler,
                ambiguityResolver,
                assigner,
                targetCandidates);
    }

    /**
     * Defines an ambiguity resolver to be appended to the already defined ambiguity resolver for resolving binding conflicts.
     *
     * @param ambiguityResolver The ambiguity resolver to append to the already defined ambiguity resolvers.
     * @return A method delegation implementation that makes use of the given ambiguity resolver.
     */
    public MethodDelegation appendAmbiguityResolver(MethodDelegationBinder.AmbiguityResolver ambiguityResolver) {
        return defineAmbiguityResolver(MethodDelegationBinder.AmbiguityResolver.Chain
                .of(this.ambiguityResolver, nonNull(ambiguityResolver)));
    }

    /**
     * Defines an ambiguity resolver to be used for resolving binding conflicts.
     *
     * @param ambiguityResolver The ambiguity resolver to use exclusively.
     * @return A method delegation implementation that makes use of the given ambiguity resolver.
     */
    public MethodDelegation defineAmbiguityResolver(MethodDelegationBinder.AmbiguityResolver... ambiguityResolver) {
        return new MethodDelegation(implementationDelegate,
                parameterBinders,
                defaultsProvider,
                terminationHandler,
                MethodDelegationBinder.AmbiguityResolver.Chain.of(nonNull(ambiguityResolver)),
                assigner,
                targetCandidates);
    }

    /**
     * Applies an assigner to the method delegation that is used for assigning method return and parameter types.
     *
     * @param assigner The assigner to apply.
     * @return A method delegation implementation that makes use of the given designer.
     */
    public MethodDelegation withAssigner(Assigner assigner) {
        return new MethodDelegation(implementationDelegate,
                parameterBinders,
                defaultsProvider,
                terminationHandler,
                ambiguityResolver,
                nonNull(assigner),
                targetCandidates);
    }

    /**
     * Applies a filter to target methods that are eligible for delegation.
     *
     * @param methodMatcher A filter where only methods that match the filter are considered for delegation.
     * @return A method delegation with the filter applied.
     */
    public MethodDelegation filter(ElementMatcher<? super MethodDescription> methodMatcher) {
        return new MethodDelegation(implementationDelegate,
                parameterBinders,
                defaultsProvider,
                terminationHandler,
                ambiguityResolver,
                assigner,
                isNotEmpty(targetCandidates.filter(nonNull(methodMatcher)), NO_METHODS_ERROR_MESSAGE));
    }

    /**
     * Appends another {@link Implementation} to a method delegation. The return
     * value of the delegation target is dropped such that the given {@code implementation} becomes responsible for
     * returning from the method instead. However, if an exception is thrown from the interception method, this
     * exception is not catched and the chained implementation is never applied. Note that this changes the binding
     * semantics as the target method's return value is not longer considered what might change the binding target.
     *
     * @param implementation The implementation to apply after the delegation.
     * @return An implementation that represents this chained implementation application.
     */
    public Implementation andThen(Implementation implementation) {
        return new Compound(new MethodDelegation(implementationDelegate,
                parameterBinders,
                defaultsProvider,
                TargetMethodAnnotationDrivenBinder.TerminationHandler.Dropping.INSTANCE,
                ambiguityResolver,
                assigner,
                targetCandidates), nonNull(implementation));
    }

    @Override
    public InstrumentedType prepare(InstrumentedType instrumentedType) {
        return implementationDelegate.prepare(instrumentedType);
    }

    @Override
    public ByteCodeAppender appender(Target implementationTarget) {
        return new Appender(implementationDelegate.getPreparingStackAssignment(implementationTarget.getTypeDescription()),
                implementationTarget,
                targetCandidates,
                new MethodDelegationBinder.Processor(new TargetMethodAnnotationDrivenBinder(parameterBinders,
                        defaultsProvider,
                        terminationHandler,
                        assigner,
                        implementationDelegate.getMethodInvoker(implementationTarget.getTypeDescription())
                ), ambiguityResolver)
        );
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;
        MethodDelegation that = (MethodDelegation) other;
        return ambiguityResolver.equals(that.ambiguityResolver)
                && assigner.equals(that.assigner)
                && defaultsProvider.equals(that.defaultsProvider)
                && terminationHandler.equals(that.terminationHandler)
                && implementationDelegate.equals(that.implementationDelegate)
                && targetCandidates.equals(that.targetCandidates)
                && parameterBinders.equals(that.parameterBinders);
    }

    @Override
    public int hashCode() {
        int result = implementationDelegate.hashCode();
        result = 31 * result + parameterBinders.hashCode();
        result = 31 * result + defaultsProvider.hashCode();
        result = 31 * result + terminationHandler.hashCode();
        result = 31 * result + ambiguityResolver.hashCode();
        result = 31 * result + assigner.hashCode();
        result = 31 * result + targetCandidates.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "MethodDelegation{" +
                "implementationDelegate=" + implementationDelegate +
                ", parameterBinders=" + parameterBinders +
                ", defaultsProvider=" + defaultsProvider +
                ", terminationHandler=" + terminationHandler +
                ", ambiguityResolver=" + ambiguityResolver +
                ", assigner=" + assigner +
                ", targetCandidates=" + targetCandidates +
                '}';
    }

    /**
     * An implementation delegate is responsible for executing the actual method delegation.
     */
    protected interface ImplementationDelegate {

        /**
         * Prepares the instrumented type.
         *
         * @param instrumentedType The instrumented type to be prepared.
         * @return The instrumented type after it was prepared.
         */
        InstrumentedType prepare(InstrumentedType instrumentedType);

        /**
         * Returns the stack manipulation responsible for preparing the instance representing the implementation.
         *
         * @param instrumentedType A description of the instrumented type to which the implementation is applied.
         * @return A stack manipulation representing the preparation.
         */
        StackManipulation getPreparingStackAssignment(TypeDescription instrumentedType);

        /**
         * Returns the method invoker responsible for invoking the delegation method.
         *
         * @param instrumentedType The instrumented type to which the implementation is applied.
         * @return A method invoker responsible for invoking the delegation method.
         */
        MethodDelegationBinder.MethodInvoker getMethodInvoker(TypeDescription instrumentedType);

        /**
         * An implementation applied to a static method.
         */
        enum ForStaticMethod implements ImplementationDelegate {

            /**
             * The singleton instance.
             */
            INSTANCE;

            @Override
            public InstrumentedType prepare(InstrumentedType instrumentedType) {
                return instrumentedType;
            }

            @Override
            public StackManipulation getPreparingStackAssignment(TypeDescription instrumentedType) {
                return StackManipulation.LegalTrivial.INSTANCE;
            }

            @Override
            public MethodDelegationBinder.MethodInvoker getMethodInvoker(TypeDescription instrumentedType) {
                return MethodDelegationBinder.MethodInvoker.Simple.INSTANCE;
            }

            @Override
            public String toString() {
                return "MethodDelegation.ImplementationDelegate.ForStaticMethod." + name();
            }
        }

        /**
         * An implementation applied on a static field.
         */
        class ForStaticField implements ImplementationDelegate {

            /**
             * The modifier to be assigned to a static field interceptor.
             */
            private static final int FIELD_MODIFIERS = Opcodes.ACC_PRIVATE | Opcodes.ACC_STATIC | Opcodes.ACC_SYNTHETIC;

            /**
             * The name prefix for the {@code static} field that is containing the delegation target.
             */
            private static final String PREFIX = "methodDelegate";

            /**
             * The name of the field that is containing the delegation target.
             */
            private final String fieldName;

            /**
             * The delegation target.
             */
            private final Object delegate;

            /**
             * Creates a new implementation for delegating to an instance that is stored in a {@code static} field.
             * The field name will be created randomly.
             *
             * @param delegate The actual delegation target.
             */
            public ForStaticField(Object delegate) {
                this(delegate, String.format("%s$%d", PREFIX, delegate.hashCode()));
            }

            /**
             * Creates a new implementation for delegating to an instance that is stored in a {@code static} field.
             *
             * @param delegate  The actual delegation target.
             * @param fieldName The name of the field for storing the delegate instance.
             */
            public ForStaticField(Object delegate, String fieldName) {
                this.delegate = delegate;
                this.fieldName = fieldName;
            }

            @Override
            public InstrumentedType prepare(InstrumentedType instrumentedType) {
                return instrumentedType
                        .withField(fieldName, new TypeDescription.ForLoadedType(delegate.getClass()), FIELD_MODIFIERS)
                        .withInitializer(LoadedTypeInitializer.ForStaticField.nonAccessible(fieldName, delegate));
            }

            @Override
            public StackManipulation getPreparingStackAssignment(TypeDescription instrumentedType) {
                return FieldAccess.forField(instrumentedType.getDeclaredFields().filter((named(fieldName))).getOnly()).getter();
            }

            @Override
            public MethodDelegationBinder.MethodInvoker getMethodInvoker(TypeDescription instrumentedType) {
                return new MethodDelegationBinder.MethodInvoker.Virtual(new TypeDescription.ForLoadedType(delegate.getClass()));
            }

            @Override
            public boolean equals(Object other) {
                return this == other || !(other == null || getClass() != other.getClass())
                        && delegate.equals(((ForStaticField) other).delegate)
                        && fieldName.equals(((ForStaticField) other).fieldName);
            }

            @Override
            public int hashCode() {
                return 31 * fieldName.hashCode() + delegate.hashCode();
            }

            @Override
            public String toString() {
                return "MethodDelegation.ImplementationDelegate.ForStaticField{" +
                        "fieldName='" + fieldName + '\'' +
                        ", delegate=" + delegate +
                        '}';
            }
        }

        /**
         * An implementation applied on an instance field.
         */
        class ForInstanceField implements ImplementationDelegate {

            /**
             * The name of the instance field that is containing the target of the method delegation.
             */
            private final String fieldName;

            /**
             * The type of the method delegation target.
             */
            private final TypeDescription fieldType;

            /**
             * Creates a new instance field implementation delegate.
             *
             * @param fieldType A description of the type that is the target of the implementation and thus also the
             *                  field type.
             * @param fieldName The name of the field.
             */
            public ForInstanceField(TypeDescription fieldType, String fieldName) {
                this.fieldType = fieldType;
                this.fieldName = fieldName;
            }

            @Override
            public InstrumentedType prepare(InstrumentedType instrumentedType) {
                return instrumentedType.withField(fieldName, fieldType, Opcodes.ACC_PUBLIC);
            }

            @Override
            public StackManipulation getPreparingStackAssignment(TypeDescription instrumentedType) {
                return new StackManipulation.Compound(MethodVariableAccess.REFERENCE.loadOffset(0),
                        FieldAccess.forField(instrumentedType.getDeclaredFields().filter((named(fieldName))).getOnly()).getter());
            }

            @Override
            public MethodDelegationBinder.MethodInvoker getMethodInvoker(TypeDescription instrumentedType) {
                return new MethodDelegationBinder.MethodInvoker.Virtual(fieldType);
            }

            @Override
            public boolean equals(Object other) {
                return this == other || !(other == null || getClass() != other.getClass())
                        && fieldName.equals(((ForInstanceField) other).fieldName)
                        && fieldType.equals(((ForInstanceField) other).fieldType);
            }

            @Override
            public int hashCode() {
                return 31 * fieldName.hashCode() + fieldType.hashCode();
            }

            @Override
            public String toString() {
                return "MethodDelegation.ImplementationDelegate.ForInstanceField{" +
                        "fieldName='" + fieldName + '\'' +
                        ", fieldType=" + fieldType +
                        '}';
            }
        }

        /**
         * An implementation that creates new instances of a given type.
         */
        class ForConstruction implements ImplementationDelegate {

            /**
             * The type that is to be constructed.
             */
            private final TypeDescription typeDescription;

            /**
             * Creates a new constructor implementation.
             *
             * @param typeDescription The type to be constructed.
             */
            public ForConstruction(TypeDescription typeDescription) {
                this.typeDescription = typeDescription;
            }

            @Override
            public InstrumentedType prepare(InstrumentedType instrumentedType) {
                return instrumentedType;
            }

            @Override
            public StackManipulation getPreparingStackAssignment(TypeDescription instrumentedType) {
                return new StackManipulation.Compound(
                        TypeCreation.forType(typeDescription),
                        Duplication.SINGLE);
            }

            @Override
            public MethodDelegationBinder.MethodInvoker getMethodInvoker(TypeDescription instrumentedType) {
                return MethodDelegationBinder.MethodInvoker.Simple.INSTANCE;
            }

            @Override
            public boolean equals(Object other) {
                return this == other || !(other == null || getClass() != other.getClass())
                        && typeDescription.equals(((ForConstruction) other).typeDescription);
            }

            @Override
            public int hashCode() {
                return typeDescription.hashCode();
            }

            @Override
            public String toString() {
                return "MethodDelegation.ImplementationDelegate.ForConstruction{" +
                        "typeDescription=" + typeDescription +
                        '}';
            }
        }
    }

    /**
     * The appender for implementing a {@link net.bytebuddy.implementation.MethodDelegation}.
     */
    protected static class Appender implements ByteCodeAppender {

        /**
         * The stack manipulation that is responsible for loading a potential target instance onto the stack
         * on which the target method is invoked.
         */
        private final StackManipulation preparingStackAssignment;

        /**
         * The implementation target of this implementation.
         */
        private final Target implementationTarget;

        /**
         * The method candidates to consider for delegating the invocation to.
         */
        private final MethodList targetCandidates;

        /**
         * The method delegation binder processor which is responsible for implementing the method delegation.
         */
        private final MethodDelegationBinder.Processor processor;

        /**
         * Creates a new appender.
         *
         * @param preparingStackAssignment The stack manipulation that is responsible for loading a potential target
         *                                 instance onto the stack on which the target method is invoked.
         * @param implementationTarget     The implementation target of this implementation.
         * @param targetCandidates            The method candidates to consider for delegating the invocation to.
         * @param processor                The method delegation binder processor which is responsible for implementing
         *                                 the method delegation.
         */
        protected Appender(StackManipulation preparingStackAssignment,
                           Target implementationTarget,
                           MethodList targetCandidates,
                           MethodDelegationBinder.Processor processor) {
            this.preparingStackAssignment = preparingStackAssignment;
            this.implementationTarget = implementationTarget;
            this.targetCandidates = targetCandidates;
            this.processor = processor;
        }

        @Override
        public Size apply(MethodVisitor methodVisitor, Context implementationContext, MethodDescription instrumentedMethod) {
            StackManipulation.Size stackSize = new StackManipulation.Compound(
                    preparingStackAssignment,
                    processor.process(implementationTarget, instrumentedMethod, targetCandidates)
            ).apply(methodVisitor, implementationContext);
            return new Size(stackSize.getMaximalSize(), instrumentedMethod.getStackSize());
        }

        @Override
        public boolean equals(Object other) {
            if (this == other) return true;
            if (other == null || getClass() != other.getClass()) return false;
            Appender that = (Appender) other;
            return implementationTarget.equals(that.implementationTarget)
                    && preparingStackAssignment.equals(that.preparingStackAssignment)
                    && processor.equals(that.processor)
                    && targetCandidates.equals(that.targetCandidates);
        }

        @Override
        public int hashCode() {
            int result = preparingStackAssignment.hashCode();
            result = 31 * result + implementationTarget.hashCode();
            result = 31 * result + targetCandidates.hashCode();
            result = 31 * result + processor.hashCode();
            return result;
        }

        @Override
        public String toString() {
            return "MethodDelegation.Appender{" +
                    "preparingStackAssignment=" + preparingStackAssignment +
                    ", implementationTarget=" + implementationTarget +
                    ", targetCandidates=" + targetCandidates +
                    ", processor=" + processor +
                    '}';
        }
    }
}
