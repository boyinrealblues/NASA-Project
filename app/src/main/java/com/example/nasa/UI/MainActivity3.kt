package com.example.nasa.UI

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.OneShotPreDrawListener.add
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.example.nasa.R
import com.example.nasa.databinding.ActivityMain3Binding

class MainActivity3 : AppCompatActivity() {
        lateinit private var binding : ActivityMain3Binding
        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
            binding = DataBindingUtil.setContentView(this,R.layout.activity_main3)
            supportFragmentManager.commit {
                add<BlankFragment>(R.id.container_3)
                setReorderingAllowed(true)
            }
    }
}