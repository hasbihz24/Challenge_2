package com.example.challenge_2

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.example.challenge_2.databinding.FragmentMenuDetailBinding

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
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val namaData = arguments?.getString("nama")
        val hargaData = arguments?.getString("harga")
        val gambarData = arguments?.getInt("gambar")
        val lokasiData = arguments?.getString("lokasi")
        val deskData = arguments?.getString("desk")
        binding.ivBanner.setImageResource(gambarData!!)
        binding.tvNama.text = namaData.toString()
        binding.tvHarga.text = hargaData.toString()
        binding.tvAlamat.text = lokasiData.toString()
        binding.tvDeskripsi.text = deskData.toString()

        binding.tvAlamat.setOnClickListener {
            openMaps()
        }


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

    fun openMaps(){
        val urlData = arguments?.getString("url")
        val mapsUri = Uri.parse(urlData)
        val mapIntent = Intent(Intent.ACTION_VIEW, mapsUri)
        mapIntent.setPackage("com.google.android.apps.maps")
        startActivity(mapIntent)
    }

}