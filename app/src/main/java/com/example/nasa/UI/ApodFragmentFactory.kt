package com.example.nasa.UI
//
//import android.util.Log
//import androidx.fragment.app.Fragment
//import androidx.fragment.app.FragmentFactory
//import com.example.nasa.Data.APOD
//import java.lang.ClassCastException
//
//class ApodFragmentFactory(private val ApodList : List<APOD>) : FragmentFactory(){
//    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
//           when(className){
//              PhotoViewFragment::class.java.name -> return PhotoViewFragment(ApodList)
//               else -> return super.instantiate(classLoader, className)
//               }
//    }
//}