package com.application.fasrecon.ui.myoutfit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.application.fasrecon.databinding.FragmentMyoutfitBinding

class MyoutfitFragment : Fragment() {

    private var _binding: FragmentMyoutfitBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val myoutfitViewModel =
            ViewModelProvider(this).get(MyoutfitViewModel::class.java)

        _binding = FragmentMyoutfitBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textDashboard
        myoutfitViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}