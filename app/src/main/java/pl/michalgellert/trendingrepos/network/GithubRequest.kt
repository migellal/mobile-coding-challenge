package pl.michalgellert.trendingrepos.network

import kotlinx.coroutines.Deferred
import pl.michalgellert.trendingrepos.model.GithubResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubRequest {

    @GET("/search/repositories")
    fun getTrendingReposAsync(@Query("q") createdDate: String,
                              @Query("page") page: Int = 1,
                              @Query("sort") sort: String = "stars",
                              @Query("order") order: String = "desc")
            : Deferred<Response<GithubResponse>>
}