package com.tawk.example

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.tawk.example.view.retrofit.single.UsersListActivity
import com.tawk.example.view.room.RoomDBActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        if(isNetworkAvailbale()) {
            startActivity(Intent(this@MainActivity, UsersListActivity::class.java))
            finish()
        } else {
            startActivity(Intent(this@MainActivity, RoomDBActivity::class.java))
            finish()
        }
    }

    fun isNetworkAvailbale(): Boolean {
        val conManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities = conManager.getNetworkCapabilities(conManager.activeNetwork)

        val connected =
            capabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
        return connected
    }

}
