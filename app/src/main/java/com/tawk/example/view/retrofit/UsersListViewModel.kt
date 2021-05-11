package com.tawk.example.view.retrofit.single

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tawk.example.data.api.ApiHelper
import com.tawk.example.data.local.DatabaseHelper
import com.tawk.example.data.local.entity.User
import com.tawk.example.data.model.ApiUser
import com.tawk.example.utils.Resource
import kotlinx.coroutines.launch

class UsersListViewModel(
    private val apiHelper: ApiHelper,
    private val dbHelper: DatabaseHelper
) : ViewModel() {

    private val users = MutableLiveData<Resource<List<ApiUser>>>()

    init {
        fetchUsers()
    }

    private fun fetchUsers() {
        viewModelScope.launch {
            users.postValue(Resource.loading(null))
            try {

                val usersFromApi = apiHelper.getUsers()
                val usersFromDb = dbHelper.getUsers()
                if (usersFromDb.isEmpty()) {
                    val usersToInsertInDB = mutableListOf<User>()

                    for (apiUser in usersFromApi) {
                        val user = User(
                            apiUser.id,
                            apiUser.login,
                            apiUser.node_id,
                            apiUser.avatar_url,
                            apiUser.gravatar_id,
                            apiUser.url,
                            apiUser.html_url,
                            apiUser.followers_url,
                            apiUser.following_url,
                            apiUser.gists_url,
                            apiUser.starred_url,
                            apiUser.subscriptions_url,
                            apiUser.organizations_url,
                            apiUser.repos_url,
                            apiUser.events_url,
                            apiUser.received_events_url,
                            apiUser.type,
                            apiUser.site_admin
                        )
                        usersToInsertInDB.add(user)
                    }

                    dbHelper.insertAll(usersToInsertInDB)
                }
                users.postValue(Resource.success(usersFromApi))

            } catch (e: Exception) {
                users.postValue(Resource.error(e.toString(), null))
            }
        }
    }

    fun getUsers(): LiveData<Resource<List<ApiUser>>> {
        return users
    }

}