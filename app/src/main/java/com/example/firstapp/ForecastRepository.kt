package com.example.firstapp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.firstapp.api.CurrentWeather
import com.example.firstapp.api.WeeklyForecast
import com.example.firstapp.api.createOpenWeatherMapService
import retrofit2.Call
import retrofit2.Response
import java.io.Serializable
import java.util.*
import javax.security.auth.callback.Callback
import kotlin.random.Random


class  ForecastRepository {



    private val _currentWeather =MutableLiveData<CurrentWeather>()
    val currentWeather : LiveData<CurrentWeather> = _currentWeather










    /*We are defining this private property that will be internal to our forecast
     and this will be the property which we will use to update data. This is going
     to return week's worth of our forecast data.This property is going to be Mutable live data.
     Live data is templated class means it can hold different types of data and you
     have to tell it what type of data you want to hold.In our case we want to hold a list of
     daily forecast items since we want to represent a week's worth of forecast info.This will
     let us update that observable value holder internally to this class  */

    /*Defined private read only property called _weeklyForecast and assigned
      a value of MutableLiveData that will hold a list of dailyforecast item*/
     private val _weeklyForecast = MutableLiveData<WeeklyForecast>()


    /*But we still need to make a way for our activity to listen for that update so
    now we are going to create a public live data. weeklyForecast is public so the
    activity can get access to it and its LiveData which means that anything that
    references it like our activity will be able to get updates but not to publish
    its own changes and its really important because we want repository to be
    only place that can modify this data
     */

    val weeklyForecast : LiveData<WeeklyForecast> = _weeklyForecast


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













    /*
because we're going to see how we can
chain together
two asynchronous calls so what we're
going to do is make a call
to the current weather endpoint because
it will return us back a lot long
we'll then use the lat long to call the
second endpoint and get the seven day
forecast
so to start off
we'll type val call equals

 */
    fun loadWeeklyForecast(zipcode: String)
    {
        val call = createOpenWeatherMapService().currentWeather(zipcode,"imperial",BuildConfig.OPEN_WEATHER_MAP_API_KEY)
        //copypasted from below
        call.enqueue(object : retrofit2.Callback<CurrentWeather>
        {
            override fun onFailure(call: Call<CurrentWeather>, t: Throwable) {

                Log.e(ForecastRepository::class.java.simpleName,"error loading location for weekly forecast", t)
            }


            //When things load correctly
            override fun onResponse(call: Call<CurrentWeather>, response: Response<CurrentWeather>)
            {

                val weatherResponse= response.body()
                if (weatherResponse!=null)
                {
                    /*
                    and so now down here if we get
the weather response instead of updating
the live data
we are going to load seven day
forecast
so within this we'll then make another
call
so this will be val
forecast call equals
                     */
                    //Load 7 days forecast
                    val forecastCall = createOpenWeatherMapService().sevenDayForecast(
                        lat = weatherResponse.coord.lat,
                        lon = weatherResponse.coord.lan,
                        /*
                        hourly so this endpoint actually
can return you back current weather oh
minute by minute weather
hourly weather we only want the seven
day forecast so we exclude
all of the rest of that stuff

                         */
                        exclude = "current,minutely,hourly",
                        units = "imperial",
                        apiKey = BuildConfig.OPEN_WEATHER_MAP_API_KEY
                    )
                    /*
                    we have that new large call
created now we need to call in queue on
it

                     */

                    forecastCall.enqueue(object : retrofit2.Callback<WeeklyForecast>{
                        override fun onFailure(call: Call<WeeklyForecast>, t: Throwable) {
                            Log.e(ForecastRepository::class.java.simpleName,"error loading weekly forecast")
                        }

                        override fun onResponse( call: Call<WeeklyForecast>,response: Response<WeeklyForecast> )
                        {
                            val weeklyForecastResponse = response.body()
                            if (weeklyForecastResponse!=null)
                            {
                                _weeklyForecast.value = weeklyForecastResponse
                            }
                            /*
                             so that will
update our
live data and ultimately update our ui
when we have loaded that forecast

                             */
                        }

                    })


                }
            }

        })


    }





    fun loadCurrentForecast(zipcode: String)
    {
        /*
        so now let's go ahead and kick this off
        by creating a call class that represents that request to the endpoint we want

         */
        val call = createOpenWeatherMapService().currentWeather(zipcode,"imperial",BuildConfig.OPEN_WEATHER_MAP_API_KEY)

        /*
        so then how do we actually use this call
to get a response back
well as we saw in the the lecture notes
previously we can type
call.nq
and then we need to pass a callback in
here so we're going to basically create
a
an anonymous inner class here so this
will be an implementation of the
callback
without any actual name it'll be used
just in this location

         */


        call.enqueue(object : retrofit2.Callback<CurrentWeather>
        {
            override fun onFailure(call: Call<CurrentWeather>, t: Throwable) {
            /*
            log dot e will be use the the default
android logger
to just log that issue locally

             */
                Log.e(ForecastRepository::class.java.simpleName,"error loading current weather", t)
            }


            //When things load correctly
            override fun onResponse(call: Call<CurrentWeather>, response: Response<CurrentWeather>)
            {
                /*
                dot
body that dot body call will essentially
unwrap it from the response call
at this point we can check if that is
null or not so we can say
if whether response does not equal
null then we could update
our current forecast

data

                 */
                val weatherResponse= response.body()
                if (weatherResponse!=null)
                {
                    /*
                    now you might see a little bit of an
issue here
we have current forecast which is
expecting a daily forecast but we have
our weather response which is a current
weather object

so we need to come back up to the top of
our repository
and update current forecast to take in
that new current
weather model that we defined


                     */
                    _currentWeather.value = weatherResponse
                }
            }

        })


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