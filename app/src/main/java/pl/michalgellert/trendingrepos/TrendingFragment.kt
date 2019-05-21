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
        return fView
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        downloadData()
    }


    private fun downloadData(page: Int = 1, list: List<Repository> = listOf()) {
        if (::loadingProgressBar.isInitialized)
            loadingProgressBar.visibility = View.VISIBLE
        val service = GithubFactory.service()
        CoroutineScope(Dispatchers.IO).launch {
            val request = service.getTrendingReposAsync(DateCalculator().getCreatedDate30DaysAgo(), page)
            try {
                val response = request.await()
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        Log.d(TAG, response.message())
                        initRecyclerView(response.body()?.toRepository(page, list) ?: listOf())
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
            val lastPage = list.maxBy { it.page }?.page ?: 1
            repositoryList.scrollToPosition(list.count { it.page < lastPage } - 1)
            repositoryList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if (!recyclerView.canScrollVertically(1) &&
                        ::loadingProgressBar.isInitialized &&
                        loadingProgressBar.visibility == View.GONE
                    ) {
                        downloadData(list.lastOrNull()?.page?.plus(1) ?: 1, list)
                    }
                }
            })
        }
        if (::loadingProgressBar.isInitialized) {
            loadingProgressBar.visibility = View.GONE
        }
    }

    private fun logError(string: String) {
        Log.e(TAG, string)
        Toast.makeText(activity, string, Toast.LENGTH_LONG).show()
    }

}
