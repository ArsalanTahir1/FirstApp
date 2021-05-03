package com.example.firstapp.location

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.firstapp.AppNavigator
import com.example.firstapp.R


class LocationEntrykFragment : Fragment() {


    /*
    As we can see that onCreateView returns a view, it needs to returns the view
    that you wanna show on the screen. By default it returns the view it returns
    by layout inflator however we need to update that as we are filling out this
    implementation
    1- We are going to create a variable to store the view returned by
      layout.inflator
    2- We will return that view

    3- So now in between those lines update UI, get view ref...

    4- Acitivities have method called findViewbyId, fragements don't. To
       do this in fragments, we need to get reference to view cuz its
       the parent that was inflated
     */







    /*
    The reason its lateinit bcoz we are going to initialize it onAttach()
     */
    private lateinit var appNavigator: AppNavigator


    /* onAttach is one of the lifrcycle methods that happens
       very early in fragment LC it when the fragemnet is addaed to
       the activity. So this is the first point where we can get
       reference to activity or context. Bcoz fragement have to live
       with in the activity we can take adv of that fact and bcoz we
       except our activity to implement this appNavigator interface,
       we can assume that this context is our app Navigator

     */
    override fun onAttach(context: Context) {
        super.onAttach(context)

        /*
          as keyword is how we cast variable to another type.
          We are saying that we want to assign appnavigator the
          value of context. It means that appnavigator will now
          have reference to our Activity. Now that we have
          reference we will go to submit button....

         */
        appNavigator = context as AppNavigator
    }








    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_location_entryk, container, false)

        // ...update UI, get view ref

        val zipcode_edittext: EditText = view.findViewById(R.id.zipcode_editText)
        val submitbutton: Button = view.findViewById(R.id.submit_button)

        submitbutton.setOnClickListener {

            val zipcode: String =zipcode_edittext.text.toString()

            if (zipcode.length != 5)
            {
                /*
               Fragements don't extend so they are not a context by default.
               However, they do have a convenient method to get context
               called requireContext()
                */

                Toast.makeText(requireContext(),R.string.zipcode_error,Toast.LENGTH_SHORT).show()
            }

            else
            {
                /*
                When we click on button, its going to call navigateToCurrentForecast on
                the appNavigator interface that is then going to come in the main activity
                and come in to navigateToCurrentForecast method which will then trigger the
                loadforecast call on the repository. We have the same flow as before but now
                its being started in the activity and being handled by activity
                 */

                appNavigator.navigateToCurrentForecast(zipcode)


              //  forecastRepository.loadForecast(zipcode)
            }


        }

        return view
    }



}