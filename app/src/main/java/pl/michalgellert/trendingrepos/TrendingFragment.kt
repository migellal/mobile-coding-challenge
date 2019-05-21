package pl.michalgellert.trendingrepos

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_trending.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import pl.michalgellert.trendingrepos.model.Repository
import pl.michalgellert.trendingrepos.model.RepositoryList
import retrofit2.HttpException

class TrendingFragment : Fragment() {

    private lateinit var repositoryListView: RecyclerView
    private lateinit var loadingCircle: ProgressBar
    private var repositoryList = RepositoryList()
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
        repositoryListView = fView.findViewById(R.id.repositoryList_rv)
        loadingCircle = fView.findViewById(R.id.loading_pb)
        repositoryListView.layoutManager = LinearLayoutManager(activity as Context)
        repositoryListView.adapter = RepositoryListAdapter(listOf())
        if (activity != null)
            repositoryList = ViewModelProviders.of(activity as FragmentActivity).get(RepositoryList::class.java)
        if (repositoryList.repos.isEmpty())
            repositoryList.update = true
        prepareData()
        return fView
    }

    private fun prepareData(list: List<Repository> = listOf()) {
        enableLoadingCircle()
        if (repositoryList.update) {
            val service = GithubFactory.service()
            CoroutineScope(Dispatchers.IO).launch {
                val request =
                    service.getTrendingReposAsync(DateCalculator().getCreatedDate30DaysAgo(), repositoryList.nextPage())
                try {
                    val response = request.await()
                    withContext(Dispatchers.Main) {
                        if (response.isSuccessful) {
                            Log.d(TAG, response.message())
                            initRecyclerView(response.body()?.toRepository(list) ?: listOf())
                        } else {
                            error("Response message: ${response.message()}")
                        }
                    }
                } catch (e: HttpException) {
                    error("Exception: ${e.message}")
                } catch (e: Throwable) {
                    error("Something else went wrong: ${e.message}")
                }
            }
        } else
            initRecyclerView(repositoryList.repos)
    }

    private fun initRecyclerView(list: List<Repository>) {
        repositoryList.repos = list

        repositoryListView.adapter = RepositoryListAdapter(list)
        repositoryListView.scrollToPosition(repositoryList.scrollPosition)
        repositoryListView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val lm = recyclerView.layoutManager as LinearLayoutManager
                repositoryList.scrollPosition = lm.findFirstCompletelyVisibleItemPosition()
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1) &&
                    !isLoadingCircleEnabled()
                ) {
                    repositoryList.update = true
                    prepareData(list)
                }
            }
        })
        disableLoadingCircle()
    }

    private fun enableLoadingCircle() {
        loadingCircle.visibility = View.VISIBLE
    }

    private fun disableLoadingCircle() {
        loadingCircle.visibility = View.GONE
    }

    private fun isLoadingCircleEnabled(): Boolean =
        loadingCircle.visibility == View.VISIBLE

    private fun error(string: String) {
        Log.e(TAG, string)
        Snackbar.make(trendingFragment, string, Snackbar.LENGTH_LONG).show()
        disableLoadingCircle()
    }

}
