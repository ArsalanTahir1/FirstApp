package com.example.firstapp.details

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.firstapp.*

class ForecastDetailsFragment : Fragment() {

    /* lateinit var means this is a variable that at somepoint will be
       assigned a value but itn't going to be assigned in right now.We did
       this cuz it needs a context and without going to onCreate, there isn't
       any context
    * */

                            //navArgs look for args and get data for us
    private val args : ForecastDetailsFragmentArgs by navArgs()
    private lateinit var tempDisplaySettingsManager: TempDisplaySettingsManager


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        val layout = inflater.inflate(R.layout.fragement_forecast_details,container,false)



        tempDisplaySettingsManager = TempDisplaySettingsManager(requireContext())

        val details_temp_text  = layout.findViewById<TextView>(R.id.details_tempText)
        val details_description_text = layout.findViewById<TextView>(R.id.details_description_text)

        details_temp_text.text = formatTempForDisplay(args.temp,tempDisplaySettingsManager.getTempDisplaySetting() )
        details_description_text.text = args.description


        return layout

    }






}