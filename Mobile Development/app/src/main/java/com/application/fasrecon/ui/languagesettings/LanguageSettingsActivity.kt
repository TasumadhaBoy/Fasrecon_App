package com.application.fasrecon.ui.languagesettings

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.application.fasrecon.R
import com.application.fasrecon.databinding.ActivityLanguageSettingsBinding

class LanguageSettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLanguageSettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLanguageSettingsBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_language_settings)

        val layoutManager = LinearLayoutManager(this)
        binding.listLanguage.layoutManager = layoutManager

        setListLanguage()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun setListLanguage() {
        val languageData = LanguageObject(this).allLanguage()
        val adapter = LanguageListAdapter()
        adapter.submitList(languageData)
        binding.listLanguage.adapter = adapter
    }
}