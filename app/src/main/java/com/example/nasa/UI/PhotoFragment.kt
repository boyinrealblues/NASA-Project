package com.example.nasa.UI

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.example.nasa.R
import com.example.nasa.databinding.FragmentPhotoBinding

class PhotoFragment : Fragment() {
    lateinit private var binding : FragmentPhotoBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_photo,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let{
            it.getString("123")?.let{
                Glide.with(this).load(it).into(binding.apodImage)
            }
        }
    }
}