package com.bogazliyan.anket66

import android.content.Intent
import android.net.Uri
import android.net.http.SslError
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.SslErrorHandler
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button

import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView

import com.google.android.gms.ads.MobileAds

class MainActivity5 : AppCompatActivity() {
    lateinit var mAdView15 : AdView
    lateinit var mAdView16 : AdView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main5)

        MobileAds.initialize(this) {}

        mAdView15 = findViewById(R.id.adView15)
        val adRequest15 = AdRequest.Builder().build()
        mAdView15.loadAd(adRequest15)

        mAdView16 = findViewById(R.id.adView16)
        val adRequest16 = AdRequest.Builder().build()
        mAdView16.loadAd(adRequest16)


        val button = findViewById<Button>(R.id.button_iletisim)
        button.setOnClickListener {
            val url = "https://form.jotform.com/231976611139965"

            val defaultBrowserIntent = Intent(Intent.ACTION_VIEW)
            defaultBrowserIntent.data = Uri.parse(url)
            startActivity(defaultBrowserIntent)
        }

    }
}