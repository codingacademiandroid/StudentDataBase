package com.mani.studentapp.Screens

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.mani.studentapp.DB.DBHelper
import com.mani.studentapp.R
import com.mani.studentapp.model.Student

class RegisterActivity : Activity(), View.OnClickListener {


    lateinit var etMobileNumber : TextInputEditText
    lateinit var etPassword : TextInputEditText
    lateinit var etCPassword : TextInputEditText
    lateinit var etFirstName : TextInputEditText
    lateinit var etLastName : TextInputEditText

    lateinit var tvRegister : TextView
    lateinit var tvCancel : TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        etMobileNumber = findViewById(R.id.etMobileNumber)
        etPassword = findViewById(R.id.etPassword)
        etCPassword = findViewById(R.id.etCPassword)
        etFirstName = findViewById(R.id.etFirstName)
        etLastName = findViewById(R.id.etLastName)



        tvCancel = findViewById(R.id.tvCancel)
        tvRegister = findViewById(R.id.tvRegister)

        tvCancel.setOnClickListener(this)
        tvRegister.setOnClickListener(this)
    }

    override fun onClick(view: View?) {

        when(view?.id) {

            R.id.tvRegister -> {

                val firstName = etFirstName.text.toString()
                val lastName = etLastName.text.toString()
                val mobileNumber = etMobileNumber.text.toString()
                val password = etPassword.text.toString()
                val cpassword = etCPassword.text.toString()


                if (password == cpassword) {

                    val student = Student(firstName,lastName,mobileNumber,password)
                    val dbHelper = DBHelper(this)
                   val id = dbHelper.insertStudentData(student)

                    if (id.toInt() != -1) {
                        startActivity(Intent(this, LoginActivity :: class.java))
                    } else {
                        Toast.makeText(this,"MobileNumber is Already Exists",Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this,"Password and Confirm Password not match",Toast.LENGTH_SHORT).show()
                }
            }

            R.id.tvCancel -> {

            }
        }
    }
}