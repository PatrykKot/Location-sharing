package com.kotlarz.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import spark.ResponseTransformer;

public class JsonTransformer implements ResponseTransformer {

	private ObjectMapper mapper;

	public JsonTransformer() {
		mapper = new ObjectMapper();
	}

	public String render(Object model) throws JsonProcessingException {
		return mapper.writeValueAsString(model);
	}

}