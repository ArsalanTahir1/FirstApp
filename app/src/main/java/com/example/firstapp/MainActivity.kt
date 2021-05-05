 package com.example.firstapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toolbar
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.firstapp.forecast.CurrentForecastFragementDirections
import com.google.android.material.bottomnavigation.BottomNavigationView


 class MainActivity : AppCompatActivity() {


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



//       creating appBarConfiguration based on navgraph
        val navController = findNavController(R.id.nav_host_fragement)
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        //Now connecting it to toolbar
        findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar).setTitle(R.string.app_name)




        //Putting our bottomNavigation

        findViewById<BottomNavigationView>(R.id.bottomnavigtion).setupWithNavController(navController)


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
//
//     override fun navigateToCurrentForecast(zipcode: String)
//
//   {
////         val action = LocationEntrykFragmentDirections.actionLocationEntrykFragmentToCurrentForecastFragement2()
////         findNavController(R.id.nav_host_fragement).navigate(action)
//
//     }
//
//
//     override fun navigateToLocationEntry() {
//
//        val action = CurrentForecastFragementDirections.actionCurrentForecastFragementToLocationEntrykFragment()
//         findNavController(R.id.nav_host_fragement).navigate(action)
//
//     }
//
//
//     override fun navigateToForecastDetails(forecast: DailyForecast) {
//
//         val action = CurrentForecastFragementDirections.actionCurrentForecastFragementToForecastDetailsFragment(forecast.temp,forecast.description)
//         findNavController(R.id.nav_host_fragement).navigate(action)
//     }



     // Menu




}