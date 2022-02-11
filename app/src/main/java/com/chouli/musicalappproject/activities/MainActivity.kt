package com.chouli.musicalappproject.activities

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.room.Room
import com.chouli.musicalappproject.R
import com.chouli.musicalappproject.database.DataBase
import com.chouli.musicalappproject.databinding.ActivityMainBinding
import com.chouli.musicalappproject.jsonResponse.TrendingResponse
import com.chouli.musicalappproject.services.SingleService
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.converter.moshi.MoshiConverterFactory

import retrofit2.Retrofit

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        supportActionBar?.hide();

        val navHost = supportFragmentManager.findFragmentById(R.id.home_details_nav_host) as NavHostFragment
        NavigationUI.setupWithNavController(home_bottom_nav, navHost.navController)

    }
}