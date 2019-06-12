package com.example.user.remote;

import com.example.user.model.Usermodel;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface APIService {

    @GET("/todos/1")
    Observable<Usermodel> getUserModel();

}
