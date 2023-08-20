package com.bogazliyan.anket66

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.TextView
import android.widget.Toast


import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

import android.app.AlertDialog
import android.content.Context


import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView

import com.google.android.gms.ads.MobileAds


class MainActivity2 : AppCompatActivity() {
    private lateinit var firestore: FirebaseFirestore
    var cevaplarArray : MutableList<ItemsViewModel> = mutableListOf()
    //var cevaplarArray : MutableMap<String,String> = mutableMapOf()

    lateinit var mAdView11 : AdView
    lateinit var mAdView12 : AdView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)


        MobileAds.initialize(this) {}


        mAdView11 = findViewById(R.id.adView11)
        val adRequest = AdRequest.Builder().build()
        mAdView11.loadAd(adRequest)

        mAdView12 = findViewById(R.id.adView12)
        val adRequest1 = AdRequest.Builder().build()
        mAdView12.loadAd(adRequest1)


        firestore = FirebaseFirestore.getInstance()
        var deviceID = Settings.Secure.getString(this.contentResolver, Settings.Secure.ANDROID_ID)
        var deviceID_string = deviceID.toString()


        val  intent = intent
        val bilgi = intent.getStringExtra("bilgi")
        val textView = findViewById<TextView>(R.id.textView2)
        //textView.text = "Anket ID : "+" "+bilgi


        // getting the recyclerview by its id
        val recyclerview = findViewById<RecyclerView>(R.id.recyclerview)

        // this creates a vertical layout Manager
        recyclerview.layoutManager = LinearLayoutManager(this)

        // ArrayList of class ItemsViewModel

        if (bilgi.toString().isNotEmpty()) {
            var kontrolFirestore1 = firestore.collection(bilgi.toString())
            var anket_numarasi_cevap = bilgi.toString() + "_Cevaplar"


            val kontrolFirestore = firestore.collection(anket_numarasi_cevap).document(deviceID_string)
            kontrolFirestore.get().addOnSuccessListener { document ->
                if (document.exists()) {
                    val toast_cevaplanmis =
                        Toast.makeText(this, "Bu anket, bu kullanıcı tarafından cevaplanmıştır.", Toast.LENGTH_LONG)
                    toast_cevaplanmis.show()

                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)

                    //showAlertDialog(this, "Cevaplanmış Anket!", "Bu anket, bu kullanıcı tarafından cevaplanmıştır.")

                } else {

                    kontrolFirestore1.get().addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            if (task.result?.isEmpty == false) {
                                // Koleksiyon var

                                firestore.collection(bilgi.toString()).get().addOnSuccessListener {task->
                                    val data = ArrayList<ItemsViewModel>()

                                    // This loop will create 20 Views containing
                                    // the image with the count of view
                                    val aciklama = task.documents.get(0).getString("aciklama")
                                    textView.text = "Anket ID : "+" "+bilgi +"\n" + aciklama

                                    for (document in task.documents){
                                        val cevaplar = ArrayList<String>()
                                        val cevapSirasi = ArrayList<String>()
                                        val field_alan = document.data?.size


                                        val soru = document.getString("soru_Metni")



                                        for (i in 1..field_alan!!){
                                            val cevap_metin = document.getString("cevap_$i")
                                            if(cevap_metin!=null){
                                                cevaplar.add(cevap_metin)
                                                cevapSirasi.add("cevap_$i")
                                            }
                                        }

                                        data.add(ItemsViewModel(soru,cevaplar,cevapSirasi,document.id,bilgi.toString()))
                                    }

                                    // This will pass the ArrayList to our Adapter

                                    val adapter = CustomAdapter(data)

                                    // Setting the Adapter with the recyclerview
                                    recyclerview.adapter = adapter
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

            /*
            kontrolFirestore1.get().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    if (task.result?.isEmpty == false) {
                        // Koleksiyon var

                        firestore.collection(bilgi.toString()).get().addOnSuccessListener {task->
                            val data = ArrayList<ItemsViewModel>()

                            // This loop will create 20 Views containing
                            // the image with the count of view
                            val aciklama = task.documents.get(0).getString("aciklama")
                            textView.text = "Anket ID : "+" "+bilgi +"\n" + aciklama

                            for (document in task.documents){
                                val cevaplar = ArrayList<String>()
                                val cevapSirasi = ArrayList<String>()
                                val field_alan = document.data?.size


                                val soru = document.getString("soru_Metni")



                                for (i in 1..field_alan!!){
                                    val cevap_metin = document.getString("cevap_$i")
                                    if(cevap_metin!=null){
                                        cevaplar.add(cevap_metin)
                                        cevapSirasi.add("cevap_$i")
                                    }
                                }

                                data.add(ItemsViewModel(soru,cevaplar,cevapSirasi,document.id,bilgi.toString()))
                            }

                            // This will pass the ArrayList to our Adapter

                            val adapter = CustomAdapter(data)

                            // Setting the Adapter with the recyclerview
                            recyclerview.adapter = adapter
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
            }*/





        }



    /*
       firestore.collection(bilgi.toString()).get().addOnSuccessListener {task->
            val data = ArrayList<ItemsViewModel>()

            // This loop will create 20 Views containing
            // the image with the count of view
           val aciklama = task.documents.get(0).getString("aciklama")
           textView.text = "Anket ID : "+" "+bilgi +"\n" + aciklama

            for (document in task.documents){
                val cevaplar = ArrayList<String>()
                val cevapSirasi = ArrayList<String>()
                val field_alan = document.data?.size


                val soru = document.getString("soru_Metni")



                for (i in 1..field_alan!!){
                    val cevap_metin = document.getString("cevap_$i")
                    if(cevap_metin!=null){
                        cevaplar.add(cevap_metin)
                        cevapSirasi.add("cevap_$i")
                    }
                }

                data.add(ItemsViewModel(soru,cevaplar,cevapSirasi,document.id,bilgi.toString()))
            }

            // This will pass the ArrayList to our Adapter

            val adapter = CustomAdapter(data)

            // Setting the Adapter with the recyclerview
            recyclerview.adapter = adapter
        }
        
     */


        val button_kaydet = findViewById<Button>(R.id.button_kaydet)
        button_kaydet.setOnClickListener{showAlertDialog(this, "Cevaplarınızı kontrol ediniz", "Göndermek için Tamam butonuna basınız.")}









    }

    fun showAlertDialog(context: Context, title: String, message: String) {

        val bilgi = intent.getStringExtra("bilgi")
        var deviceID = Settings.Secure.getString(this.contentResolver, Settings.Secure.ANDROID_ID)
        var deviceID_string = deviceID.toString()

        val alertDialogBuilder = AlertDialog.Builder(context)
        alertDialogBuilder.setTitle(title)
        alertDialogBuilder.setMessage(message)
        alertDialogBuilder.setPositiveButton("Tamam") { dialog, _ ->
            // Tamam butonuna basıldığında yapılacak işlemler
            dialog.dismiss() // Uyarı kutusunu kapat


            val cevap_document = bilgi + "_Cevaplar"
            val kaydet_firebase = firestore.collection(cevap_document).document(deviceID_string)

            for (data in cevaplarArray) {
                println(data)
                println(deviceID_string)
                kaydet_firebase.set(
                    mapOf(data.collectionID to data.kullaniciCevabi),
                    SetOptions.merge()
                )
                    .addOnSuccessListener {
                        println("Veri başarıyla kaydedildi.")
                    }
                    .addOnFailureListener { e ->
                        println("Veri kaydedilirken hata oluştu: $e")
                    }
            }

            val intent = Intent(this, MainActivity8::class.java)
            startActivity(intent)

            }

        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }


    fun onItemModelUpdated(itemViewModel: ItemsViewModel){

        var deviceID = Settings.Secure.getString(this.contentResolver, Settings.Secure.ANDROID_ID)
        var deviceID_string = deviceID.toString()

        val kullaniciCevabi=itemViewModel.kullaniciCevabi
        //println(itemViewModel)

        val bilgi = itemViewModel.collectionID
        val cevap_document = bilgi + "_Cevap"



        println(cevaplarArray)

        val yeniCevap = itemViewModel
        val index = cevaplarArray.indexOfFirst { it.collectionID == yeniCevap.collectionID }

        if (index == -1) {
            cevaplarArray.add(yeniCevap) // Koleksiyon eklenir
        } else {
            cevaplarArray[index] = yeniCevap // Koleksiyon güncellenir
            println("Aynı collectionID'ye sahip öğe zaten mevcut. Öğe güncellendi.")
        }

        println(cevaplarArray)


    }


}


