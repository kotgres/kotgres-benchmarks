package io.kotgres.benchmarks.ktorm

import java.time.LocalDate

class UserKtorm(
    val id: Int,
    val name: String?,
    val age: Int?,
    val dateCreated: LocalDate?,
)
