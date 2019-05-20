package pl.michalgellert.trendingrepos.model

import org.junit.Test

class GithubRepositoryTest {

    @Test
    fun toRepository_isEmptyIfNull_returnsTrue() {
        val ghRepo = GithubRepository()
        assert(ghRepo.toRepository().isEmpty())
    }

    @Test
    fun toRepository_isNotEmptyIfNotNull_returnsTrue() {
        val ghRepo = GithubRepository(false, listOf(Item()), 1)
        assert(ghRepo.toRepository().size == 1)
    }
}