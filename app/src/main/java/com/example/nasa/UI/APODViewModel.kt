package com.example.nasa.UI

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nasa.Data.APOD
import com.example.nasa.Data.APODServiceInstance
import com.example.nasa.Utils.Utils
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

private const val TAG = "APODViewModel"
class APODViewModel : ViewModel() {
    private val apodApi = APODServiceInstance.APODApi

    private var _APODData : MutableLiveData<List<APOD>> = MutableLiveData()
    val APODData : LiveData<List<APOD>>
    get() = _APODData

    private var _target : MutableLiveData<Int> = MutableLiveData()
    val target : LiveData<Int>
    get() = _target

    init{
        _target.value =10
    }
     fun getAPOD() {
         //The current date in local time
         val localTime = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE)
            //date 10 days before the current date
         val beforeDate = getDateBeforeLocal(localTime)

         viewModelScope.launch {
             Log.e(TAG,beforeDate)
             _APODData.value = apodApi.getAPODList(Utils.apiKey,beforeDate).filter { it.media_type.equals("image") }

         }
     }

    //returns the date 10 days before the current one by calling the getDateFromMonth and getFormattedDate
    private fun getDateBeforeLocal(localTime: String) : String {
          var year = localTime.subSequence(0,localTime.indexOf("-")).toString().toInt()
            var month = localTime.subSequence(localTime.indexOf("-")+1,localTime.lastIndexOf("-")).toString().toInt()
                var date = localTime.subSequence(localTime.lastIndexOf("-")+1,localTime.length).toString().toInt()
                    if(date>_target.value!!){
                        date = date-_target.value!!
                    }else{
                            while(date<_target.value!!) {
                                date = getDaysFromMonth(month, year) + date
                                if(month==1)
                                {
                                    month=12
                                    year--
                                    continue
                                }
                                month--
                            }
                            date = date-_target.value!!
                            }

                    val Format:String = getFormattedDate(date,month,year)
            return Format
    }

    //return the formatted date
    fun getFormattedDate(date: Int, month: Int, year: Int): String {
        val sb = StringBuilder()
         sb.append(year).append("-")
        if(month<10)
            sb.append("0"+month.toString()).append("-")
        else
            sb.append(month).append("-")
        if(date<10)
            sb.append("0"+date.toString())
        else
            sb.append(date)

        return sb.toString()
    }

    //returns number of days from months
     fun getDaysFromMonth(month: Int,year : Int): Int {
        when(month){
            1,3,5,7,8,10,12 -> return 31
            2-> {
                if(year%400==0||(year%4==0&&year%100!=0))
                    return 29
                else
                    return 28
            }
            else-> return 30
        }
    }

    //returns the total number of days since epoch
    fun getDays(date:Int,month:Int,year:Int):Long{
        var sumY =0
        var sumM =0
        viewModelScope.launch{
            for(i in 1970..year-1){
                if(i%400==0||(i%4==0&&i%100!=0))
                    sumY+=366
                else
                    sumY+=365
            }
        }
        viewModelScope.launch{
            for(i in 1..month-1){
                sumM+=getDaysFromMonth(i,year)
            }
        }
        return (sumM+sumY+date).toLong()-1
    }

    //changed number of days in between today and the last day
    fun targetChange(newTarget : Int){
        _target.value = newTarget
    }
}