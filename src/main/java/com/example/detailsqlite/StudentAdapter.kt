package com.example.detailsqlite

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class StudentAdapter(
    val context: Context,
    val studList: ArrayList<Student?>,
    val onItemClick: OnItemClick
) : RecyclerView.Adapter<StudentAdapter.ViewHolder>() {

    class ViewHolder(iteamView: View) : RecyclerView.ViewHolder(iteamView) {
        val rvName: TextView = iteamView.findViewById(R.id.listName)
        val rvPassword: TextView = iteamView.findViewById(R.id.listpassword)
        val layout: LinearLayout = iteamView.findViewById(R.id.list_View)
        val deleteIcon: ImageView = iteamView.findViewById(R.id.deleteIcon)
        val updateIcon: ImageView = iteamView.findViewById(R.id.updateIcon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val iteamView =
            LayoutInflater.from(parent.context).inflate(R.layout.list_stud, parent, false)
        return ViewHolder(iteamView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.rvName.text = studList[position]?.userName
        holder.rvPassword.text = studList[position]?.password
        if (position % 2 == 0) {
            holder.layout.setBackgroundResource(R.color.black1)
        }
        holder.deleteIcon.setOnClickListener {
            onItemClick.onDeleteClick(studList[position])

        }
        holder.updateIcon.setOnClickListener {
            onItemClick.onUpdateClick(studList[position])
        }

    }

    override fun getItemCount(): Int {
        return studList.size
    }
}