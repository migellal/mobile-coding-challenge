package pl.michalgellert.trendingrepos.model

import androidx.lifecycle.ViewModel

class RepositoryList : ViewModel() {
    var repos: List<Repository> = listOf()
        set(value) {
            update = false
            field = value
        }
    private var page: Int = 0
    var scrollPosition: Int = 0
    var update = false

    fun nextPage(): Int {
        page += 1
        return page
    }


}