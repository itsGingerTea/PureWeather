package com.example.pureweather.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.pureweather.R
import com.example.pureweather.databinding.FragmentFirstBinding
import com.example.pureweather.App

class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        injectDependencies()
        binding.tvChooseACity.setOnClickListener{
            findNavController().navigate(R.id.searchFragment)
        }
        binding.tvFindYourLocation.setOnClickListener{
            val action = FirstFragmentDirections.actionFirstFragmentToMainFragment2(true)
            findNavController().navigate(action)
        }
    }

    private fun injectDependencies() {
        App.appComponent.firstComponentBuilder().bindInflater(layoutInflater).build().inject(this)
    }
}