package com.application.fasrecon.ui.historychatbot

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.application.fasrecon.R
import com.application.fasrecon.databinding.FragmentHistoryChatbotBinding
import com.application.fasrecon.ui.chatbot.ChatbotActivity

class HistoryChatbotFragment : Fragment() {

    private var _binding: FragmentHistoryChatbotBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHistoryChatbotBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setActionBar()

        binding?.newChatbot?.setOnClickListener {
            val intent = Intent(requireActivity(), ChatbotActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setActionBar() {
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding?.topAppBar)
        (requireActivity() as AppCompatActivity).supportActionBar?.title =
            getString(R.string.history_chatbot)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}