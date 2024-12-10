package com.application.fasrecon.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import cn.pedant.SweetAlert.SweetAlertDialog
import com.application.fasrecon.R
import com.application.fasrecon.data.local.entity.UserEntity
import com.application.fasrecon.databinding.FragmentHomeBinding
import com.application.fasrecon.ui.chatbot.ChatbotActivity
import com.application.fasrecon.ui.viewmodelfactory.ViewModelFactoryUser
import com.application.fasrecon.util.WrapMessage
import com.bumptech.glide.Glide
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val homeViewModel: HomeViewModel by viewModels {
        ViewModelFactoryUser.getInstance(
            requireActivity()
        )
    }
    private var user = FirebaseAuth.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.cardContainerCamera.setOnClickListener {

        }

        binding.cardContainerChatbot.setOnClickListener {
            startActivity(Intent(requireActivity(), ChatbotActivity::class.java))
        }

        homeViewModel.getUserData().observe(requireActivity()) {
           setUserData(it)
        }
    }

    private fun setUserData(userData: UserEntity) {
        binding.tvUserHomepage.text = getString(R.string.hai_user, userData.name)
        if (userData.photoUrl != null) {
            Log.d("test5", user.currentUser?.photoUrl.toString())
            binding.imageProfileHomepage.setImageURI(user.currentUser?.photoUrl)
        }
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}