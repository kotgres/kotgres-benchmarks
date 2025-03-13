package io.kotgres.benchmarks.exposed

import kotlinx.datetime.LocalDateTime


data class UserExposed(
    val id: Int,
    val name: String?,
    val age: Int?,
    val dateCreated: LocalDateTime?,
)
