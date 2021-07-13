package com.example.nasa.UI

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nasa.Data.APOD
import com.example.nasa.Data.APODServiceInstance
import com.example.nasa.Utils
import kotlinx.coroutines.launch

class APODViewModel : ViewModel() {

    private val apodApi = APODServiceInstance.APODApi

    private var _APODData : MutableLiveData<APOD> = MutableLiveData()
    val APODData : LiveData<APOD>
    get() = _APODData

     fun getAPOD() {
         viewModelScope.launch {
             _APODData.value = apodApi.getAPOD(Utils.apiKey)
         }
     }

}