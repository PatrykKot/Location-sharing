package com.kotlarz.injector;

import com.google.inject.AbstractModule;
import com.google.inject.Injector;
import com.google.inject.Scopes;
import com.google.inject.TypeLiteral;
import com.google.inject.binder.AnnotatedBindingBuilder;
import com.google.inject.spi.InjectionListener;
import com.google.inject.spi.TypeEncounter;
import com.google.inject.spi.TypeListener;
import com.kotlarz.component.annotation.Component;
import com.kotlarz.component.annotation.Init;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.reflections.Reflections;
import spark.utils.Assert;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;

public class DependencyInjector extends AbstractModule implements TypeListener {
    private static Logger log = LogManager.getLogger(DependencyInjector.class);

    private String[] basePackages;

    private Set<Class<?>> types;

    public DependencyInjector(String... basePackages) {
        super();
        this.basePackages = basePackages;
    }

    @Override
    protected void configure() {
        log.debug("Binding components");
        types = bindAnnotatedTypes();
    }

    private Reflections createReflections() {
        return new Reflections(basePackages);
    }

    private Set<Class<?>> bindAnnotatedTypes() {
        log.debug("Finding component classes");

        Set<Class<?>> types = createReflections().getTypesAnnotatedWith(Component.class, false);
        log.debug("Found " + types.size() + " classes");

        for (Class<?> type : types) {
            log.debug("Found class " + type.getSimpleName());
            bindAsClass(type);
            bindInterfaces(type);
        }

        return types;
    }

    public void invokeInitMethods(Injector injector) throws InvocationTargetException, IllegalAccessException {
        for (Class<?> type : types) {
            for (Method method : type.getMethods()) {
                if (method.isAnnotationPresent(Init.class)) {
                    log.debug("Invoking init for class " + type.getSimpleName());
                    Object instance = injector.getInstance(type);
                    invokeVoidMethod(method, instance);
                }
            }
        }
    }

    private void bindAsClass(Class<?> type) {
        bind(type).in(Scopes.SINGLETON);
        log.debug("Type " + type.getSimpleName() + " binded");
    }

    private void bindInterfaces(Class<?> type) {
        for (Class<?> interfaceType : type.getInterfaces()) {
            AnnotatedBindingBuilder binder = bind(interfaceType);
            binder.to(type).in(Scopes.SINGLETON);
            log.debug("Type " + type.getSimpleName() + " binded as implementation of interface " + interfaceType.getSimpleName());
        }
    }

    @Override
    public <I> void hear(TypeLiteral<I> type, TypeEncounter<I> encounter) {
        encounter.register((InjectionListener<I>) injectee -> {
            log.debug("Invoking injectee " + injectee);
            for (Method method : injectee.getClass().getMethods()) {
                if (method.isAnnotationPresent(Init.class)) {
                    try {
                        invokeVoidMethod(method, injectee);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
    }

    private static void invokeVoidMethod(Method method, Object instance) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Assert.isTrue(method.getParameterTypes().length == 0,
                "Init method " + instance.getClass() + "#" + method.getName() + " has parameters");
        Assert.isTrue(method.getReturnType().equals(Void.TYPE), "Init method cannot return result");

        method.invoke(instance);
    }
}
