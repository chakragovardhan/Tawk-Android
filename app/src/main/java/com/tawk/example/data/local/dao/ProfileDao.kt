package com.tawk.example.data.local.dao

import androidx.room.*
import com.tawk.example.data.local.entity.UserProfile


@Dao
interface ProfileDao {

    @Query("SELECT * FROM userProfile where login = :user")
    suspend fun getProfile(user : String): UserProfile

    @Insert
    suspend fun insertProfile(userProfile: UserProfile)

    @Update
    suspend fun updateProfile(userProfile: UserProfile)

    @Delete
    suspend fun delete(userProfile: UserProfile)

}