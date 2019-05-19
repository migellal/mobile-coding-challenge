package pl.michalgellert.trendingrepos

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import pl.michalgellert.trendingrepos.model.GithubRepository
import pl.michalgellert.trendingrepos.model.Repository
import retrofit2.HttpException

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
        val service = GithubFactory.service()
        CoroutineScope(Dispatchers.IO).launch {
            val request = service.getTrendingReposAsync()
            try {
                val response = request.await()
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        Log.d("REQUEST", response.message())
                        response.body()?.let { initRecyclerView(it) }

                    } else {
                        Toast.makeText(
                            this@MainActivity, "Error network operation failed with ${response.code()}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            } catch (e: HttpException) {
                Log.e("REQUEST", "Exception ${e.message}")
            } catch (e: Throwable) {
                Log.e("REQUEST", "Ooops: Something else went wrong")
                Log.e("REQUEST", e.message)
            }
        }

    }

    private fun initRecyclerView(ghRepo: GithubRepository) {
        Log.d("REQUEST", ghRepo.items.toString())
        repositoryList_rv.layoutManager = LinearLayoutManager(this@MainActivity)
        repositoryList_rv.adapter = RepositoryListAdapter(githubRepoToRepo(ghRepo), this)
    }

    fun fakeData(): List<Repository> = listOf(
        Repository("repo1", "repo1 desc", 123456, "user", "https://avatars0.githubusercontent.com/u/17506342?v=4"),
        Repository("repo2", "repo2 desc", 1, "user2", "https://avatars0.githubusercontent.com/u/17506342?v=4"),
        Repository("repo3", "repo3 desc", 99, "user3", "https://avatars0.githubusercontent.com/u/17506342?v=4")
    )

    fun githubRepoToRepo(ghRepo: GithubRepository?): List<Repository> {
        val list = ArrayList<Repository>()
        if (ghRepo?.items != null) {
            for (gh in ghRepo.items) {
                list.add(
                    Repository(
                        name = gh?.name ?: "",
                        description = gh?.description ?: "",
                        avatar = gh?.owner?.avatarUrl ?: "",
                        username = gh?.owner?.login ?: "",
                        stars = gh?.stargazersCount ?: -1
                    )
                )
            }
        }
        return list
    }
}
