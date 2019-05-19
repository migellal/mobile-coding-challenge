package pl.michalgellert.trendingrepos

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_trending -> {
                message_tv.setText(R.string.title_trending)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_settings -> {
                message_tv.setText(R.string.title_settings)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navView_bnv.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
    }
}
