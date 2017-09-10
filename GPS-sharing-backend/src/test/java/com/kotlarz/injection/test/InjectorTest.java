package com.kotlarz.injection.test;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.kotlarz.injection.*;
import com.kotlarz.injector.DependencyInjector;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(BlockJUnit4ClassRunner.class)
public class InjectorTest {
    private Injector injector;

    @Before
    public void init() {
        injector = Guice.createInjector(new DependencyInjector("com.kotlarz.injection"));
    }

    @Test
    public void testServiceScanning() {
        ServiceOne serviceOne = injector.getInstance(ServiceOne.class);
        ServiceTwo serviceTwo = injector.getInstance(ServiceTwo.class);

        assertNotNull(serviceOne);
        assertNotNull(serviceTwo);
        assertSame(serviceTwo, serviceOne.getServiceTwo());
    }

    @Test
    public void circularDependency() {
        ServiceThree serviceThree = injector.getInstance(ServiceThree.class);
        ServiceFour serviceFour = injector.getInstance(ServiceFour.class);

        assertNotNull(serviceThree);
        assertNotNull(serviceFour);
        assertNotNull(serviceFour.getServiceThree());
        assertNotNull(serviceThree.getServiceFour());
    }

    @Test
    public void theSameInstance() {
        ServiceFour serviceFour1 = injector.getInstance(ServiceFour.class);
        ServiceFourImpl serviceFour2 = injector.getInstance(ServiceFourImpl.class);
        assertTrue(serviceFour1 == serviceFour2);
    }
}
