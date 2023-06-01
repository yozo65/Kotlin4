package com.example.kotlin3laba

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btn = findViewById<Button>(R.id.autorizebtn)
//        btn.setOnClickListener {
//                val intentLog = Intent(this, SecondActivity::class.java)
//                startActivity(intentLog)
//                    finish()
//            }
    }

    override fun onBackPressed() {
        val fragment = supportFragmentManager.findFragmentById(R.id.fragment)

        // Если это фрагмент ChildFragment, переключаемся на ParentFragment
        if (fragment is cartooninfoFragment) {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment, MainFragment())
            transaction.commit()
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {

        return super.onCreateView(name, context, attrs)

    }

}