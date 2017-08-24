package com.kotlarz.configuration;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.reflections.Reflections;

import com.google.inject.AbstractModule;
import com.google.inject.Binding;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import com.google.inject.binder.AnnotatedBindingBuilder;
import com.kotlarz.component.annotation.Component;
import com.kotlarz.component.annotation.Source;
import spark.utils.Assert;

public class DependencyInjector extends AbstractModule {
	private static Logger log = LogManager.getLogger(DependencyInjector.class);

	private String basePackage;

	public DependencyInjector(String basePackage) {
		super();
		this.basePackage = basePackage;
	}

	@Override
	protected void configure() {
		log.debug("Configuring components");
		bindAnnotatedTypes(Component.class);
	}

	private Reflections createReflections() {
		return new Reflections(basePackage);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void bindAnnotatedTypes(Class<? extends Annotation> annotation) {
		log.debug("Finding component classess");

		Set<Class<?>> types = createReflections().getTypesAnnotatedWith(annotation, false);
		log.debug("Found " + types.size() + " classess");

		for (Class<?> type : types) {
			log.debug("Found class " + type);
			if (type.isAnnotationPresent(Source.class)) {
				Source source = type.getAnnotation(Source.class);
				Class<?> interfaceType = source.value();
				Assert.isTrue(interfaceType.isAssignableFrom(type), interfaceType + " is not assignable from " + type);

				log.debug(interfaceType + " is the source if component " + type);
				AnnotatedBindingBuilder binder = bind(interfaceType);
				binder.to(type).in(Singleton.class);
			} else {
				bind(type).in(Singleton.class);
			}

			log.debug(type + " component has been binded");
		}
	}

	public static void invokeMethods(Class<? extends Annotation> annotation, Injector injector)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Collection<Binding<?>> bindings = injector.getAllBindings().values();
		log.debug("Found bindings " + bindings.size());

		for (Binding<?> binding : bindings) {
			Class<?> type = binding.getKey().getTypeLiteral().getRawType();
			Method[] methods = type.getMethods();
			for (Method method : methods) {
				if (method.isAnnotationPresent(annotation)) {
					log.debug("Type " + type + " has init annotation method " + method.getName());
					invokeVoidMethod(method, binding.getProvider().get());
				}
			}
		}
	}

	private static void invokeVoidMethod(Method method, Object instance) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Assert.isTrue(method.getParameterTypes().length == 0,
				"Init method " + instance.getClass() + "#" + method.getName() + " has parameters");
		Assert.isTrue(method.getReturnType().equals(Void.TYPE), "Init method cannot return result");

		method.invoke(instance);
	}
}
