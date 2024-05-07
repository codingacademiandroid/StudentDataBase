package com.mani.studentapp.Screens

import android.app.Activity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mani.studentapp.DB.DBHelper
import com.mani.studentapp.listiners.OnDeleteClickListener
import com.mani.studentapp.R
import com.mani.studentapp.model.Student
import com.mani.studentapp.adapter.StudentAdapter

class StudentRecordsActivity : Activity(), OnDeleteClickListener {

    var dbHelper = DBHelper(this)
    lateinit var rvStudentRecords : RecyclerView
    var studentList = ArrayList<Student>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_records)

        rvStudentRecords = findViewById(R.id.rvStudentRecords)
        val linearLayoutManager = LinearLayoutManager(this)
        rvStudentRecords.layoutManager = linearLayoutManager
        rvStudentRecords.setHasFixedSize(true)

        getStudentRecords()



    }

    private fun getStudentRecords() {

        studentList = dbHelper.getAllStudentRecords()
        val studentAdapter = StudentAdapter(this,studentList , this)
        rvStudentRecords.adapter = studentAdapter
    }

    override fun deleteStudentRecord(student: Student) {

        dbHelper.deleteStudentRecord(student.mobileNumber)
        studentList.clear()
        getStudentRecords()

    }
}