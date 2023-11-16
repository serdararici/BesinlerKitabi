package com.serdararici.besinlerkitabi.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import androidx.recyclerview.widget.RecyclerView.VISIBLE
import com.serdararici.besinlerkitabi.R
import com.serdararici.besinlerkitabi.adapter.BesinRecyclerAdapter
import com.serdararici.besinlerkitabi.databinding.FragmentBesinListesiBinding
import com.serdararici.besinlerkitabi.viewmodel.BesinListesiViewModel

class BesinListesiFragment : Fragment() {

    private lateinit var viewModel: BesinListesiViewModel
    private val recyclerBesinAdapter = BesinRecyclerAdapter(arrayListOf())
    private var fragmentBesinListesiBinding: FragmentBesinListesiBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_besin_listesi, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding= FragmentBesinListesiBinding.bind(view)
        fragmentBesinListesiBinding=binding

        viewModel = ViewModelProvider(this).get(BesinListesiViewModel::class.java)
        viewModel.refreshData()

        binding.besinListRecycler.layoutManager = LinearLayoutManager(context)
        binding.besinListRecycler.adapter = recyclerBesinAdapter

        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.besinYukleniyor.visibility = View.VISIBLE
            binding.besinHataMesaji.visibility = View.GONE
            binding.besinListRecycler.visibility = View.GONE
            viewModel.refreshFromInternet()
            binding.swipeRefreshLayout.isRefreshing = false
        }

        observeLiveData()
    }

    fun observeLiveData(){
        viewModel.besinler.observe(viewLifecycleOwner, Observer { besinler ->
            besinler?.let{
                fragmentBesinListesiBinding!!.besinListRecycler.visibility= View.VISIBLE
                recyclerBesinAdapter.besinListesiniGuncelle(besinler)
            }
        })

        viewModel.besinHataMesaji.observe(viewLifecycleOwner, Observer { hata ->
            hata?.let{
                if(it){
                    fragmentBesinListesiBinding!!.besinHataMesaji.visibility = View.VISIBLE
                    fragmentBesinListesiBinding!!.besinListRecycler.visibility = View.GONE
                } else {
                    fragmentBesinListesiBinding!!.besinHataMesaji.visibility = View.GONE
                }
            }
        })

        viewModel.besinYukleniyor.observe(viewLifecycleOwner, Observer { yukleniyor ->
            yukleniyor?.let{
                if(it){
                    fragmentBesinListesiBinding!!.besinListRecycler.visibility = View.GONE
                    fragmentBesinListesiBinding!!.besinHataMesaji.visibility = View.GONE
                    fragmentBesinListesiBinding!!.besinYukleniyor.visibility = View.VISIBLE
                } else {
                    fragmentBesinListesiBinding!!.besinYukleniyor.visibility = View.GONE
                }
            }
        })
    }

}