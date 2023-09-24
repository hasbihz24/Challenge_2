package com.example.challenge_2

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var rvMenu: RecyclerView
    private val list = ArrayList<MyMenu>()
    private lateinit var lyUtama: ConstraintLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rvMenu = findViewById(R.id.rvMenu)
        list.addAll(getListMenu())
        rvMenu.layoutManager = LinearLayoutManager(this, GridLayoutManager.VERTICAL, false)
        val listMenuAdapter = MenuAdapter(list)
        rvMenu.adapter = listMenuAdapter
        listMenuAdapter.setOnItemClickCallback(object : MenuAdapter.OnItemClickCallback{
            override fun onItemClicked(data: MyMenu) {
                openFragment(data)
            }
        })

    }
    private fun getListMenu(): ArrayList<MyMenu> {
        val dataName = resources.getStringArray(R.array.nama_menu)
        val dataHarga = resources.getStringArray(R.array.harga_menu)
        val dataGambar = resources.obtainTypedArray(R.array.gambar_menu)
        val dataLokasi = resources.getStringArray(R.array.lokasi_menu)
        val dataURL = resources.getStringArray(R.array.url_menu)
        val dataDesk = resources.getStringArray(R.array.desk_menu)
        val listMenu = ArrayList<MyMenu>()

        for(i in dataHarga.indices){
            val menu = MyMenu(dataGambar.getResourceId(i, -1), dataName[i], dataHarga[i], dataLokasi[i], dataURL[i], dataDesk[i])
            listMenu.add(menu)
            }
        return listMenu
    }

    private fun openFragment(myMenu: MyMenu) {
        val detailFragment = MenuDetail()
        val fragmentManager: FragmentManager = supportFragmentManager
        val namaData = myMenu.nama
        val hargaData = myMenu.harga
        val gambarData = myMenu.gambar
        val lokasiData = myMenu.lokasi
        val urlData = myMenu.urlLokasi
        val deskData = myMenu.deskripsi
        val bundle = Bundle()
        bundle.putString("nama", namaData)
        bundle.putString("harga", hargaData)
        bundle.putInt("gambar", gambarData)
        bundle.putString("lokasi", lokasiData)
        bundle.putString("url", urlData)
        bundle.putString("desk", deskData)
        detailFragment.arguments = bundle
        // Membuat transaksi fragment
        val transaction = fragmentManager?.beginTransaction()
        transaction?.replace(R.id.fragmentContainer, detailFragment)
        transaction?.addToBackStack(null)
        transaction?.commit()


    }
    fun newInstance(): MenuDetail? {
        return MenuDetail()
    }

}