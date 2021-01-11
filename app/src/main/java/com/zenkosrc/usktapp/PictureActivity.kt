package com.zenkosrc.usktapp

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.activity_picture.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.util.*


class PictureActivity : AppCompatActivity() {

    private val TAG = PictureActivity::class.simpleName

    companion object {
        const val ORIGINAL_SIZE_URL = "original_picture_url"
        const val REGULAR_SIZE_URL  = "picture_url"
        const val REQUEST_CODE      = 1;
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_picture)

        loadPicture()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_picture, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_save -> {
                Log.d(TAG, "CLICK + ${isPermissionsAllowed()}")

                if (isPermissionsAllowed()){
                    saveImage()
                }else{
                    checkPermission()
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun loadPicture() {

        Glide.with(this)
            .load(intent.getStringExtra(REGULAR_SIZE_URL))
            .thumbnail(0.5f)
            .into(pictureImageView)
    }

    private fun checkPermission(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!isPermissionsAllowed()) requestPermissions()
        }
    }

    private fun isPermissionsAllowed(): Boolean{
        return ContextCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(this as Activity,arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),REQUEST_CODE)
    }

    override fun onRequestPermissionsResult(requestCode: Int,permissions: Array<String>,grantResults: IntArray) {
        when (requestCode) {
            REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d(TAG, "permission is granted")
                } else {
                    Log.d(TAG, "permission is denied")
                    saveImage()
                }
                return
            }
        }
    }

    private fun saveImage() {

        Glide.with(this)
            .asBitmap()
            .load(intent.getStringExtra(ORIGINAL_SIZE_URL))
            .listener(object : RequestListener<Bitmap> {
                override fun onLoadFailed(p0: GlideException?, p1: Any?, p2: Target<Bitmap>?, p3: Boolean): Boolean {
                    Log.d(TAG, "onLoadFailed ${p0}")
                    return false
                }
                override fun onResourceReady(p0: Bitmap?, p1: Any?, p2: Target<Bitmap>?, p3: DataSource?, p4: Boolean): Boolean {
                    Log.d(TAG, "OnResourceReady ${p0?.byteCount}")

                    CoroutineScope(Dispatchers.IO).launch {
                        p0?.let { saveImageToInternalStorage(it) }
                    }
                    return true
                }
            }).submit()
    }

    private fun saveImageToInternalStorage(image:Bitmap){

        var filePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
        var file = File(filePath, "${UUID.randomUUID()}.jpg")

        try {
            val stream: OutputStream = FileOutputStream(file)
            image.compress(Bitmap.CompressFormat.JPEG, 100, stream)
            stream.flush()
            stream.close()

//            Looper.prepare()
//            Toast.makeText(this, "not connected", Toast.LENGTH_LONG).show()
//            Looper.loop()

            scanFile(Uri.fromFile(file))
        } catch (e: IOException){ // Catch the exception
            e.printStackTrace()
        }
    }

    private fun scanFile(imageUri: Uri){
        // Refresh gallery
        val scanIntent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
        scanIntent.setData(imageUri)
        sendBroadcast(scanIntent)
    }
}
