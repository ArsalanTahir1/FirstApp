/* like we did for the current
weather
we're going to define several new models
that will
build up the response for our weekly
forecast
data

 */

package com.example.firstapp.api

import com.squareup.moshi.Json





data class WeatherDescription(val main:String,val description: String, val icon:String)

data class Temp(val min:Float,val max:Float)


/*
the json response names it differently
we need to
once again use at field
colon json
name equals dt
so in this case dt stands for date time
but we want to
refer to that property as date because
it's more specific
then we'll define another property here
called
temp that'll be an instance of the temp
class we just created

 */
data class DailyForecast(@field:Json(name = "dt") val date:Long, val temp: Temp, val weather:List<WeatherDescription>)


/*
so now this daily or this weekly
forecast object will be the response
for our weekly forecast endpoint
so like we did before let's now go to
forecast repository and we will update
our
live data to account for these new api
models

 */
data class WeeklyForecast(val daily: List<DailyForecast>)

