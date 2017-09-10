package com.kotlarz.app;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.kotlarz.injector.DependencyInjector;
import com.kotlarz.injector.web.WebConfigure;
import lombok.extern.log4j.Log4j2;
import spark.Service;

import java.awt.*;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;

@Log4j2
public class App {
    private static final String BASE_PACKAGE = "com.kotlarz";

    private static final String STATIC_CONTENT = "/frontend/dist";

    private static final Integer DEFAULT_PORT = 8080;

    private static Injector injector;

    private static WebConfigure webConfigure;

    private static Service service;

    public static void main(String[] args)
            throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException, URISyntaxException {
        log.debug("Starting app at " + new Date());

        log.debug("Creating injector");
        DependencyInjector dependencyInjector = new DependencyInjector(BASE_PACKAGE);
        injector = Guice.createInjector(dependencyInjector);

        log.debug("Initializing components");
        dependencyInjector.invokeInitMethods(injector);

        Integer port = findIntegerArg(args, "-port", DEFAULT_PORT);
        log.debug("Configuring web service at port " + port);

        service = Service.ignite();
        webConfigure = new WebConfigure(service, injector);
        webConfigure.setPort(port);

        log.debug("Configuring static content at location " + STATIC_CONTENT);
        webConfigure.setupStaticContent(STATIC_CONTENT);

        log.debug("Binding controllers");
        webConfigure.bindRequests();
        webConfigure.redirectOnError("/");

        if (findBooleanArg(args, "-preview", false)) {
            showBrowser("localhost", port);
        }
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

    private static Boolean findBooleanArg(String[] args, String arg, Boolean defaultValue) {
        try {
            return Boolean.parseBoolean(findArg(args, arg));
        } catch (IllegalArgumentException ex) {
            log.warn(ex.getMessage());
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }

        return defaultValue;
    }

    private static Integer findIntegerArg(String[] args, String arg, Integer defaultValue) {
        try {
            return Integer.parseInt(findArg(args, arg));
        } catch (IllegalArgumentException e) {
            log.warn(e.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        return defaultValue;
    }

    private static void showBrowser(String domain, Integer port) throws URISyntaxException, IOException {
        String url = "http://" + domain + ":" + port;

        if (Desktop.isDesktopSupported()) {
            Desktop.getDesktop().browse(new URI(url));
        } else {
            log.warn("Browser preview is not supported");
        }
    }
}
