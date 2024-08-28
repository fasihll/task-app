package com.otakkanan.taskapp.ui.main.profile

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textview.MaterialTextView
import com.otakkanan.taskapp.R
import com.google.android.material.button.MaterialButton
import com.otakkanan.taskapp.ui.auth.login.LoginActivity

class ProfileFragment : Fragment() {

    private lateinit var emailTextView: MaterialTextView
    private lateinit var logoutButton: MaterialButton
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        emailTextView = view.findViewById(R.id.emailTextView)
        logoutButton = view.findViewById(R.id.logoutButton)

        sharedPreferences = requireContext().getSharedPreferences("appPrefs", Context.MODE_PRIVATE)

        // Retrieve and display the user's email
        val email = sharedPreferences.getString("userEmail", "")
        if (email.isNullOrEmpty()) {
            Snackbar.make(view, "No email found", Snackbar.LENGTH_SHORT).show()
        } else {
            emailTextView.text = email
        }

        // Handle logout button click
        logoutButton.setOnClickListener {
            // Clear the login state
            val editor = sharedPreferences.edit()
            editor.clear()
            editor.apply()

            // Navigate to LoginActivity
            val intent = Intent(requireContext(), LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            requireActivity().finish()
        }

        return view
    }
}
