package com.digiart.ui.login

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.digiart.MainActivity
import com.digiart.R
import com.digiart.ui.signup.SignupActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class LoginActivity : AppCompatActivity() {

    private lateinit var emailEditText: TextInputEditText
    private lateinit var passwordEditText: TextInputEditText
    private lateinit var emailTextInputLayout: TextInputLayout
    private lateinit var passwordTextInputLayout: TextInputLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Set status bar color to black
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = resources.getColor(R.color.black, theme)
        }

        // Set navigation bar color to white
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.navigationBarColor = resources.getColor(R.color.white, theme)
        }

        emailEditText = findViewById(R.id.loginEmailEditText)
        passwordEditText = findViewById(R.id.loginPasswordEditText)
        emailTextInputLayout = findViewById(R.id.emailTextInputLayout)
        passwordTextInputLayout = findViewById(R.id.passwordTextInputLayout)

        val signInButton = findViewById<Button>(R.id.signInButton)

        signInButton.setOnClickListener {
            if (validateForm()) {
                // Proceed with login logic
                // You can start the next activity or perform the login action here
                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                startActivity(intent)
                finish();
            }
        }

        val signUpBtn = findViewById<Button>(R.id.createAccountButton)

        signUpBtn.setOnClickListener {
            val intent = Intent(this@LoginActivity, SignupActivity::class.java)
            startActivity(intent)
        }
    }

    private fun validateForm(): Boolean {
        var isValid = true

        // Reset errors
        emailTextInputLayout.error = null
        passwordTextInputLayout.error = null

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

        return isValid
    }
}