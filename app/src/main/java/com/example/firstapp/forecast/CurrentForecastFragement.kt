package com.example.firstapp.forecast

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.firstapp.*
import com.example.firstapp.details.ForecastDetailsActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton


class CurrentForecastFragement : Fragment() {


    private lateinit var tempDisplaySettingsManager: TempDisplaySettingsManager

    private val forecastRepository = ForecastRepository()



    // pasted from locationfrag

    private lateinit var appNavigator: AppNavigator


    override fun onAttach(context: Context) {
        super.onAttach(context)


        appNavigator = context as AppNavigator
    }

    // pasted from locationfrag



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {


        tempDisplaySettingsManager = TempDisplaySettingsManager(requireContext())



        /*arguments will give us access to the bundle that was passed
          into that fragement when it was created

          !! it will crash if it is going to be null
        */
        val zipcode =arguments!!.getString(KEY_ZIPCODE) ?: ""




        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_current_forecast_fragement, container, false)



        val locationEntryButton :FloatingActionButton = view.findViewById(R.id.floatingActionButton2)
            locationEntryButton.setOnClickListener(){
                appNavigator.navigateToLocationEntry()

            }

        val forecastListRecyclerView: RecyclerView = view.findViewById(R.id.forecastListRecyclerView)
        forecastListRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        val dailyForecastAdapter = DailyForecastAdapter(tempDisplaySettingsManager){

            showForecastDetails(it)

        }
        forecastListRecyclerView.adapter = dailyForecastAdapter


        val weeklyForcastObserver = Observer<List<DailyForecast>>{ forecastItems->

            dailyForecastAdapter.submitList(forecastItems)

        }

        forecastRepository.weeklyForecast.observe(this,weeklyForcastObserver)

        forecastRepository.loadForecast(zipcode)
        return view
    }


    private fun showForecastDetails(forecast: DailyForecast)
    { /*Its going to be an explicit intent cuz we are going
              to tell us what activity to launch. startActivity is
              method in activity that lets you go from one activity
              to other
            */
        val forecastDetailsIntent = Intent(requireContext(), ForecastDetailsActivity::class.java)
        forecastDetailsIntent.putExtra("key_temp",forecast.temp)
        forecastDetailsIntent.putExtra("key_description",forecast.description)
        startActivity(forecastDetailsIntent)


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