package com.example.socialproject.DangNhapDangKy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.socialproject.ManHinhHome.ManHinhBase
import com.example.socialproject.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_man_hinh_dang_nhap.*

class ManHinhDangNhap : AppCompatActivity() {

    companion object {
        val TAG = "ManHinhDangNhap"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_man_hinh_dang_nhap)

        DangNhap_Button.setOnClickListener {
            Log.d(TAG, "Tiến hành đăng nhập")
            tienHanhDangNhap()

        }

        DangNhap_SignUp_Button.setOnClickListener {
            val intent = Intent(this, ManHinhDangKy::class.java)
            startActivity(intent)
        }
    }

    fun tienHanhDangNhap() {
        val email = DangNhap_Email_EditText.text.toString()
        val password = DangNhap_Password_EditText.text.toString()

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                Log.d(TAG,"Đăng nhập thành công : ${it.user?.uid}")

                val intent = Intent(this, ManHinhBase::class.java)
                // Khi đăng kí/đăng nhập xong thì lúc ấn nút back không trở về màn hình đăng nhập/ đăng kí được
                // Cũng như khi khởi động lại ứng dụng sẽ vào luôn màn hình Home
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
            .addOnFailureListener {
                Log.d(TAG, "Đăng nhập không thành công")
            }
    }
}