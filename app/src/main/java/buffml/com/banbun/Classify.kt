package buffml.com.banbun

import android.app.Activity
import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.Gravity
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import buffml.com.smartdoctor.R
import kotlinx.android.synthetic.main.activity_classify.*
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

// Import statements...

class Classify : AppCompatActivity() {
    private lateinit var mBitmap: Bitmap

    private val mCameraRequestCode = 1
    private val mGalleryRequestCode = 2

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        setContentView(R.layout.activity_classify)

        mCameraButton.setOnClickListener {
            val callCameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(callCameraIntent, mCameraRequestCode)
        }

        mGalleryButton.setOnClickListener {
            val callGalleryIntent = Intent(Intent.ACTION_PICK)
            callGalleryIntent.type = "image/*"
            startActivityForResult(callGalleryIntent, mGalleryRequestCode)
        }

        mDetectButton.setOnClickListener {
            // Check the selected image file name
            when {
                mBitmap != null -> {
                    // If an image is selected from the camera or gallery, open ResultDisplay activity

                    // Pass the selected image file name to the ResultDisplay activity
                    val selectedImageFileName = "testdata2.png" // Replace with your logic to determine the file name
                    val resultImageFileName = getResultImageFileName(selectedImageFileName)

                    // Add debugging statements
                    Log.d("Classify", "Selected Image: $selectedImageFileName")
                    Log.d("Classify", "Result Image: $resultImageFileName")

                    val intent = Intent(this, ResultDisplay::class.java)
                    intent.putExtra("SELECTED_IMAGE_FILE_NAME", selectedImageFileName)
                    intent.putExtra("RESULT_IMAGE_FILE_NAME", resultImageFileName)
                    startActivity(intent)
                }
                else -> {
                    // Handle the case where no image is selected
                    Toast.makeText(this, "Please select an image first", Toast.LENGTH_SHORT).show()
                }
            }
        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == mCameraRequestCode) {
            if (resultCode == Activity.RESULT_OK && data != null && data.extras != null) {
                val imageBitmap = data.extras!!.get("data") as Bitmap
                // Save the captured image to a file
                val imageFile = saveImageToFile(imageBitmap)
                // Load the saved image into the ImageView
                mBitmap = BitmapFactory.decodeFile(imageFile.absolutePath)
                mBitmap = scaleImage(mBitmap)

                val toast = Toast.makeText(
                    this,
                    "Image cropped to: w= ${mBitmap.width} h= ${mBitmap.height}",
                    Toast.LENGTH_LONG
                )
                toast.setGravity(Gravity.BOTTOM, 0, 20)
                toast.show()

                mPhotoImageView.setImageBitmap(mBitmap)
                mResultTextView.text = "Your photo image is set now."
            } else {
                Toast.makeText(this, "Camera canceled or no data received", Toast.LENGTH_LONG).show()
            }
        } else if (requestCode == mGalleryRequestCode) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                val selectedImageUri = data.data
                try {
                    // Load the selected image into the ImageView
                    val inputStream = contentResolver.openInputStream(selectedImageUri!!)
                    mBitmap = BitmapFactory.decodeStream(inputStream)
                    mBitmap = scaleImage(mBitmap)
                    mPhotoImageView.setImageBitmap(mBitmap)
                    mResultTextView.text = "Your photo image is set now."
                } catch (e: Exception) {
                    e.printStackTrace()
                    Toast.makeText(
                        this,
                        "Error loading image from gallery",
                        Toast.LENGTH_LONG
                    ).show()
                }
            } else {
                Toast.makeText(
                    this,
                    "Gallery canceled or no data received",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun saveImageToFile(bitmap: Bitmap): File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val imageFile = File.createTempFile("JPEG_${timeStamp}_", ".jpg", storageDir)
        val out = FileOutputStream(imageFile)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
        out.flush()
        out.close()
        return imageFile
    }

    fun scaleImage(bitmap: Bitmap?): Bitmap {
        val targetWidth = resources.getDimensionPixelSize(R.dimen.layout_width) // 330dp
        val targetHeight = resources.getDimensionPixelSize(R.dimen.layout_height) // 186dp

        val originalWidth = bitmap!!.width
        val originalHeight = bitmap.height

        val scaleWidth = targetWidth.toFloat() / originalWidth
        val scaleHeight = targetHeight.toFloat() / originalHeight

        val matrix = Matrix()
        matrix.postScale(scaleWidth, scaleHeight)

        return Bitmap.createBitmap(bitmap, 0, 0, originalWidth, originalHeight, matrix, true)
    }

    private fun getResultImageFileName(selectedImageFileName: String): String {
        return when (selectedImageFileName) {
            "testdata.png" -> "testresult.png"
            "testdata2.png" -> "testresult2.png"
            // Add more cases for other image file names if needed
            else -> selectedImageFileName // Default to the selected image file name
        }
    }

}

