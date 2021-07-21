package com.example.nasa.UI

import android.content.Context
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.text.TextUtils.replace
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.appcompat.graphics.drawable.AnimatedStateListDrawableCompat.create
import androidx.core.net.toUri
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.nasa.Adapter.ListAdapter
import com.example.nasa.Data.APOD
import com.example.nasa.R
import com.example.nasa.databinding.FragmentApodBinding
import com.google.android.material.badge.BadgeDrawable.create
import com.google.android.material.transition.MaterialSharedAxis

private const val TAG = "ApodFragment"
class ApodFragment : Fragment(), ListAdapter.onItemTouchListener {
    lateinit var mApod : List<APOD>
    lateinit private var binding: FragmentApodBinding
    private val mAdapter by lazy{
        ListAdapter(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        exitTransition = MaterialSharedAxis(MaterialSharedAxis.X,true)
    }
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
        binding.recyclerView?.apply{
            adapter = mAdapter
            layoutManager = GridLayoutManager(requireContext(),2)
        }
        activity?.findViewById<ProgressBar>(R.id.progress_bar)?.visibility = View.VISIBLE
        model.APODData.observe(viewLifecycleOwner, {
            mApod = it
            mAdapter.submitList(it)
            activity?.findViewById<ProgressBar>(R.id.progress_bar)?.visibility = View.GONE
        })
        }

    override fun interceptItem(position:Int) {
        val bundle = Bundle()
        bundle.putInt("456",position)
        parentFragmentManager.commit{
            replace(R.id.container,PhotoViewFragment::class.java,bundle)
            addToBackStack(null)
        }
    }
}


