package pl.michalgellert.trendingrepos.model

import org.junit.Test

class GithubResponseTest {

    @Test
    fun toRepository_isEmptyIfNull_returnsTrue() {
        val ghRepo = GithubResponse()
        assert(ghRepo.toRepository().isEmpty())
    }

    @Test
    fun toRepository_isNotEmptyIfNotNull_returnsTrue() {
        val ghRepo = GithubResponse(false, listOf(Item()), 1)
        assert(ghRepo.toRepository().size == 1)
    }
}