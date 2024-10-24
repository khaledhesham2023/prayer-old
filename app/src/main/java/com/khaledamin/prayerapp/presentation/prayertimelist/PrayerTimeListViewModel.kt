package com.khaledamin.prayerapp.presentation.prayertimelist

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khaledamin.prayerapp.domain.model.Day
import com.khaledamin.prayerapp.domain.usecases.GetPrayerTimesUseCase
import com.khaledamin.prayerapp.utils.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PrayerTimeListViewModel @Inject constructor(
    private val getPrayerTimesUseCase: GetPrayerTimesUseCase,
//    private val getDayUseCase: GetDayUsecase,
//    @ApplicationContext private val context: Context,
) : ViewModel() {

    private val _getPrayerTimeListLiveData = MutableLiveData<State<ArrayList<Day>>>()
    val getPrayerTimeListLiveData: LiveData<State<ArrayList<Day>>>
        get() = _getPrayerTimeListLiveData

    private val _showProgress = MutableLiveData<Boolean>()
    val showProgress: LiveData<Boolean>
        get() = _showProgress

    private val _toast = MutableLiveData<String>()
    val toast: LiveData<String>
        get() = _toast


    private val _updateDayLiveData = MutableLiveData<Int>()
    val updateDayLiveData:LiveData<Int>
        get() = _updateDayLiveData

    fun getPrayerTimes(
        day: Int,
        year: Int,
        month: Int,
        latitude: Double,
        longitude: Double,
    ) = viewModelScope.launch(provideCoroutineExceptionHandler(_getPrayerTimeListLiveData)) {
        _getPrayerTimeListLiveData.value = State.Loading()
        try {
            val response = getPrayerTimesUseCase.invoke(day, year, month, latitude, longitude)
            Log.i("TAGGG", "Day is ${response[0].date}, Year is ${response[0].year}, Month is ${response[0].month}")
            _getPrayerTimeListLiveData.value = State.Success(data = response)
        } finally {
            _showProgress.value = false
        }
    }

//    fun getDay(day: String) = viewModelScope.launch {
//        getDayUseCase.invoke(day).collectLatest { state ->
//            when(state){
//                is State.Loading -> {
//                    _showProgress.value = true
//                }
//                is State.Success -> {
//                    _getDayLiveData.value = state.data!!
//                    _showProgress.value = false
//                }
//                is State.Error -> {
//                    _toast.value = state.message!!
//                    _showProgress.value = false
//                }
//            }
//        }
//    }
    private fun provideCoroutineExceptionHandler(liveData: MutableLiveData<State<ArrayList<Day>>>): CoroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
        _showProgress.value = false
        liveData.value = State.Error(message = throwable.message)
    }

    fun updateDay(day:Int){
        _updateDayLiveData.value = day
    }
}