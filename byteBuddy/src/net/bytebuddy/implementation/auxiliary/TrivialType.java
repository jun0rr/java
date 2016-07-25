package net.bytebuddy.implementation.auxiliary;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.ClassFileVersion;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.description.method.MethodList;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.dynamic.scaffold.MethodLookupEngine;
import net.bytebuddy.dynamic.scaffold.subclass.ConstructorStrategy;

import java.util.Collections;
import java.util.Set;

/**
 * A trivial type that extends {@link java.lang.Object} without defining any fields, methods or constructors.
 * This type is meant to be used as a marker type only.
 */
public enum TrivialType implements AuxiliaryType, MethodLookupEngine.Factory, MethodLookupEngine {

    /**
     * The singleton instance.
     */
    INSTANCE;

    @Override
    public DynamicType make(String auxiliaryTypeName,
                            ClassFileVersion classFileVersion,
                            MethodAccessorFactory methodAccessorFactory) {
        return new ByteBuddy(classFileVersion)
                .subclass(Object.class, ConstructorStrategy.Default.NO_CONSTRUCTORS)
                .name(auxiliaryTypeName)
                .modifiers(DEFAULT_TYPE_MODIFIER)
                .methodLookupEngine(this)
                .make();
    }

    @Override
    public MethodLookupEngine make(boolean extractDefaultMethods) {
        return this;
    }

    @Override
    public Finding process(TypeDescription typeDescription) {
        return new Finding.Default(typeDescription,
                new MethodList.Empty(),
                Collections.<TypeDescription, Set<MethodDescription>>emptyMap());
    }

    @Override
    public String toString() {
        return "TrivialType." + name();
    }
}
