package com.kotlarz.web.test;

import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.kotlarz.configuration.DependencyInjector;
import com.kotlarz.configuration.WebConfigurer;

import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import spark.Service;
import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

@RunWith(BlockJUnit4ClassRunner.class)
public class WebConfigurerTest {
	private Injector injector;

	private WebConfigurer configurer;

	private Service service;

	private TestRestClient client;

	@Before
	public void init() {
		injector = Guice.createInjector(new DependencyInjector("com.kotlarz.web.test"));
		service = Service.ignite();
		configurer = new WebConfigurer(service, injector);

		Retrofit retrofit = new Retrofit.Builder().baseUrl("http://localhost:8080/")
				.addConverterFactory(ScalarsConverterFactory.create()).build();
		client = retrofit.create(TestRestClient.class);
	}

	@After
	public void stop() {
		service.stop();
	}

	@Test
	public void startService() throws IOException, InterruptedException {
		configurer.setPort(8080);
		configurer.bindRequests();

		String get = client.getTestData().execute().body();
		assertEquals(get, "\"TEST_GET\"");

		String post = client.postTestData().execute().body();
		assertEquals(post, "\"TEST_POST\"");

		assertEquals(client.putTestData().execute().body(), "\"TEST_PUT\"");
		assertEquals(client.deleteTestData().execute().body(), "\"TEST_DELETE\"");
	}
}
