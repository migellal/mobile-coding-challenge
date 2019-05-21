package pl.michalgellert.trendingrepos.network

import android.widget.ImageView
import com.squareup.picasso.Picasso
import pl.michalgellert.trendingrepos.util.DateCalculator

class NetworkService {

    fun picasso(avatar: String, repoAvatar: ImageView) {
        Picasso.get().load(avatar).into(repoAvatar)
    }

    fun trendingRepos(page: Int) = GithubFactory.api.getTrendingReposAsync(
        DateCalculator().getCreatedDate30DaysAgo(), page
    )

}