// ResultDisplay.kt
package buffml.com.banbun

import android.content.res.AssetManager
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import buffml.com.smartdoctor.R
import java.io.IOException

class ResultDisplay : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result_display)

        // Retrieve the result image file name from the intent
        val resultImageFileName = intent.getStringExtra("RESULT_IMAGE_FILE_NAME")

        // Set the result image dynamically from the assets folder
        val imageView: ImageView = findViewById(R.id.imageView)
        try {
            val assetManager: AssetManager = assets
            val inputStream = assetManager.open(resultImageFileName!!)
            val drawable = Drawable.createFromStream(inputStream, null)
            imageView.setImageDrawable(drawable)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}
