package com.kotlarz.configuration;

import java.lang.reflect.Method;
import java.util.Collection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.inject.Binding;
import com.google.inject.Injector;
import com.kotlarz.web.JsonTransformer;
import com.kotlarz.web.RequestMethod;
import com.kotlarz.web.annotation.RestMapping;

import spark.Request;
import spark.Response;
import spark.ResponseTransformer;
import spark.Route;
import spark.Service;

public class WebConfigurer {
	private static Logger log = LogManager.getLogger(WebConfigurer.class);

	private Service webService;

	private Injector injector;

	public WebConfigurer(Service webService, Injector injector) {
		this.webService = webService;
		this.injector = injector;
	}

	public void bindRequests() {
		Collection<Binding<?>> bindings = injector.getAllBindings().values();
		log.debug("Found bindings " + bindings.size());

		for (Binding<?> binding : bindings) {
			Class<?> type = binding.getKey().getTypeLiteral().getRawType();
			Method[] methods = type.getMethods();
			for (Method method : methods) {
				if (method.isAnnotationPresent(RestMapping.class)) {
					log.debug("Type " + type + " has rest annotation method " + method.getName());
					bindRestMethod(method);
				}
			}
		}
	}

	public void setupStaticContent(String filePath) {
		webService.staticFileLocation(filePath);
	}

	private void bindRestMethod(final Method method) {
		RestMapping mappping = method.getAnnotation(RestMapping.class);
		ResponseTransformer transformer = new JsonTransformer();
		RequestMethod requestMethod = mappping.method();

		log.debug("Binding mapping \"" + mappping.mapping() + "\" with method " + requestMethod.toString() + " to method "
				+ method.getDeclaringClass() + "#" + method.getName());

		switch (requestMethod) {
		case GET: {
			webService.get(mappping.mapping(), invokeRequest(method), transformer);
			break;
		}
		case POST: {
			webService.post(mappping.mapping(), invokeRequest(method), transformer);
			break;
		}
		case PUT: {
			webService.put(mappping.mapping(), invokeRequest(method), transformer);
			break;
		}
		case DELETE: {
			webService.delete(mappping.mapping(), invokeRequest(method), transformer);
			break;
		}
		default: {
			throw new IllegalArgumentException(requestMethod.toString());
		}
		}
	}

	public void setPort(Integer port) {
		webService.port(port);
	}

	public void redirectOnError(String path) {
		webService.get("*", (request, response) -> {
			response.redirect(path);
			return null;
		});
	}

	private Route invokeRequest(final Method method) {
		Class<?> declaringClass = method.getDeclaringClass();
		final Object instance = injector.getInstance(declaringClass);
		return new Route() {
			public Object handle(Request request, Response response) throws Exception {
				return method.invoke(instance, request, response);
			}
		};
	}
}
