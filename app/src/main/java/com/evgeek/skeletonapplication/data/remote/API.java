package com.evgeek.skeletonapplication.data.remote;

import com.evgeek.skeletonapplication.data.model.DataExample;
import com.evgeek.skeletonapplication.data.model.Token;
import com.evgeek.skeletonapplication.data.model.User;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface API {

    @POST("auth/sign_in")
    Single<Token> auth(@Query("email") String email, @Query("password") String password);


    @GET("profile")
    Single<User> getUserProfile(@Header("Authorization") String bearer);

    @GET("data")
    Single<List<DataExample>> getExampleData(String page);

}
