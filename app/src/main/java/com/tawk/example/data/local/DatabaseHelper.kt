package com.tawk.example.data.local

import com.tawk.example.data.local.entity.User
import com.tawk.example.data.local.entity.UserProfile


interface DatabaseHelper {

    suspend fun getUsers(): List<User>

    suspend fun insertAll(users: List<User>)

    suspend fun getProfile(user: String): UserProfile

    suspend fun insertProfile(userProfile: UserProfile)

    suspend fun updateProfile(userProfile: UserProfile)

}