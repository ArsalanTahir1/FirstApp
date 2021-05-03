 package com.example.firstapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.firstapp.details.ForecastDetailsActivity
import com.example.firstapp.forecast.CurrentForecastFragement
import com.example.firstapp.location.LocationEntrykFragment

 class MainActivity : AppCompatActivity(), AppNavigator {


    // Object of ForecastRepository.

    // Pasted in currentFOrecastFreagemnt private val forecastRepository = ForecastRepository()


     private lateinit var tempDisplaySettingsManager: TempDisplaySettingsManager

    /*Now we have forecastRepository so we can reference it down
    below in our code
    */


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setTitle("Weather")

        tempDisplaySettingsManager = TempDisplaySettingsManager(this)
/*
        val zipcode_edittext: EditText = findViewById(R.id.zipcode_editText)
        val submitbutton: Button = findViewById(R.id.submit_button)

        submitbutton.setOnClickListener {

            val zipcode: String =zipcode_edittext.text.toString()

            if (zipcode.length != 5)
            {

                Toast.makeText(this,R.string.zipcode_error,Toast.LENGTH_SHORT).show()
            }

            else
            {
                forecastRepository.loadForecast(zipcode)
            }


            }


 */
        //reference to recyclerview


        /*
        //going to paste in new CurrentForecastFragement

        forecastListRecyclerView.layoutManager = LinearLayoutManager(this)

        val dailyForecastAdapter = DailyForecastAdapter(tempDisplaySettingsManager){
            /* getString is a special method in activity that lets you
            access the string resources directly and to get that we need
            to pass in a string identifier in this case its forecast_clicked_format
            Now we can pass in any number of aurguments and those aurguments will be
            used to fill in the string templaate parameters
            */


            /*
            val msg = getString(R.string.forecast_clicked_format,it.temp,it.description)
            Toast.makeText(this,msg,Toast.LENGTH_SHORT).show()
             */




            showForecastDetails(it)

        }
        forecastListRecyclerView.adapter = dailyForecastAdapter






        /* Observer is an special class in androidx.lifecycle. We made an Observer
           and items available in its lambda are forecast items. What we need to do
           with these forecast items is to update our recycler view. For right now
           this observer will do nothing anytime it will be updated however, now
           that we have an observer, we can finish the observe implementation below
        * */
        val weeklyForcastObserver = Observer<List<DailyForecast>>{ forecastItems->

            //update our list adapter
            /*
            submitList will let us send a new list of items which will then update
            whats on screen
             */
            dailyForecastAdapter.submitList(forecastItems)

        }


        /*Observer is going to let us know about updates. Each observe methods requires
          a lifecyle owner. Lifecycle is something that currently knows about there
          lifecycle. Activity is lifecycle owner. "this" represents enclousing outer
          class and in this case its MainActivity. Now we passed an observer, so this
          observer will be updated anytime our live data changes in the repository. And
          because we have passed that lifecyle observer, all the these changes will be
          bound to the lifecycle of activity, so if any loading is taking too long, it won't
          return once the activity is destroyed
        * */

        forecastRepository.weeklyForecast.observe(this,weeklyForcastObserver)





         */




        //fragement implementation
        supportFragmentManager.beginTransaction().add(R.id.fragementContainer,LocationEntrykFragment()).commit()

    }


     // Menu



     override fun onCreateOptionsMenu(menu: Menu?): Boolean {
         val inflater: MenuInflater = menuInflater
         inflater.inflate(R.menu.settings_menu, menu)
         return true
     }

     /*The OptionsMenu , OptionsItem refer to the options in our menu

      */


     /* In this method we are going to pass in a menu item which is going
     to be whatever item was selected. So we basically check the id that was
     clicked and respond accordingly
    */

     override fun onOptionsItemSelected(item: MenuItem): Boolean {
         return when(item.itemId)
         {
             R.id.tempDisplaySetting->
             {
                 showTempDisplaySettingDialogue(this,tempDisplaySettingsManager)
                 true
             }

             else -> super.onOptionsItemSelected(item)
         }

     }


     /*
     Overiding method of AppNavigator
     So now when in Activity, navigateToCurrentForecast is called
     by some external class we will call loadForecast on our
     repository which will then show that weather data. So how are we
     going to call this method? We will go in locationentry fragment
      */

     override fun navigateToCurrentForecast(zipcode: String) {
         /*going to pass current forecast fragement cuz it is going
          to be responsible for loading and showing data
         */

         supportFragmentManager.beginTransaction()
                                             //calling new instnce method from companion object
                 .replace(R.id.fragementContainer,CurrentForecastFragement.newInstance((zipcode)))
                 .commit()

         //forecastRepository.loadForecast(zipcode)
     }


     override fun navigateToLocationEntry() {

         supportFragmentManager.beginTransaction().replace(R.id.fragementContainer, LocationEntrykFragment()).commit()
     }




     // Menu


     /* Pasted in new fragment
     private fun showForecastDetails(forecast: DailyForecast)
     { /*Its going to be an explicit intent cuz we are going
              to tell us what activity to launch. startActivity is
              method in activity that lets you go from one activity
              to other
            */
         val forecastDetailsIntent = Intent(this,ForecastDetailsActivity::class.java)
         forecastDetailsIntent.putExtra("key_temp",forecast.temp)
         forecastDetailsIntent.putExtra("key_description",forecast.description)
         startActivity(forecastDetailsIntent)


     }
      */

}