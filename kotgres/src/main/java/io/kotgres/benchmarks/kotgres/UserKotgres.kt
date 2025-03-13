package io.kotgres.benchmarks.kotgres

import io.kotgres.orm.annotations.Generated
import io.kotgres.orm.annotations.PrimaryKey
import io.kotgres.orm.annotations.Table
import java.time.LocalDateTime

@Table(name = "users_with_id")
data class UserKotgres(
    @PrimaryKey
    @Generated
    val id: Int,
    val name: String?,
    val age: Int?,
    val dateCreated: LocalDateTime?,
)
