package com.kotlarz.injection.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import static org.junit.Assert.*;

import org.junit.Before;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.kotlarz.configuration.DependencyInjector;
import com.kotlarz.injection.ServiceFour;
import com.kotlarz.injection.ServiceOne;
import com.kotlarz.injection.ServiceThree;
import com.kotlarz.injection.ServiceTwo;

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
}
