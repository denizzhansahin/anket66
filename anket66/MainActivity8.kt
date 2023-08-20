package com.bogazliyan.anket66

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView

import com.google.android.gms.ads.MobileAds

class MainActivity8 : AppCompatActivity() {

    lateinit var mAdView16 : AdView
    lateinit var mAdView17 : AdView
    lateinit var mAdView18 : AdView
    lateinit var mAdView19 : AdView
    lateinit var mAdView20 : AdView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main8)


        MobileAds.initialize(this) {}

        mAdView16 = findViewById(R.id.adView16)
        val adRequest16 = AdRequest.Builder().build()
        mAdView16.loadAd(adRequest16)

        mAdView17 = findViewById(R.id.adView17)
        val adRequest17 = AdRequest.Builder().build()
        mAdView17.loadAd(adRequest17)

        mAdView18 = findViewById(R.id.adView18)
        val adRequest18 = AdRequest.Builder().build()
        mAdView18.loadAd(adRequest18)

        mAdView19 = findViewById(R.id.adView19)
        val adRequest19 = AdRequest.Builder().build()
        mAdView19.loadAd(adRequest19)

        mAdView20 = findViewById(R.id.adView20)
        val adRequest20 = AdRequest.Builder().build()
        mAdView20.loadAd(adRequest20)


        var button : Button = findViewById(R.id.button6)
        button.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }
}