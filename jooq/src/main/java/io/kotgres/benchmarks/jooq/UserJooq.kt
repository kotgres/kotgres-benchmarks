package io.kotgres.benchmarks.jooq

import java.time.LocalDate

data class UserJooq(
    val id: Int,
    val name: String?,
    val age: Int?,
    val dateCreated: LocalDate?,
)
