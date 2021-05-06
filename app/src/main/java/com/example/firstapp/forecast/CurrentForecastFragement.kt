package com.example.firstapp.forecast

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.firstapp.*
import com.example.firstapp.api.CurrentWeather
import com.example.firstapp.api.DailyForecast
import com.example.firstapp.details.ForecastDetailsFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton


class CurrentForecastFragement : Fragment() {


    private lateinit var tempDisplaySettingsManager: TempDisplaySettingsManager

    private val forecastRepository = ForecastRepository()
    private  lateinit var locationRepository: LocationRepository




    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {


        tempDisplaySettingsManager = TempDisplaySettingsManager(requireContext())



        /*arguments will give us access to the bundle that was passed
          into that fragement when it was created

          !! it will crash if it is going to be null
        */
     val zipcode = arguments?.getString(KEY_ZIPCODE) ?: ""




        // Inflate the layout for this fragment
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
        //we are going to pass in a key value pair to this fragement
        /* and use this to access current zipcode. we are going to do
           this by creating method called  new instance method.
           New instance method becomes a factory fragement that
           takes in any aurgument that that fragment needs to operate
           correctly. In our case it is going to take in a zipcode
           and then we can use that zipcode to load the required data
        */
        const val KEY_ZIPCODE = "key_zipcode"


        fun newInstance(zipcode:String) : CurrentForecastFragement
        {
            val fragement = CurrentForecastFragement()

            /*Bundle is simple class defined to store key value pairs in android
              we caan use bundles to pass things to intents or to pass in around
              fragement aurguments. Now we have a bundle we can put zipcode into it
            * */
            val args = Bundle()
            args.putString(KEY_ZIPCODE,zipcode)
            //setting bundle into fragement
            fragement.arguments = args

            return fragement

        }

    }


}