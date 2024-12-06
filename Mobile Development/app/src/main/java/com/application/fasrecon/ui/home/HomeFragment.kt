package com.application.fasrecon.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import cn.pedant.SweetAlert.SweetAlertDialog
import com.application.fasrecon.R
import com.application.fasrecon.data.model.UserModel
import com.application.fasrecon.databinding.FragmentHomeBinding
import com.application.fasrecon.ui.chatbot.ChatbotActivity
import com.application.fasrecon.ui.login.LoginActivity
import com.application.fasrecon.ui.viewmodelfactory.ViewModelFactoryUser
import com.application.fasrecon.util.WrapMessage
import com.bumptech.glide.Glide

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val homeViewModel: HomeViewModel by viewModels {
        ViewModelFactoryUser.getInstance(
            requireActivity()
        )
    }

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

        homeViewModel.getUserData()
        homeViewModel.errorHandling.observe(requireActivity()) {
            handleError(it)
        }

        homeViewModel.userData.observe(requireActivity()) {
            setUserData(it)
        }
    }

    private fun setUserData(userData: UserModel) {
        binding.tvUserHomepage.text = getString(R.string.hai_user, userData.name)
        if (userData.photoUrl != null) {
            Glide.with(requireActivity())
                .load(userData.photoUrl)
                .into(binding.imageProfileHomepage)
        }
    }

    private fun handleError(msg: WrapMessage<String?>) {
        msg.getDataIfNotDisplayed().let { message ->
            SweetAlertDialog(requireActivity(), SweetAlertDialog.ERROR_TYPE)
                .setTitleText("Failed")
                .setConfirmText("Try Again")
                .setContentText(message)
                .setConfirmClickListener { sDialog ->
                    sDialog.dismissWithAnimation()
                    homeViewModel.getUserData()
                }
                .show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}