package com.application.fasrecon.ui.historychatbot

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.application.fasrecon.R
import com.application.fasrecon.databinding.FragmentHistoryChatbotBinding

class HistoryChatbotFragment : Fragment() {

    private var _binding: FragmentHistoryChatbotBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryChatbotBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setActionBar()

        binding.newChatbot.setOnClickListener {

        }
    }

    private fun setActionBar() {
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.topAppBar)
        (requireActivity() as AppCompatActivity).supportActionBar?.title =
            getString(R.string.chatbot)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}