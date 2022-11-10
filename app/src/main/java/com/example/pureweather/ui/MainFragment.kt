package com.example.pureweather.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.view.MenuProvider
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.pureweather.R
import com.example.pureweather.databinding.FragmentMainBinding
import com.example.pureweather.App
import com.example.pureweather.data.models.Day
import com.example.pureweather.ui.main.MainActivity
import com.example.pureweather.ui.main.MainViewModel
import com.example.pureweather.ui.main.MainViewModelFactory
import com.example.pureweather.utils.Error
import com.example.pureweather.utils.Loading
import com.example.pureweather.utils.Success
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.CancellationToken
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.tasks.OnTokenCanceledListener
import com.google.android.material.tabs.TabLayoutMediator
import java.util.*
import javax.inject.Inject

class MainFragment : Fragment() {

    @Inject
    lateinit var factory: MainViewModelFactory.Factory

    private val viewModel: MainViewModel by activityViewModels {
        factory.create()
    }
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: TabsAdapter
    private val args : MainFragmentArgs by navArgs()

    val fusedLocationClient: FusedLocationProviderClient by lazy {
        LocationServices.getFusedLocationProviderClient(requireContext())
    }

    private var flag : Boolean = true

    @SuppressLint("MissingPermission")
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            if (ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                fusedLocationClient.lastLocation
                    .addOnSuccessListener { location ->
                        viewModel.onLocationExtracted(location)
                        setCity(location)
                    }
                    .addOnFailureListener {
                        loadPermissionRequest()
                    }
            }
        } else {
            showLocationError()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as MainActivity).setSupportActionBar(binding.tbMain)
        setHasOptionsMenu(true)
        adapter = TabsAdapter(this)

        val menuHost = requireActivity()
        (requireActivity() as MainActivity).setSupportActionBar(binding.tbMain)
        flag = args.flag
        injectDependencies()
        if (flag) {
            getLastKnownLocation()
        }

/*        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menu.clear()
                menuInflater.inflate(R.menu.menu_main, menu)
                super.onPrepareMenu(menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when(menuItem.itemId) {
                    R.id.search_city -> {
                        val action = MainFragmentDirections.actionMainFragmentToSearchFragment()
                        findNavController().navigate(action)
                        true
                    }
                    else -> false
                }
            }
        })*/

        with(binding) {
            vpMain.adapter = adapter

            tvDayTryAgain.setOnClickListener {
                getLastKnownLocation()
            }

            tbMain.setNavigationOnClickListener {
                val action = MainFragmentDirections.actionMainFragmentToFirstFragment()
                findNavController().navigate(action)
            }
        }

        TabLayoutMediator(binding.tlMain, binding.vpMain) { tab, position ->
            if (position == 0) {
                tab.text = getString(R.string.tab_today)
            } else {
                tab.text = getString(R.string.tab_week)
            }
        }.attach()

        viewModel.dayState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is Success<*> -> showData(state.data as Day?)
                is Error -> showError()
                Loading -> showLoading()
                else -> {}
            }
        }

        viewModel.selectingCity.observe(viewLifecycleOwner) {
            binding.tbMain.title = it[0].toString().uppercase(Locale.ROOT).plus(it.subSequence(1, it.length).toString())

        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_main, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.search_city -> {
                val action = MainFragmentDirections.actionMainFragmentToSearchFragment()
                findNavController().navigate(action)
                true
            }
            else -> false
        }
    }

    private fun showLoading() {
        binding.tbMain.isVisible = false
        binding.vpMain.isVisible = false
        binding.tlMain.isVisible = false
        binding.pbDay.isVisible = true
        binding.tvDayTryAgain.isVisible = false
        binding.tvMainErrorRequest.isVisible = false
        binding.tvDayErrorInternetConnection.isVisible = false
    }

    private fun showError() {
        binding.tbMain.isVisible = false
        binding.vpMain.isVisible = false
        binding.tlMain.isVisible = false
        binding.pbDay.isVisible = false
        binding.tvDayTryAgain.isVisible = true
        binding.tvMainErrorRequest.isVisible = false
        binding.tvDayErrorInternetConnection.isVisible = true
    }

    private fun injectDependencies() {
        App.appComponent.tabsComponentBuilder().bindInflater(layoutInflater).build().inject(this)
    }

    private fun showLocationError() {
        with(binding) {
            tlMain.isVisible = !isVisible
            vpMain.isVisible = !isVisible
            pbDay.isVisible = false
            tvMainErrorRequest.isVisible = !isVisible
        }
    }

    @SuppressLint("MissingPermission")
    private fun getLastKnownLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                loadPermissionRequest()
            } else {
                Toast.makeText(context, "Turn on location", Toast.LENGTH_LONG).show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        } else {
            showLoading()

            fusedLocationClient.getCurrentLocation(LocationRequest.PRIORITY_HIGH_ACCURACY, object : CancellationToken() {
                override fun onCanceledRequested(p0: OnTokenCanceledListener) = CancellationTokenSource().token

                override fun isCancellationRequested() = false
            })
                .addOnSuccessListener { location: Location? ->
                    if (location == null)
                        Toast.makeText(requireContext(), "Cannot get location.", Toast.LENGTH_SHORT).show()
                    else {
                        viewModel.onLocationExtracted(location)
                    }
                }
        }
    }

    private fun showData(day: Day?) {
        binding.tbMain.title = day?.data?.get(0)?.cityName
        binding.vpMain.isVisible = true
        binding.tlMain.isVisible = true
        binding.tbMain.isVisible = true
        binding.pbDay.isVisible = false
        binding.tvDayTryAgain.isVisible = false
        binding.tvMainErrorRequest.isVisible = false
        binding.tvDayErrorInternetConnection.isVisible = false
    }

    private fun setCity(location: Location) {
        val geoCoder = Geocoder(requireContext()).getFromLocation(location.latitude, location.longitude, 1)
        binding.tbMain.title = geoCoder.toString()
    }

    private fun isLocationEnabled(): Boolean {
        val locationManager: LocationManager = context?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    private fun loadPermissionRequest() {
        requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 101)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        Log.i("code", requestCode.toString())
        when (requestCode) {
            101 -> {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                    (ActivityCompat.checkSelfPermission(
                        activity as MainActivity,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) == PackageManager.PERMISSION_GRANTED)
                ) {
                    fusedLocationClient.lastLocation
                        .addOnSuccessListener { location ->
                            if (location != null) {
                                viewModel.onLocationExtracted(location)
                                Log.i("location", location.latitude.toString())
                            } else {
                                loadPermissionRequest()
                            }
                        }
                } else {
                    showLocationError()
                }
            }
        }
    }

    private fun checkPermissions(): Boolean {
        return ActivityCompat.checkSelfPermission(
            activity as MainActivity,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
            activity as MainActivity,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED
    }
}