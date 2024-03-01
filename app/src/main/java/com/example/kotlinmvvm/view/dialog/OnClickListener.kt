package com.example.kotlinmvvm.view.dialog

interface OnClickListener {
    fun onClick()

    fun onClick(name: String, price: Double, image: String)
}