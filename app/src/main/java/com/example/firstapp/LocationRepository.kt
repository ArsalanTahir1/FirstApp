package com.example.firstapp

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData


//// A sealed class to represent different type of within our app. It will
//enumerate all the different type of locations we handle in the app. We got
//a zipcode as specific type. In future we will use gps for this

sealed class Location
{
    data class Zipcode(val zipcode :String) : Location()
}


//only available in this file. const so this value will not change
private const val KEY_ZIPCODE="key_zipcode"


////We are going to save our zipcode using shared preferences, to use it we need
//reference to context

class LocationRepository(context: Context)
{
    private val preferences = context.getSharedPreferences("settings",Context.MODE_PRIVATE)


    //Going to use Livedata for providing our data with rest of the app
    private val _savedLocation: MutableLiveData<Location> = MutableLiveData()
    val savedLocation : LiveData<Location> = _savedLocation















    //init will run when this class is created/instantiated. We are going to put a shared preference change listener so we can listen to changes
    //When we create an instance of locaation repository, it will look up current value of saved zipcode, broadcast that zipcode if there is one and listen to future changes in that zipcode
    init {

        //we registered a SharedPreferenceChangeListener so anytime sharedpref change we will notified. We only care about changes to location setting rightnow so if key!=zipcode we will return and jux ignore it if it does we will get current zipcode value and if its not null and blank meaning we have some saved data there, we will forward it to our saved location observers
        preferences.registerOnSharedPreferenceChangeListener{ sharedPreferences, key ->

            if (key!= KEY_ZIPCODE) return@registerOnSharedPreferenceChangeListener

            //else
            broadcastSavedZipcode()
        }













        //After we hve registered listeners, we want to notify our observers about whatever the value is
        // so if its not null, go ahead and notify our LiveData

        broadcastSavedZipcode()
    }




    //Now we need a way to save the location when we enter it in submit location screen
    //Anytime we are going to call save location, its going to save location in shared pref
    fun saveLocation(location: Location)
    {
        when(location)
        {
            is Location.Zipcode -> preferences.edit().putString(KEY_ZIPCODE,location.zipcode).apply()
        }
    }


    //Now we need a way that if that location changes, we can notify other callers
    //we want to listen to those changes so we can notify our observers



    private fun broadcastSavedZipcode()
    {

        val zipcode = preferences.getString(KEY_ZIPCODE,"")
        if (zipcode!=null && zipcode.isNotBlank() )
        {
            _savedLocation.value= Location.Zipcode(zipcode)
        }
        // so if its not null, go ahead and notify our LiveData
    }

}
