package com.tawk.example.view.retrofit.single

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tawk.example.data.api.ApiHelper
import com.tawk.example.data.local.DatabaseHelper
import com.tawk.example.data.local.entity.UserProfile
import com.tawk.example.utils.Resource
import kotlinx.coroutines.launch

class UserProfileViewModel(
    private val apiHelper: ApiHelper,
    private val dbHelper: DatabaseHelper
) : ViewModel() {

    private val profile = MutableLiveData<Resource<UserProfile>>()

    private fun fetchProfile(user: String?) {
        viewModelScope.launch {
            profile.postValue(Resource.loading(null))
            try {
                val profileFromApi = apiHelper.getProfile(user)
                var userProfileFromDb = dbHelper.getProfile(user!!)
                if (userProfileFromDb == null) {
                    val profileDb = UserProfile(
                        profileFromApi.id,
                        profileFromApi.login,
                        profileFromApi.node_id,
                        profileFromApi.avatar_url,
                        profileFromApi.gravatar_id,
                        profileFromApi.url,
                        profileFromApi.html_url,
                        profileFromApi.followers_url,
                        profileFromApi.following_url,
                        profileFromApi.gists_url,
                        profileFromApi.starred_url,
                        profileFromApi.subscriptions_url,
                        profileFromApi.organizations_url,
                        profileFromApi.repos_url,
                        profileFromApi.events_url,
                        profileFromApi.received_events_url,
                        profileFromApi.type,
                        profileFromApi.site_admin,
                        profileFromApi.name,
                        profileFromApi.company,
                        profileFromApi.blog,
                        profileFromApi.location,
                        profileFromApi.email,
                        "",
                        profileFromApi.hireable,
                        profileFromApi.bio,
                        profileFromApi.twitter_username,
                        profileFromApi.public_repos,
                        profileFromApi.public_gists,
                        profileFromApi.followers,
                        profileFromApi.following,
                        profileFromApi.created_at,
                        profileFromApi.updated_at
                    )

                    dbHelper.insertProfile(profileDb)
                    profile.postValue(Resource.success(profileDb))
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
                profile.postValue(Resource.success(dbHelper.getProfile(user)))
            } catch (e: Exception) {
                profile.postValue(Resource.error(e.toString(), null))
            }
        }
    }

}