package com.example.todolist.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.asLiveData
import com.example.todolist.MyApp
import com.example.todolist.R
import com.example.todolist.data.datastore.UserPreferenceRepository
import com.example.todolist.databinding.ActivityLoginBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    lateinit var loginBinding: ActivityLoginBinding
    lateinit var userPreferenceRepository: UserPreferenceRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginBinding = ActivityLoginBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        supportActionBar?.hide()
        setContentView(loginBinding.root)


        userPreferenceRepository = UserPreferenceRepository(context = this)



        /*ViewCompat.setOnApplyWindowInsetsListener(loginBinding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }*/

        loginBinding.btnLogin.setOnClickListener {
            userPreferenceRepository.password.asLiveData().observe(this) {password ->
                if(loginBinding.etPassword.text.toString() == password) {
                    var savedPassword = userPreferenceRepository.password
                    var savedUserName = userPreferenceRepository.userName
                    Log.d("Tag1", "saved login userDatails ${password} + ${savedUserName} ")
                    var intent = Intent(applicationContext, MainActivity::class.java)
                    startActivity(intent)
                }
            }
        }

        loginBinding.btnRegister.setOnClickListener {
            var userPassword = loginBinding.etPassword.text.toString()
            var userName = loginBinding.etUsername.text.toString()
            GlobalScope.launch {
                userPreferenceRepository.saveUserDetails(password = userPassword, userName = userName)

            }

        }
    }
}