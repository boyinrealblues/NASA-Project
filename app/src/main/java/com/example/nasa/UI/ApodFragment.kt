package com.example.nasa.UI

import android.app.DatePickerDialog
import android.app.Dialog
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
import android.widget.DatePicker
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.graphics.drawable.AnimatedStateListDrawableCompat.create
import androidx.core.net.toUri
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
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
import java.text.DateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

private const val TAG = "ApodFragment"
class ApodFragment : Fragment(), ListAdapter.onItemTouchListener ,DatePickerDialog.OnDateSetListener{
    lateinit var mApod : List<APOD>
    lateinit private var binding: FragmentApodBinding
    lateinit private var model : APODViewModel
    private var diff = 10
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
        model = ViewModelProvider(this).get(APODViewModel::class.java)
        model.target.observe(viewLifecycleOwner,{
            model.getAPOD()
        })
        binding.recyclerView?.apply{
            adapter = mAdapter
            layoutManager = GridLayoutManager(requireContext(),2)
        }
        binding.fab.setOnClickListener{
            DatePickerDialog(this).show(parentFragmentManager,"dialog")
           }
        activity?.findViewById<ProgressBar>(R.id.progress_bar)?.visibility = View.VISIBLE
        model.APODData.observe(viewLifecycleOwner, {
            mApod = it
            parentFragmentManager.fragmentFactory = ApodFragmentFactory(mApod)
            mAdapter.submitList(mApod)
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

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        Log.e(TAG,"$dayOfMonth/$month/$year")
        val s = model.getFormattedDate(dayOfMonth,month+1,year)
        val localTime = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE)
        val year2 = localTime.subSequence(0,localTime.indexOf("-")).toString().toInt()
        val month2 = localTime.subSequence(localTime.indexOf("-")+1,localTime.lastIndexOf("-")).toString().toInt()
        val date2 = localTime.subSequence(localTime.lastIndexOf("-")+1,localTime.length).toString().toInt()
        val days2 = model.getDays(date2,month2,year2)
        val days1 = model.getDays(dayOfMonth,month+1,year)
        model.targetChange((days2-days1).toInt())
        Log.e(TAG,(days2-days1).toString())
        Toast.makeText(activity, s, Toast.LENGTH_SHORT).show()
    }
}


