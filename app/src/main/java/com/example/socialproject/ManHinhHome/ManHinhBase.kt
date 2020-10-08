package com.example.socialproject.ManHinhHome

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.socialproject.DangNhapDangKy.ManHinhDangKy
import com.example.socialproject.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_man_hinh_base.*

class ManHinhBase : AppCompatActivity() {

    companion object {
        val TAG = "ManHinhBase"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_man_hinh_base)

        // Kiểm tra đã được đăng nhập bằng 1 tài khoản nào chưa?
        verifyUserIsLoggedIn()

//        dangxuat_button.setOnClickListener {
//            FirebaseAuth.getInstance().signOut()
//            val intent = Intent(this, ManHinhDangKy::class.java)
//            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
//            startActivity(intent)
//        }
    }

    private fun verifyUserIsLoggedIn() {
        val uid = FirebaseAuth.getInstance().uid

        if (uid == null) {
            val intent = Intent(this, ManHinhDangKy::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
        else {
            Log.d(TAG, "Đăng nhập với tải khoản: $uid")
        }
    }
}