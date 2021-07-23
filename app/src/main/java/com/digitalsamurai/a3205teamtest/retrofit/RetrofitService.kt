package com.digitalsamurai.a3205teamtest.retrofit

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path


interface RetrofitService {

    @GET("repos/{user}/{repo}/zipball/")
    fun downloadZipRepos(@Path("user") user : String, @Path("repo") repo : String) : Call<ResponseBody>


    @GET("/users/{user}/repos")
    fun searchRepositories(@Path("user") user : String) : Call<ArrayList<DataUserRepo>>

    companion object GitHub{
        fun create() : RetrofitService{
            val retrofit = Retrofit.Builder()
                .baseUrl("https://api.github.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(RetrofitService::class.java)
        }
    }
}