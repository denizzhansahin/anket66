package com.bogazliyan.anket66

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView

import com.google.android.gms.ads.MobileAds

class MainActivity4 : AppCompatActivity() {

    lateinit var mAdView13 : AdView
    lateinit var mAdView14 : AdView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main4)
        MobileAds.initialize(this) {}

        mAdView13 = findViewById(R.id.adView13)
        val adRequest13 = AdRequest.Builder().build()
        mAdView13.loadAd(adRequest13)

        mAdView14 = findViewById(R.id.adView14)
        val adRequest14 = AdRequest.Builder().build()
        mAdView14.loadAd(adRequest14)

        val button = findViewById<Button>(R.id.button)
        button.setOnClickListener {
            val url = "https://www.instagram.com/bogazliyanmobil/"

            val defaultBrowserIntent = Intent(Intent.ACTION_VIEW)
            defaultBrowserIntent.data = Uri.parse(url)
            startActivity(defaultBrowserIntent)
        }

    }
}