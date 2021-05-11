package com.tawk.example.data.local

import com.tawk.example.data.local.entity.User
import com.tawk.example.data.local.entity.UserProfile

class DatabaseHelperImpl(private val appDatabase: AppDatabase) : DatabaseHelper {

    override suspend fun getUsers(): List<User> = appDatabase.userDao().getAll()

    override suspend fun insertAll(users: List<User>) = appDatabase.userDao().insertAll(users)

    override suspend fun getProfile(user: String): UserProfile = appDatabase.profileDao().getProfile(user)

    override suspend fun insertProfile(userProfile: UserProfile) = appDatabase.profileDao().insertProfile(userProfile)

    override suspend fun updateProfile(userProfile: UserProfile) = appDatabase.profileDao().updateProfile(userProfile)

}