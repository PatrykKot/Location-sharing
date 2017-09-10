package com.kotlarz.system.web.test.client;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface TestClient {
    @GET("test/value")
    Call<String> getTestData();

    @POST("test/value")
    Call<String> postTestData();

    @PUT("test/value")
    Call<String> putTestData();

    @DELETE("test/value")
    Call<String> deleteTestData();
}
