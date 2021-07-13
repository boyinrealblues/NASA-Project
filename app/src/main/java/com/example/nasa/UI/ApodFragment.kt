package com.example.nasa.UI

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.nasa.R
import com.example.nasa.databinding.FragmentApodBinding

private const val TAG = "ApodFragment"
class ApodFragment : Fragment() {

   lateinit private var binding :FragmentApodBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
            binding = DataBindingUtil.inflate(inflater,R.layout.fragment_apod,container,false)
            return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val model = ViewModelProvider(this).get(APODViewModel::class.java)
        model.getAPOD()
        activity?.findViewById<ProgressBar>(R.id.progress_bar)?.visibility = View.VISIBLE
        model.APODData.observe(viewLifecycleOwner,{
            binding.textTitle.setText(it.title)
            activity?.findViewById<ProgressBar>(R.id.progress_bar)?.visibility = View.GONE
            Log.e(TAG,"${it.title}")
        })
    }
}