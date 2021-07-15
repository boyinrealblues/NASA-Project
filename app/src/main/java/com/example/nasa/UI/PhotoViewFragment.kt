package com.example.nasa.UI

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.example.nasa.R
import com.example.nasa.databinding.FragmentPhotoViewBinding

class PhotoViewFragment : Fragment() {
    lateinit private var binding: FragmentPhotoViewBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_photo_view, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.findViewById<Toolbar>(R.id.toolbar)?.visibility = View.GONE
        arguments?.let {
            it.getString("123")?.let {
                Glide.with(this).load(it).into(binding.photoViewer)
            }
        }
    }
}