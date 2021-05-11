package com.tawk.example.view.retrofit.single

import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.tawk.example.R
import com.tawk.example.data.api.ApiHelperImpl
import com.tawk.example.data.api.RetrofitBuilder
import com.tawk.example.data.local.DatabaseBuilder
import com.tawk.example.data.local.DatabaseHelperImpl
import com.tawk.example.data.local.entity.UserProfile
import com.tawk.example.utils.Status
import com.tawk.example.utils.ViewModelFactory
import kotlinx.android.synthetic.main.activity_profile_view.*
import kotlinx.android.synthetic.main.activity_recycler_view.progressBar

class UserProfileActivity : AppCompatActivity() {

    private lateinit var viewModel: UserProfileViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_view)
        val user = intent.getStringExtra("userName")
        setupViewModel()
        setupObserver(user)
    }

    private fun setupUI(apiProfile: UserProfile) {
        user_title.text = apiProfile.name
        followers_count.text = apiProfile.followers.toString()
        following_count.text = apiProfile.following.toString()
        Glide.with(profile_image.context)
            .load(apiProfile.avatar_url)
            .into(profile_image)
        name.text = apiProfile.name
        company.text = apiProfile.company
        blog.text = apiProfile.blog
        notes.text = Editable.Factory.getInstance().newEditable(apiProfile.notes)
        save.setOnClickListener {
            saveNotes(apiProfile.login)
        }
    }

    private fun setupObserver(user: String?) {
        viewModel.getProfile(user).observe(this, {
            when (it.status) {
                Status.SUCCESS -> {
                    progressBar.visibility = View.GONE
                    it.data?.let { apiProfile -> setupUI(apiProfile) }
                    profile_layout.visibility = View.VISIBLE
                }
                Status.LOADING -> {
                    progressBar.visibility = View.VISIBLE
                    profile_layout.visibility = View.GONE
                }
                Status.ERROR -> {
                    //Handle Error
                    progressBar.visibility = View.GONE
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(
                ApiHelperImpl(RetrofitBuilder.apiService),
                DatabaseHelperImpl(DatabaseBuilder.getInstance(applicationContext))
            )
        ).get(UserProfileViewModel::class.java)

    }

    private fun saveNotes(user: String) {
        if (!notes.text.toString().isNullOrEmpty()) {
            viewModel.updateNotes(user, notes.text.toString())
        } else {
            Toast.makeText(this, "Notes field is empty", Toast.LENGTH_LONG).show()
        }
    }
}
