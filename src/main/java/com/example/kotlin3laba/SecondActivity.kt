package com.example.kotlin3laba

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class SecondActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.seen_layout)
        val btn = findViewById<Button>(R.id.mainbtn)
        btn.setOnClickListener {
            val IntentLog = Intent(this, MainActivity::class.java)
            startActivity(IntentLog)
            finish()
        }
    }

    override fun onBackPressed() {
        val fragment = supportFragmentManager.findFragmentById(R.id.fragment)

        // Если это фрагмент ChildFragment, переключаемся на ParentFragment
        if (fragment is cartooninfoFragment) {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment, SeenCartoons())
            transaction.commit()
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {

        return super.onCreateView(name, context, attrs)

    }

}