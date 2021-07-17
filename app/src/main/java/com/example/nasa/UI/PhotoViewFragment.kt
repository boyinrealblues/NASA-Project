package com.example.nasa.UI

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.core.net.toUri
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.nasa.Data.APOD
import com.example.nasa.R
import com.example.nasa.databinding.FragmentPhotoViewBinding

class PhotoViewFragment : Fragment() {
    lateinit private var binding: FragmentPhotoViewBinding
    lateinit private var model : APODViewModel
    lateinit private var apod : APOD
    private var position : Int? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let{
            it.getInt("456")?.let{
                position =it
            }
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_photo_view, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.findViewById<Toolbar>(R.id.toolbar)?.visibility = View.GONE
        model = ViewModelProvider(this).get(APODViewModel::class.java)
        model.getAPOD()
        model.APODData.observe(viewLifecycleOwner){
            apod = it[position!!]
            initApod(apod)
        }
        binding.header.setOnClickListener {
            if(binding.imageView.isVisible&&::apod.isInitialized)
            {
                val bundle : Bundle? = Bundle()
                bundle?.putString("123",apod.url)
                parentFragmentManager.commit{
                    replace<PhotoFragment>(R.id.container,args = bundle)
                    addToBackStack(null)
                }
            }
        }
    }
    fun mediaType(dataSet: APOD) {
        when (dataSet.media_type) {
            "video" -> {
                binding.imageView.visibility = View.GONE
                binding.videoView.visibility = View.VISIBLE
                binding.videoView.setVideoURI(dataSet.url.toUri())
                binding.videoView.setOnPreparedListener {
                    binding.videoView.start()
                }
            }

            "image" -> {
                binding.imageView.visibility = View.VISIBLE
                binding.videoView.visibility = View.GONE
                Glide.with(this).load(dataSet.url).centerCrop().into(binding.imageView)
            }

        }
    }
    fun initApod(dataSet : APOD){
        binding.textTitle.setText(dataSet.title)
        binding.date.setText(dataSet.date)
        binding.description.setText(dataSet.explanation)
        mediaType(dataSet)
    }

}