package com.example.challenge_2.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.challenge_2.model.CartChart
import com.example.challenge_2.model.MenuDatabase
import com.example.challenge_2.databinding.MyCartItemsBinding

class KeranjangAdapter(private val context: Context):RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = MyCartItemsBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return LinearCartHolder(binding)
    }
    private lateinit var onIncrementClickCallback: OnIncrementClickCallback
    private lateinit var onDecrementClickCallback: OnDecrementClickCallback
    fun setOnDecrementClickCallback(onDecrementClickCallback: OnDecrementClickCallback) {
        this.onDecrementClickCallback = onDecrementClickCallback
    }
    fun setOnIncrementClickCallback(onIncrementClickCallback: OnIncrementClickCallback) {
        this.onIncrementClickCallback = onIncrementClickCallback
    }

    private val dataList = mutableListOf<CartChart>()
    override fun getItemCount(): Int = dataList.size
    var count = 0
    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<CartChart>) {
        // Mengisi data dengan data dari database
        this.dataList.clear()
        this.dataList.addAll(data)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val linearHolder = holder as LinearCartHolder

        linearHolder.onBind(dataList[position])
        holder.binding.btnDelete.setOnClickListener {
            val deletedItem =  dataList[position].itemId
            val database = MenuDatabase.getInstance(context).simpleCartDao
            database.delteByItemId(deletedItem)
            dataList.removeAt(position)
            notifyItemRemoved(position)
        }

        holder.binding.increment.setOnClickListener {
            val updatedItem =  dataList[position].itemId
            onIncrementClickCallback.onIncrementClicked((dataList[holder.adapterPosition]),(updatedItem))
        }
        holder.binding.decrement.setOnClickListener {
            val updatedItem =  dataList[position].itemId
           onDecrementClickCallback.onDecrementClicked((dataList[holder.adapterPosition]),(updatedItem))
        }
    }
    interface OnIncrementClickCallback {
        fun onIncrementClicked(data: CartChart, updateId: Long?)
    }
    interface OnDecrementClickCallback {
        fun onDecrementClicked(data: CartChart, updateId: Long?)
    }


}

class LinearCartHolder(val binding: MyCartItemsBinding) : RecyclerView.ViewHolder(binding.root) {
    fun onBind(data: CartChart){
        val (Itemid,nama,harga,hargasatuan,gambar, jumlah) = data
        Glide.with(binding.IvMenu).load(gambar).into(binding.IvMenu)
        binding.display.text = jumlah.toString()
        binding.tvNamaMenu.text = nama
        binding.tvHargaMenu.text = harga

    }
}
