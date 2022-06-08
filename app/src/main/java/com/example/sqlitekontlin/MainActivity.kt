package com.example.sqlitekontlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var edName : EditText
    private lateinit var edEmail : EditText
    private lateinit var btnAdd : Button
    private lateinit var btnView : Button
    private lateinit var btnUpdate : Button

    private  lateinit var sqLiteHelper: SQLiteHelper
    private  lateinit var recyclerView: RecyclerView
    private var adapter : MahasiswaAdapter? = null
    private  var msh: MahasiswaModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
        initRecyclerView()
        sqLiteHelper = SQLiteHelper(this)
        btnAdd.setOnClickListener { addMahasiswa() }
        btnView.setOnClickListener { getMahasiswa() }
        btnUpdate.setOnClickListener { updateMahsiswa() }
        adapter?.setOnclickitem { Toast.makeText(this, it.name, Toast.LENGTH_SHORT).show()

            edName.setText(it.name)
            edEmail.setText(it.email)
            msh = it
        }

        adapter?.setOnclickDeleteItem { deleteMahasiswa(it.id) }

    }

    private fun updateMahsiswa() {
        val name = edName.text.toString()
        val email = edEmail.text.toString()

        if(name == msh?.name && email == msh?.email) {
            Toast.makeText(this,"Record not changed...", Toast.LENGTH_SHORT).show()
            return
        }
        if (msh == null) return
        val  msh = MahasiswaModel(id= msh!!.id, name=name,email=email)
        val status = sqLiteHelper.updateMahasiswa(msh)
        if (status > -1) {
            clearEditText()
            getMahasiswa()
        } else {
            Toast.makeText(this,"Updated Failed", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getMahasiswa() {
       val mshList = sqLiteHelper.getAllMahasiswa()
        Log.e("Wawaawa", "${mshList.size}")

        adapter?.addItems(mshList)
    }

    private fun addMahasiswa() {
        val name = edName.text.toString()
        val email = edEmail.text.toString()

        if(name.isEmpty() || email.isEmpty()) {
            Toast.makeText(this, "Please enter requried filed", Toast.LENGTH_SHORT).show()
        } else {
            val msh = MahasiswaModel(name = name, email = email)
            val status = sqLiteHelper.insertMahasiswa(msh)
            if (status > -1) {
                Toast.makeText(this, "SuccessFully Add Data...", Toast.LENGTH_SHORT).show()
                clearEditText()
                getMahasiswa()
            } else {
                Toast.makeText(this, "Failed save data..", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun clearEditText() {
      edName.setText("")
      edEmail.setText("")
    }

    private fun deleteMahasiswa(id: Int){

        val builder = AlertDialog.Builder(this)
        builder.setMessage("Are You Sure want to delete item?")
        builder.setCancelable(true)
        builder.setPositiveButton("Yes") { dialog,_ ->
            sqLiteHelper.deleteMahasiswaById(id)
            getMahasiswa()
            dialog.dismiss()
        }
        builder.setNegativeButton("No") { dialog,_ ->
            dialog.dismiss()
        }
        val alert = builder.create()
        alert.show()
    }

    private  fun initRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = MahasiswaAdapter()
        recyclerView.adapter = adapter
    }

    private fun initView() {
        edName = findViewById(R.id.edName)
        edEmail = findViewById(R.id.edEmail)
        btnAdd = findViewById(R.id.btnAdd)
        btnUpdate = findViewById(R.id.btnUpdate)
        btnView = findViewById(R.id.btnView)
        recyclerView = findViewById(R.id.reacyclerView)
    }
}