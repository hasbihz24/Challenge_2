package com.example.challenge_2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.RecyclerView

class MenuAdapter(private val listMenu: ArrayList<MyMenu>):RecyclerView.Adapter<MenuAdapter.ListViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }
    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val gambar : ImageView = itemView.findViewById(R.id.Iv_menu)
        val nama : TextView = itemView.findViewById(R.id.tvNamaMenu)
        val harga : TextView = itemView.findViewById(R.id.tvHargaMenu)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.my_menu_items,parent, false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int = listMenu.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val(gambar, nama, harga) = listMenu[position]
        holder.gambar.setImageResource(gambar)
        holder.nama.text = nama
        holder.harga.text = harga
        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked((listMenu[holder.adapterPosition]))
        }

    }


    interface OnItemClickCallback {
        fun onItemClicked(data: MyMenu)
    }

}