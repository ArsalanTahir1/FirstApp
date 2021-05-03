package com.example.firstapp


/*
    It is going to signify ways for us to indicate if want to navigate
    from one screen to the other. So now we have this naivgator, we need
    to have some class which implements this and defines its behaviour, so
    if we call this method, we actually navigate to our current forecast.
    So in our case that class is going to be mainActivity
 */

interface AppNavigator {

    fun navigateToCurrentForecast(zipcode: String)
    {

    }


    fun navigateToLocationEntry()
    {

    }
}