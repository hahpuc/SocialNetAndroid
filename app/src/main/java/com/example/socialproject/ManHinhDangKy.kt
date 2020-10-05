package com.example.socialproject

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.socialproject.Model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_man_hinh_dang_ky.*
import java.util.*
import kotlin.collections.HashMap

class ManHinhDangKy : AppCompatActivity() {

    companion object {
        val TAG = "ManHinhDangKy"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_man_hinh_dang_ky)

        DangKy_ThemAnh_Button.setOnClickListener {
            Log.d(TAG, "Them anh vao nut")

            // Chọn ảnh trong album
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 0)
        }

        DangKy_SignIn_Button.setOnClickListener {
            Log.d(TAG, "Chuyen sang dang nhap")
            val intent = Intent(this, ManHinhDangNhap::class.java)
            startActivity(intent)
        }

        DangKy_Button.setOnClickListener {
            Log.d(TAG, "Tien hanh dang ky")
            tienHanhDangKy()
        }
    }

    var selectedPhotoUri: Uri? = null
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 0 && resultCode == Activity.RESULT_OK && data != null) {
            // Xử lý và kiểm tra ảnh đã chọn...
            Log.d(TAG, "Ảnh đã được chọn")

            selectedPhotoUri = data.data
            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, selectedPhotoUri)
            DangKy_ImageView.setImageBitmap(bitmap)
            DangKy_ThemAnh_Button.visibility = View.INVISIBLE
        }
    }

    fun tienHanhDangKy() {
        val email = DangKy_Email_EditText.text.toString()
        val password = DangKy_Password_EditText.text.toString()

        // Kiểm tra email, password đã được điền
        if (email.isEmpty() || password.isEmpty()) {
            // Make alert in screen
            Toast.makeText(this, "Please enter text in email/password", Toast.LENGTH_SHORT).show()
            return
        }

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (!it.isSuccessful) return@addOnCompleteListener

                // Else if successful
                Log.d(TAG, "Successfully create user with uid: ${it.result?.user?.uid}")


                // Đăng tải ảnh lên Firebase
                uploadImageToFirebase()
            }
            .addOnFailureListener {
                Log.d(TAG, "Fail to create user ${it.message}")
                Toast.makeText(this, "Fail to create user  ${it.message}", Toast.LENGTH_SHORT)
                    .show()

            }
    }

    fun uploadImageToFirebase() {
        if (selectedPhotoUri == null) return

        val fileName = UUID.randomUUID().toString()
        val ref = FirebaseStorage.getInstance().getReference("/Profile_images/$fileName")

        ref.putFile(selectedPhotoUri!!)
            .addOnSuccessListener {
                // Thành công khi đăng tải ảnh
                Log.d(TAG, "Đăng tải ảnh thành công ${it.metadata?.path}")

                // Lấy link có thể xem ảnh
                ref.downloadUrl.addOnSuccessListener {
                    Log.d(TAG, "Link tải ảnh: $it")
                    saveUserToFirebase(it.toString())
                }
            }
            .addOnFailureListener {
                Log.d(TAG, "Đăng tải ảnh không thành công")
            }
    }

    fun saveUserToFirebase(profileImageUrl: String)  {

        //  Lấy uid của tài khoản hiện tại đang đăng nhập
        val uid = FirebaseAuth.getInstance().uid?: ""
        val ref = FirebaseDatabase.getInstance().getReference("/Users/$uid")


        var user = User(
            DangKy_Email_EditText.text.toString(),
            DangKy_Username_editText.text.toString(),
            profileImageUrl
        )
        ref.setValue(user)
            .addOnSuccessListener {
                Log.d(TAG, "Lưu thành công tài khoản vào database")

                // Set up tài khoản follow chính bản thân mình
                setUpFollowing()
            }

    }

    class Following(val uid: String) {
        constructor(): this("")
    }

    fun setUpFollowing() {
        val uid = FirebaseAuth.getInstance().uid
        val value = HashMap<String, Any?>()
        value.put(uid.toString(), 1)

        val ref = FirebaseDatabase.getInstance().getReference("/Following/$uid")
        ref.setValue(value)
            .addOnSuccessListener {
                Log.d(TAG, "Tài khoản đã follow chính mình")
            }
            .addOnFailureListener {
                Log.d(TAG, "Follow thất bại")
            }
    }

}