package com.example.socialproject.Model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class User(val uid: String, val email: String, val username: String, val profileImageUrl: String, val following: Int, val follower: Int) : Parcelable {
    constructor(): this ("","","", "", 1, 1)

}