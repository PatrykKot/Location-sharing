package com.kotlarz.injection;

import com.google.inject.Inject;
import com.kotlarz.component.annotation.Component;
import com.kotlarz.component.annotation.Init;

@Component
public class ServiceFourImpl implements ServiceFour {
    private ServiceThree serviceThree;

    @Init
    public void init() {
    }


    @Inject
    public ServiceFourImpl(ServiceThree serviceThree) {
        this.serviceThree = serviceThree;
    }

    public ServiceThree getServiceThree() {
        return serviceThree;
    }
}
