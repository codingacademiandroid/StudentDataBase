package com.mani.studentapp.listiners

import com.mani.studentapp.model.Student

interface OnDeleteClickListener {

    fun deleteStudentRecord(student: Student)
}