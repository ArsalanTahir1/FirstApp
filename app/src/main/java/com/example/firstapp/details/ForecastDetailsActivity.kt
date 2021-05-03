package com.example.firstapp.details

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.firstapp.*

class ForecastDetailsActivity : AppCompatActivity() {

    /* lateinit var means this is a variable that at somepoint will be
       assigned a value but itn't going to be assigned in right now.We did
       this cuz it needs a context and without going to onCreate, there isn't
       any context
    * */
    private lateinit var tempDisplaySettingsManager: TempDisplaySettingsManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forecast_details)

        tempDisplaySettingsManager = TempDisplaySettingsManager(this)

        setTitle(R.string.forecast_details)

        val details_temp_text  =findViewById<TextView>(R.id.details_tempText)
        val details_description_text = findViewById<TextView>(R.id.details_description_text)

        val temp = intent.getFloatExtra("key_temp",0f)
        details_temp_text.text = formatTempForDisplay(temp,tempDisplaySettingsManager.getTempDisplaySetting() )
        details_description_text.text = intent.getStringExtra("key_description")


    }

    /* For inflating the menu when activity is created so we can see that menu
        1- Get reference to menu inflater
    */
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
    We are going to use this method to display an alert dialogue
    We can create an alert dialogue by using AlertDialog.Builder. It lets
    us customize the dialog without having to know how its actually built
    under the hood. After getting reference, we will start to customize
    properties on that
     */
    // we moved this function in util
    /*
    private fun showTempDisplaySettingDialogue()
    {

        val dialogBuilder = AlertDialog.Builder(this)
        .setTitle("Choose Display Units")
        .setMessage("Choose Celsius or Fahrenheit")
        /*Body defines passing in clickListener for this . Its clickHandler
          typically takes 2 parameters, however in our case we are not
          going to use them so thats why we will write _. Now we will
          define what will happen when we will click the positive button
          which for now is just going to be Toast
        * */
        .setPositiveButton("F°") { _, _ ->

            tempDisplaySettingsManager.updateSetting(TempDisplaySetting.Fahrenheit)
        }

        .setNeutralButton("C°") { _, _ ->

            tempDisplaySettingsManager.updateSetting(TempDisplaySetting.Celsius)

        }
        //displays a message
        .setOnDismissListener{
            Toast.makeText(this,"Settings will take effect on restarting app",Toast.LENGTH_SHORT).show()
        }
        dialogBuilder.show()
    }

     */
}