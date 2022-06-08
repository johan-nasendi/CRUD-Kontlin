package com.example.sqlitekontlin

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SQLiteHelper(context : Context):
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION){

        companion object {
            private  const val DATABASE_VERSION = 1
            private  const val DATABASE_NAME = "mu.db"
            private const val TBL_STUDENT ="tbl_student"
            private const val ID ="id"
            private const val NAME ="name"
            private const val EMAIL = "email"
        }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTblMahasiswa = ("CREATE TABLE " + TBL_STUDENT + "("
                + ID + " INTEGER PRIMARY KEY," + NAME + " TEXT,"
                + EMAIL + " TEXT" + ")")
        db?.execSQL(createTblMahasiswa)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
       db!!.execSQL("DROP TABLE IF EXISTS $TBL_STUDENT")
        onCreate(db)
    }

    fun  insertMahasiswa(msh : MahasiswaModel): Long {
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(ID, msh.id)
        contentValues.put(NAME, msh.name)
        contentValues.put(EMAIL, msh.email)

        val success = db.insert(TBL_STUDENT, null,contentValues)
        db.close()
        return success
    }

    @SuppressLint("Range")
    fun getAllMahasiswa(): ArrayList<MahasiswaModel> {
        val mshList: ArrayList<MahasiswaModel> = ArrayList()
        val selectQuery = "SELECT * FROM $TBL_STUDENT"
        val db = this.readableDatabase

        val cursor: Cursor?

        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e : Exception) {
            e.printStackTrace()
            db.execSQL(selectQuery)
            return  ArrayList()
        }

        var id: Int
        var name: String
        var email: String

        if (cursor.moveToFirst()) {
            do {
                id = cursor.getInt(cursor.getColumnIndex("id"))
                name = cursor.getString(cursor.getColumnIndex("name"))
                email = cursor.getString(cursor.getColumnIndex("email"))

                val msh = MahasiswaModel(id =id, name=name, email =email)
                mshList.add(msh)
            }while(cursor.moveToNext())
        }

      return  mshList
    }

    fun updateMahasiswa(msh: MahasiswaModel): Int {
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(ID, msh.id)
        contentValues.put(NAME, msh.name)
        contentValues.put(EMAIL, msh.email)

        val success = db.update(TBL_STUDENT, contentValues, "id=" + msh.id, null)
        db.close()
        return success
    }

    fun deleteMahasiswaById(id:Int): Int {
        val db = this.writableDatabase

        val  contentValues = ContentValues()
        contentValues.put(ID, id)

        val success = db.delete(TBL_STUDENT, "id=$id", null)
        db.close()
        return success
    }

}