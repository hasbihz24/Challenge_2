package com.example.challenge_2

import android.os.Bundle
import android.view.Display
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.challenge_2.databinding.FragmentKeranjangBinding

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
        val adapter = KeranjangAdapter(requireActivity())
        dataFromDatabase.observe(requireActivity(), Observer {cartChartList ->
            adapter.setData(cartChartList)
        })
        var rvKeranjang = binding.rvKeranjang
        rvKeranjang.adapter = adapter
        binding.rvKeranjang.layoutManager = LinearLayoutManager(activity)

        adapter.setOnIncrementClickCallback(object : KeranjangAdapter.OnIncrementClickCallback{
            override fun onIncrementClicked(data: CartChart, updateId: Long?) {
                viewModel._edCounter.value = data.itemQuantity
                val observer = Observer<Int>{newValue ->
                    countInc = newValue
                }
                viewModel.edcounter.observe(requireActivity(), observer)
                wIncrementCount()
                database.updateQuantityByItemId(countInc, updateId)
                viewModel.edcounter.removeObserver(observer)
            }
        })

        adapter.setOnDecrementClickCallback(object : KeranjangAdapter.OnDecrementClickCallback{
            override fun onDecrementClicked(data: CartChart, updateId: Long?) {
                val observer = Observer<Int>{newValue ->
                    countDecr = newValue
                }

                viewModel.edcounter.observe(requireActivity(), observer)
                wDecrementCount()
                database.updateQuantityByItemId(countDecr, updateId)
                viewModel.edcounter.removeObserver(observer)
            }
        })
        return binding.root
    }
    private fun wDecrementCount() {
        viewModel.DecrementCount()
    }

    private fun wIncrementCount() {
        viewModel.IncrementCount()
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