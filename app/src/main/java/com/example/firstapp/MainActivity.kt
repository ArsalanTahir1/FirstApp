package com.example.firstapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val zipcode_edittext: EditText = findViewById(R.id.zipcode_editText)
        val submitbutton: Button = findViewById(R.id.submit_button)

        submitbutton.setOnClickListener {

            val msg: String =zipcode_edittext.text.toString()

            if (msg.length != 5)
            {

                Toast.makeText(this,R.string.zipcode_error,Toast.LENGTH_SHORT).show()
            }

            else
            {
                Toast.makeText(this,msg,Toast.LENGTH_SHORT).show()
            }


            }


    }
}