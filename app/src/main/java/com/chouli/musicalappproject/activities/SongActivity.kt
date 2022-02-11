package com.chouli.musicalappproject.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.chouli.musicalappproject.R

class SongActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_song)
        supportActionBar?.hide();
    }
}