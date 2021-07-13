package com.example.nasa.UI

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.example.nasa.R
import com.example.nasa.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit private var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
            supportFragmentManager.commit{
                add<ApodFragment>(R.id.container)
                setReorderingAllowed(true)
        }
    }
}