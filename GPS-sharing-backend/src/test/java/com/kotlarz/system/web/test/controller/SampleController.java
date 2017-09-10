package com.kotlarz.system.web.test.controller;

import com.kotlarz.component.annotation.Component;
import com.kotlarz.web.RequestMethod;
import com.kotlarz.web.annotation.RestMapping;
import spark.Request;
import spark.Response;

@Component
public class SampleController {
    @RestMapping(value = "/test/value", method = RequestMethod.GET)
    public Object getTestData(Request request, Response response) {
        return "TEST_GET";
    }

    @RestMapping(value = "/test/value", method = RequestMethod.POST)
    public Object postTestData(Request request, Response response) {
        return "TEST_POST";
    }

    @RestMapping(value = "/test/value", method = RequestMethod.PUT)
    public Object putTestData(Request request, Response response) {
        return "TEST_PUT";
    }

    @RestMapping(value = "/test/value", method = RequestMethod.DELETE)
    public Object deleteTestData(Request request, Response response) {
        return "TEST_DELETE";
    }
}
