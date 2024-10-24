package com.khaledamin.prayerapp.presentation.prayertimelist

import android.Manifest
import android.content.pm.PackageManager
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.khaledamin.prayerapp.R
import com.khaledamin.prayerapp.databinding.FragmentPrayerTimeListBinding
import com.khaledamin.prayerapp.domain.model.Day
import com.khaledamin.prayerapp.presentation.abstracts.BaseFragment
import com.khaledamin.prayerapp.utils.Constants
import com.khaledamin.prayerapp.utils.State
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar
import java.util.Locale

@AndroidEntryPoint
class PrayerTimeListFragment :
    BaseFragment<FragmentPrayerTimeListBinding, PrayerTimeListViewModel>() {


    override val layout: Int
        get() = R.layout.fragment_prayer_time_list
    override val viewModelClass: Class<PrayerTimeListViewModel>
        get() = PrayerTimeListViewModel::class.java

    //    private var currentDay = 0
    private var index = 0

    //    private var monthDays = 0
    private lateinit var weekDays: ArrayList<Day>
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private var latitude: Double = 0.0
    private var longitude: Double = 0.0
    private lateinit var calendar: Calendar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        weekDays = ArrayList()
        calendar = Calendar.getInstance()
        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireContext())
        getLastLocation()
    }

    private fun getLastLocation() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationProviderClient.lastLocation.addOnSuccessListener { location ->
                if (location != null) {
                    latitude = location.latitude
                    longitude = location.longitude
                    Log.i("TAGGG", "Day is ${calendar.get(Calendar.DAY_OF_MONTH)}, Year is ${calendar.get(Calendar.YEAR)}, Month is ${calendar.get(Calendar.MONTH) + 1}")
                    viewModel.getPrayerTimes(
                        day = calendar.get(Calendar.DAY_OF_MONTH),
                        year = calendar.get(Calendar.YEAR),
                        month = calendar.get(Calendar.MONTH) + 1,
                        latitude = latitude,
                        longitude = longitude
                    )
                    viewBinding.address.text = getCityName()
                } else {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.permission_required),
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }
        } else {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                Constants.REQUEST_CODE
            )
        }
    }

    private fun getCityName(): String {
        val geocoder = Geocoder(requireContext(), Locale.getDefault())
        val addresses = geocoder.getFromLocation(latitude, longitude, 3)
        val city = "${addresses?.get(0)?.subAdminArea}, ${addresses?.get(0)?.countryCode}, ${
            addresses?.get(0)?.thoroughfare
        }"
        return city
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        if (requestCode == Constants.REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation()
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
    }

    private fun setupListeners() {
        viewBinding.showQiblaBtn.setOnClickListener {
            findNavController().navigate(
//                PrayerTimeListFragmentDirections.actionPrayerTimeListFragmentToQiblaDirectionFragment(
//                    latitude = latitude.toString(),
//                    longitude = longitude.toString()
//                )
                PrayerTimeListFragmentDirections.actionPrayerTimeListFragmentToQiblaFragment(
                    latitude = 21.433777317549556.toString(),
                    longitude = 39.83618866853325.toString()
                )
            )
        }
        viewBinding.forward.setOnClickListener {
            index++
            viewModel.updateDay(index)
//            viewModel.getDay(currentDay.toString())
        }
        viewBinding.backward.setOnClickListener {
            index--
            viewModel.updateDay(index)
//            viewModel.getDay(currentDay.toString())
        }
    }

    override fun setupObservers() {
//        viewModel.getPrayerTimeListLiveData.observe(viewLifecycleOwner) {
//            monthDays = it.size
//            viewModel.getDay(Calendar.getInstance().get(Calendar.DAY_OF_MONTH).toString())
//        }
        viewModel.showProgress.observe(viewLifecycleOwner) {
            if (it) {
                viewBinding.progress.visibility = View.VISIBLE
            } else {
                viewBinding.progress.visibility = View.GONE
            }
        }
        viewModel.toast.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            Log.i("TAGGG",it)
        }
//        viewModel.getDayLiveData.observe(viewLifecycleOwner) {
//            viewBinding.day = it
//            currentDay = it.day
//            configureViews()
//        }
        viewModel.updateDayLiveData.observe(viewLifecycleOwner) {
            viewBinding.day = weekDays[index]
        }
        viewModel.getPrayerTimeListLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is State.Loading -> {
                    viewBinding.progress.visibility = View.VISIBLE
                }

                is State.Success -> {
                    weekDays.addAll(it.data!!)
//                    Log.i("TAGGG",weekDays[0].date)
                    viewBinding.progress.visibility = View.GONE
                    viewModel.updateDay(index)
                    configureViews()
                }

                is State.Error -> {
                    viewBinding.progress.visibility = View.GONE
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun configureViews() {
        when (index) {
            0 -> {
                viewBinding.backward.apply {
                    visibility = View.INVISIBLE
                    isEnabled = false
                }
                viewBinding.forward.apply {
                    visibility = View.VISIBLE
                    isEnabled = true
                }
            }

            weekDays.size -> {
                viewBinding.forward.apply {
                    visibility = View.INVISIBLE
                    isEnabled = false
                }
                viewBinding.backward.apply {
                    visibility = View.VISIBLE
                    isEnabled = true
                }
            }

            else -> {
                viewBinding.forward.apply {
                    visibility = View.VISIBLE
                    isEnabled = true
                }
                viewBinding.backward.apply {
                    visibility = View.VISIBLE
                    isEnabled = true
                }
            }
        }
    }
}