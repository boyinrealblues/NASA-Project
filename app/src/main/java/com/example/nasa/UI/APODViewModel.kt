package com.example.nasa.UI

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nasa.Data.APOD
import com.example.nasa.Data.APODServiceInstance
import com.example.nasa.Utils.Utils
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

private const val TAG = "APODViewModel"
class APODViewModel : ViewModel() {

    private val apodApi = APODServiceInstance.APODApi

    private var _APODData : MutableLiveData<List<APOD>> = MutableLiveData()
    val APODData : LiveData<List<APOD>>
    get() = _APODData

     fun getAPOD() {
         //The current date in local time
         val localTime = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE)
         //date 10 days before the current date
         val beforeDate = getDateBeforeLocal(localTime)
         Log.e(TAG,beforeDate)
         viewModelScope.launch {
             _APODData.value = apodApi.getAPODList(Utils.apiKey,beforeDate).filter { it.media_type.equals("image") }
         }
     }

    //returns the date 10 days before the current one by calling the getDateFromMonth and getFormattedDate
    private fun getDateBeforeLocal(localTime: String) : String {
          var year = localTime.subSequence(0,localTime.indexOf("-")).toString().toInt()
            var month = localTime.subSequence(localTime.indexOf("-")+1,localTime.lastIndexOf("-")).toString().toInt()
                var date = localTime.subSequence(localTime.lastIndexOf("-")+1,localTime.length).toString().toInt()
                    if(date>10){
                        date = date-10
                    }else{
                        if(month==1){
                            month = 12
                            date = getDaysFromMonth(month,year)+date-10
                            year--
                        }else{
                            date = getDaysFromMonth(month,year)+date-10
                            month--
                        }
                    }
                    val Format:String = getFormattedDate(date,month,year)
            return Format
    }

    //return the formatted date
    private fun getFormattedDate(date: Int, month: Int, year: Int): String {
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
    private fun getDaysFromMonth(month: Int,year : Int): Int {
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


}