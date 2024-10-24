package com.khaledamin.prayerapp.presentation.qibladirection

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khaledamin.prayerapp.domain.model.Qibla
import com.khaledamin.prayerapp.domain.usecases.GetQiblaDirectionUseCase
import com.khaledamin.prayerapp.utils.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QiblaDirectionViewModel @Inject constructor(
    private val getQiblaDirectionUseCase: GetQiblaDirectionUseCase,
) : ViewModel() {

    private val _getQiblaDirectionLiveData = MutableLiveData<Qibla>()
    val getQiblaDirectionLiveData: LiveData<Qibla>
        get() = _getQiblaDirectionLiveData

    private val _showProgress = MutableLiveData<Boolean>()
    val showProgress: LiveData<Boolean>
        get() = _showProgress

    private val _showToast = MutableLiveData<String>()
    val showToast: LiveData<String>
        get() = _showToast

    fun getQiblaDirection(latitude: Double, longitude: Double) =
        viewModelScope.launch {
            getQiblaDirectionUseCase.invoke(latitude, longitude)
                .collectLatest { state ->
                    when (state) {
                        is State.Loading -> {
                            _showProgress.value = true
                        }

                        is State.Success -> {
                            _getQiblaDirectionLiveData.value = state.data!!
                            _showProgress.value = false
                        }

                        is State.Error -> {
                            _showProgress.value = false
                            _showToast.value = state.message!!
                        }
                    }
                }
        }
}