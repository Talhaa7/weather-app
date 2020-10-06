package com.example.myapplication.ui.details_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.example.myapplication.R
import com.example.myapplication.utils.toCelsius
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.details_fragment.*
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalStdlibApi
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class DetailsFragment : Fragment() {

    private val viewModel by viewModels<DetailsViewModel>()
    private val args: DetailsFragmentArgs by navArgs()
    private lateinit var adapter: DetailsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.details_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = DetailsAdapter()
        recyclerView.adapter = adapter

        viewModel.isLoading.observe(viewLifecycleOwner, Observer { isLoading ->
            progressBar2.isVisible = isLoading
        })

        viewModel.todayWeather.observe(viewLifecycleOwner, Observer { todayWeather ->
            degree.text = todayWeather.theTemp?.toCelsius()
            state.text = todayWeather.weatherStateName
            minMaxTemp.text =
                "${todayWeather.maxTemp?.toCelsius()} / ${todayWeather.minTemp?.toCelsius()}"
        })

        viewModel.weeklyWeather.observe(viewLifecycleOwner, Observer { weeklyWeather ->
            adapter.data = weeklyWeather
        })

        viewModel.errorString.observe(viewLifecycleOwner, Observer { errorString ->
            Toast.makeText(
                requireContext(),
                errorString,
                Toast.LENGTH_LONG
            ).show()
        })

        viewModel.getDetails(woeid = args.nearLocation.woeid)

    }


}