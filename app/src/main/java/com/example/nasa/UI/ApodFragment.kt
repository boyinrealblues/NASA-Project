package com.example.nasa.UI

import android.content.Context
import android.os.Bundle
import android.text.TextUtils.replace
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.net.toUri
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.nasa.Data.APOD
import com.example.nasa.R
import com.example.nasa.databinding.FragmentApodBinding

private const val TAG = "ApodFragment"
class ApodFragment : Fragment() {
    lateinit var mApod : APOD
    lateinit private var binding: FragmentApodBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_apod, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val model = ViewModelProvider(this).get(APODViewModel::class.java)
        model.getAPOD()
        activity?.findViewById<ProgressBar>(R.id.progress_bar)?.visibility = View.VISIBLE
        model.APODData.observe(viewLifecycleOwner, {
            mApod = it
            initApod(mApod)
            activity?.findViewById<ProgressBar>(R.id.progress_bar)?.visibility = View.GONE
        })

        binding.header.setOnClickListener {
            if(binding.imageView.isVisible&&::mApod.isInitialized)
            {
                val bundle : Bundle? = Bundle()
                bundle?.putString("123",mApod.url)
                parentFragmentManager.commit{
                    replace<PhotoViewFragment>(R.id.container,args = bundle)
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