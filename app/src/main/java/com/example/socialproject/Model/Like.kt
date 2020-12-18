package com.example.socialproject.Model

import android.os.Build
import androidx.annotation.RequiresApi

class Like(val fromID: String, val toID: String, val statusID: String) {
    @RequiresApi(Build.VERSION_CODES.O)
    constructor() : this("", "", "")
}