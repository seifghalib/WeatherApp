package com.example.cubicdemoapp.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.cubicdemoapp.R
import com.example.cubicdemoapp.api.OpenWeatherApi
import com.example.cubicdemoapp.databinding.HomeFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.roundToInt

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.home_fragment) {

    private val viewModule by viewModels<HomeViewModel>()

    private var _binding : HomeFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = HomeFragmentBinding.bind(view)

        viewModule.weatherResponseLiveData.observe(viewLifecycleOwner){ response ->

            binding.apply {

                progressBar.visibility = View.GONE
                textViewCity.text = response.name

                response.weather.firstNotNullOf {
                    val description = "${it.main}, ${it.description}"
                    textViewDescription.text = description
                    loadImage(it.icon)
                }
                val temperatureValue = "${response.main.temp.roundToInt()} \u2109"
                textViewTemp.text = temperatureValue
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