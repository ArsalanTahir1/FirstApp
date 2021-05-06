package com.example.firstapp.api

import com.example.firstapp.Location
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


// A function that will return us a valid implementation of this interface, Its going to be service implementaion that we will use to load our data
//Its going to return an instance of our interface
//When we are going to call createOpenWeatherMapService, its going to create an implementation of our api and we will use that api class to return the waether data
fun createOpenWeatherMapService() : OpenWeatherMapService
{
    //An instance of retrofit bilder
    val retrofit = Retrofit.Builder()
        //base url for retrofit endpoint. It should aquet to base url of whatever api you are using
        .baseUrl("http://api.openweathermap.org")
        .addConverterFactory(MoshiConverterFactory.create())
        .build() //our object is created so now we will use this to create our service implementation


    return  retrofit.create(OpenWeatherMapService::class.java)

}



//Its gonna serve the basis for our retrofit api service
interface OpenWeatherMapService {

    //first method on this service which will corespond to the openweathermap/weatherapi
    //To define a new retrofit endpoint method we will start will @GET....

    //@GET to specify that this going to be a get rest request and in this we are going to define the path to the endpoint we wanna use. Its is oing to take the base url we defined above and then its going to take the bit for each individual method we defined above
    //For this endpoint we are gonna use
     @GET("/data/2.5/weather")//now we are going to define method as we normally would
    fun currentWeather(//Then parameters should map to the query parameters that we were using in our browser and that present in documentation
        @Query("zip") zipcode: String,// "zip" is the request that going out to the endpoint we want this to be called zip and for name of parameter on client side we can call this anything so zipcode:String
        @Query("units") units: String,//units that we wanna back cuz we don't wanna kelvin all the time "
        @Query ("appid") apiKey:String  // "appid" api key that we got when we opened our account
    ): Call<CurrentWeather>//Now we need to specify the return type which is the response that will comeback from above queries we made, so so we're going to use that retrofit call class to wrap this response and then we need to tell it what type to expect back and so we want to expect back one of our current weather models so now when we call this methodwe should be able to actually get backvalid weather datafor the zip code that's passed in


    /*

get this time the endpoint will be
slash data slash
2.5 one call

we're going to name this function seven
day
forecast and now it's going to take a
number of parameters

     */
    @GET("/data/2.5/onecall")
    fun sevenDayForecast(
        @Query("lat")     lat    : Float,
        @Query("lon")    lon     : Float,
        @Query("exclude") exclude: String,
        @Query("units")   units  : String,//units that we wanna back cuz we don't wanna kelvin all the time "
        @Query ("appid")  apiKey : String  // "appid" api key that we got when we opened our account

    ): Call<WeeklyForecast>//returns weeklyforecast

}