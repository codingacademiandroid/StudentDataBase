package com.mani.studentapp.DB

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.mani.studentapp.model.Student


class DBHelper(context: Context?) : SQLiteOpenHelper(context, DB_NAME , null, DB_VERSION) {

    companion object {

        val DB_NAME = "StudentDB"
        val DB_VERSION = 1
        val TBL_NAME = "Student"
        val COL_STUDENT_FIRST_NAME = "firstName"
        val COL_STUDENT_LAST_NAME = "lastName"
        val COL_STUDENT_MOBILE_NUMBER = "mobileNumber"
        val COL_STUDENT_PASSWORD = "password"

    }





    override fun onCreate(sqLiteDatabase: SQLiteDatabase?) {
        sqLiteDatabase?.execSQL("create table $TBL_NAME (" +
                "$COL_STUDENT_FIRST_NAME TEXT ," +
                "$COL_STUDENT_LAST_NAME TEXT, " +
                "$COL_STUDENT_MOBILE_NUMBER TEXT PRIMARY KEY," +
                "$COL_STUDENT_PASSWORD TEXT)")
    }

    fun insertStudentData(student: Student) : Long {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COL_STUDENT_FIRST_NAME,student.firstName)
        contentValues.put(COL_STUDENT_LAST_NAME,student.lastName)
        contentValues.put(COL_STUDENT_MOBILE_NUMBER,student.mobileNumber)
        contentValues.put(COL_STUDENT_PASSWORD,student.password)
        return db.insert(TBL_NAME,null,contentValues)
    }

    fun validateLoginCredentials(mobileNumber : String,password : String) : Boolean {
        val db = this.readableDatabase
        val cursor = db.rawQuery("select * from $TBL_NAME where $COL_STUDENT_MOBILE_NUMBER=? AND $COL_STUDENT_PASSWORD=?", arrayOf(mobileNumber,password))
        return cursor != null && cursor.count > 0
    }

    fun getStudentRecord(mobileNumber: String): Student {
        var fName = ""
        var lName = ""
        var sPassword = ""
        val db = this.readableDatabase
        val cursor = db.rawQuery("select * from $TBL_NAME where $COL_STUDENT_MOBILE_NUMBER = ?", arrayOf(mobileNumber))
        if (cursor != null && cursor.count > 0) {
            if (cursor.moveToFirst()) {
                do {
                    fName = cursor.getString(cursor.getColumnIndexOrThrow(COL_STUDENT_FIRST_NAME))
                    lName = cursor.getString(cursor.getColumnIndexOrThrow(COL_STUDENT_LAST_NAME))
                    sPassword = cursor.getString(cursor.getColumnIndexOrThrow(COL_STUDENT_PASSWORD))
                } while (cursor.moveToNext())

            }
        }
        return Student(fName, lName, mobileNumber, sPassword)
    }

    fun updateStudentRecord(student: Student) : Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COL_STUDENT_FIRST_NAME,student.firstName)
        contentValues.put(COL_STUDENT_LAST_NAME,student.lastName)
        contentValues.put(COL_STUDENT_PASSWORD,student.password)
        return db.update(TBL_NAME,contentValues,"$COL_STUDENT_MOBILE_NUMBER = ?", arrayOf(student.mobileNumber))
    }


    fun getAllStudentRecords() : ArrayList<Student> {

        val db = this.readableDatabase

        val studentList = ArrayList<Student>()
        val cursor = db.rawQuery("select * from $TBL_NAME",null)
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    val fName = cursor.getString(cursor.getColumnIndexOrThrow(COL_STUDENT_FIRST_NAME))
                    val lName = cursor.getString(cursor.getColumnIndexOrThrow(COL_STUDENT_LAST_NAME))
                    val mobileNumber = cursor.getString(cursor.getColumnIndexOrThrow(COL_STUDENT_MOBILE_NUMBER))
                    var student = Student(fName,lName,mobileNumber,"")
                    studentList.add(student)
                }while (cursor.moveToNext())
            }
        }
        return studentList
    }

    fun deleteStudentRecord(mobileNumber: String) {
        val db = this.writableDatabase
        db.delete(TBL_NAME,"$COL_STUDENT_MOBILE_NUMBER =?" , arrayOf(mobileNumber))
    }

    override fun onUpgrade(sqLiteDatabase: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {}


}