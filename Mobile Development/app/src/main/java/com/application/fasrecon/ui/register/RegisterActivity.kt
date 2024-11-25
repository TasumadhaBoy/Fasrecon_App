package com.application.fasrecon.ui.register

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.application.fasrecon.R
import com.application.fasrecon.databinding.ActivityRegisterBinding
import com.application.fasrecon.ui.BaseActivity
import com.application.fasrecon.ui.login.LoginActivity
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class RegisterActivity : BaseActivity(), View.OnClickListener {

    private lateinit var binding: ActivityRegisterBinding

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val gender = resources.getStringArray(R.array.gender_array)
        val arrayAdapter = ArrayAdapter(this, R.layout.dropdown_item, gender)
        val autoCompleteTextView = findViewById<AutoCompleteTextView>(R.id.autoCompleteTextView)
        autoCompleteTextView.setAdapter(arrayAdapter)

        val text = "Sudah punya akun? Login"
        val spannableString = SpannableString(text)

        val blueColor = ContextCompat.getColor(this, R.color.custom_blue)
        val blueColorSpan = ForegroundColorSpan(blueColor)
        spannableString.setSpan(blueColorSpan, 18, text.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        val clickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                val moveToMainActivity = Intent(this@RegisterActivity, LoginActivity::class.java)
                startActivity(moveToMainActivity)
            }

            override fun updateDrawState(ds: android.text.TextPaint) {
                super.updateDrawState(ds)
                ds.isUnderlineText = false
                ds.color = blueColor
            }
        }
        spannableString.setSpan(clickableSpan, 18, text.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        binding.textView.text = spannableString
        binding.textView.movementMethod = android.text.method.LinkMovementMethod.getInstance()
        binding.btnDaftar.setOnClickListener(this)
        binding.dateInput.transformIntoDatePicker(this, "yyyy/MM/dd", Date())
    }

    private fun EditText.transformIntoDatePicker(context: Context, format: String, maxDate: Date? = null) {
        isFocusableInTouchMode = true
        isClickable = false
        isFocusable = true

        val myCalendar = Calendar.getInstance()
        val datePickerOnDataSetListener =
            DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                myCalendar.set(Calendar.YEAR, year)
                myCalendar.set(Calendar.MONTH, monthOfYear)
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                val sdf = SimpleDateFormat(format, Locale.UK)
                setText(sdf.format(myCalendar.time))
            }

        setOnClickListener {
            DatePickerDialog(
                context, datePickerOnDataSetListener, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)
            ).run {
                maxDate?.time?.also { datePicker.maxDate = it }
                show()
            }
        }
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.btn_daftar -> {
                val username = binding.textInputUsername.text.toString()
                val email = binding.textInputEmail.text.toString()
                val password = binding.textInputPassword.text.toString()
                val gender = binding.autoCompleteTextView.text.toString()
                val date = binding.dateInput.text.toString()
                val checkBox = binding.checkBox

                if (username.isEmpty()){
                    binding.textInputUsername.setError(
                        resources.getString(R.string.field_username),
                        null
                    )
                    binding.textInputUsername.requestFocus()
                    return
                }

                if (email.isEmpty()) {
                    binding.textInputEmail.setError(
                        resources.getString(R.string.field_email),
                        null
                    )
                    binding.textInputEmail.requestFocus()
                    return
                }
                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email)
                        .matches() && email.isNotBlank()
                ) {
                    binding.textInputEmail.setError(
                        resources.getString(R.string.format_email),
                        null
                    )
                    return
                }
                if (password.isEmpty()) {
                    binding.textInputPassword.setError(
                        resources.getString(R.string.field_password),
                        null
                    )
                    binding.textInputPassword.requestFocus()
                    return
                }
                if (password.length < 8) {
                    binding.textInputPassword.setError(
                        resources.getString(R.string.format_password),
                        null
                    )
                    binding.textInputPassword.requestFocus()
                    return
                }
                if (gender.isEmpty()) {
                    binding.autoCompleteTextView.setError(
                        resources.getString(R.string.field_gender),
                        null
                    )
                    return
                }
                if (date.isEmpty()){
                    binding.dateInput.setError(
                        resources.getString(R.string.field_date),
                        null
                    )
                    return
                }
                if (!checkBox.isChecked){
                    binding.checkBox.setError(
                        resources.getString(R.string.field_checkbox),
                        null
                    )
                    return
                }
                Toast.makeText(this@RegisterActivity, "Daftar Berhasil", Toast.LENGTH_SHORT).show()
                val moveToMainActivity = Intent(this@RegisterActivity, LoginActivity::class.java)
                startActivity(moveToMainActivity)
            }
        }
    }
}