package com.kotlarz.app;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.kotlarz.component.annotation.Init;
import com.kotlarz.configuration.DependencyInjector;
import com.kotlarz.configuration.WebConfigurer;

import spark.Service;

public class App {
	private static final String BASE_PACKAGE = "com.kotlarz";

	private static final String STATIC_CONTENT = "/frontend/dist";

	private static final Integer DEFAULT_PORT = 80;

	private static final Logger log = LogManager.getLogger(App.class);

	private static Injector injector;

	private static WebConfigurer webConfigurer;

	private static Service service;

	public static void main(String[] args)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		log.debug("Starting app at " + new Date());

		log.debug("Creating injector");
		injector = Guice.createInjector(new DependencyInjector(BASE_PACKAGE));

		log.debug("Invoking init component methods");
		DependencyInjector.invokeMethods(Init.class, injector);

		Integer port = findPortArg(args, DEFAULT_PORT);
		log.debug("Configuring web service at port " + port);

		service = Service.ignite();
		webConfigurer = new WebConfigurer(service, injector);
		webConfigurer.setPort(port);

		log.debug("Configuring static content at location " + STATIC_CONTENT);
		webConfigurer.setupStaticContent(STATIC_CONTENT);

		log.debug("Binding controllers");
		webConfigurer.bindRequests();
		webConfigurer.redirectOnError("/");
	}

	private static String findArg(String[] args, String arg) {
		for (int i = 0; i < args.length; i++) {
			String sampleArg = args[i];
			if (sampleArg.equals(arg)) {
				if (args.length - 1 <= i) {
					throw new IllegalArgumentException("Not enough arguments for argument " + arg);
				}

				return args[i + 1];
			}
		}

		throw new IllegalArgumentException("Cannot find argument " + arg);
	}

	private static Integer findPortArg(String[] args, Integer defaultPort) {
		try {
			return Integer.parseInt(findArg(args, "-port"));
		} catch (IllegalArgumentException e) {
			log.warn(e.getMessage());
			return defaultPort;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return defaultPort;
		}
	}
}
