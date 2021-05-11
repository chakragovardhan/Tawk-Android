package com.tawk.example.data.api

class ApiHelperImpl(private val apiService: ApiService) : ApiHelper {

    override suspend fun getUsers() = apiService.getUsers()

    override suspend fun getProfile(user:String?) = apiService.getProfile(user)

    override suspend fun getMoreUsers() = apiService.getMoreUsers()

    override suspend fun getUsersWithError() = apiService.getUsersWithError()

}