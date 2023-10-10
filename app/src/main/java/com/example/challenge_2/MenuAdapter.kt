package com.example.challenge_2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.RecyclerView
import com.example.challenge_2.databinding.FragmentMenuDetailBinding
import com.example.challenge_2.databinding.MyMenuItemsBinding

class MenuAdapter(private val listMenu: ArrayList<MyMenu>, val isGrid: Boolean):RecyclerView.Adapter<RecyclerView.ViewHolder>() {
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
        fun onItemClicked(data: MyMenu)
    }

}

class GridMenuHolder(val itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun onBind(listMenu: MyMenu){
        val ivMenu = itemView.findViewById<ImageView>(R.id.Iv_menu)
        val tvMenu = itemView.findViewById<TextView>(R.id.tvNamaMenu)
        val hargaMenu = itemView.findViewById<TextView>(R.id.tvHargaMenu)
        val(gambar, nama, harga) = listMenu
        ivMenu.setImageResource(gambar)
        tvMenu.text =  nama
        hargaMenu.text = harga
    }
}

class LinearMenuHolder(val binding: MyMenuItemsBinding): RecyclerView.ViewHolder(binding.root) {
    fun onBind(listMenu: MyMenu){
        val(gambar, nama, harga) = listMenu
        binding.IvMenu.setImageResource(gambar)
        binding.tvNamaMenu.text = nama
        binding.tvHargaMenu.text = harga
    }
}
