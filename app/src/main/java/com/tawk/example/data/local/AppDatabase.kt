package com.tawk.example.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tawk.example.data.local.dao.ProfileDao
import com.tawk.example.data.local.dao.UserDao
import com.tawk.example.data.local.entity.User
import com.tawk.example.data.local.entity.UserProfile

@Database(entities = [User::class, UserProfile::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun profileDao(): ProfileDao

}