package com.bogazliyan.anket66

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.PopupMenu
import android.widget.TextView

import android.widget.Toast
import android.widget.Toolbar
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bogazliyan.anket66.R.id.recyclerview
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView

import com.google.android.gms.ads.MobileAds


import com.google.android.material.navigation.NavigationView


import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var firestore: FirebaseFirestore


    lateinit var mAdView : AdView
    lateinit var mAdView1 : AdView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        MobileAds.initialize(this) {}


        mAdView = findViewById(R.id.adView6)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)

        mAdView1 = findViewById(R.id.adView)
        val adRequest1 = AdRequest.Builder().build()
        mAdView1.loadAd(adRequest1)



        // getting the recyclerview by its id
        val recyclerview = findViewById<RecyclerView>(R.id.recyclerview_genel)

        // this creates a vertical layout Manager
        recyclerview.layoutManager = LinearLayoutManager(this)

        firestore = FirebaseFirestore.getInstance()


        var filtreleme_firebase = firestore.collection("GenelAnketler")
        filtreleme_firebase.get().addOnCompleteListener { task ->
            if(task.isSuccessful){
                val data = ArrayList<GenelAnketViewModel>()
                if(task.result?.isEmpty == false){
                    filtreleme_firebase.get().addOnSuccessListener { documents->

                        for(document in documents.documents){
                            data.add(GenelAnketViewModel(document.id,document.id))
                            println(document.id)

                        }
                        println(data)
                        val adapter = GenelAnketAdapter(data)

                        // Setting the Adapter with the recyclerview
                        recyclerview.adapter = adapter

                    }
                }

            }
        }





        var deviceID = Settings.Secure.getString(this.contentResolver, Settings.Secure.ANDROID_ID)
        var deviceID_string = deviceID.toString()

        var button_anket_getir = findViewById<Button>(R.id.button_anket_getir)
        var anket_numarasi = findViewById<EditText>(R.id.editTextText)

        button_anket_getir.setOnClickListener {
            var anket_numarasi1 = anket_numarasi.text.toString()
            var anket_numarasi_cevap = anket_numarasi1 + "_Cevaplar"

            if (anket_numarasi1.isNotEmpty()) {
                var kontrolFirestore1 = firestore.collection(anket_numarasi1)
                kontrolFirestore1.get().addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        if (task.result?.isEmpty == false) {
                            // Koleksiyon var
                            val kontrolFirestore = firestore.collection(anket_numarasi_cevap).document(deviceID_string)
                            kontrolFirestore.get().addOnSuccessListener { document ->
                                if (document.exists()) {
                                    val toast_cevaplanmis =
                                        Toast.makeText(this, "Bu anket, bu kullanıcı tarafından cevaplanmıştır.", Toast.LENGTH_LONG)
                                    toast_cevaplanmis.show()
                                } else {
                                    val toast_cevaplanmamis =
                                        Toast.makeText(this, "Anket sayfası açılıyor", Toast.LENGTH_LONG)
                                    toast_cevaplanmamis.show()

                                    val baslat_intent = Intent(this, MainActivity2::class.java)
                                    baslat_intent.putExtra("bilgi", anket_numarasi1)
                                    startActivity(baslat_intent)
                                }
                            }
                        } else {
                            // Koleksiyon yok
                            val toast_koleksiyonYok = Toast.makeText(this, "Bu anket mevcut değil bulunamadı.", Toast.LENGTH_LONG)
                            toast_koleksiyonYok.show()
                            showAlertDialog(this, "Bu anket mevcut değil!", "Lütfen başka bir anket kodu yazınız.")
                        }
                    } else {
                        // Hata durumu
                        val toast_hata = Toast.makeText(this, "Sunucu veya cihazd için bir hata oluştu.", Toast.LENGTH_LONG)
                        toast_hata.show()
                    }
                }





            }
        }


        val button = findViewById<Button>(R.id.button2)
        button.setOnClickListener {
            val baslat_intent = Intent(this, MainActivity6::class.java)
            startActivity(baslat_intent)
        }


        val button2 = findViewById<Button>(R.id.button3)
        button2.setOnClickListener {
            val baslat_intent = Intent(this, MainActivity7::class.java)
            startActivity(baslat_intent)
        }


        val navView1 : NavigationView = findViewById(R.id.nav_view1)
        navView1.visibility = View.GONE

        val buttonMenu = findViewById<ImageButton>(R.id.imageButton2)
        buttonMenu.setOnClickListener {
            if (navView1.visibility == View.VISIBLE) {
                // Menü görünür durumdaysa, gizleyin
                navView1.visibility = View.GONE
                navView1.layoutParams.height = 0
            } else {
                // Menü gizli durumdaysa, görünür yapın
                navView1.visibility = View.VISIBLE
                navView1.layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
            }
        }

        navView1.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.kullanim_kosullari->{
                    val intent = Intent(this@MainActivity, MainActivity3::class.java)
                    startActivity(intent)
                }

                R.id.hakkinda->{
                    val intent = Intent(this@MainActivity, MainActivity4::class.java)
                    startActivity(intent)
                }

                R.id.destek_ol->{
                    val intent = Intent(this@MainActivity, MainActivity8::class.java)
                    startActivity(intent)
                }

                R.id.iletisim->{
                    val intent = Intent(this@MainActivity, MainActivity5::class.java)
                    startActivity(intent)
                }

            }
            true
        }




    }

    fun showAlertDialog(context: Context, title: String, message: String) {


        val alertDialogBuilder = AlertDialog.Builder(context)
        alertDialogBuilder.setTitle(title)
        alertDialogBuilder.setMessage(message)
        alertDialogBuilder.setPositiveButton("Tamam") { dialog, _ ->
            // Tamam butonuna basıldığında yapılacak işlemler
            dialog.dismiss() // Uyarı kutusunu kapat


        }

        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }


}
