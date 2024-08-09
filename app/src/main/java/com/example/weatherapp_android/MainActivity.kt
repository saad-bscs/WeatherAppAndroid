package com.example.weatherapp_android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import coil.load
import com.example.weatherapp_android.databinding.ActivityMainBinding
import com.example.weatherapp_android.models.WeatherModel
import com.example.weatherapp_android.utils.Constant
import com.example.weatherapp_android.utils.NetworkResult
import com.example.weatherapp_android.utils.WeatherGridAdapter
import com.example.weatherapp_android.viewModels.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val weatherViewModel by viewModels<WeatherViewModel>()

    private val weatherArrayList = ArrayList<WeatherModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initializeLayout()
    }

    private fun initializeLayout() {

        getWeatherObserver()

        binding.imgSearch.setOnClickListener {
            if (binding.etLocation.text!!.isNotEmpty()) {
                hideKeyboard()
                Timber.e(binding.etLocation.text.toString())
                weatherViewModel.getWeatherData(Constant.apiKey, binding.etLocation.text.toString())
            } else {
                Toast.makeText(this, "Enter Location", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getWeatherObserver() {
        weatherViewModel.weatherLiveData.observe(this, Observer {
            binding.progressBar.isVisible = false

            when (it) {
                is NetworkResult.Success -> {
                    Timber.e("Weather List Observer")
                    Timber.e(it.data.toString())

                    setLocationData(it.data!!)
                }

                is NetworkResult.Error -> {
                    Timber.e(it.message)
                }

                is NetworkResult.Loading -> {
                    binding.progressBar.isVisible = true
                }

                else -> {
                    Timber.e("else: ")
                }
            }
        })
    }

    private fun setLocationData(weatherData: WeatherModel) {
        binding.cardView.isVisible = true
        binding.txtCity.text = weatherData.location.name
        binding.txtCountry.text = weatherData.location.country
        binding.txtTemperature.text = (weatherData.current.temp_c + "° c")
        binding.imgCloud.load(
            "https:${weatherData.current.condition.icon}".replace(
                "64x64",
                "128x128"
            )
        )
        binding.imgCloud.isVisible = true

        binding.txtWeatherType.text = weatherData.current.condition.text

        weatherArrayList.add(weatherData)
        binding.weatherRView.adapter = WeatherGridAdapter(weatherArrayList)
        binding.weatherRView.layoutManager = GridLayoutManager(this, 2)

    }

    private fun hideKeyboard() {
        val view: View? = this.currentFocus

        if (view != null) {

            val inputMethodManager =
                getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager

            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}