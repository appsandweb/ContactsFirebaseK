package com.example.contactsfirebasek

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import com.google.firebase.database.*


class MainActivity : AppCompatActivity() {

    private lateinit var mDatabase: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mDatabase = FirebaseDatabase.getInstance().reference

        mDatabase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                getAllTask(dataSnapshot)
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w("users", "Failed to read value.", error.toException())
            }
        })

        val button = findViewById<Button>(R.id.button)
        button.setOnClickListener {
            val name_txt = findViewById<EditText>(R.id.name_txt)
            val name = name_txt.text.toString()

            val phone_txt = findViewById<EditText>(R.id.phone_txt)
            val phone = phone_txt.text.toString()

            val email_txt = findViewById<EditText>(R.id.email_txt)
            val email = email_txt.text.toString()

            val id = name.replace("\\s".toRegex(), "") + phone

            writeNewUser(id, name, email, phone)

            name_txt.setText("")
            phone_txt.setText("")
            email_txt.setText("")
        }
    }

    private fun writeNewUser(userId: String, name: String, email: String, phone: String) {
        val user = User(name, email, phone)
        mDatabase.child(userId).setValue(user)
        // should use autoid and push
    }

    private fun getAllTask(dataSnapshot: DataSnapshot) {
        val listView = findViewById<ListView>(R.id.listView)
        val aUsers = ArrayList<String>()

        /*for (singleSnapshot in dataSnapshot.children) {
            val user = singleSnapshot.getValue(User::class.java)
            user?.let {
                aUsers.add(it.name)
            }
        }*/
        for (singleSnapshot in dataSnapshot.children) {

            val user = singleSnapshot.getValue(User::class.java)
            if (user != null) {
                aUsers.add(user.name)
            }
        }



        val arrayAdapter = ArrayAdapter(this@MainActivity, android.R.layout.simple_list_item_1, aUsers)
        listView.adapter = arrayAdapter
    }
}
