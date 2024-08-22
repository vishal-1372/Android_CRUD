package com.example.database

import android.content.ContentValues
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity() {

    lateinit var ed_Sname : EditText
    lateinit var ed_Sem : EditText
    lateinit var btn_Insert : Button
    lateinit var btn_Clear : Button
    lateinit var btn_Update : Button
    lateinit var btn_Delete : Button
    lateinit var btn_Next : Button
    lateinit var btn_Perv : Button
    lateinit var btn_First : Button
    lateinit var btn_Last : Button
    lateinit var showlist : ListView
    lateinit var btn_Showdata : Button
    lateinit var  search_data : SearchView
    lateinit var  rs : Cursor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        ed_Sname = findViewById(R.id.ed_Sname)
        ed_Sem = findViewById(R.id.ed_Sem)
        btn_Insert = findViewById(R.id.btn_Insert)
        btn_Clear = findViewById(R.id.btn_Clear)
        btn_Update = findViewById(R.id.btn_Update)
        btn_Delete = findViewById(R.id.btn_Detele)
        btn_Next = findViewById(R.id.btn_Next)
        btn_Perv = findViewById(R.id.btn_Perv)
        btn_First = findViewById(R.id.btn_First)
        btn_Last  = findViewById(R.id.btn_Last)
        showlist = findViewById(R.id.showList)
        btn_Showdata = findViewById(R.id.btn_showdata)
        search_data = findViewById(R.id.search_data)

        var helper = MyDBHelper(applicationContext)
        var db = helper.writableDatabase
        Toast.makeText(applicationContext, "database and table created", Toast.LENGTH_SHORT).show()
        rs = db.rawQuery("Select Sid _id , Sname , Sem FROM Student ",null)

        if(rs.moveToFirst())
        {
            ed_Sname.setText(rs.getString(1))
            ed_Sem.setText(rs.getString(2))
        }


        btn_Insert.setOnClickListener {
            var cv = ContentValues()
            cv.put("Sname",ed_Sname.text.toString())
            cv.put("Sem",ed_Sem.text.toString())
            db.insert("Student" , null ,cv)
            rs = db.rawQuery("Select Sid _id , Sname , Sem FROM Student ",null)
            showMessage("Record Inserted Successfully")
        }
        btn_Update.setOnClickListener {
            var cv = ContentValues()
            cv.put("Sname",ed_Sname.text.toString())
            cv.put("Sem",ed_Sem.text.toString())
            db.update("Student"  ,cv,"Sid = ?", arrayOf(rs.getString(0)))
            rs = db.rawQuery("Select Sid _id , Sname , Sem FROM Student ",null)
            showMessage("Record Updated Successfully")
            clear()
        }
        btn_Delete.setOnClickListener {
            db.delete("Student" ,"Sid = ?", arrayOf(rs.getString(0)))
            rs = db.rawQuery("Select Sid _id , Sname , Sem FROM Student ",null)
            showMessage("Record Deleted Successfully")
            clear()
        }


        btn_Clear.setOnClickListener {
            clear()
        }

        btn_Next.setOnClickListener {
            if(rs.moveToNext())
            {
                ed_Sname.setText(rs.getString(1))
                ed_Sem.setText(rs.getString(2))
            }
            else if(rs.moveToFirst())
            {
                ed_Sname.setText(rs.getString(1))
                ed_Sem.setText(rs.getString(2))
            }
            else{
                Toast.makeText(applicationContext, "Data Not found !!", Toast.LENGTH_SHORT).show()
            }
        }

        btn_Perv.setOnClickListener {
            if(rs.moveToPrevious())
            {
                ed_Sname.setText(rs.getString(1))
                ed_Sem.setText(rs.getString(2))
            }
            else if(rs.moveToFirst())
            {
                ed_Sname.setText(rs.getString(1))
                ed_Sem.setText(rs.getString(2))
            }
            else{
                Toast.makeText(applicationContext, "Data Not found !!", Toast.LENGTH_SHORT).show()
            }
        }

        btn_First.setOnClickListener {
            if(rs.moveToFirst()){
                ed_Sname.setText(rs.getString(1))
                ed_Sem.setText(rs.getString(2))
            }
            else{
                Toast.makeText(applicationContext, "Data Note Found !!", Toast.LENGTH_SHORT).show()
            }
        }

        btn_Last.setOnClickListener {
            if(rs.moveToLast()){
                ed_Sname.setText(rs.getString(1))
                ed_Sem.setText(rs.getString(2))
            }
            else{
                Toast.makeText(applicationContext, "Data Note Found !!", Toast.LENGTH_SHORT).show()
            }
        }


//        var fromColumns = arrayOf("Sname", "Sem")
//        var toViews = intArrayOf(android.R.id.text1, android.R.id.text2)
//        var adapter = SimpleCursorAdapter(this,android.R.layout.simple_list_item_2,   rs, fromColumns, toViews, 0)
//        showlist.adapter = adapter


        btn_Showdata.setOnClickListener {
            var fromColumns = arrayOf("Sname", "Sem")
            var toViews = intArrayOf(R.id.textView , R.id.textView1)
            var adapter = SimpleCursorAdapter(applicationContext,R.layout.my_layout,   rs, fromColumns, toViews)
            showlist.adapter = adapter

            search_data.queryHint = ("Search Among ${rs.count} Records")
            search_data.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(p0: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(p0: String?): Boolean {
                    rs = db.rawQuery("select Sid _id , Sname , Sem from Student where Sname like '%${p0}%'",null)
                    adapter.changeCursor(rs)
                    return false
                }

            })

        }
    }

    private fun clear() {
        ed_Sname.setText("")
        ed_Sem.setText("")
        ed_Sname.requestFocus()
    }

    private fun showMessage(s: String) {
        AlertDialog.Builder(this)
            .setTitle("Success!!!")
            .setMessage(s)
            .setPositiveButton("Ok", {dialogInterface, i->
                if(rs.moveToFirst()){
                    ed_Sname.setText(rs.getString(1))
                    ed_Sem.setText(rs.getString(2))
                }else{
                    Toast.makeText(applicationContext, "Data not Found", Toast.LENGTH_SHORT).show()
                }
            }).show()
    }

}