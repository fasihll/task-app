package com.otakkanan.taskapp.ui.auth.login

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.textview.MaterialTextView
import com.otakkanan.taskapp.R
import com.otakkanan.taskapp.ui.auth.register.RegisterActivity
import com.otakkanan.taskapp.ui.main.MainActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var emailTextInputLayout: TextInputLayout
    private lateinit var emailEditText: TextInputEditText
    private lateinit var passwordTextInputLayout: TextInputLayout
    private lateinit var passwordEditText: TextInputEditText
    private lateinit var loginButton: MaterialButton
    private lateinit var registerTextView: MaterialTextView
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        emailTextInputLayout = findViewById(R.id.emailTextInputLayout)
        emailEditText = findViewById(R.id.emailEditText)
        passwordTextInputLayout = findViewById(R.id.passwordTextInputLayout)
        passwordEditText = findViewById(R.id.passwordEditText)
        loginButton = findViewById(R.id.loginButton)
        registerTextView = findViewById(R.id.registerTextView)

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

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("appPrefs", Context.MODE_PRIVATE)

        // Handle login button click
        loginButton.setOnClickListener {
            // Perform login logic here
            if (validateInputs()) {
                // If validation passes, save the email, set logged in state, and proceed to MainActivity
                val email = emailEditText.text.toString()

                val editor = sharedPreferences.edit()
                editor.putString("userEmail", email)
                editor.putBoolean("isLoggedIn", true)
                editor.apply()

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()  // Optional: Close the LoginActivity so the user can't go back to it
            } else {
                // Show Snackbar if inputs are invalid
                Snackbar.make(loginButton, "Please fill in both email and password.", Snackbar.LENGTH_SHORT).show()
            }
        }

        // Make "Daftar" clickable and different color
        val text = "Belum punya akun? Daftar"
        val spannableString = SpannableString(text)

        val clickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
                startActivity(intent)
            }

            override fun updateDrawState(ds: android.text.TextPaint) {
                ds.isUnderlineText = false  // Hapus underline
            }
        }

        val colorSpan = ForegroundColorSpan(ContextCompat.getColor(this, R.color.md_theme_primaryContainer))

        val startIndex = text.indexOf("Daftar")
        val endIndex = startIndex + "Daftar".length

        spannableString.setSpan(clickableSpan, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannableString.setSpan(colorSpan, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        registerTextView.text = spannableString
        registerTextView.movementMethod = LinkMovementMethod.getInstance()
    }

    private fun validateInputs(): Boolean {
        // Validation logic: Check if email and password are not empty
        val email = emailEditText.text.toString()
        val password = passwordEditText.text.toString()

        return when {
            email.isEmpty() -> {
                emailTextInputLayout.error = "Email is required"
                false
            }
            password.isEmpty() -> {
                passwordTextInputLayout.error = "Password is required"
                false
            }
            else -> true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
