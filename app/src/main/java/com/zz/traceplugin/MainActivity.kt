package com.zz.traceplugin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        println("MainActivity -> onCreate")
    }

    @Cat
    fun meth1() {
        println("MainActivity -> meth1")
    }

    @ASM
    fun meth2() {
        println("MainActivity -> meth2")
    }
}