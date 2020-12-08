package com.example.socialproject.ViewController.ManHinhChinh

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.socialproject.Helper.VerticalSpaceItemDecoration
import com.example.socialproject.Model.Status
import com.example.socialproject.Model.User
import com.example.socialproject.R
import com.example.socialproject.ViewController.ManHinhTinNhan.ManHinhSearchAccount
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_man_hinh_profile.*
import kotlinx.android.synthetic.main.user_info_row.view.*

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

        Log.d(TAG, "Choose USER: ${chooseUser!!.uid}")
        // Set Itemview Spacing
        profile_recycler_view.setHasFixedSize(true)
        profile_recycler_view.addItemDecoration(
            VerticalSpaceItemDecoration(10)
        )

        adapter.add(HeaderItem())

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
                        adapter.add(StatusItem())
                }
            }

            override fun onCancelled(p0: DatabaseError) {
            }

        })
    }

}
class HeaderItem(): Item<ViewHolder>() {
    override fun getLayout(): Int {
        return R.layout.user_header_profile
    }

    override fun bind(viewHolder: ViewHolder, position: Int) {

    }

}

class StatusItem(): Item<ViewHolder>() {
    override fun getLayout(): Int {
        return R.layout.user_status_item_row
    }

    override fun bind(viewHolder: ViewHolder, position: Int) {
    }

}