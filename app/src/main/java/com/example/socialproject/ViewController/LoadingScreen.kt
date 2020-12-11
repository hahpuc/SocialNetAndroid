package com.example.socialproject.ViewController

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.socialproject.R
import com.example.socialproject.ViewController.ManHinhCoSo.ManHinhBase
import kotlinx.android.synthetic.main.activity_loading_screen.*

class LoadingScreen : AppCompatActivity() {
    @SuppressLint("NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading_screen)

        loading_logo.alpha = 0f
        loading_logo.animate().setDuration(1700).alpha(1f).withEndAction {
            val i = Intent(this, ManHinhBase::class.java)

            startActivity(i)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            finish()
        }
    }
}