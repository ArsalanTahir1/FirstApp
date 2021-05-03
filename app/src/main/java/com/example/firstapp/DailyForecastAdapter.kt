package com.example.firstapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView


/*
    ViewHolder is going to be responsible for binding the individual view
    which is tempText and descriptionText. Now we are going to create
    two views we are going to create reference to. Used private so it should
    not be visible outside of this class.*DailyForecastViewHolder is not a context so we cant call findviewby id
    like we could do in MainActivity. But we can do it from view. So in this
    case view, view thats being passed in is the view we just created so that will
    allign with item_daily_forecast.xml

 */
class DailyForecastViewHolder(view:View, private val tempDisplaySettingsManager: TempDisplaySettingsManager) : RecyclerView.ViewHolder(view)

{
    private val tempText : TextView = view.findViewById(R.id.tempText)
    private val descriptionText : TextView = view.findViewById(R.id.descriptionText)

    /*So now we have access to those views, we are going to create a simple
      method Bind. Bind is gonna get called from the adapter's onBind method
      and its where we are going to connect the individual daily forecast item
      to those views we just referenced
    * */

    fun bind(dailyForecast: DailyForecast)
    {
        tempText.text = formatTempForDisplay(dailyForecast.temp,tempDisplaySettingsManager.getTempDisplaySetting())
        descriptionText.text = dailyForecast.description
    }
}




/* Daily Forecast Adapter is going to take one parameter eventually but for now
   we are going to leave it empty. We do wanna inherit from a RecyclerViewAdapter
   class. There is subclass available of recyclerviewAdapter class that is going
   to setting this up a little bit easier. It requires type and a viewholder
   viewholder is used to bind those items to layout.List Adapters constructor
   requies itemCallBack instance. It is a utility class used to make recycler
   view binding a little more efficient


 */

/*
We are defining private read only val clickHander so when we click on an item
in our recycler view it gives some response. It is going to
 be of type function which takes a Dailyforecast item and returns
 unit. Functions are valid type in kotlin. So we can parameters or say
 variables which can be of type function. So in this case we will have
 to pass a funciton which takes a dailyforecast item and returns unit which
 is same as void
 */

class DailyForecastAdapter(private val tempDisplaySettingsManager: TempDisplaySettingsManager,private val clickHandler:(DailyForecast)->Unit) : ListAdapter<DailyForecast,DailyForecastViewHolder>(DIFF_CONFIG)
{
    //over rided functions of ListAdapter

    /*onCreateViewHolder is going to create a new ViewHolder for us within
      within that viewHolder we will create a new view that will used to
      represent each item in our list
      */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyForecastViewHolder {

        /*First off we need to create onViewHolder, so we are going to create
        a new instance of viewholder which will then also inflate our layout
        for those layout items we have been working with. We are going instantiate
        a class called LayoutInflater which lets you inflate a layout file that
        you have defined. We are not in activity so how we are going to get context
        Views and viewgroups have context, so in this case we have a viewgroup called
        parent. Inflate method requies the layout you want to inflate, viewgroup. Teacher
        is saying that in most cases its going to be false and don't worry :)

         */
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_daily_forecast,parent,false)
        /*
        So after we have that, itemview should reference that layout, so now
        we can create our viewholder. So now, anytime recyclerview needs to create
        a new viewHolder, its gonna call this method. And anytime it needs to bind a
        view holder, so it can put new information on the screen, its gonna call
        onBindViewHolder
         */

        return DailyForecastViewHolder(itemView,tempDisplaySettingsManager)
    }

    /*
     In onBindViewHolder we are going to get each individual element of
     forecastList and basically pass that data along with viewHolder so
     the viewItems can be updated. Anytime recyclerview needs to bind a
        view holder, so it can put new information on the screen, its gonna call
        onBindViewHolder
     */


    /*
    Now in onBindViewHolder, we get passed back an instance of the viewholder
    we just created
     */
    override fun onBindViewHolder(holder: DailyForecastViewHolder, position: Int) {
    /*
        Remember, bind is the method we just created which takes  forecastItem
        and then updates the view Values. So we need a get access to a dailyfore-
        cast item. How we are going to do that??  ? By using the ListAdapter we extended
        Because to use the list adapter, it will store the List of daily forecast
        items. That's we defined DailyForecastAdapter item as the type of ListAdapter
        By using method getItem of class ListAdapter, which takes an index representing
        what position in the index, and its going to return an instance of dailyForecast
        item. position represents the position that is being bound by the adapter
         */
        holder.bind(getItem(position))
        /*
        After we bind data we going to set a clicklistener on that itemview
         */

        /*
        Inside the lamda is what happens anytime the view is clicked. We are
        going to invoke the clickhandler that was passed to our adapter. Now
        when we click we will get that forecastitem for that individual view
        and that will be passed back to whoever called it. Now we need to pass
        in that callback with in main activity
         */
        holder.itemView.setOnClickListener{

            clickHandler(getItem(position))

        }
    }



    /*
    Companion object is similar to static concept of java. It allows
    us to use methods of class without creating instansizing or creating
    object of it. Anything that we are going to define here is going to
    exist outside of  object/instance of DailyForecastAdapter class and we
    will be able to access it.
     */
    companion object
    {
        /*
        We wrote DIFF_CONFIG in Caps to show that its static. Object is showing
        that we are defining an anonymous innerclass which is extending
        DiffUtil.ItemCallback class
         */
        val DIFF_CONFIG = object: DiffUtil.ItemCallback<DailyForecast>()
        {
            override fun areItemsTheSame(oldItem: DailyForecast, newItem: DailyForecast): Boolean {
                /*
                === tells us that whether or not oldItem and newItem
                are exact same object reference
                 */

                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: DailyForecast, newItem: DailyForecast): Boolean {
                        /*
                        == tells us that whether or they have same contents
                        e.g 50 = 50
                        */

                return oldItem == newItem
            }

        }

    }

}