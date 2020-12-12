package com.example.courseproject.api

import com.example.courseproject.body.AuthenticateBody
import com.example.courseproject.body.RegistrationBody
import com.example.courseproject.response.AuthenticationResponse
import com.example.courseproject.response.CategoryResponse
import com.example.courseproject.response.QuestionResponse
import retrofit2.Call
import retrofit2.http.*

interface JsonPlaceHolderApi {

    @Headers("Content-Type: application/json")
    @POST("authenticate")
    fun authenticate(@Body authenticationBody: AuthenticateBody): Call<AuthenticationResponse>

    @Headers("Content-Type: application/json")
    @POST("api/users/registration")
    fun checkIn(@Body registrationBody: RegistrationBody): Call<Void>

    @Headers("Content-Type: application/json")
    @GET("api/categories/free")
    fun getFreeCategories(): Call<List<CategoryResponse>>

    @Headers("Content-Type: application/json")
    @GET("api/categories/paid")
    fun getPaidCategories(): Call<List<CategoryResponse>>

    @Headers("Content-Type: application/json")
    @GET("api/categories/user")
    fun getPurchasedCategories(@Header("Authorization") token: String): Call<List<CategoryResponse>>

    @Headers("Content-Type: application/json")
    @GET("api/categories/available")
    fun getNoPurchasedCategories(@Header("Authorization") token: String): Call<List<CategoryResponse>>

    @Headers("Content-Type: application/json")
    @GET("api/categories/custom")
    fun getCustomCategories(@Header("Authorization") token: String): Call<List<CategoryResponse>>

    @Headers("Content-Type: application/json")
    @GET("api/categories/admin")
    fun getAdminCategories(): Call<List<CategoryResponse>>

    @Headers("Content-Type: application/json")
    @GET("api/questions/{id}")
    fun getQuestion(@Path("id") id: Int): Call<List<QuestionResponse>>
}