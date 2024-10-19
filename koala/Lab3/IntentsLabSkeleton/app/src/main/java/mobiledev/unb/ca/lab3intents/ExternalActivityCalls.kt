package mobiledev.unb.ca.lab3intents

import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import androidx.annotation.RequiresApi
import java.io.File
import java.io.IOException
import java.io.OutputStream
import android.widget.Button
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import java.text.SimpleDateFormat
import java.util.Date

class ExternalActivityCalls : AppCompatActivity() {
    // Attributes for storing the file photo path
    private lateinit var currentPhotoPath: String
    
    // Activity listeners
    private var cameraActivityResultLauncher: ActivityResultLauncher<Intent>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.external_activity_calls)

        val cameraButton = findViewById<Button>(R.id.camera)
        val emailButton = findViewById<Button>(R.id.email)
        val backButton = findViewById<Button>(R.id.back)
        cameraButton.setOnClickListener {
            dispatchTakePictureIntent()
        }
        emailButton.setOnClickListener {
            dispatchSendEmailIntent()
        }
        backButton.setOnClickListener {
            val intent = Intent(this@ExternalActivityCalls, MainActivity::class.java)
            startActivity(intent)
            this.finish()
        }

        // Register the activity listener
        setCameraActivityResultLauncher()
    }

    private fun dispatchSendEmailIntent() {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:")
            putExtra(Intent.EXTRA_EMAIL, arrayOf("bfanjoy@unb.ca",))
            putExtra(Intent.EXTRA_SUBJECT, "CS2063 Lab 3")
            putExtra(Intent.EXTRA_TEXT, "This is a test email!")
        }
        startActivity(intent)
    }

    private fun setCameraActivityResultLauncher() {
        // Handle the image capture result
        // TODO - Implement later
        cameraActivityResultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result: ActivityResult ->
            if (result.resultCode == RESULT_OK) {
                galleryAddPic()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_external_activity_calls, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId
        return if (id == R.id.action_settings) {
            true
        } else super.onOptionsItemSelected(item)
    }

    private fun galleryAddPic() {
        Log.d(TAG, "Saving image to the gallery")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            // Android 10 (API 29) and above
            mediaStoreAddPicToGallery()
        } else {
            // Pre Android 10
            mediaScannerAddPicToGallery()
        }
        Log.i(TAG, "Image saved!")
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun mediaStoreAddPicToGallery() {
        val bitmap = BitmapFactory.decodeFile(currentPhotoPath)

        val contentValues = getContentValues()
        contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
        contentValues.put(MediaStore.Images.Media.IS_PENDING, true)

        val resolver = contentResolver
        val imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
        if (imageUri != null) {
            saveImageToStream(bitmap, resolver.openOutputStream(imageUri))
            contentValues.put(MediaStore.Images.Media.IS_PENDING, false)
            resolver.update(imageUri, contentValues, null, null)
        }
    }

    private fun getContentValues() : ContentValues {
        val values = ContentValues()
        values.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
        values.put(MediaStore.Images.Media.DATE_ADDED, System.currentTimeMillis() / 1000)
        values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis())
        return values
    }

    private fun saveImageToStream(bitmap: Bitmap, outputStream: OutputStream?) {
        try {
            if (outputStream != null) {
                try {
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                    outputStream.close()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        } catch (e: IOException) {
            Log.e(TAG, "Error saving the file ", e)
        }
    }

    private fun mediaScannerAddPicToGallery() {
        val file = File(currentPhotoPath)
        MediaScannerConnection.scanFile(this@ExternalActivityCalls,
            arrayOf(file.toString()),
            arrayOf(file.name),
            null)
    }
    companion object {
        // String for LogCat documentation
        private const val TAG = "External Activity Calls"
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = absolutePath
        }
    }

    private fun dispatchTakePictureIntent() {
        intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            // Ensure that there's a camera activity to handle the intent
            takePictureIntent.resolveActivity(packageManager)?.also {
                // Create the File where the photo should go
                val photoFile: File? = try {
                    createImageFile()
                } catch (ex: IOException) {
                    // Error occurred while creating the File
                    null
                }
                // Continue only if the File was successfully created
                photoFile?.also {
                    val photoURI: Uri = FileProvider.getUriForFile(
                        this,
                        "mobiledev.unb.ca.lab3intents.provider",
                        it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    cameraActivityResultLauncher!!.launch(takePictureIntent)
                }
            }
        }
        startActivity(intent)
    }
}