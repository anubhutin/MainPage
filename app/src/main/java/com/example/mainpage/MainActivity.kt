package com.example.mainpage

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val imageView1: ImageView = findViewById(R.id.airplane)
        val imageView2: ImageView = findViewById(R.id.cycle)
        val imageView3: ImageView = findViewById(R.id.imageView3)

        imageView1.setOnClickListener {
            val intent = Intent(this, DisplayActivity::class.java)
            startActivity(intent)
        }
        imageView2.setOnClickListener {
            val intent = Intent(this, CompassActivity::class.java)
            startActivity(intent)
        }
        imageView3.setOnClickListener {
            val intent = Intent(this, BatteryActivity::class.java)
            startActivity(intent)
        }
    }
}