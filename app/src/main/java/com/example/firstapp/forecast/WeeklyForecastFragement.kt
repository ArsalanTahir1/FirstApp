package com.example.firstapp.forecast

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.firstapp.*
import com.example.firstapp.api.DailyForecast
import com.example.firstapp.api.WeeklyForecast
import com.example.firstapp.details.ForecastDetailsFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton


class WeeklyForecastFragement : Fragment() {


    private lateinit var tempDisplaySettingsManager: TempDisplaySettingsManager

    private lateinit var locationRepository: LocationRepository
    private val forecastRepository = ForecastRepository()



    // pasted from locationfrag


    // pasted from locationfrag



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {


        tempDisplaySettingsManager = TempDisplaySettingsManager(requireContext())



        /*arguments will give us access to the bundle that was passed
          into that fragement when it was created

          !! it will crash if it is going to be null
        */
        val zipcode = ""//arguments!!.getString(KEY_ZIPCODE) ?: ""




        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_weekly_forecast, container, false)



        val locationEntryButton :FloatingActionButton = view.findViewById(R.id.floatingActionButton2)
            locationEntryButton.setOnClickListener(){
                showLocationEntry()

            }

        val forecastListRecyclerView: RecyclerView = view.findViewById(R.id.forecastListRecyclerView)
        forecastListRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        val dailyForecastAdapter = DailyForecastAdapter(tempDisplaySettingsManager){

            showForecastDetails(it)

        }
        forecastListRecyclerView.adapter = dailyForecastAdapter


        val weeklyForcastObserver = Observer<WeeklyForecast>{ weeklyForecast->
            /*
            and now we'll pass in weekly forecast
dot daily that'll pass in that
list of the daily forecast items to our
adapter

             */
            dailyForecastAdapter.submitList(weeklyForecast.daily)

        }

        forecastRepository.weeklyForecast.observe(viewLifecycleOwner,weeklyForcastObserver)










        //Created Observer

        locationRepository = LocationRepository(requireContext())
        val savedLocationObserver = Observer<Location> {savedLocaation ->
        when(savedLocaation)
        {
            is Location.Zipcode -> forecastRepository.loadWeeklyForecast(savedLocaation.zipcode)
        }
        }

        //Now to observe value

        locationRepository.savedLocation.observe(viewLifecycleOwner,savedLocationObserver)


















        return view
    }


    private fun showLocationEntry()
    {
        val action = WeeklyForecastFragementDirections.actionWeeklyForecastFragementToLocationEntrykFragment()
        findNavController().navigate(action)

    }


    /*
    we also are going to want to update um
how we are passing this data in so again
let's update the import for daily
forecast
and then now we're going to need to
update how we're getting the temperature
and the description
so let's create a new property for the
temperature
so we'll say val temp equals

     */


    private fun showForecastDetails(forecast: DailyForecast)
    {
        val temp = forecast.temp.max
        val description = forecast.weather[0].description
        val action = WeeklyForecastFragementDirections.actionWeeklyForecastFragementToForecastDetailsFragment(temp,description)
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


        fun newInstance(zipcode:String) : WeeklyForecastFragement
        {
            val fragement = WeeklyForecastFragement()

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