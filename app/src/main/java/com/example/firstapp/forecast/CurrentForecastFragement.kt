package com.example.firstapp.forecast

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import coil.load
import com.example.firstapp.*
import com.example.firstapp.api.CurrentWeather
import com.google.android.material.floatingactionbutton.FloatingActionButton


class CurrentForecastFragement : Fragment() {


    private lateinit var tempDisplaySettingsManager: TempDisplaySettingsManager

    private val forecastRepository = ForecastRepository()
    private  lateinit var locationRepository: LocationRepository




    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {


        tempDisplaySettingsManager = TempDisplaySettingsManager(requireContext())



     val zipcode = arguments?.getString(KEY_ZIPCODE) ?: ""





        val view = inflater.inflate(R.layout.fragment_current_forecast_fragement, container, false)
        val locationName:TextView = view.findViewById(R.id.locationName)
         val mytemptext:TextView= view.findViewById(R.id.mytemptext)
         val currentImg:ImageView = view.findViewById(R.id.currentImg)













        //create observer which updates UI in response to forecast updates
        val currentWeatherObserver = Observer<CurrentWeather> {weather->
            // update our list adapter
            locationName.text = weather.name
            mytemptext.text = formatTempForDisplay(weather.forecast.temp,tempDisplaySettingsManager.getTempDisplaySetting())
//            val iconId = weather.icon
            currentImg.load(("http://openweathermap.org/img/wn/10n@2x.png"))
            //forecastIcon.load(("http://openweathermap.org/img/wn/${iconId2}@2x.png"))

        }

        forecastRepository.currentWeather.observe(viewLifecycleOwner,currentWeatherObserver)











        val locationEntryButton :FloatingActionButton = view.findViewById(R.id.floatingActionButton2)
            locationEntryButton.setOnClickListener(){
                showLocationEntry() }









        //We are going to listen changes and here and when we get update in location we will refresh our UI
        locationRepository = LocationRepository(requireContext())
        //Now want to observe changes to the location
        val savedLocationObserver = Observer<Location> { savedLocation->
            when(savedLocation)
            {
                is Location.Zipcode -> forecastRepository.loadCurrentForecast(savedLocation.zipcode)
            }
        }
        locationRepository.savedLocation.observe(viewLifecycleOwner,savedLocationObserver)









        return view
    }


    private fun showLocationEntry()
    {
        val action = CurrentForecastFragementDirections.actionCurrentForecastFragementToLocationEntrykFragment()
        findNavController().navigate(action)
    }




    companion object
    {

        const val KEY_ZIPCODE = "key_zipcode"


        fun newInstance(zipcode:String) : CurrentForecastFragement
        {
            val fragement = CurrentForecastFragement()


            val args = Bundle()
            args.putString(KEY_ZIPCODE,zipcode)

            fragement.arguments = args

            return fragement

        }

    }


}