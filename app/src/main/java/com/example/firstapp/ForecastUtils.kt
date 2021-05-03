package com.example.firstapp

import android.content.Context
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

fun formatTempForDisplay(temp:Float, tempDisplaySetting: TempDisplaySetting):String
{
    return when(tempDisplaySetting)
    {
        TempDisplaySetting.Fahrenheit -> String.format("%.2f F°",temp)

        //converting fahrenheit to celsius
        TempDisplaySetting.Celsius ->
        {
            val temp = (temp -32f) * (5f/9f)
            String.format("%.2f C°",temp)

        }
    }
}


/*
We are going to use this method to display an alert dialogue
We can create an alert dialogue by using AlertDialog.Builder. It lets
us customize the dialog without having to know how its actually built
under the hood. After getting reference, we will start to customize
properties on that
 */


 fun showTempDisplaySettingDialogue(context: Context, tempDisplaySettingsManager: TempDisplaySettingsManager)
{

    val dialogBuilder = AlertDialog.Builder(context)
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
            Toast.makeText(context,"Settings will take effect on restarting app", Toast.LENGTH_SHORT).show()
        }
    dialogBuilder.show()
}