package buffml.com.banbun

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import buffml.com.smartdoctor.R

class Resources : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resources)

        val openActivity1 = findViewById<Button>(R.id.ResButton1)
        openActivity1.setOnClickListener {
            // Create an Intent to start the NewActivity
            val intent = Intent(this, BBTVResources::class.java)
            startActivity(intent)
        }

        val openActivity2 = findViewById<Button>(R.id.ResButton2)
        openActivity2.setOnClickListener {
            // Create an Intent to start the NewActivity
            val intent = Intent(this, ElisaResources::class.java)
            startActivity(intent)
        }

        val openActivity3 = findViewById<Button>(R.id.ResButton3)
        openActivity3.setOnClickListener {
            // Create an Intent to start the NewActivity
            val intent = Intent(this, AgdiaResources::class.java)
            startActivity(intent)
        }

    }
}