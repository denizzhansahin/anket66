package com.bogazliyan.anket66

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import android.widget.Button
import android.widget.TextView
import com.google.zxing.BinaryBitmap
import com.google.zxing.DecodeHintType
import com.google.zxing.RGBLuminanceSource
import com.google.zxing.common.HybridBinarizer
import com.google.zxing.qrcode.QRCodeReader

class MainActivity7 : AppCompatActivity() {

    private val REQUEST_IMAGE_PICK = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main7)

        val textView = findViewById<TextView>(R.id.textView7)

        val button = findViewById<Button>(R.id.button3)
        button.setOnClickListener()
        {
            val baslat_intent = Intent(this, MainActivity::class.java)
            startActivity(baslat_intent)
        }

        // Galeriden görüntü seçme işlemini başlat
        val pickImageIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(pickImageIntent, REQUEST_IMAGE_PICK)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val textView = findViewById<TextView>(R.id.textView7)

        if (requestCode == REQUEST_IMAGE_PICK && resultCode == RESULT_OK) {
            val imageUri = data?.data

            if (imageUri != null) {
                val imageBitmap = decodeBitmapFromUri(imageUri)

                if (imageBitmap != null) {
                    // QR kodunu görüntüden okuyun ve sonucu işleyin
                    val result = readQRCodeFromBitmap(imageBitmap)
                    if (result.isNullOrEmpty()) {
                        textView.text = "QR kodu bulunamadı."
                    } else {
                        val baslatIntent = Intent(this, MainActivity2::class.java)
                        baslatIntent.putExtra("bilgi", result)
                        startActivity(baslatIntent)
                    }
                } else {
                    textView.text = "Görüntü işlenemedi."
                }
            } else {
                textView.text = "Görüntü alınamadı."
            }
        }
    }

    private fun decodeBitmapFromUri(uri: Uri): Bitmap? {
        return try {
            val inputStream = contentResolver.openInputStream(uri)
            BitmapFactory.decodeStream(inputStream)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun readQRCodeFromBitmap(bitmap: Bitmap): String? {
        try {
            val intArray = IntArray(bitmap.width * bitmap.height)
            bitmap.getPixels(intArray, 0, bitmap.width, 0, 0, bitmap.width, bitmap.height)
            val source = RGBLuminanceSource(bitmap.width, bitmap.height, intArray)
            val binaryBitmap = BinaryBitmap(HybridBinarizer(source))
            val reader = QRCodeReader()
            val hints = HashMap<DecodeHintType, Any>()
            hints[DecodeHintType.CHARACTER_SET] = "UTF-8"
            val result = reader.decode(binaryBitmap, hints)
            return result.text
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }
}
