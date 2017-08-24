package com.kotlarz.web.test;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface TestRestClient {

	@GET("test/mapping")
	Call<String> getTestData();

	@POST("test/mapping")
	Call<String> postTestData();

	@PUT("test/mapping")
	Call<String> putTestData();

	@DELETE("test/mapping")
	Call<String> deleteTestData();
}
