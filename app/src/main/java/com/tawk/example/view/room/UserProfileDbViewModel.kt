package com.tawk.example.view.room

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tawk.example.data.api.ApiHelper
import com.tawk.example.data.local.DatabaseHelper
import com.tawk.example.data.local.entity.UserProfile
import com.tawk.example.utils.Resource
import kotlinx.coroutines.launch

class UserProfileDbViewModel(
    private val apiHelper: ApiHelper,
    private val dbHelper: DatabaseHelper
) : ViewModel() {

    private val profile = MutableLiveData<Resource<UserProfile>>()

    private fun fetchProfile(user: String?) {
        viewModelScope.launch {
            profile.postValue(Resource.loading(null))
            try {
                val userProfileFromDb = dbHelper.getProfile(user!!)
                if (userProfileFromDb == null) {
                    profile.postValue(Resource.error("No data found", null))
                } else {
                    profile.postValue(Resource.success(userProfileFromDb))
                }


            } catch (e: Exception) {
                profile.postValue(Resource.error(e.toString(), null))
            }
        }
    }

    fun getProfile(user: String?): LiveData<Resource<UserProfile>> {
        fetchProfile(user)
        return profile
    }

    fun updateNotes(user: String,notes: String) {
        viewModelScope.launch {
            profile.postValue(Resource.loading(null))
            try {
                var userProfileDb= dbHelper.getProfile(user)
                userProfileDb.notes=notes
                dbHelper.updateProfile(userProfileDb)
                userProfileDb= dbHelper.getProfile(user)
                if (userProfileDb == null) {
                    profile.postValue(Resource.error("No data found", null))
                } else {
                    profile.postValue(Resource.success(userProfileDb))
                }


            } catch (e: Exception) {
                profile.postValue(Resource.error(e.toString(), null))
            }
        }
    }

}