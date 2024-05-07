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
import com.mani.studentapp.model.Student

class DashBoardActivity : Activity(), View.OnClickListener {

    lateinit var etMobileNumber : TextInputEditText
    lateinit var etPassword : TextInputEditText
    lateinit var etFirstName : TextInputEditText
    lateinit var etLastName : TextInputEditText
    lateinit var tvLogout : TextView

    lateinit var tvUpdate : TextView
    lateinit var tvStudentRecords : TextView

    val dbHelper = DBHelper(this)
    var mobileNumber = ""

    lateinit var sharedPreferences: SharedPreferences
    lateinit var editor: Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        sharedPreferences = getSharedPreferences("StudentPref", MODE_PRIVATE)
        editor = sharedPreferences.edit()

        tvLogout = findViewById(R.id.tvLogout)
        etMobileNumber = findViewById(R.id.etMobileNumber)
        etPassword = findViewById(R.id.etPassword)
        etFirstName = findViewById(R.id.etFirstName)
        etLastName = findViewById(R.id.etLastName)
        tvUpdate = findViewById(R.id.tvUpdate)
        tvStudentRecords = findViewById(R.id.tvRecords)

        tvUpdate.setOnClickListener(this)
        tvStudentRecords.setOnClickListener(this)
        tvLogout.setOnClickListener(this)

        mobileNumber = sharedPreferences.getString("MOBILE_NUMBER",null).toString()

        val student = dbHelper.getStudentRecord(mobileNumber)

        etMobileNumber.setText(student.mobileNumber)
        etFirstName.setText(student.firstName)
        etLastName.setText(student.lastName)
        etPassword.setText(student.password)
    }

    override fun onClick(view: View?) {

        when(view?.id) {

            R.id.tvUpdate -> {
                updateStudentRecord()
            }

            R.id.tvRecords -> {
                navigateToStudentRecordScreen();
            }

            R.id.tvLogout -> {

                editor.putString("MOBILE_NUMBER","")
                editor.commit()

                startActivity(Intent(this,LoginActivity::class.java))
                finish()

            }
        }


    }

    private fun navigateToStudentRecordScreen() {

        var intent = Intent(this, StudentRecordsActivity :: class.java)
        startActivity(intent)
    }

    private fun updateStudentRecord() {

        val firstName = etFirstName.text.toString()
        val lastName = etLastName.text.toString()
        val mobileNumber = etMobileNumber.text.toString()
        val password = etPassword.text.toString()

        var student = Student(firstName,lastName,mobileNumber,password)
        var id = dbHelper.updateStudentRecord(student)

        if (id != -1) {
            Toast.makeText(this,"Record is Updated Successfully",Toast.LENGTH_SHORT).show()
        }else {
            Toast.makeText(this,"Record is Not Updated ",Toast.LENGTH_SHORT).show()
        }
    }
}