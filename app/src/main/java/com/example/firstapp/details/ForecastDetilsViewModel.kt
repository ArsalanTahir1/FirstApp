package com.example.firstapp.details

import java.text.SimpleDateFormat


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException


class ForecastDetailsViewModelFactory(private val args:ForecastDetailsFragementArgs):ViewModelProvider.Factory
{
    //creating generic type with this fun
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ForecastDetilsViewModel::class.java))
        {
            return ForecastDetilsViewModel(args) as T
        }

        throw IllegalArgumentException("Unknown Viewmodel class")
    }

}




class ForecastDetilsViewModel(args: ForecastDetailsFragementArgs):ViewModel() {

    private val _viewState: MutableLiveData<ForecastDetailsViewState> = MutableLiveData()
    public val viewState : LiveData<ForecastDetailsViewState> = _viewState

    init {
        _viewState.value = ForecastDetailsViewState(
            temp = args.temp,
            description = args.description,
            date = args.date,
            iconUrl = "http://openweathermap.org/img/wn/${args.icon}@2x.png"
        )
    }


}