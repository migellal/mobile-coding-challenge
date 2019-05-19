package pl.michalgellert.trendingrepos

import android.os.Bundle
import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import pl.michalgellert.trendingrepos.model.Repository

class MainActivity : AppCompatActivity() {

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_trending -> {
                message_tv.setText(R.string.title_trending)
                downloadData()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_settings -> {
                message_tv.setText(R.string.title_settings)
                repositoryList_rv.visibility = View.INVISIBLE
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navView_bnv.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
        downloadData()
    }

    private fun downloadData() {
        repositoryList_rv.apply {
            visibility = View.VISIBLE
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = RepositoryListAdapter(fakeData())
        }
    }

    fun fakeData(): List<Repository> = listOf(
        Repository("repo1", "repo1 desc", 123456, "user", "https://avatars0.githubusercontent.com/u/17506342?v=4"),
        Repository("repo2", "repo2 desc", 1, "user2", "https://avatars0.githubusercontent.com/u/17506342?v=4"),
        Repository("repo3", "repo3 desc", 99, "user3", "https://avatars0.githubusercontent.com/u/17506342?v=4")
    )
}
