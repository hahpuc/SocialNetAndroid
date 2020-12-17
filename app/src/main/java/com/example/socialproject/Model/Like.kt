package com.example.socialproject.Model

class Like(val fromID: String, val toID: String, val statusID: String) {
    constructor() : this("", "", "")
}