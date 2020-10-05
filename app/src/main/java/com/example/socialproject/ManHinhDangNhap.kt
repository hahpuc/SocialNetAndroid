package com.example.socialproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_man_hinh_dang_nhap.*

class ManHinhDangNhap : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_man_hinh_dang_nhap)

        DangNhap_SignUp_Button.setOnClickListener {
            val intent = Intent(this, ManHinhDangKy::class.java)
            startActivity(intent)
        }
    }
}