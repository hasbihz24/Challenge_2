package com.example.challenge_2

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.challenge_2.databinding.FragmentMainBinding
import com.example.challenge_2.databinding.FragmentMenuDetailBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MainFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MainFragment : Fragment() {
    private lateinit var sharedPreferences: SharedPreferences
    private val sharedPrefName = "rvlayout"
    private lateinit var rvMenu: RecyclerView
    private val list = ArrayList<MyMenu>()
    private lateinit var lyUtama: ConstraintLayout
    private var param1: String? = null
    private var param2: String? = null
    private var isGrid = true
    private var _binding : FragmentMainBinding? = null
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

//        val view = inflater.inflate(R.layout.fragment_main, container, false)
//        rvMenu = view.findViewById(R.id.rvMenu)
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        sharedPreferences = requireActivity().getSharedPreferences(sharedPrefName, MODE_PRIVATE)
        isGrid = sharedPreferences.getBoolean("Grid?", isGrid)
        setupRecycleView(isGrid)
        setupChangeLayout()
        val bottomNavigationView = activity?.findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView?.visibility = View.VISIBLE
        return binding.root
    }

    private fun setupChangeLayout() {
        binding.changelayout.setOnClickListener {
            isGrid = !isGrid
            setupRecycleView(isGrid)
            val editor = sharedPreferences.edit()
            editor.putBoolean("Grid?", isGrid)
            editor.apply()
        }
    }


    private fun setupRecycleView(isGrid: Boolean) {
        list.clear()
        list.addAll(getListMenu())
        rvMenu = binding.rvMenu
//        rvMenu.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        val listMenuAdapter = MenuAdapter(list, isGrid)
        rvMenu.adapter = listMenuAdapter
        if(isGrid){
            rvMenu.layoutManager =  GridLayoutManager(activity, 2)
        }else{
            rvMenu.layoutManager = LinearLayoutManager(activity)
        }
        listMenuAdapter.setOnItemClickCallback(object : MenuAdapter.OnItemClickCallback{
            override fun onItemClicked(data: MyMenu) {
                val  mBundle = Bundle()
                mBundle.putParcelable("DataMenu", data)
                findNavController().navigate(R.id.action_mainFragment3_to_menuDetail2, mBundle)
            }
        })
    }

    companion object {
        val EXTRA_DATA = "EXTRA_DATA"
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MainFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
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

}