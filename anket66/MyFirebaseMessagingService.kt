package com.bogazliyan.anket66

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {
    override fun onMessageReceived(remoteMessage: RemoteMessage) {

        // Bildirimdeki başlık ve mesaj alınır
        val title = remoteMessage.notification?.title
        val message = remoteMessage.notification?.body

        val keys = remoteMessage.data.keys
        println(remoteMessage.data["key"])
        println(remoteMessage.data["key"].toString())

        var deger = null

        println(keys)
        println(remoteMessage.data["key"].toString())

        for (key in keys) {
            println("Key: $key")
            deger = key as Nothing?
        }

        // Eğer bildirimde başlık ya da mesaj yoksa, bunlar varsayılan olarak atanır
        val notificationTitle = title ?: "Bildirim"
        val notificationMessage = message ?: "Bir bildirim var."


        // Key ve value değerlerini kontrol ederek uygun Activity'yi belirler
        lateinit var intent: Intent
        println("degerrrrrrrrrrrrrr $deger")

        when (deger) {
            "activity2" -> {
                intent = Intent(this, MainActivity2::class.java)
            }
            else -> {
                intent = Intent(this, MainActivity::class.java)
            }
        }

        // Bildirim tıklanınca açılacak olan Activity
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)



        // Notification göstermek için
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notificationChannel = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel("default", "Bildirimler", NotificationManager.IMPORTANCE_DEFAULT)
        } else {
            TODO("VERSION.SDK_INT < O")
        }
        notificationManager.createNotificationChannel(notificationChannel)

        val notification = NotificationCompat.Builder(this, "default")
            .setContentTitle(notificationTitle)
            .setContentText(notificationMessage)
            .setSmallIcon(R.drawable.bildirim_icon)
            .setContentIntent(pendingIntent)
            .build()

        notificationManager.notify(0, notification)
    }

}


