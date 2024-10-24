package com.khaledamin.prayerapp.presentation.prayertimelist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khaledamin.prayerapp.domain.model.Day
import com.khaledamin.prayerapp.domain.usecases.GetWeekPrayersUseCase
import com.khaledamin.prayerapp.utils.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PrayersViewModel @Inject constructor(
    private val weekPrayersUseCase: GetWeekPrayersUseCase
) : ViewModel() {
    private val _getPrayerDaysLiveData = MutableLiveData<State<ArrayList<Day>>>()
    val getPrayerDaysLiveData: LiveData<State<ArrayList<Day>>>
        get() = _getPrayerDaysLiveData

    fun getPrayerDays(year:Int, month:Int, latitude: Double, longitude: Double) = viewModelScope.launch {
        _getPrayerDaysLiveData.postValue(State.Loading())
        try {
            val response = weekPrayersUseCase.getWeeklyPrayers(year, month, latitude, longitude)
            _getPrayerDaysLiveData.postValue(State.Success(data = response))
        } catch (e: Exception){
            _getPrayerDaysLiveData.postValue(State.Error(message = e.message))
        }

    }
}