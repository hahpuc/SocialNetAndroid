package com.example.socialproject.ViewController.ManHinhUser

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import com.example.socialproject.ViewController.DangNhapDangKy.ManHinhDangNhap
import com.example.socialproject.ViewController.ManHinhCoSo.ManHinhBase
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
import java.util.*

class EditProfile : AppCompatActivity() {

    companion object {
        val TAG = "ManHinhEdit"
    }

    var currentUser: User? = null
    var newProfileImageUrl: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        // Hiển thị image, name, email
        val uid = FirebaseAuth.getInstance().uid
        val ref = FirebaseDatabase.getInstance().getReference("/Users/$uid")

        Log.d(TAG, "Current user: $ref")

        ref.addListenerForSingleValueEvent(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                currentUser = snapshot.getValue(User::class.java)
                edit_user_name.setText(currentUser?.username.toString())
                edit_email.setText(currentUser?.email.toString())

                Picasso.get().load(currentUser?.profileImageUrl).into(edit_profile_image)


                Log.d(TAG, "Data is $currentUser")
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })

        //---------------------------------------
        edit_log_out.setOnClickListener {
            Log.d(TAG, "Tien hanh dang xuat")
            FirebaseAuth.getInstance().signOut()

            val intent = Intent(this, ManHinhDangNhap::class.java)
            startActivity(intent)

        }

        edit_change_image_button.setOnClickListener {
            Log.d(TAG, "Tien hanh thay doi image")

            // Chọn ảnh trong album
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 0)
        }

        edit_luu_thong_tin.setOnClickListener {
            uploadImageToFirebase()
        }
    }

    fun uploadImageToFirebase() {
        if (selectedPhotoUri != null) {
            val fileName = UUID.randomUUID().toString()
            val ref = FirebaseStorage.getInstance().getReference("/Profile_images/$fileName")

            ref.putFile(selectedPhotoUri!!)
                .addOnSuccessListener {
                    // Thành công khi đăng tải ảnh
                    Log.d(TAG, "Đăng tải ảnh thành công ${it.metadata?.path}")

                    // Lấy link có thể xem ảnh
                    ref.downloadUrl
                        .addOnSuccessListener {
                            Log.d(TAG, "Link tải ảnh: ${it.toString()}")
                            setValue(it.toString())
                        }
                        .addOnFailureListener {
                            Log.d(TAG, "Không thể lấy link ảnh")
                        }
                }
                .addOnFailureListener {
                    Log.d(TAG, "Đăng tải ảnh không thành công")
                }
        }
        else {
            newProfileImageUrl = currentUser?.profileImageUrl
            setValue(newProfileImageUrl.toString())
        }

    }

    fun  setValue(profileImageUrl: String) {
        val uid = FirebaseAuth.getInstance().uid
        val ref = FirebaseDatabase.getInstance().getReference("/Users/$uid")

        val user = User(uid.toString(), edit_email.text.toString(), edit_user_name.text.toString(), profileImageUrl)
        Log.d(TAG, "new user image link: ${user.profileImageUrl}")

        ref.setValue(user)
            .addOnSuccessListener {
                Log.d(TAG, "Luu thong tin thanh cong")
                val intent = Intent(this, ManHinhBase::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
            .addOnFailureListener {
                Log.d(TAG, "Loi khi luu thong tin")
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
            edit_profile_image.setImageBitmap(bitmap)
        }
    }

}