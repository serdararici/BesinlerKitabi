package com.serdararici.besinlerkitabi.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.serdararici.besinlerkitabi.R
import com.serdararici.besinlerkitabi.databinding.FragmentBesinDetayiBinding
import com.serdararici.besinlerkitabi.util.gorselIndir
import com.serdararici.besinlerkitabi.util.placeHolderYap
import com.serdararici.besinlerkitabi.viewmodel.BesinDetayiViewModel

class BesinDetayiFragment : Fragment() {
    private lateinit var viewModel : BesinDetayiViewModel
    private var fragmentBesinDetayiBinding: FragmentBesinDetayiBinding? = null
    private var besinId=0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_besin_detayi, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let{
            besinId = BesinDetayiFragmentArgs.fromBundle(it).besinid

        }

        viewModel = ViewModelProvider(this).get(BesinDetayiViewModel::class.java)
        viewModel.roomVerisiniAl(besinId)

        val binding= FragmentBesinDetayiBinding.bind(view)
        fragmentBesinDetayiBinding=binding



        observeLiveData()
    }

    fun observeLiveData() {
        viewModel.besinLiveData.observe(viewLifecycleOwner, Observer { besin ->

            besin?.let{

                fragmentBesinDetayiBinding!!.besinIsim.text = it.besinIsim
                fragmentBesinDetayiBinding!!.besinKalori.text = it.besinKalori
                fragmentBesinDetayiBinding!!.besinKarbonhidrat.text = it.besinKarbonhidrat
                fragmentBesinDetayiBinding!!.besinProtein.text = it.besinProtein
                fragmentBesinDetayiBinding!!.besinYag.text = it.besinYag
                context?.let {
                    fragmentBesinDetayiBinding!!.besinImage.gorselIndir(besin.besinGorsel, placeHolderYap(it))
                }
            }
        })
    }

}