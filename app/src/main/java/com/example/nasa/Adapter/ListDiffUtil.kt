package com.example.nasa.Adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.nasa.Data.APOD

class ListDiffUtil(private val oldList:List<APOD>,private val newList:List<APOD>) : DiffUtil.Callback(){
    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return  oldList[oldItemPosition].url == newList[newItemPosition].url
    }
}