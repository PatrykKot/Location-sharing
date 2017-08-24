package com.kotlarz.injection;

import com.google.inject.Inject;
import com.kotlarz.component.annotation.Component;

@Component
public class ServiceOne {
	private ServiceTwo serviceTwo;

	@Inject
	public ServiceOne(ServiceTwo serviceOne) {
		this.serviceTwo = serviceOne;
	}

	public ServiceTwo getServiceTwo() {
		return serviceTwo;
	}

}
