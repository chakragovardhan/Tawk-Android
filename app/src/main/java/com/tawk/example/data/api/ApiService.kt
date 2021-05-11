package com.tawk.example.data.api


import com.tawk.example.data.model.ApiProfile
import com.tawk.example.data.model.ApiUser
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("users?since=0")
    suspend fun getUsers(): List<ApiUser>

    @GET("users/{user_name}")
    suspend fun getProfile(@Path(value="user_name", encoded = true)user: String?): ApiProfile

    @GET("more-users")
    suspend fun getMoreUsers(): List<ApiUser>

    @GET("error")
    suspend fun getUsersWithError(): List<ApiUser>

}