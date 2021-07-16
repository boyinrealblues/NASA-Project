package com.example.nasa.UI

import android.graphics.drawable.AnimatedVectorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat
import com.example.nasa.R
import com.example.nasa.databinding.ActivityMain2Binding
import com.example.nasa.databinding.ActivityMainBinding

class MainActivity2 : AppCompatActivity() {
     lateinit private var binding : ActivityMain2Binding
     override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main2)
        binding.clicker.setOnClickListener{
            val drawable = binding.done.drawable
            when(drawable){
                is AnimatedVectorDrawable ->{
                    drawable.start()
                }
                is AnimatedVectorDrawableCompat ->{
                    drawable.start()
                }
            }
        }
     }
}