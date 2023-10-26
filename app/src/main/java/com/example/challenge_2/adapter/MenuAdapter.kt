package com.example.challenge_2.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.challenge_2.R
import com.example.challenge_2.databinding.MyMenuItemsBinding
import com.example.challenge_2.model.Data

class MenuAdapter(val listMenu: ArrayList<Data>,val isGrid: Boolean):RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback
    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if(isGrid){
            val context = parent.context
            val inflater = LayoutInflater.from(context)
            val menuView = inflater.inflate(R.layout.grid_menu_item, parent, false)
            return GridMenuHolder(menuView)
        }
        else{
            val binding = MyMenuItemsBinding.inflate(LayoutInflater.from(parent.context),parent,false)
            return LinearMenuHolder(binding)
        }
//        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.my_menu_items,parent, false)
//        return ListViewHolder(view)
    }



    override fun getItemCount(): Int = listMenu.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(isGrid){
            val gridHolder = holder as GridMenuHolder
            gridHolder.onBind(listMenu[position])
        }
        else{
            val linearHolder = holder as LinearMenuHolder
            linearHolder.onBind(listMenu[position])
        }
        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked((listMenu[holder.adapterPosition]))
        }

    }


    interface OnItemClickCallback {
        fun onItemClicked(data: Data)
    }

}

class GridMenuHolder(val itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun onBind(listMenu: Data){
        val ivMenu = itemView.findViewById<ImageView>(R.id.Iv_menu)
        val tvMenu = itemView.findViewById<TextView>(R.id.tvNamaMenu)
        val hargaMenu = itemView.findViewById<TextView>(R.id.tvHargaMenu)
        val(alamat, createAt, detail, harga, hargaFormat, id, gambar, nama, updateAt) = listMenu
        Glide.with(ivMenu).load(gambar).into(ivMenu)
        tvMenu.text =  nama
        hargaMenu.text = hargaFormat
    }
}

class LinearMenuHolder(val binding: MyMenuItemsBinding): RecyclerView.ViewHolder(binding.root) {
    fun onBind(listMenu: Data){
        val(alamat, createAt, detail, harga, hargaFormat, id, gambar, nama, updateAt) = listMenu
        Glide.with(binding.IvMenu)
            .load(gambar)
            .into(binding.IvMenu);
        binding.tvNamaMenu.text = nama
        binding.tvHargaMenu.text = hargaFormat
    }
}
