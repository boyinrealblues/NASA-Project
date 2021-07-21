package com.example.nasa.UI

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.nasa.R
import com.example.nasa.databinding.FragmentBlank2Binding
import com.google.android.material.transition.platform.MaterialContainerTransform

class BlankFragment2 : Fragment() {
    lateinit private var binding : FragmentBlank2Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = MaterialContainerTransform(requireContext(),true)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_blank2,container,false)
        return binding.root
    }

}