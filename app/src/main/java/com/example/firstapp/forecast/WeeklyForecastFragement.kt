package com.example.firstapp.forecast

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.firstapp.*
import com.example.firstapp.api.DailyForecast
import com.example.firstapp.api.WeeklyForecast
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.text.SimpleDateFormat
import java.util.*

private val DATE_FORMAT = SimpleDateFormat("MM-dd-yyyy")

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

            dailyForecastAdapter.submitList(weeklyForecast.daily)

        }

        forecastRepository.weeklyForecast.observe(viewLifecycleOwner,weeklyForcastObserver)












        locationRepository = LocationRepository(requireContext())
        val savedLocationObserver = Observer<Location> {savedLocaation ->
        when(savedLocaation)
        {
            is Location.Zipcode -> forecastRepository.loadWeeklyForecast(savedLocaation.zipcode)
        }
        }

        locationRepository.savedLocation.observe(viewLifecycleOwner,savedLocationObserver)


















        return view
    }


    private fun showLocationEntry()
    {
        val action = WeeklyForecastFragementDirections.actionWeeklyForecastFragementToLocationEntrykFragment()
        findNavController().navigate(action)

    }





    private fun showForecastDetails(forecast: DailyForecast)
    {
        val temp = forecast.temp.max
        val description = forecast.weather[0].description
        val date = DATE_FORMAT.format(Date(forecast.date * 1000))
        val icon = forecast.weather[0].icon

        val action = WeeklyForecastFragementDirections.actionWeeklyForecastFragementToForecastDetailsFragment(temp,description,date,icon)
        findNavController().navigate(action)
    }

    companion object
    {

        const val KEY_ZIPCODE = "key_zipcode"


        fun newInstance(zipcode:String) : WeeklyForecastFragement
        {
            val fragement = WeeklyForecastFragement()


            val args = Bundle()
            args.putString(KEY_ZIPCODE,zipcode)
            //setting bundle into fragement
            fragement.arguments = args

            return fragement

        }

    }


}