package buffml.com.banbun

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import buffml.com.smartdoctor.R
import buffml.com.smartdoctor.R.id
import com.google.android.material.bottomnavigation.BottomNavigationView

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigationView = findViewById<BottomNavigationView>(id.bottomNavigationView)

        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                id.home -> {
                    startActivity(Intent(this@MainActivity, MainActivity::class.java))
                    return@setOnNavigationItemSelectedListener true
                }
                id.guide -> {
                    startActivity(Intent(this@MainActivity, Guide::class.java))
                    return@setOnNavigationItemSelectedListener true
                }
                id.classify -> {
                    startActivity(Intent(this@MainActivity, Classify::class.java))
                    return@setOnNavigationItemSelectedListener true
                }
                id.resources -> {
                    startActivity(Intent(this@MainActivity, Resources::class.java))
                    return@setOnNavigationItemSelectedListener true
                }
                id.profile -> {
                    startActivity(Intent(this@MainActivity, Profile::class.java))
                    return@setOnNavigationItemSelectedListener true
                }
                else -> return@setOnNavigationItemSelectedListener false
            }
        }
    }

}


