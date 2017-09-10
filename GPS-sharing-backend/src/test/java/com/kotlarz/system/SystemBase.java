package com.kotlarz.system;

import com.google.common.collect.Lists;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.kotlarz.database.Database;
import com.kotlarz.injector.DependencyInjector;
import com.kotlarz.system.exception.SystemDestroyException;
import com.kotlarz.system.exception.SystemInitializingException;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class SystemBase {
    protected Injector injector;

    protected DependencyInjector dependencyInjector;

    protected void init(String... scanningPackages) {
        try {
            scanningPackages = addDatabasePackage(scanningPackages);

            dependencyInjector = new DependencyInjector(scanningPackages);
            injector = Guice.createInjector(dependencyInjector);
            dependencyInjector.invokeInitMethods(injector);
        } catch (Exception ex) {
            throw new SystemInitializingException(ex);
        }
    }

    private String[] addDatabasePackage(String[] packages) {
        List<String> newPackages = Lists.newLinkedList(Arrays.asList(packages));
        newPackages.add(Database.class.getPackage().getName());
        return newPackages.toArray(new String[newPackages.size()]);
    }

    protected void close() {
        try {
            Database instance = injector.getInstance(Database.class);
            instance.close();

            Files.deleteIfExists(Paths.get(Database.DEFAULT_FILENAME));
        } catch (Exception ex) {
            throw new SystemDestroyException(ex);
        }
    }
}
