package com.example.socialproject.ViewController.ManHinhChinh

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.socialproject.Helper.VerticalSpaceItemDecoration
import com.example.socialproject.Model.Status
import com.example.socialproject.Model.User
import com.example.socialproject.R
import com.example.socialproject.View.StatusView.StatusItem
import com.example.socialproject.View.HeaderProfile.HeaderItem
import com.example.socialproject.ViewController.ManHinhTinNhan.ManHinhSearchAccount
import com.google.firebase.database.*
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_man_hinh_profile.*

class ManHinhProfile : AppCompatActivity() {

    companion object {
        val TAG = "ManHinhProfile"
    }

    var chooseUser: User? = null

    var adapter = GroupAdapter<ViewHolder>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_man_hinh_profile)

        chooseUser = intent.getParcelableExtra<User>(ManHinhSearchAccount.USER_KEY)

        //Log.d(TAG, "Current USER: ${ManHinhBase.currentUser!!.uid}")

        //Log.d(TAG, "Choose USER: ${chooseUser!!.uid}")
        // Set Itemview Spacing
        profile_recycler_view.setHasFixedSize(true)
        profile_recycler_view.addItemDecoration(
            VerticalSpaceItemDecoration(10)
        )

        adapter.add(HeaderItem(chooseUser!!))

        fetchStatus()

        profile_recycler_view.adapter = adapter

    }

    fun fetchStatus() {
        val ref = FirebaseDatabase.getInstance().getReference("Status/${chooseUser!!.uid}")

        ref.addListenerForSingleValueEvent(object: ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach {
                    val status = it.getValue(Status::class.java)
                    if (status != null)
                        adapter.add(
                            StatusItem(
                                status,
                                chooseUser!!
                            )
                        )
                }
            }

            override fun onCancelled(p0: DatabaseError) {
            }

        })
    }

}

