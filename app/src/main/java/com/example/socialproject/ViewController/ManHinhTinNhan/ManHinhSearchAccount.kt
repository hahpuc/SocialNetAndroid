package com.example.socialproject.ViewController.ManHinhTinNhan

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.socialproject.Model.User
import com.example.socialproject.R
import com.example.socialproject.View.UserSearchView.UserItem
import com.example.socialproject.ViewController.ManHinhChinh.ManHinhProfile
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_man_hinh_search_account.*

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

        val caller = intent.getStringExtra("Caller")

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

                    if (caller == "SearchMessage") {
                        Log.d(TAG, "Chuyen toi man hinh tin nhan")
                        val intent = Intent(view.context, ChatLogActivity::class.java)
                        intent.putExtra(USER_KEY, userItem.user)
                        startActivity(intent)
                    }
                    else {
                        Log.d(TAG, "Chuyen toi man hinh user")
                        val intent = Intent(view.context, ManHinhProfile::class.java)
                        intent.putExtra(USER_KEY, userItem.user)
                        startActivity(intent)
                    }


                    //Log.d(TAG, userItem.user.toString())
                }
                
                search_screen_recycler_view.adapter = adapter
            }

            override fun onCancelled(p0: DatabaseError) {

            }
        })

    }
}

