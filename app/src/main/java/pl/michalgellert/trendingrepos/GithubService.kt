package pl.michalgellert.trendingrepos

import kotlinx.coroutines.Deferred
import pl.michalgellert.trendingrepos.model.GithubRepository
import retrofit2.Response
import retrofit2.http.GET

interface GithubService {

    @GET("/search/repositories?q=created:>2017-10-22&sort=stars&order=desc")
    fun getTrendingReposAsync(): Deferred<Response<GithubRepository>>
}