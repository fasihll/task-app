package com.otakkanan.taskapp.ui.auth.register

import android.content.Intent
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView
import com.otakkanan.taskapp.R
import com.otakkanan.taskapp.ui.auth.login.LoginActivity

class RegisterActivity : AppCompatActivity() {

    private lateinit var registerButton: MaterialButton
    private lateinit var loginTextView: MaterialTextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        registerButton = findViewById(R.id.registerButton)
        loginTextView = findViewById(R.id.loginTextView)

        val toolbar: Toolbar = findViewById(R.id.topAppBar)
        setSupportActionBar(toolbar)

        // Enable the Up button
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        // Handle navigation button click
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        registerButton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        // Make "Masuk" clickable and different color
        val text = "Sudah punya akun? Masuk"
        val spannableString = SpannableString(text)

        val clickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                startActivity(intent)
            }

            override fun updateDrawState(ds: android.text.TextPaint) {
                ds.isUnderlineText = false  // Hapus underline
            }
        }

        val colorSpan = ForegroundColorSpan(ContextCompat.getColor(this, R.color.md_theme_primaryContainer))

        val startIndex = text.indexOf("Masuk")
        val endIndex = startIndex + "Masuk".length

        spannableString.setSpan(clickableSpan, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannableString.setSpan(colorSpan, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        loginTextView.text = spannableString
        loginTextView.movementMethod = LinkMovementMethod.getInstance()
    }
}