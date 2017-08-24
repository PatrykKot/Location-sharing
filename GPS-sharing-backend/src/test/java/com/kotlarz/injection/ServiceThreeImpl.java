package com.kotlarz.injection;

import com.google.inject.Inject;
import com.kotlarz.component.annotation.Component;
import com.kotlarz.component.annotation.Source;

@Component
@Source(ServiceThree.class)
public class ServiceThreeImpl implements ServiceThree {
	private ServiceFour serviceFour;

	@Inject
	public ServiceThreeImpl(ServiceFour serviceFour) {
		this.serviceFour = serviceFour;
	}

	public ServiceFour getServiceFour() {
		return serviceFour;
	}

}
