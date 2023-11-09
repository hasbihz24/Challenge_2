package com.example.challenge_2

import android.annotation.SuppressLint
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.challenge_2.ViewModel.ApiViewModel
import com.example.challenge_2.adapter.MenuAdapter
import com.example.challenge_2.api.APIClient
import com.example.challenge_2.databinding.FragmentMainBinding
import com.example.challenge_2.model.Data
import com.example.challenge_2.model.MenuResponse
import com.example.challenge_2.model.Status
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.Gson
import org.koin.android.ext.android.inject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
    private var param1: String? = null
    private var param2: String? = null
    private var isGrid = true
    private var _binding : FragmentMainBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ApiViewModel by inject()
    private val dataList = ArrayList<Data>()

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
        //setupRecycleView(isGrid)
        setupRecycleViewCoroutines(isGrid)
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

    private fun setupRecycleViewCoroutines(isGrid : Boolean){
        viewModel.getAllMenu().observe(requireActivity()){
            when(it.status){
                Status.SUCCESS -> {
                    Log.e("Data API", Gson().toJson(it.data))
                    binding.progressBar.isVisible = false
                    val imageUrl = it.data?.data?.get(0)?.imageUrl
                    rvMenu = binding.rvMenu
                    dataList.clear()
                    dataList.addAll(it.data!!.data)
                    val listMenuAdapter = MenuAdapter(dataList, isGrid)
                    rvMenu.adapter = listMenuAdapter
                    if(isGrid){
                        rvMenu.layoutManager =  GridLayoutManager(activity, 2)
                    }else{
                        rvMenu.layoutManager = LinearLayoutManager(activity)
                    }
                    listMenuAdapter.setOnItemClickCallback(object : MenuAdapter.OnItemClickCallback{
                        override fun onItemClicked(data: Data) {
                            val  mBundle = Bundle()
                            mBundle.putParcelable("DataMenu", data)
                            findNavController().navigate(R.id.action_mainFragment3_to_menuDetail2, mBundle)
                        }
                    })
                }
                Status.LOADING -> {
                    binding.progressBar.isVisible = true
                }
                Status.ERROR -> {
                    Log.e("API Data", it.message.toString())
                    binding.progressBar.isVisible = false
                    Toast.makeText(requireActivity(),"Data Error", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
    private fun setupRecycleView(isGrid: Boolean) {
//        list.clear()
//        list.addAll(getListMenu())
        APIClient.instance.getMenu()
            .enqueue(object : Callback<MenuResponse>{
                @SuppressLint("SuspiciousIndentation")
                override fun onResponse(
                    call: Call<MenuResponse>,
                    response: Response<MenuResponse>
                ) {
                    val body = response.body()
                    Log.e("SimpleNetworking", Gson().toJson(body))
                    body?.let {
                        val data = body.data
                        val status = if (body.status != null) true else false
                        if(status){
                            if (!data.isNullOrEmpty()){
                                binding.progressBar.isVisible = false
                                rvMenu = binding.rvMenu
                                dataList.clear()
                                dataList.addAll(it.data)
                                val listMenuAdapter = MenuAdapter(dataList, isGrid)
                                rvMenu.adapter = listMenuAdapter
                                if(isGrid){
                                    rvMenu.layoutManager =  GridLayoutManager(activity, 2)
                                }else{
                                    rvMenu.layoutManager = LinearLayoutManager(activity)
                                }
                                listMenuAdapter.setOnItemClickCallback(object : MenuAdapter.OnItemClickCallback{
                                    override fun onItemClicked(data: Data) {
                                        val  mBundle = Bundle()
                                        mBundle.putParcelable("DataMenu", data)
                                        findNavController().navigate(R.id.action_mainFragment3_to_menuDetail2, mBundle)
                                    }
                                })
                            }
                            else {
                                Log.e("SimpleNetworking", "Kosong")
                            }
                        }else{
                            Log.e("SimpleNetworking", "KOSONG")
                        }
                    }
                }

                override fun onFailure(call: Call<MenuResponse>, t: Throwable) {
                    Log.e("SimpleNetworking", t.message.toString())
                }
            })

//        rvMenu.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
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


}