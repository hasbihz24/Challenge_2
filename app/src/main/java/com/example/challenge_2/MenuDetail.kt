package com.example.challenge_2

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.challenge_2.databinding.FragmentMainBinding
import com.example.challenge_2.databinding.FragmentMenuDetailBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MenuDetail.newInstance] factory method to
 * create an instance of this fragment.
 */
class MenuDetail : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var _binding: FragmentMenuDetailBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by viewModels()
    var count = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMenuDetailBinding.inflate(inflater, container, false)
        val bottomNavigationView = activity?.findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView?.visibility = View.GONE
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bundle = arguments
        val data = bundle?.getParcelable<MyMenu>("DataMenu")
        if (data != null) {
            binding.ivBanner.setImageResource(data.gambar)
            binding.tvNama.text = data.nama.toString()
            binding.tvHarga.text = data.harga.toString()
            binding.tvAlamat.text = data.lokasi.toString()
            binding.tvDeskripsi.text = data.deskripsi.toString()
        }
        val urlData = data?.urlLokasi
        val observer = Observer<Int>{newValue ->
            binding.display.text = newValue.toString()
            count = newValue

        }
        viewModel.counter.observe(requireActivity(), observer)
        binding.tvAlamat.setOnClickListener {
            openMaps(urlData)
        }
        binding.increment.setOnClickListener {
            wIncrementCount()
        }
        binding.decrement.setOnClickListener {
            wDecrementCount()
        }

        binding.btnKeranjang.setOnClickListener {
            MasukKeranjang(data)

        }

    }

    private fun MasukKeranjang(data: MyMenu?) {
       var dataSource = MenuDatabase.getInstance(requireActivity()).simpleCartDao
        dataSource.insert(CartChart(itemMenu = data?.nama,
            itemGambar = data?.gambar,
            itemHarga = data?.harga,
            itemQuantity = count))
        var nama = data?.nama
        val cekData = dataSource.getAllItemByName(nama)

        if(cekData != null){
            Toast.makeText(requireActivity(),"Data Berhasil Ditambahkan", Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(requireActivity(),"Data Gagal Ditambahkan", Toast.LENGTH_SHORT).show()
        }
    }

    private fun wDecrementCount() {
        viewModel.DecrementCount()
    }

    private fun wIncrementCount() {
        viewModel.IncrementCount()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MenuDetail.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MenuDetail().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    fun openMaps(urlData: String?){
        val mapsUri = Uri.parse(urlData)
        val mapIntent = Intent(Intent.ACTION_VIEW, mapsUri)
        mapIntent.setPackage("com.google.android.apps.maps")
        startActivity(mapIntent)
    }

}