package com.serdararici.besinlerkitabi.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.serdararici.besinlerkitabi.R
import com.serdararici.besinlerkitabi.databinding.BesinRecyclerRowBinding
import com.serdararici.besinlerkitabi.model.Besin
import com.serdararici.besinlerkitabi.util.gorselIndir
import com.serdararici.besinlerkitabi.util.placeHolderYap
import com.serdararici.besinlerkitabi.view.BesinListesiFragmentDirections

class BesinRecyclerAdapter(val besinListesi : ArrayList<Besin>) : RecyclerView.Adapter<BesinRecyclerAdapter.BesinViewHolder> () {
    class BesinViewHolder(val binding: BesinRecyclerRowBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BesinViewHolder {
        val binding = BesinRecyclerRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.besin_recycler_row, parent, false)
        return BesinViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return besinListesi.size
    }

    override fun onBindViewHolder(holder: BesinViewHolder, position: Int) {
        // holder.binding.besin = besinListesi[position]     //data binding kullanıldığında bu şekilde uyapılıyor bne kullanmadım


        holder.binding.isim.text=besinListesi.get(position).besinIsim
        holder.binding.kalori.text=besinListesi.get(position).besinKalori

        holder.itemView.setOnClickListener {
            val action = BesinListesiFragmentDirections.actionBesinListesiFragmentToBesinDetayiFragment(besinListesi.get(position).uuid)
            Navigation.findNavController(it).navigate(action)
        }

        holder.binding.imageView.gorselIndir(besinListesi.get(position).besinGorsel, placeHolderYap(holder.itemView.context))


    }



    fun besinListesiniGuncelle(yeniBesinListesi: List<Besin>){
        besinListesi.clear()
        besinListesi.addAll(yeniBesinListesi)
        notifyDataSetChanged()
    }
}