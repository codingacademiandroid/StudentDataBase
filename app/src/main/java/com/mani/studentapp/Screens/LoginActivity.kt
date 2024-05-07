package com.mani.studentapp.Screens

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.mani.studentapp.DB.DBHelper
import com.mani.studentapp.R

class LoginActivity : Activity(), View.OnClickListener {

    lateinit var etMobileNumber : TextInputEditText
    lateinit var etPassword : TextInputEditText

    lateinit var tvLogin : TextView
    lateinit var tvCancel : TextView
    lateinit var tvRegister : TextView
    val dbHelper = DBHelper(this)

    var mobileNumber : String = "";

    lateinit var sharedPreferences: SharedPreferences
    lateinit var editor: Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedPreferences = getSharedPreferences("StudentPref", MODE_PRIVATE)
        editor = sharedPreferences.edit();
        mobileNumber = sharedPreferences.getString("MOBILE_NUMBER",null).toString()

        if (mobileNumber.isNotEmpty()) {
            startActivity(Intent(this, DashBoardActivity :: class.java))
        }

        setContentView(R.layout.activity_login)


        etMobileNumber = findViewById(R.id.etMobileNumber)
        etPassword = findViewById(R.id.etPassword)

        tvLogin = findViewById(R.id.tvLogin)
        tvCancel = findViewById(R.id.tvCancel)
        tvRegister = findViewById(R.id.tvRegister)

        tvLogin.setOnClickListener(this)
        tvCancel.setOnClickListener(this)
        tvRegister.setOnClickListener(this)


    }

    override fun onClick(v: View?) {
        when(v?.id) {

            R.id.tvLogin -> {

                val mobileNumber = etMobileNumber.text.toString()
                val password = etPassword.text.toString()

                val isLoggedIn = dbHelper.validateLoginCredentials(mobileNumber,password)
                if (isLoggedIn) {

                    editor.putString("MOBILE_NUMBER",mobileNumber)
                    editor.commit()

                    startActivity(Intent(this, DashBoardActivity :: class.java))

                }else {
                    Toast.makeText(this,"Please Check your Login Credentials",Toast.LENGTH_SHORT).show()
                }
            }

            R.id.tvCancel -> {}

            R.id.tvRegister -> {

                startActivity(Intent(this, RegisterActivity :: class.java))
            }

        }
    }
}