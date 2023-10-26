package com.example.challenge_2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.challenge_2.ViewModel.EditViewModel
import com.example.challenge_2.adapter.KeranjangAdapter
import com.example.challenge_2.databinding.FragmentKeranjangBinding
import com.example.challenge_2.model.CartChart
import com.example.challenge_2.model.MenuDatabase

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [KeranjangFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class KeranjangFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var _binding : FragmentKeranjangBinding? = null
    private val binding get() = _binding!!
    private val viewModel: EditViewModel by viewModels()
    var countInc: Int = 0
    var countDecr: Int = 0
    var priceCount: Int = 0
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
        _binding = FragmentKeranjangBinding.inflate(inflater, container, false)
        val database = MenuDatabase.getInstance(requireActivity()).simpleCartDao
        val dataFromDatabase = database.getAllItem()
        var priceFromDatabase = database.getPriceCount()
        val adapter = KeranjangAdapter(requireActivity())
        dataFromDatabase.observe(requireActivity(), Observer {cartChartList ->
            adapter.setData(cartChartList)
        })
        priceFromDatabase.observe(requireActivity(), Observer{price ->
            binding.etTotalHarga.setText(price.toString())
        })
        var rvKeranjang = binding.rvKeranjang
        rvKeranjang.adapter = adapter
        binding.rvKeranjang.layoutManager = LinearLayoutManager(activity)

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

        binding.btnPesan.setOnClickListener {
            findNavController().navigate(R.id.action_keranjangFragment_to_pesanFragment)
        }
        return binding.root
    }
    private fun wDecrementCount(harga: Int) {
        viewModel.DecrementCount(harga)
    }

    private fun wIncrementCount(harga: Int) {
        viewModel.IncrementCount(harga)
    }
    companion object {
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            KeranjangFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}