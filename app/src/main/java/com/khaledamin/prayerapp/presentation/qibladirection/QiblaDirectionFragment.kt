package com.khaledamin.prayerapp.presentation.qibladirection

import android.Manifest
import android.animation.ObjectAnimator
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.khaledamin.prayerapp.R
import com.khaledamin.prayerapp.databinding.FragmentQiblaDirectionBinding
import com.khaledamin.prayerapp.presentation.abstracts.BaseFragment
import com.khaledamin.prayerapp.utils.Constants
import dagger.hilt.android.AndroidEntryPoint
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.overlay.Marker
import kotlin.math.cos
import kotlin.math.sin

@AndroidEntryPoint
class QiblaDirectionFragment :
    BaseFragment<FragmentQiblaDirectionBinding, QiblaDirectionViewModel>() {
    override val layout: Int
        get() = R.layout.fragment_qibla_direction
    override val viewModelClass: Class<QiblaDirectionViewModel>
        get() = QiblaDirectionViewModel::class.java

    private var latitude: Double = 0.0
    private var longitude: Double = 0.0


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        latitude = QiblaDirectionFragmentArgs.fromBundle(requireArguments()).latitude.toDouble()
        longitude = QiblaDirectionFragmentArgs.fromBundle(requireArguments()).longitude.toDouble()

        if (isLocationPermissionGranted()) {
            setupMap()
            viewModel.getQiblaDirection(latitude, longitude)
        } else {
            requestPermission()
        }
    }

    private fun setupMap() {
        val startPoint = GeoPoint(latitude, longitude)

        val marker = Marker(viewBinding.mapView).apply {
            position = startPoint
            setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
            title = getString(R.string.current_location)
        }

        viewBinding.mapView.apply {
            setTileSource(TileSourceFactory.MAPNIK)
            setMultiTouchControls(true)
            controller.setCenter(startPoint)
            controller.setZoom(Constants.ZOOM_LEVEL)
            overlays.add(marker)
        }
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ), Constants.REQUEST_CODE
        )
    }

    override fun onPause() {
        super.onPause()
        viewBinding.mapView.onPause()
    }

    override fun onResume() {
        super.onResume()
        viewBinding.mapView.onResume()
    }

    override fun setupObservers() {
        viewModel.getQiblaDirectionLiveData.observe(viewLifecycleOwner) {
            val direction = it.direction
            val rotateAnimator =
                ObjectAnimator.ofFloat(viewBinding.qiblaArrow, "rotation", 0f, direction.toFloat())
            rotateAnimator.duration = 1000L
            rotateAnimator.start()

            positionKaaba(direction)
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

    private fun positionKaaba(direction: Double) {
        viewBinding.icKaaba.visibility = View.VISIBLE

        // Get the center coordinates of the arrow ImageView
        val arrowWidth = viewBinding.qiblaArrow.width
        val arrowHeight = viewBinding.qiblaArrow.height
        val arrowCenterX = viewBinding.qiblaArrow.x + arrowWidth / 2
        val arrowCenterY = viewBinding.qiblaArrow.y + arrowHeight / 2

        // Distance from the arrow center to place the Kaaba (can adjust this value)
        val kaabaDistance = 600f  // You can adjust this value for a proper distance

        // Convert the Qibla direction from degrees to radians for calculation
        val radians = Math.toRadians(direction)

        // Calculate the new X and Y coordinates for the Kaaba based on the Qibla direction
        val kaabaX = (arrowCenterX + kaabaDistance * cos(radians)).toFloat()
        val kaabaY = (arrowCenterY + kaabaDistance * sin(radians)).toFloat()

        // Set the new position of the Kaaba ImageView
        viewBinding.icKaaba.x = kaabaX - viewBinding.icKaaba.width / 2
        viewBinding.icKaaba.y = kaabaY - viewBinding.icKaaba.height / 2

//        val arrowCenterX = viewBinding.qiblaArrow.x + viewBinding.qiblaArrow.width / 2
//        val arrowCenterY = viewBinding.qiblaArrow.y + viewBinding.qiblaArrow.height / 2
//
//        val distance = 300f
//
//        val radians = Math.toRadians(direction)
//
//        val kaabaX = (arrowCenterX + distance * cos(radians).toFloat())
//        val kaabaY = (arrowCenterY + distance * sin(radians).toFloat())
//
//        viewBinding.icKaaba.x = kaabaX - viewBinding.icKaaba.width / 2
//        viewBinding.icKaaba.y = kaabaY - viewBinding.icKaaba.height / 2
    }

    private fun isLocationPermissionGranted(): Boolean {
        return if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(), arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ), Constants.REQUEST_CODE
            )
            false
        } else {
            true
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        if (requestCode == Constants.REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                setupMap()
                viewModel.getQiblaDirection(latitude, longitude)

            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewBinding.mapView.onDetach()
    }
}