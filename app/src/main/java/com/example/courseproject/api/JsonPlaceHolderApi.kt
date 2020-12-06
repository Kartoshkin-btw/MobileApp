package com.example.courseproject.api

import com.example.courseproject.body.AuthenticateBody
import com.example.courseproject.body.RegistrationBody
import com.example.courseproject.response.AuthenticationResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface JsonPlaceHolderApi {

    @Headers("Content-Type: application/json")
    @POST("authenticate")
    fun authenticate(@Body authenticationBody: AuthenticateBody): Call<AuthenticationResponse>

    @Headers("Content-Type: application/json")
    @POST("api/users/registration")
    fun checkIn(@Body registrationBody: RegistrationBody): Call<Void>
}