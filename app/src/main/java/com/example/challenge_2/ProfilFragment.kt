package com.example.challenge_2

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import com.example.challenge_2.Activity.UserActivity
import com.example.challenge_2.ViewModel.AuthViewModel
import com.example.challenge_2.ViewModel.MainViewModel
import com.example.challenge_2.databinding.FragmentProfilBinding
import com.example.challenge_2.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ProfilFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfilFragment : Fragment() {
    private lateinit var mAuth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var userRef: DatabaseReference
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var _binding : FragmentProfilBinding? = null
    private val viewModel: AuthViewModel by viewModels()
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
        _binding = FragmentProfilBinding.inflate(inflater, container, false)
        mAuth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance("https://challenge-binar-152a8-default-rtdb.asia-southeast1.firebasedatabase.app/")
        userRef = database.reference.child("user")
        viewModel.fetchData { myUser ->
            binding.EtUsername.setText(myUser.username)
            binding.EtPassword.setText(myUser.password)
            binding.EtEmail.setText(myUser.email)
            binding.EtNoTelp.setText(myUser.phone)
        }
        binding.BtnLogout.setOnClickListener {
            mAuth.signOut()
            val intent = Intent(requireActivity(), UserActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }
        binding.topAppBar.setOnMenuItemClickListener {menuItem ->
            when(menuItem.itemId){
                R.id.editProfileFragment -> {
                    findNavController().navigate(R.id.action_profilFragment_to_editProfileFragment)
                    true
                }
                else -> {
                    false
                }
            }

        }
        return binding.root

    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ProfilFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProfilFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}