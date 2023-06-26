package com.example.detailsqlite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.detailsqlite.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var mAdapter:StudentAdapter
   // lateinit var studentList:ArrayList<Student>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnInsert.setOnClickListener {

            val db = DBHelper(this, null)
          //  val id: Int = binding.edID.text.toString().toInt()
            val status= db.insertStud( binding.edName.text.toString(), binding.edPassword.text.toString())

            if (status > -1) {
            Toast.makeText(applicationContext, "Inserted", Toast.LENGTH_SHORT).show()

        }else{
            Toast.makeText(applicationContext, "error", Toast.LENGTH_SHORT).show()
        }
        }

        binding.btnShow.setOnClickListener {
            val db = DBHelper(this, null)

             val studentList=db.getAllStud()
             mAdapter = try {
               StudentAdapter(this, studentList, object : OnItemClick {
                   override fun onDeleteClick(stud: Student?) {
                       val deleteuser=stud?.userName.toString()
                       deleteRecordAlertDialog(deleteuser)

//                        val db = DBHelper(applicationContext, null)
//                       db.deleteUser(deleteuser)
                   }

                   override fun onUpdateClick(stud: Student?) {
                       val deleteuser=stud?.Id.toString()
                       openUpdateDialog(deleteuser)
                   }
               })
           } catch (e: Exception) {
               TODO("Not yet implemented")
           }

            binding.rvView.layoutManager = LinearLayoutManager(applicationContext)
            binding.rvView.setHasFixedSize(true)
            mAdapter.notifyDataSetChanged()
            binding.rvView.adapter = mAdapter
        }



    }


    fun deleteRecordAlertDialog(name:String) {
//        val db = DBHelper(this, null)
//        val status = db.deleteUser(name)
//        if (status > -1) {
//            Toast.makeText(applicationContext, "deleted", Toast.LENGTH_SHORT).show()
//        }

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Delete Record")
        builder.setMessage("Are you sure you want to delete $name")
        builder.setIcon(R.drawable.baseline_delete_24)

        builder.setPositiveButton("Yes") { dialogInterface, which ->

            val db = DBHelper(this, null)
            val status = db.deleteUser(name)
            if (status > -1) {
                Toast.makeText(applicationContext, "deleted", Toast.LENGTH_SHORT).show()
                mAdapter.notifyDataSetChanged()
            }
            dialogInterface.dismiss()
        }
        builder.setNegativeButton("NO") { dialogInterface, which ->
            dialogInterface.dismiss()
        }
        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

    fun openUpdateDialog(Id: String) {
        val mdialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val mdialogView = inflater.inflate(R.layout.update_dialog,null)

        mdialog.setView(mdialogView)

        val etName = mdialogView.findViewById<EditText>(R.id.edNameUpdate)
        val etPassword = mdialogView.findViewById<EditText>(R.id.edPasswordUpdate)
        val btnupdate=mdialogView.findViewById<TextView>(R.id.btnUpdate)
        val alertDialog = mdialog.create()
        alertDialog.show()


        btnupdate.setOnClickListener{
            val okName=etName.text.toString()
            val okPass=etPassword.text.toString()
            if(okName.isEmpty()){
                etName.error="field can't blank"
               // Toast.makeText(this, "field can't blank", Toast.LENGTH_SHORT).show()
            }
            else if(okPass.isEmpty()){
                etPassword.error="field can't blank"
               // Toast.makeText(this, "field can't blank", Toast.LENGTH_SHORT).show()

            }
            else{
                val db = DBHelper(this, null)
                val status = db.updateUser(Id,okName,okPass)
                if (status > -1) {
                    Toast.makeText(applicationContext, "updated", Toast.LENGTH_SHORT).show()
                    mAdapter.notifyDataSetChanged()
                    alertDialog.dismiss()

                }else{
                    Toast.makeText(applicationContext, "error", Toast.LENGTH_SHORT).show()
                }
            }



        }

    }
}


