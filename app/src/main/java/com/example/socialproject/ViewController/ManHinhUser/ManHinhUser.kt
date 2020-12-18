package com.example.socialproject.ViewController.ManHinhUser

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.socialproject.Model.Status
import com.example.socialproject.Model.User
import com.example.socialproject.R
import com.example.socialproject.Helper.VerticalSpaceItemDecoration
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
 
class ManHinhUser : Fragment() {

    var currentUser: User? = null
    private fun generateDummyList(): List<Status> {

        val uid = FirebaseAuth.getInstance().uid
        val ref = FirebaseDatabase.getInstance().getReference("Status/$uid")

        val list = ArrayList<Status>()
        ref.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapShot: DataSnapshot) {
                snapShot.children.forEach() {
                    //Log.d(TAG, "Load status ${it.toString()} ")
                    val status = it.getValue(Status::class.java)
                    if (status != null)
                        list += status
                }

                list.reverse()
            }

            override fun onCancelled(snapShot: DatabaseError) {

            }
        })

        return list
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_man_hinh_user, container, false)
        val rec = view.findViewById(R.id.user_recycler_view) as RecyclerView
        val statusList = generateDummyList()
        
        rec.adapter = ManHinhUserAdapter(statusList)
        rec.setHasFixedSize(true)
        rec.addItemDecoration(
            VerticalSpaceItemDecoration(
                10
            )
        )

        return view
    }

}

