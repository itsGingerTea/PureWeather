package com.example.pureweather.ui.day

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.pureweather.R
import com.example.pureweather.data.models.Day
import com.example.pureweather.databinding.FragmentDayBinding
import com.example.pureweather.App
import com.example.pureweather.ui.main.MainViewModel
import com.example.pureweather.ui.main.MainViewModelFactory
import com.example.pureweather.utils.Error
import com.example.pureweather.utils.Loading
import com.example.pureweather.utils.Success
import com.squareup.picasso.Picasso
import javax.inject.Inject

class DayFragment : Fragment() {

    @Inject
    lateinit var factory: MainViewModelFactory.Factory

    private val viewModel: MainViewModel by activityViewModels {
        factory.create()
    }
    private var _binding: FragmentDayBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDayBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        injectDependencies()

        viewModel.dayState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is Success<*> -> showData(state.data as Day?)
                is Error -> showError()
                Loading -> showLoading()
                else -> {}
            }
        }
    }

    private fun injectDependencies() {
        App.appComponent.dayComponentBuilder().bindInflater(layoutInflater).build().inject(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun showLoading() {
        with(binding) {
            cvDayCont.isVisible = false
        }
    }

    private fun showError() {
        with(binding) {
            cvDayCont.isVisible = false
        }
    }

    private fun showData(day: Day?) {
        with(binding) {
            cvDayCont.isVisible = true
            Picasso.get()
                .load(root.context.getString(R.string.load_image, day?.data?.get(0)?.weather?.icon))
                .into(ivDayMain)
            tvRhValue.text = getString(R.string.rh_value, day?.data?.get(0)?.rh)
            tvUvValue.text = getString(R.string.uv_value, day?.data?.get(0)?.uv)
            tvCloudsValue.text = getString(R.string.clouds_value, day?.data?.get(0)?.clouds)
            tvDayTemp.text = getString(R.string.temp, day?.data?.get(0)?.temp)
            tvWindSpdValue.text = getString(R.string.wind_spd_value, day?.data?.get(0)?.windSpd)
            tvWindDirValue.text = day?.data?.get(0)?.windCdir
            tvPresValue.text = getString(R.string.pres_value, day?.data?.get(0)?.pres)
        }
    }

}