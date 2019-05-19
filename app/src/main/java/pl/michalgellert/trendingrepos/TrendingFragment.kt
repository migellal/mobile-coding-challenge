package pl.michalgellert.trendingrepos

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import pl.michalgellert.trendingrepos.model.Repository
import retrofit2.HttpException

class TrendingFragment : Fragment() {

    private lateinit var repositoryList: RecyclerView
    private lateinit var loadingProgressBar: ProgressBar
    private val TAG = "REQUEST"

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
        val fView = inflater.inflate(R.layout.fragment_trending, container, false)
        repositoryList = fView.findViewById(R.id.repositoryList_rv)
        loadingProgressBar = fView.findViewById(R.id.loading_pb)
        repositoryList.layoutManager = LinearLayoutManager(activity as Context)
        repositoryList.adapter = RepositoryListAdapter(listOf())
        loadingProgressBar.visibility = View.VISIBLE
        return fView
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
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
                        Log.d(TAG, response.message())
                        initRecyclerView(response.body()?.toRepository() ?: listOf())
                    } else {
                        logError("Response message: ${response.message()}")
                    }
                }
            } catch (e: HttpException) {
                logError("Exception ${e.message}")
            } catch (e: Throwable) {
                logError("Something else went wrong\n${e.message}")
            }
        }

    }

    private fun initRecyclerView(list: List<Repository>) {
        if (::repositoryList.isInitialized) {
            repositoryList.adapter = RepositoryListAdapter(list)
        }
        if(::loadingProgressBar.isInitialized) {
            loadingProgressBar.visibility = View.GONE
        }
    }

    private fun logError(string: String) {
        Log.e(TAG, string)
        Toast.makeText(activity, string, Toast.LENGTH_LONG).show()
    }

    fun fakeData(): List<Repository> = listOf(
        Repository("repo1", "repo1 desc", 123456, "user", "https://avatars0.githubusercontent.com/u/17506342?v=4"),
        Repository("repo2", "repo2 desc", 1, "user2", "https://avatars0.githubusercontent.com/u/17506342?v=4"),
        Repository("repo3", "repo3 desc", 99, "user3", "https://avatars0.githubusercontent.com/u/17506342?v=4")
    )

}
