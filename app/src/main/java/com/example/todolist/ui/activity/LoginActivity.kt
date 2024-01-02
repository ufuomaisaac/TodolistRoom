package com.example.todolist.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ViewSwitcher.ViewFactory
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.example.todolist.MyApp
import com.example.todolist.R
import com.example.todolist.data.datastore.DataStoreViewModel
import com.example.todolist.data.datastore.UserPreferenceRepository
import com.example.todolist.databinding.ActivityLoginBinding
import com.example.todolist.ui.DataStoreViewModelFactory
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    lateinit var loginBinding: ActivityLoginBinding
    //lateinit var userPreferenceRepository: UserPreferenceRepository
    lateinit var viewModel : DataStoreViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginBinding = ActivityLoginBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        supportActionBar?.hide()
        setContentView(loginBinding.root)



        val factory = DataStoreViewModelFactory(MyApp.userPreferenceRepository)
        viewModel = ViewModelProvider(this, factory).get(DataStoreViewModel::class.java)


        //userPreferenceRepository = UserPreferenceRepository(context = this)


        /*ViewCompat.setOnApplyWindowInsetsListener(loginBinding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }*/


        loginBinding.btnLogin.setOnClickListener {
            viewModel.password.asLiveData().observe(this) {password ->
                if(loginBinding.etPassword.text.toString() == password) {
                    var savedPassword = viewModel.password
                    var savedUserName = viewModel.userName
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
              //  MyApp.userPreferenceRepository.saveUserDetails(password = userPassword, userName = userName)
                viewModel.saveUserDetails(password = userPassword, userName = userName)

            }

        }
    }
}