package com.example.challenge_2

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.challenge_2.ViewModel.AuthViewModel
import com.example.challenge_2.ViewModel.EditViewModel
import com.example.challenge_2.adapter.KeranjangAdapter
import com.example.challenge_2.api.APIClient
import com.example.challenge_2.databinding.FragmentPesanBinding
import com.example.challenge_2.model.CartChart
import com.example.challenge_2.model.MenuDatabase
import com.example.challenge_2.model.Order
import com.example.challenge_2.model.OrderRequest
import com.example.challenge_2.model.OrderResponse
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback
import kotlin.math.log

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PesanFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PesanFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var _binding : FragmentPesanBinding? = null
    private val binding get() = _binding!!
    private val viewModel: EditViewModel by viewModels()
    private val authModel: AuthViewModel by viewModels()
    var countInc: Int = 0
    var countDecr: Int = 0
    var priceCount: Int = 0
    var username : String = ""

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
        _binding = FragmentPesanBinding.inflate(inflater, container, false)
        val bottomNavigationView = activity?.findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView?.visibility = View.GONE
        authModel.fetchData { myUser ->
            username = myUser.username.toString()
        }

        val database = MenuDatabase.getInstance(requireActivity()).simpleCartDao
        val dataFromDatabase = database.getAllItem()
        val priceFromDatabase = database.getPriceCount()
        val adapter = KeranjangAdapter(requireActivity())
        var order: Order = Order()
        dataFromDatabase.observe(requireActivity(), Observer {cartChartList ->
            adapter.setData(cartChartList)
            for (jumlahData in cartChartList){
                order = Order(
                    nama = jumlahData.itemMenu.toString(),
                    qty = jumlahData.itemQuantity.toInt(),
                    catatan = "Nice!!",
                    harga = jumlahData.itemHargaSatuan?.toInt()
                )
                Log.e("Di Loop", order.toString())

            }
        })
        priceFromDatabase.observe(requireActivity(), Observer{price ->
            binding.tvTotalHargaFinal.text = price.toString()
        })
        val rvKeranjang = binding.rvKeranjang
        rvKeranjang.adapter = adapter
        binding.rvKeranjang.layoutManager = LinearLayoutManager(activity)
        binding.btnPesan.setOnClickListener {
            sendOrder(order)
            Log.e("SimpleNetworking", order.toString())
        }
        adapter.setOnIncrementClickCallback(object : KeranjangAdapter.OnIncrementClickCallback{
            override fun onIncrementClicked(data: CartChart, updateId: Long?) {
                viewModel._edCounter.value = data.itemQuantity
                viewModel._priceCounter.value = data.itemHarga!!.toInt()
                val observer = Observer<Int>{newValue ->
                    countInc = newValue
                }
                val priceObserver = Observer<Int>{newPrice ->
                    priceCount = newPrice
                }
                val harga: Int = data.itemHargaSatuan!!
                viewModel.edcounter.observe(requireActivity(), observer)
                viewModel.pricecounter.observe(requireActivity(), priceObserver)
                wIncrementCount(harga)
                val price = priceCount.toString()
                database.updateQuantityByItemId(countInc,price,updateId)
                viewModel.edcounter.removeObserver(observer)
                viewModel.pricecounter.removeObserver(priceObserver)
            }
        })

        adapter.setOnDecrementClickCallback(object : KeranjangAdapter.OnDecrementClickCallback{
            override fun onDecrementClicked(data: CartChart, updateId: Long?) {
                viewModel._edCounter.value = data.itemQuantity
                viewModel._priceCounter.value = data.itemHarga!!.toInt()
                val observer = Observer<Int>{newValue ->
                    countDecr = newValue
                }
                val priceObserver = Observer<Int>{newPrice ->
                    priceCount = newPrice
                }
                val harga: Int = data.itemHargaSatuan!!
                viewModel.edcounter.observe(requireActivity(), observer)
                viewModel.pricecounter.observe(requireActivity(), priceObserver)
                wDecrementCount(harga)
                val price = priceCount.toString()
                database.updateQuantityByItemId(countDecr,price,updateId)
                viewModel.edcounter.removeObserver(observer)
                viewModel.pricecounter.removeObserver(priceObserver)
            }
        })

        binding.topAppBar.setNavigationOnClickListener {
            findNavController().navigate(R.id.action_pesanFragment_to_keranjangFragment)
        }



        return binding.root
    }

    fun sendOrder(order: Order){
        val database = MenuDatabase.getInstance(requireActivity()).simpleCartDao
        var request = OrderRequest(
            username = username,
            total = priceCount,
            orders = listOf(order)
        )

        APIClient.instance.postOrder(request)
            .enqueue(object : retrofit2.Callback<OrderResponse> {
                override fun onResponse(
                    call: Call<OrderResponse>,
                    response: Response<OrderResponse>
                ){
                    val code = response.code()
                    if(code == 201){
                        Toast.makeText(requireActivity(), "Pesanan Berhasil Dipesan", Toast.LENGTH_LONG).show()
                        database.delete(CartChart())
                        findNavController().navigate(R.id.action_pesanFragment_to_mainFragment3)
                    }else{
                        Toast.makeText(requireActivity(), "Pesanan Gagal Dipesan", Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<OrderResponse>, t: Throwable) {
                    TODO("Not yet implemented")
                }
            })

    }
    private fun wDecrementCount(harga: Int) {
        viewModel.DecrementCount(harga)
    }

    private fun wIncrementCount(harga: Int) {
        viewModel.IncrementCount(harga)
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment PesanFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PesanFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}