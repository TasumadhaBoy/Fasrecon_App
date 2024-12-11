package com.application.fasrecon.ui.historychatbot

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.application.fasrecon.R
import com.application.fasrecon.data.local.entity.ChatEntity
import com.application.fasrecon.databinding.FragmentHistoryChatbotBinding
import com.application.fasrecon.ui.chatbot.ChatbotActivity
import com.application.fasrecon.ui.myclothes.MyClothesTypeAdapter
import com.application.fasrecon.ui.viewmodelfactory.ViewModelFactoryUser

class HistoryChatbotFragment : Fragment() {

    private var _binding: FragmentHistoryChatbotBinding? = null
    private val binding get() = _binding
    private val historyChatbotViewModel: HistoryChatbotViewModel by viewModels { ViewModelFactoryUser.getInstance(requireActivity()) }

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

        historyChatbotViewModel.getAllHistoryChat().observe(requireActivity()) {
            setAllHistoryChat(it)
        }

        val orientation = resources.configuration.orientation
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            val layoutManager = LinearLayoutManager(requireActivity())
            binding?.listChatbotHistory?.layoutManager = layoutManager
        } else {
            val layoutManager = GridLayoutManager(requireActivity(), 2)
            binding?.listChatbotHistory?.layoutManager = layoutManager
        }
    }

    private fun setActionBar() {
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding?.topAppBar)
        (requireActivity() as AppCompatActivity).supportActionBar?.title =
            getString(R.string.history_chatbot)
    }

    private fun setAllHistoryChat(listChat: List<ChatEntity>) {
        val adapter = HistoryChatbotAdapter()
        adapter.submitList(listChat)
        binding?.listChatbotHistory?.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}