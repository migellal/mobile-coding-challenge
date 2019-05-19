package pl.michalgellert.trendingrepos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class TrendingFragment: Fragment() {

    companion object {
        fun newInstance(): TrendingFragment {
            return TrendingFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_trending, container, false)
    }

    /*

        private val repos = ArrayList<Repository>()


    private fun downloadData() {
        if(repos.isEmpty()) {
            val service = GithubFactory.service()
            CoroutineScope(Dispatchers.IO).launch {
                val request = service.getTrendingReposAsync()
                try {
                    val response = request.await()
                    withContext(Dispatchers.Main) {
                        if (response.isSuccessful) {
                            Log.d("REQUEST", response.message())
                            repos.addAll(response.body()?.toRepository() ?: listOf())
                            initRecyclerView()
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
        } else {
            initRecyclerView()
        }

    }

    private fun initRecyclerView() {
        repositoryList_rv.layoutManager = LinearLayoutManager(this)
        repositoryList_rv.adapter = RepositoryListAdapter(repos, this)
    }

    fun fakeData(): List<Repository> = listOf(
        Repository("repo1", "repo1 desc", 123456, "user", "https://avatars0.githubusercontent.com/u/17506342?v=4"),
        Repository("repo2", "repo2 desc", 1, "user2", "https://avatars0.githubusercontent.com/u/17506342?v=4"),
        Repository("repo3", "repo3 desc", 99, "user3", "https://avatars0.githubusercontent.com/u/17506342?v=4")
    )
*/
}
