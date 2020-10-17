package com.example.socialproject.ManHinhUser

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.content.Intent
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.view.menu.MenuView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.socialproject.DangNhapDangKy.ManHinhDangNhap
import com.example.socialproject.Model.Status
import com.example.socialproject.Model.User
import com.example.socialproject.R
import com.example.socialproject.VerticalSpaceItemDecoration
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.fragment_man_hinh_user.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ManHinhUser.newInstance] factory method to
 * create an instance of this fragment.
 */
 
class ManHinhUser : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

    }

    var currentUser: User? = null
    private fun generateDummyList(): List<Status> {

        val uid = FirebaseAuth.getInstance().uid
        val ref = FirebaseDatabase.getInstance().getReference("Status/$uid")

        val list = ArrayList<Status>()
        ref.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapShot: DataSnapshot) {
                snapShot.children.forEach() {
                    Log.d(TAG, "Load status ${it.toString()} ")
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
        rec.addItemDecoration(VerticalSpaceItemDecoration(10))

        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ManHinhUser.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ManHinhUser().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }

        val TAG = "ManHinhUser"
    }
}

