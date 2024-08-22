package com.example.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class MyDBHelper (context : Context) : SQLiteOpenHelper(context, "Student_DB", null,1){
    override fun onCreate(p0: SQLiteDatabase?) {
        p0?.execSQL("create table Student (Sid integer primary key autoincrement , Sname text , Sem number)")
        p0?.execSQL("insert into Student(Sname , Sem) values ('rahul',3)")
        p0?.execSQL("insert into Student(Sname , Sem) values ('jay',3)")
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {

    }

}