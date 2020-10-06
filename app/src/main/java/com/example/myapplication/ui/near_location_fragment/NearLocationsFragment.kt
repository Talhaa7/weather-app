package com.example.myapplication.ui.near_location_fragment

import android.Manifest
import android.annotation.SuppressLint
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.domain.NearLocation
import com.example.myapplication.utils.Constants.REQUEST_CODE_LOCATION_PERMISSION
import com.example.myapplication.utils.LocationUtility
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.near_locations_fragment.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions


@ExperimentalCoroutinesApi
@AndroidEntryPoint
class NearLocationsFragment : Fragment(), EasyPermissions.PermissionCallbacks {
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private lateinit var adapter: NearLocationAdapter

    val viewModel by viewModels<NearLocationsViewModel>()

    private fun requestPermisions() {
        if (LocationUtility.hasLocationPermission(requireContext())) {

            return
        }
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            EasyPermissions.requestPermissions(
                this,
                "You need to accept locations permission to use this app.",
                REQUEST_CODE_LOCATION_PERMISSION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        } else {
            EasyPermissions.requestPermissions(
                this,
                "You need to accept locations permission to use this app.",
                REQUEST_CODE_LOCATION_PERMISSION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_BACKGROUND_LOCATION
            )

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.near_locations_fragment, container, false)
    }


    @SuppressLint("MissingPermission")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        adapter = NearLocationAdapter() {
            goToDetails(it)
        }
        nearLocationList.adapter = adapter

        observeFields()

        requestPermisions()

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                val locX = location?.latitude ?: 0.0
                val locY = location?.longitude ?: 0.0
                getNearLocations(locX, locY)
            }.addOnFailureListener {
                Toast.makeText(
                    requireContext(),
                    "Konum alinamadi",
                    Toast.LENGTH_LONG
                ).show()
                progressBar.isVisible = false
            }

    }

    private fun observeFields() {
        viewModel.isLoading.observe(viewLifecycleOwner, Observer { isLoading ->
            progressBar.isVisible = isLoading
        })

        viewModel.nearLocationList.observe(viewLifecycleOwner, Observer { nearLocationList ->
            adapter.data = nearLocationList
        })

        viewModel.errorString.observe(viewLifecycleOwner, Observer { errorString ->
            Toast.makeText(
                requireContext(),
                errorString,
                Toast.LENGTH_LONG
            ).show()
        })
    }

    private fun getNearLocations(locX: Double, locY: Double) {
        viewModel.nearLocations(locX, locY)
    }

    private fun goToDetails(nearLocation: NearLocation) {
        findNavController().navigate(
            NearLocationsFragmentDirections.actionNearLocationsFragmentToDetailsFragment(
                nearLocation,
                nearLocation.title ?: "Details"
            )
        )
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(this).build().show()
        } else {
            requestPermisions()
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {}

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }
}