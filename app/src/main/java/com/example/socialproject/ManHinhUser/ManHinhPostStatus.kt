package com.example.socialproject.ManHinhUser

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.socialproject.DangNhapDangKy.ManHinhDangKy
import com.example.socialproject.ManHinhCoSo.ManHinhBase
import com.example.socialproject.Model.Status
import com.example.socialproject.Model.User
import com.example.socialproject.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_edit_profile.*
import kotlinx.android.synthetic.main.activity_man_hinh_post_status.*
import java.time.LocalDateTime
import java.util.*

class ManHinhPostStatus : AppCompatActivity() {

    companion object {
        val TAG = "ManHinhPostStatus"
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_man_hinh_post_status)


        // Chọn ảnh
        post_choose_image_button.setOnClickListener {
            Log.d(TAG, "Tiến hành chọn ảnh")

            // Chọn ảnh trong album
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 0)
        }

        // Post status
        post_post_status_button.setOnClickListener {
            Log.d(TAG, "Tiến hành đăng status")
            uploadImageToFirebase()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun uploadImageToFirebase() {
        if (selectedPhotoUri == null) return

        val fileName = UUID.randomUUID().toString()
        val ref = FirebaseStorage.getInstance().getReference("/Post_images/$fileName")

        ref.putFile(selectedPhotoUri!!)
            .addOnSuccessListener {
                // Thành công khi đăng tải ảnh
                Log.d(TAG, "Đăng tải ảnh thành công ${it.metadata?.path}")

                // Lấy link có thể xem ảnh
                ref.downloadUrl.addOnSuccessListener {
                    Log.d(TAG, "Link tải ảnh: $it")
                    saveStatusToFirebase(it.toString())
                }
            }
            .addOnFailureListener {
                Log.d(TAG, "Đăng tải ảnh không thành công")
            }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun saveStatusToFirebase(statusImageUrl: String) {

        val userID = FirebaseAuth.getInstance().uid

        val ref = FirebaseDatabase.getInstance().getReference("Status/$userID").push()
        val currentDateTime = LocalDateTime.now().toString()

        val statusPost = Status(ref.key!!, userID.toString(), statusImageUrl, post_edit_text.text.toString(), currentDateTime)
        ref.setValue(statusPost)
            .addOnSuccessListener {
                Log.d(TAG, "Đăng status thành công")

                val intent = Intent(this, ManHinhBase::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
            .addOnFailureListener {
                Log.d(TAG, "Xảy ra lỗi khi đăng status")
            }
    }


    var selectedPhotoUri: Uri? = null
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 0 && resultCode == Activity.RESULT_OK && data != null) {
            // Xử lý và kiểm tra ảnh đã chọn...
            Log.d(EditProfile.TAG, "Ảnh đã được chọn")

            selectedPhotoUri = data.data
            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, selectedPhotoUri)
            post_image_view.setImageBitmap(bitmap)
        }
    }
}