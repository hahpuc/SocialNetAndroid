package com.example.socialproject.ManHinhUser

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.socialproject.DangNhapDangKy.ManHinhDangNhap
import com.example.socialproject.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_edit_profile.*

class EditProfile : AppCompatActivity() {

    companion object {
        val TAG = "ManHinhEdit"
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        // Hiển thị image, name, email
        fetchData()

        edit_log_out.setOnClickListener {
            Log.d(TAG, "Tien hanh dang xuat")
            FirebaseAuth.getInstance().signOut()

            val intent = Intent(this, ManHinhDangNhap::class.java)
            startActivity(intent)

        }

    }

    fun fetchData() {
        val currentUser = FirebaseAuth.getInstance().currentUser

    }
}