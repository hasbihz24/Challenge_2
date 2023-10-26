package com.example.challenge_2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.challenge_2.ViewModel.AuthViewModel
import com.example.challenge_2.databinding.FragmentEditProfileBinding
import com.example.challenge_2.databinding.FragmentProfilBinding
import com.example.challenge_2.model.User

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [EditProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EditProfileFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private val viewModel: AuthViewModel by viewModels()
    private var _binding: FragmentEditProfileBinding? = null
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
        _binding = FragmentEditProfileBinding.inflate(inflater, container, false)

        viewModel.fetchData { myUser ->
            binding.EtUsername.setText(myUser.username)
            binding.EtPassword.setText(myUser.password)
            binding.EtEmail.setText(myUser.email)
            binding.EtNoTelp.setText(myUser.phone)
        }

        binding.topAppBar.setNavigationOnClickListener {
            findNavController().navigate(R.id.action_editProfileFragment_to_profilFragment)
        }

        binding.topAppBar.setOnMenuItemClickListener {menuItem ->
            when(menuItem.itemId){
                R.id.done_edit_profile -> {
                    val username = binding.EtUsername.text.toString()
                    val password = binding.EtPassword.text.toString()
                    val email = binding.EtEmail.text.toString()
                    val phone = binding.EtNoTelp.text.toString()
                    viewModel.fetchData { myUser ->
                        myUser.username = username
                        myUser.password = password
                        myUser.email = email
                        myUser.phone = phone

                        viewModel.editData(myUser){ success ->
                            if(success){
                                Toast.makeText(requireActivity(), "Data Berhasil diubah", Toast.LENGTH_LONG).show()
                                findNavController().navigate(R.id.action_editProfileFragment_to_profilFragment)
                            }else{
                                Toast.makeText(requireActivity(),"Data Gagal Diubah", Toast.LENGTH_LONG).show()
                            }
                        }
                    }
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
         * @return A new instance of fragment EditProfileFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            EditProfileFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}