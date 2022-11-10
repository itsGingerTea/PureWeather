package com.example.pureweather.ui.week

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pureweather.data.models.Week
import com.example.pureweather.databinding.FragmentWeekBinding
import com.example.pureweather.App
import com.example.pureweather.ui.main.MainViewModel
import com.example.pureweather.ui.main.MainViewModelFactory
import com.example.pureweather.utils.Error
import com.example.pureweather.utils.Loading
import com.example.pureweather.utils.Success
import javax.inject.Inject

class WeekFragment : Fragment() {

    @Inject
    lateinit var factory: MainViewModelFactory.Factory

    private val viewModel: MainViewModel by activityViewModels {
        factory.create()
    }
    private var _binding: FragmentWeekBinding? = null
    private val binding get() = _binding!!
    private val adapter by lazy(LazyThreadSafetyMode.NONE) {
        WeekAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWeekBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        injectDependencies()

        binding.rvWeek.layoutManager = LinearLayoutManager(requireContext())
        binding.rvWeek.adapter = adapter

        viewModel.weekState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is Success<*> -> showData(state.data as Week?)
                is Error -> showError()
                Loading -> showLoading()
                else -> {}
            }
        }
    }

    private fun injectDependencies() {
        App.appComponent.weekComponentBuilder().bindInflater(layoutInflater).build().inject(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun showLoading() {
        with(binding) {
            rvWeek.isVisible = false
        }
    }

    private fun showError() {
        with(binding) {
            rvWeek.isVisible = false
        }
    }

    private fun showData(week: Week?) {
        with(binding) {
            rvWeek.isVisible = true
            adapter.submitList(week?.data)
        }
    }
}