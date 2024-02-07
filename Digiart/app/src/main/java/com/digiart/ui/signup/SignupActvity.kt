package com.digiart.ui.signup

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.digiart.R
import com.digiart.ui.login.LoginActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class SignupActivity : AppCompatActivity() {

    private lateinit var emailEditText: TextInputEditText
    private lateinit var passwordEditText: TextInputEditText
    private lateinit var conPasswordEditText: TextInputEditText
    private lateinit var emailTextInputLayout: TextInputLayout
    private lateinit var passwordTextInputLayout: TextInputLayout
    private lateinit var conPasswordTextInputLayout: TextInputLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        // Set status bar color to black
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = resources.getColor(R.color.black, theme)
        }

        // Set navigation bar color to white
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.navigationBarColor = resources.getColor(R.color.white, theme)
        }

        emailEditText = findViewById(R.id.emailEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        conPasswordEditText = findViewById(R.id.conpasswordEditText)
        emailTextInputLayout = findViewById(R.id.emailTextInputLayout)
        passwordTextInputLayout = findViewById(R.id.passwordTextInputLayout)
        conPasswordTextInputLayout = findViewById(R.id.conpasswordTextInputLayout)

        val signUpButton = findViewById<Button>(R.id.signUpButton)

        signUpButton.setOnClickListener {
            if (validateForm()) {
                // Proceed with signup logic
                // You can start the next activity or perform the signup action here
                val intent = Intent(this@SignupActivity, LoginActivity::class.java)
                startActivity(intent)
            }
        }

        val loginBtn = findViewById<Button>(R.id.loginButton)

        loginBtn.setOnClickListener {
            val intent = Intent(this@SignupActivity, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun validateForm(): Boolean {
        var isValid = true

        // Reset errors
        emailTextInputLayout.error = null
        passwordTextInputLayout.error = null
        conPasswordTextInputLayout.error = null

        // Validate email
        val email = emailEditText.text.toString()
        if (email.isEmpty()) {
            emailTextInputLayout.error = getString(R.string.email_required)
            isValid = false
        }

        // Validate password
        val password = passwordEditText.text.toString()
        if (password.isEmpty()) {
            passwordTextInputLayout.error = getString(R.string.password_required)
            isValid = false
        }

        // Validate confirm password
        val confirmPassword = conPasswordEditText.text.toString()
        if (confirmPassword.isEmpty()) {
            conPasswordTextInputLayout.error = getString(R.string.confirm_password_required)
            isValid = false
        } else if (password != confirmPassword) {
            conPasswordTextInputLayout.error = getString(R.string.password_mismatch)
            isValid = false
        }

        return isValid
    }
}