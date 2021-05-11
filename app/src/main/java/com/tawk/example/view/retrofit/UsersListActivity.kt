package com.tawk.example.view.retrofit.single

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.tawk.example.R
import com.tawk.example.data.api.ApiHelperImpl
import com.tawk.example.data.api.RetrofitBuilder
import com.tawk.example.data.local.DatabaseBuilder
import com.tawk.example.data.local.DatabaseHelperImpl
import com.tawk.example.data.model.ApiUser
import com.tawk.example.view.base.ApiUserAdapter
import com.tawk.example.utils.Status
import com.tawk.example.utils.ViewModelFactory
import kotlinx.android.synthetic.main.activity_recycler_view.*

class UsersListActivity : AppCompatActivity() {

    private lateinit var viewModel: UsersListViewModel
    private lateinit var adapter: ApiUserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view)
            setupUI()
            setupViewModel()
            setupObserver()
    }

    private fun setupUI() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter =
            ApiUserAdapter(
                arrayListOf(), this
            )
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                recyclerView.context,
                (recyclerView.layoutManager as LinearLayoutManager).orientation
            )
        )
        recyclerView.adapter = adapter

        simpleSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return false
            }

        })
    }

    private fun setupObserver() {
            viewModel.getUsers().observe(this, {
            when (it.status) {
                Status.SUCCESS -> {
                    progressBar.visibility = View.GONE
                    it.data?.let { users -> renderList(users) }
                    recyclerView.visibility = View.VISIBLE
                }
                Status.LOADING -> {
                    progressBar.visibility = View.VISIBLE
                    recyclerView.visibility = View.GONE
                }
                Status.ERROR -> {
                    //Handle Error
                    progressBar.visibility = View.GONE
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun renderList(users: List<ApiUser>) {
        adapter.addData(users)
        adapter.notifyDataSetChanged()
    }

    private fun setupViewModel() {
            viewModel = ViewModelProviders.of(
                this,
                ViewModelFactory(
                    ApiHelperImpl(RetrofitBuilder.apiService),
                    DatabaseHelperImpl(DatabaseBuilder.getInstance(applicationContext))
                )
            ).get(UsersListViewModel::class.java)

    }

    fun onItemClick(userName: String) {
        val intent = Intent(this, UserProfileActivity::class.java)
        intent.putExtra("userName", userName);
        startActivity(intent)
    }
}
