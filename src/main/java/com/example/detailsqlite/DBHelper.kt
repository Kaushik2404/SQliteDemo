package com.example.detailsqlite

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {

    companion object {

        private const val DATABASE_NAME = "user.db"
        private const val DATABASE_VERSION = 1
        private const val TBL_USER = "User"
        const val ID = "id"
        const val NAME = "name"
        const val PASSWORD = "password"

    }

    override fun onCreate(db: SQLiteDatabase?) {


        val query = ("CREATE TABLE " + TBL_USER + " ("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                NAME + " TEXT," +
                PASSWORD + " TEXT" + ")")

        db?.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {


        db?.execSQL("DROP TABLE IF EXISTS $TBL_USER")
        onCreate(db)

    }

    fun insertStud(user: String, password: String): Long {


        val values = ContentValues()
       // values.put(ID, id)
        values.put(NAME, user)
        values.put(PASSWORD, password)

        // insert value in our database
        val db = this.writableDatabase

        val success= db.insert(TBL_USER, null, values)


        db.close()
        return success


    }

    fun getAllStud(): ArrayList<Student?> {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TBL_USER", null)
        val listOfStudent = arrayListOf<Student?>()
        if(cursor.moveToFirst()){

            do{
                listOfStudent.add(getStudent(cursor))

            }while(cursor.moveToNext())

        }
//        while (cursor.isLast.not()){
//            listOfStudent.add(getStudent(cursor))
//            cursor.moveToNext()
//        }
        return listOfStudent
    }

    fun getStudent(cursor: Cursor): Student? {
        return try {
            val userIDIndex = cursor.getColumnIndexOrThrow(ID)
            val userNameIndex = cursor.getColumnIndexOrThrow(NAME)
            val passwordIndex = cursor.getColumnIndexOrThrow(PASSWORD)
            val id = cursor.getInt(userIDIndex)
            val userName = cursor.getString(userNameIndex)
            val password = cursor.getString(passwordIndex)
            Student(
                Id=id,
                userName = userName,
                password = password
            )
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun deleteUser(name:String): Int {
        val db =this.writableDatabase
        val contentValues=ContentValues()
        contentValues.put(NAME,name)
       val success= db.delete(TBL_USER, "name=?", arrayOf(name))
        db.close()
        return  success


    }
    fun updateUser(id: String, user: String, password: String):Int{
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(NAME, user)
        values.put(PASSWORD, password)
        val success=  db.update(TBL_USER, values, "id=?", arrayOf(id))
        db.close()
        return success
    }


}