package com.bogazliyan.anket66

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.zxing.integration.android.IntentIntegrator

class MainActivity6 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main6)

        val button = findViewById<Button>(R.id.button3)
        button.setOnClickListener()
        {
            val baslat_intent = Intent(this, MainActivity::class.java)
            startActivity(baslat_intent)
        }



        // QR kodunu tara
        val integrator = IntentIntegrator(this)
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
        integrator.setPrompt("QR kodunu tara")
        integrator.setCameraId(0)
        integrator.setBeepEnabled(true)
        integrator.setBarcodeImageEnabled(true)
        integrator.setOrientationLocked(true) // Kamera yönünü kilitleme
        integrator.initiateScan()
    }





    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)


        // QR kodunun sonuçlarını işle
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result.contents.isNullOrEmpty()) {

        } else {

            val baslat_intent = Intent(this, MainActivity2::class.java)
            baslat_intent.putExtra("bilgi", result.contents.toString())
            startActivity(baslat_intent)

        }
    }
}
