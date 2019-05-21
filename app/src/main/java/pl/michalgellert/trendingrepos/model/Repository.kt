package pl.michalgellert.trendingrepos.model

typealias Link = String

class Repository(
    val name: String,
    val description: String,
    val stars: Int,
    val username: String,
    val avatar: Link
)