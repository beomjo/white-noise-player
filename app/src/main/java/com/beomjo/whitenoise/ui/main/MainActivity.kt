package com.beomjo.whitenoise.ui.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.beomjo.whitenoise.R
import com.beomjo.whitenoise.ui.splash.SplashActivity
import com.beomjo.whitenoise.utilities.ext.applyExitMaterialTransform
import com.google.android.material.transition.platform.MaterialContainerTransformSharedElementCallback
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        applyExitMaterialTransform()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        Log.e("bsjo", FirebaseAuth.getInstance().currentUser!!.email.toString())
//        findViewById<TextView>(R.id.hello).setOnClickListener {
//            FirebaseAuth.getInstance().signOut()
//            finish()
//            startActivity(Intent(this, SplashActivity::class.java))
//        }
    }
}