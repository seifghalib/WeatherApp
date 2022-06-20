package com.example.cubicdemoapp.ui

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.cubicdemoapp.R
import com.example.cubicdemoapp.api.OpenWeatherApi
import com.example.cubicdemoapp.databinding.HomeFragmentBinding
import com.example.cubicdemoapp.utils.ApiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlin.math.roundToInt

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.home_fragment) {

    private val viewModule by viewModels<HomeViewModel>()

    private var _binding: HomeFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = HomeFragmentBinding.bind(view)

        viewModule.getWeather().observe(viewLifecycleOwner) { response ->
            binding.apply {
                when (response) {
                    is ApiState.Failure -> {
                        progressBar.isVisible = false
                        textViewDescription.text = response.msg
                    }
                    is ApiState.Loading -> {
                        progressBar.isVisible = true
                    }
                    is ApiState.Success -> {

                        textViewCity.text = response.data.name

                        val temperatureValue = "${response.data.main.temp.roundToInt()} \u2109"
                        textViewTemp.text = temperatureValue
                        response.data.weather.firstNotNullOf {
                            val description = "${it.main}, ${it.description}"
                            textViewDescription.text = description
                            loadImage(it.icon)
                        }
                        progressBar.isVisible = false
                    }
                }
            }
        }
    }

    private fun loadImage(icon: String) {

        val iconURL = OpenWeatherApi.ICON_URL.format(icon)

        Glide.with(this@HomeFragment)
            .load(iconURL)
            .error(R.drawable.ic_error)
            .into(binding.imageView)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}