package com.kotlarz.web.controller;

import java.util.LinkedList;
import java.util.List;

import com.kotlarz.component.annotation.Component;
import com.kotlarz.web.RequestMethod;
import com.kotlarz.web.annotation.RestMapping;

import spark.Request;
import spark.Response;

@Component
public class HelloController {

	@RestMapping(mapping = "/rest", method = RequestMethod.GET)
	public Object getHello(Request request, Response response) {
		List<String> list = new LinkedList<>();
		list.add("dupa");
		list.add("dupa2");
		return list;
	}
}
