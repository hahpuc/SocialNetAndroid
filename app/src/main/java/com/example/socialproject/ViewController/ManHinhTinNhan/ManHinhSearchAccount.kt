package com.example.socialproject.ViewController.ManHinhTinNhan

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import com.example.socialproject.Model.User
import com.example.socialproject.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_man_hinh_search_account.*
import kotlinx.android.synthetic.main.user_info_row.view.*

class ManHinhSearchAccount : AppCompatActivity() {

    companion object {
        val TAG = "ManHinhSearch"
        val USER_KEY = "USER_KEY"
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_man_hinh_search_account)

        search_screen_searchbutton.setOnClickListener {
            Log.d(TAG, "Tiền hành search account")

            searchUser()
        }
    }

    fun searchUser() {

        val ref = FirebaseDatabase.getInstance().getReference("/Users")

        ref.addListenerForSingleValueEvent(object: ValueEventListener {

            override fun onDataChange(p0: DataSnapshot) {
                val adapter = GroupAdapter<ViewHolder>()

                // User data loop
                p0.children.forEach() {
                    //Log.d(TAG, it.toString())
                    val user = it.getValue(User::class.java)

                    val searchText = search_screen_search_edittext.text

                    if (user != null && searchText != null && user.username.contains(searchText))
                        adapter.add((UserItem(user)))
                }

                adapter.setOnItemClickListener { item, view ->
                    val userItem = item as UserItem

                    val intent = Intent(view.context, ChatLogActivity::class.java)
                    intent.putExtra(USER_KEY, userItem.user)
                    startActivity(intent)

                    Log.d(TAG, userItem.user.toString())
                }
                
                search_screen_recycler_view.adapter = adapter
            }

            override fun onCancelled(p0: DatabaseError) {

            }
        })

    }
}

class UserItem(val user: User): Item<ViewHolder>() {
    override fun getLayout(): Int {
        return R.layout.user_info_row
    }

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.user_item_username.text = user.username

        Picasso.get().load(user.profileImageUrl).into(viewHolder.itemView.user_item_profile_image)
    }

}