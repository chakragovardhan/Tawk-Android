package com.tawk.example.data.api

import com.tawk.example.data.model.ApiProfile
import com.tawk.example.data.model.ApiUser


interface ApiHelper {

    suspend fun getUsers(): List<ApiUser>

    suspend fun getProfile(user:String?): ApiProfile

    suspend fun getMoreUsers(): List<ApiUser>

    suspend fun getUsersWithError(): List<ApiUser>

}