package com.example.firstapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.firstapp.api.DailyForecast
import java.text.SimpleDateFormat
import java.util.*

/*
so now we can use this date format to
format the time stamps that we're
getting back from the api

 */
private val DATE_FORMAT = SimpleDateFormat("MM-dd-yyyy")

class DailyForecastViewHolder(view:View, private val tempDisplaySettingsManager: TempDisplaySettingsManager) : RecyclerView.ViewHolder(view)

{
    private val tempText : TextView = view.findViewById(R.id.tempText)
    private val descriptionText : TextView = view.findViewById(R.id.descriptionText)

    private val dateText = view.findViewById<TextView>(R.id.dateText)
    private val forecastIcon = view.findViewById<ImageView>(R.id.forecastIcon)



    fun bind(dailyForecast: DailyForecast)
    {

        tempText.text = formatTempForDisplay(dailyForecast.temp.max,tempDisplaySettingsManager.getTempDisplaySetting())

        descriptionText.text = dailyForecast.weather[0].description

        dateText.text = DATE_FORMAT.format(Date(dailyForecast.date * 1000))

        val iconId2 = dailyForecast.weather[0].icon
                                             //we're gonna do a string substitution here
        forecastIcon.load(("http://openweathermap.org/img/wn/${iconId2}@2x.png"))



    }
}



class DailyForecastAdapter(private val tempDisplaySettingsManager: TempDisplaySettingsManager,private val clickHandler:(DailyForecast)->Unit) : ListAdapter<DailyForecast,DailyForecastViewHolder>(DIFF_CONFIG)
{

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyForecastViewHolder {


        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_daily_forecast,parent,false)


        return DailyForecastViewHolder(itemView,tempDisplaySettingsManager)
    }



    override fun onBindViewHolder(holder: DailyForecastViewHolder, position: Int) {
     holder.bind(getItem(position))

        holder.itemView.setOnClickListener{

            clickHandler(getItem(position))

        }
    }


   companion object
    {
          val DIFF_CONFIG = object: DiffUtil.ItemCallback<DailyForecast>()
        {
            override fun areItemsTheSame(oldItem: DailyForecast, newItem: DailyForecast): Boolean {

                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: DailyForecast, newItem: DailyForecast): Boolean {
              return oldItem == newItem
            }

        }

    }

}