package com.kotlarz.system.web;

import com.kotlarz.injector.web.WebConfigure;
import com.kotlarz.system.SystemBase;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import spark.Service;

public class WebSystemBase extends SystemBase {
    private int port = 8080;

    private Service service;

    private Retrofit retrofit;

    @Override
    protected void init(String... scanningPackages) {
        super.init(scanningPackages);

        service = Service.ignite();
        WebConfigure webConfigure = new WebConfigure(service, injector);
        webConfigure.setPort(port);
        webConfigure.bindRequests();

        retrofit = new Retrofit.Builder().baseUrl("http://localhost:" + port + "/")
                .addConverterFactory(ScalarsConverterFactory.create()).build();
    }

    protected <T> T createClient(Class<T> clazz) {
        return retrofit.create(clazz);
    }

    @Override
    protected void close() {
        super.close();
        service.stop();
    }
}
