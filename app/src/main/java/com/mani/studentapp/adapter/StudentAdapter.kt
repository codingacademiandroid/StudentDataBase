package com.mani.studentapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textview.MaterialTextView
import com.mani.studentapp.listiners.OnDeleteClickListener
import com.mani.studentapp.R
import com.mani.studentapp.model.Student

class StudentAdapter(var context: Context, var studentList : List<Student>, var onDeleteClickListener: OnDeleteClickListener) : RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {



    class StudentViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val tvFName: MaterialTextView = itemView.findViewById(R.id.tvFName)
        val tvLName: MaterialTextView = itemView.findViewById(R.id.tvLName)
        val tvMobileNumber: MaterialTextView = itemView.findViewById(R.id.tvMobileNumber)
        val ivDelete : ImageView = itemView.findViewById(R.id.ivDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.student_row,parent,false)
        return StudentViewHolder(view)
    }

    override fun getItemCount(): Int {
        return studentList.size
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {

        val student = studentList[position]
        holder.tvFName.text = student.firstName
        holder.tvLName.text = student.lastName
        holder.tvMobileNumber.text = student.mobileNumber

        holder.ivDelete.setOnClickListener({

            onDeleteClickListener.deleteStudentRecord(student);

        })



    }
}