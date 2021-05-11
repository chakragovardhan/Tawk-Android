package com.tawk.example.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tawk.example.data.api.ApiHelper
import com.tawk.example.data.local.DatabaseHelper
import com.tawk.example.view.retrofit.single.UserProfileViewModel
import com.tawk.example.view.retrofit.single.UsersListViewModel
import com.tawk.example.view.room.RoomDBViewModel
import com.tawk.example.view.room.UserProfileDbViewModel

class ViewModelFactory(private val apiHelper: ApiHelper, private val dbHelper: DatabaseHelper) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UsersListViewModel::class.java)) {
            return UsersListViewModel(apiHelper, dbHelper) as T
        }
        if (modelClass.isAssignableFrom(RoomDBViewModel::class.java)) {
            return RoomDBViewModel(apiHelper, dbHelper) as T
        }
        if (modelClass.isAssignableFrom(UsersListViewModel::class.java)) {
            return UsersListViewModel(apiHelper, dbHelper) as T
        }
        if (modelClass.isAssignableFrom(UserProfileViewModel::class.java)) {
            return UserProfileViewModel(apiHelper, dbHelper) as T
        }
        if (modelClass.isAssignableFrom(UserProfileDbViewModel::class.java)) {
            return UserProfileDbViewModel(apiHelper, dbHelper) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }

}