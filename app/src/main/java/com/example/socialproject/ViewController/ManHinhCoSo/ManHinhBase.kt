package com.example.socialproject.ViewController.ManHinhCoSo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.socialproject.*
import com.example.socialproject.ViewController.DangNhapDangKy.ManHinhDangKy
import com.example.socialproject.ViewController.ManHinhChinh.ManHinhChinh
import com.example.socialproject.ViewController.ManHinhThongBao.ManHinhThongBao
import com.example.socialproject.ViewController.ManHinhTinNhan.ManHinhTinNhan
import com.example.socialproject.ViewController.ManHinhUser.ManHinhUser
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

        setUpViewPage()
    }

    private fun setUpViewPage() {
        val adapter = PageAdapter(
            supportFragmentManager
        )
        adapter.addFragment(ManHinhChinh())
        adapter.addFragment(ManHinhTinNhan())
        adapter.addFragment(ManHinhThongBao())
        adapter.addFragment(ManHinhUser())

        viewPageContainer.adapter = adapter
        tabLayout.setupWithViewPager(viewPageContainer)

        tabLayout.getTabAt(0)!!.setIcon(R.drawable.unselected_home)
        tabLayout.getTabAt(1)!!.setIcon(R.drawable.unselected_chat)
        tabLayout.getTabAt(2)!!.setIcon(R.drawable.unselected_heart)
        tabLayout.getTabAt(3)!!.setIcon(R.drawable.unselected_user)

        val tab = tabLayout.getTabAt(3)
        tab!!.select()

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