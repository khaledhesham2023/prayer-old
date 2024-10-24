package com.khaledamin.prayerapp.presentation.qibladirection

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.khaledamin.prayerapp.R
import com.khaledamin.prayerapp.databinding.FragmentQiblaBinding
import com.khaledamin.prayerapp.presentation.abstracts.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.sin

@AndroidEntryPoint
class QiblaFragment : BaseFragment<FragmentQiblaBinding, QiblaViewModel>(), OnMapReadyCallback {

    private var latitude = 0.0
    private var longitude = 0.0

    //    private val callback = OnMapReadyCallback { googleMap ->
//        /**
//         * Manipulates the map once available.
//         * This callback is triggered when the map is ready to be used.
//         * This is where we can add markers or lines, add listeners or move the camera.
//         * In this case, we just add a marker near Sydney, Australia.
//         * If Google Play services is not installed on the device, the user will be prompted to
//         * install it inside the SupportMapFragment. This method will only be triggered once the
//         * user has installed Google Play services and returned to the app.
//         */
//        val sydney = LatLng(-34.0, 151.0)
//        googleMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
//        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
//    }
    override val layout: Int
        get() = R.layout.fragment_qibla
    override val viewModelClass: Class<QiblaViewModel>
        get() = QiblaViewModel::class.java

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        latitude = QiblaFragmentArgs.fromBundle(requireArguments()).latitude.toDouble()
        longitude = QiblaFragmentArgs.fromBundle(requireArguments()).longitude.toDouble()
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
        viewModel.getQiblaDirection(
            latitude = latitude,
            longitude = longitude
        )
    }

    override fun setupObservers() {
        viewModel.getQiblaDirectionLiveData.observe(viewLifecycleOwner) {
            val direction = it.direction
            val rotateAnimator =
                ObjectAnimator.ofFloat(viewBinding.qiblaArrow, "rotation", 0f, direction.toFloat())
            rotateAnimator.duration = 1000L
            rotateAnimator.start()
//            positionKaabaMarker(direction)
        }
        viewModel.showToast.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }
        viewModel.showProgress.observe(viewLifecycleOwner) {
            if (it) {
                viewBinding.progress.visibility = View.VISIBLE
            } else {
                viewBinding.progress.visibility = View.GONE
            }
        }
    }

    override fun onMapReady(p0: GoogleMap) {
        val sydney = LatLng(latitude, longitude)
        p0.addMarker(MarkerOptions().position(sydney).title(getString(R.string.current_location)))
        p0.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 15f))
    }

//    private fun positionKaabaMarker(bearing: Double) {
//
//        viewBinding.icKaaba.visibility = View.VISIBLE
//        val normalizedBearing = abs((bearing % 360) - 180) // Normalize bearing between 0-180 degrees
//        val maxDistance = (viewBinding.mapLayout.width / 2).toFloat() // Max distance based on screen size
//        val distance = (maxDistance * (1 - (normalizedBearing / 180))).toFloat() // Dynamic distance scaling
//
//        // Make sure layout has been drawn, so width and height are available
//        viewBinding.icKaaba.post {
//            // Assume the current location marker is in the center of the screen
//            val centerX = (viewBinding.mapLayout.width / 2).toFloat()
//            val centerY = (viewBinding.mapLayout.height / 2).toFloat()
//
//            // Convert the bearing to radians
//            val bearingRad = Math.toRadians(bearing)
//
//            // Calculate the new position for the Kaaba marker
//            val xOffset = (distance * cos(bearingRad)).toFloat()
//            val yOffset = (distance * sin(bearingRad)).toFloat()
//
//            // Set the new position for the Kaaba marker
//            viewBinding.icKaaba.translationX = centerX + xOffset - (viewBinding.icKaaba.width / 2)
//            viewBinding.icKaaba.translationY = centerY - yOffset - (viewBinding.icKaaba.height / 2)
//        }
//    }
}