package com.example.pureweather.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.pureweather.databinding.FragmentSearchBinding
import com.example.pureweather.App
import com.example.pureweather.R
import com.example.pureweather.data.models.City
import com.example.pureweather.ui.main.MainViewModel
import com.example.pureweather.ui.main.MainViewModelFactory
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*
import javax.inject.Inject

class SearchFragment : Fragment() {

    @Inject
    lateinit var factory: MainViewModelFactory.Factory
    private val viewModel: MainViewModel by activityViewModels {
        factory.create()
    }
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        injectDependencies()

        val inputStream = context?.assets?.open("cities.json")
        val size = inputStream?.available()
        val bytes = size?.let { ByteArray(it) }

        if (inputStream != null) {
            inputStream.read(bytes)
            inputStream.close()
        }
        val list = bytes?.toString(Charsets.UTF_8)
        val cities = Gson().fromJson<List<City>>(list, object  : TypeToken<List<City>>() {}.type)
        val listOfCities = cities.map { it.name.lowercase(Locale.ROOT) }

        val adapter = ArrayAdapter<String>(requireContext(), android.R.layout.simple_dropdown_item_1line, listOfCities)

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        with(binding) {
            atvSearch.setAdapter(adapter)
            atvSearch.showDropDown()
            atvSearch.setOnItemClickListener { parent, view, position, id ->
                val cityPosition = parent.getItemAtPosition(position)
                viewModel.onCitySelected(listOfCities[listOfCities.indexOf(cityPosition)])
            }
            tbSearch.setNavigationOnClickListener {
                findNavController().popBackStack()
            }
        }

        viewModel.selectingCity.observe(viewLifecycleOwner) {
            viewModel.onCityExtracted(it)
            val action = SearchFragmentDirections.actionSearchFragmentToMainFragment(false)
            findNavController().navigate(action)
        }
    }

    private fun injectDependencies() {
        App.appComponent.searchComponentBuilder().bindInflater(layoutInflater).build().inject(this)
    }
}