package com.example.firstapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import java.io.Serializable
import kotlin.random.Random


class  ForecastRepository {


    /*We are defining this private property that will be internal to our forecast
     and this will be the property which we will use to update data. This is going
     to return week's worth of our forecast data.This property is going to be Mutable live data.
     Live data is templated class means it can hold different types of data and you
     have to tell it what type of data you want to hold.In our case we want to hold a list of
     daily forecast items since we want to represent a week's worth of forecast info.This will
     let us update that observable value holder internally to this class  */

    /*Defined private read only property called _weeklyForecast and assigned
      a value of MutableLiveData that will hold a list of dailyforecast item*/
     private val _weeklyForecast = MutableLiveData<List<DailyForecast>>()


    /*But we still need to make a way for our activity to listen for that update so
    now we are going to create a public live data. weeklyForecast is public so the
    activity can get access to it and its LiveData which means that anything that
    references it like our activity will be able to get updates but not to publish
    its own changes and its really important because we want repository to be
    only place that can modify this data
     */

    val weeklyForecast : LiveData<List<DailyForecast>> = _weeklyForecast


    /*So now we have means of updating activity with data we now need
    to actually load the data. To do that we are going to define a new
    method in this class
    */

    /* We want to pass zipcode in it cuz it will be important to load
    specific forecast for that zipcode. This will let us call loadForecast
    and pass zipcode in it
     */


    /*Now in function body we wanna define a list of 7 random values.Those
    random values are gonna represent our temperature values.So we are going
    to create a local variable called randomValues. The List body is going
    to allow us to do is essentially initialize how this 7 items should be
    created so its basically going to let us define what is going to be in
    that list. To create random values there is a special class in kotlin
    called random.By using companion object of class "Random" we used its
    method nextFloat() and then method of class Float "rem" since nextFloat()
    function is of type/return type Float.
     */


    fun loadForecast(zipcode: String)
    {
        //This gives us our temperature values

        val randomValues = List(7){Random.nextFloat().rem(100) * 100}

        /*Now we wanna convert those temperatures into Forecast items, for that
          we are going to create a new local variable. We are gonna use this to
          create items that we are going to send to our LiveData. We referenced the
          list of that temperature values and then we are going to use a map function
          which lets us convert one type to another. Its going to take each of that
          forecast value and convet it to different output value. In out case we want
          to convert it to an output value of dailyforecast. As daily forecast have two
          two variables or parameters temperature and description. We filled
          forecastItemslist with each individual item of daily forecast with random
          temperature values coming from the list "randomValues" and for the description
          for all these daily forecast items we are going to use "Partly Cloudy" for now.
          We are going to comebaack to update our description later

        */

        val forecastItemslist = randomValues.map {temp->
            DailyForecast(temp,getTempDescription(temp))

        }

        /* Now wanna send this list to our Livedata, to do that we need to reference
           our _weeklyForecast property. SetValue is basically going to let us update
            whatever value is currently held by the Livedata. _weeklyForecast will be
            updated with these new items and because weeklyForecast is based on
            _weeklyForecast, that means that weeklyForecast is also going to be updated*/

        _weeklyForecast.setValue(forecastItemslist)


        /* So now the next step is to go to our main activity and actually start observing these
           values
        */




    }

    /*
    Here we are going to do implementaion for random generation of description
     */

    /*
    temp represents the current temperature value that we have generated in
    random generation method. In when body we are going to use ranges.
    First one is from smallest possible float to zero
     */

    private fun getTempDescription(temp:Float) : String
    {
        return when(temp)
        {
            in Float.MIN_VALUE.rangeTo(0f)->"No sense"
            in 0f.rangeTo(32f)->"Too cold"
            in 55f.rangeTo(100f)-> "Its ok"
            in 100f.rangeTo(Float.MAX_VALUE)->"Oven"
            else ->"Doesnt compute"
        }

    }



}