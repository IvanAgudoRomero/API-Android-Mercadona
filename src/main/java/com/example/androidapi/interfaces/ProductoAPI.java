package com.example.androidapi.interfaces;

import com.example.androidapi.models.Categoria;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ProductoAPI {
    @GET("categories/{id}")
    public Call<Categoria> find(@Path("id") int id);
}
