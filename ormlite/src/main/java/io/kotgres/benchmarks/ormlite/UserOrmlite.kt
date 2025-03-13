package io.kotgres.benchmarks.ormlite

import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable
import java.util.*

@DatabaseTable(tableName = "users_with_id")
data class UserOrmlite(
    @DatabaseField(generatedId = true)
    val id: Int,
    @DatabaseField
    val name: String?,
    @DatabaseField
    val age: Int?,
    @DatabaseField(columnName = "date_created")
    val dateCreated: Date?,
) {
    constructor() : this(-1, null, null, null)
}

