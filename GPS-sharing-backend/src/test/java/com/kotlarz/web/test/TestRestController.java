package com.kotlarz.web.test;

import com.kotlarz.component.annotation.Component;
import com.kotlarz.web.RequestMethod;
import com.kotlarz.web.annotation.RestMapping;

import spark.Request;
import spark.Response;

@Component
public class TestRestController {

	@RestMapping(mapping = "/test/mapping", method = RequestMethod.GET)
	public Object getTestData(Request request, Response response) {
		return "TEST_GET";
	}

	@RestMapping(mapping = "/test/mapping", method = RequestMethod.POST)
	public Object postTestData(Request request, Response response) {
		return "TEST_POST";
	}

	@RestMapping(mapping = "/test/mapping", method = RequestMethod.PUT)
	public Object putTestData(Request request, Response response) {
		return "TEST_PUT";
	}

	@RestMapping(mapping = "/test/mapping", method = RequestMethod.DELETE)
	public Object deleteTestData(Request request, Response response) {
		return "TEST_DELETE";
	}
}
