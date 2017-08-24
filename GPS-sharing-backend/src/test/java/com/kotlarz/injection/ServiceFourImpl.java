package com.kotlarz.injection;

import com.google.inject.Inject;
import com.kotlarz.component.annotation.Component;
import com.kotlarz.component.annotation.Source;

@Component
@Source(ServiceFour.class)
public class ServiceFourImpl implements ServiceFour {
	private ServiceThree serviceThree;

	@Inject
	public ServiceFourImpl(ServiceThree serviceThree) {
		this.serviceThree = serviceThree;
	}

	public ServiceThree getServiceThree() {
		return serviceThree;
	}
}
