package com.kotlarz.location.controller;

import com.kotlarz.component.annotation.Component;
import com.kotlarz.web.annotation.RestMapping;

@Component
public class LocationController {
	//@RestMapping(mapping = "location/update")
	public void update() {
		System.out.println("UPDATING");
	}
}
