package com.example.firstapp.details

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.firstapp.*
import com.example.firstapp.databinding.FragementForecastDetailsBinding
import com.example.firstapp.databinding.FragmentCurrentForecastFragementBinding
import java.text.SimpleDateFormat
import java.util.*
private val DATE_FORMAT = SimpleDateFormat("MM-dd-yyyy")
class ForecastDetailsFragement : Fragment() {



    private val args : ForecastDetailsFragementArgs by navArgs()


    private lateinit var viewModelFactory: ForecastDetailsViewModelFactory
    private val viewModel : ForecastDetilsViewModel by viewModels(
        factoryProducer = {viewModelFactory}
    )



    private  var _binding: FragementForecastDetailsBinding? = null
    // This property only valid between onCreateview and onDestroyView
    private  val binding get() = _binding!!

    private lateinit var tempDisplaySettingsManager: TempDisplaySettingsManager


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        _binding = FragementForecastDetailsBinding.inflate(inflater,container,false)
        viewModelFactory = ForecastDetailsViewModelFactory(args)
        tempDisplaySettingsManager = TempDisplaySettingsManager(requireContext())
        return binding.root

    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewStateObserver = Observer<ForecastDetailsViewState>{ viewState->
            //update the UI
            binding.detailsTempText.text = formatTempForDisplay(viewState.temp,tempDisplaySettingsManager.getTempDisplaySetting())
            binding.detailsDescriptionText.text = viewState.description
            binding.detailsText.text = viewState.date
            binding.detailsImg.load(viewState.iconUrl)

        }
        viewModel.viewState.observe(viewLifecycleOwner,viewStateObserver)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }






}