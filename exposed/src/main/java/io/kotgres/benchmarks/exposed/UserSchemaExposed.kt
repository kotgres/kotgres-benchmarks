package io.kotgres.benchmarks.exposed

import kotlinx.datetime.LocalDateTime
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.kotlin.datetime.datetime

// mixing DAO and SQL (?) code here, not sure how to get ID after insert without doing that
object UserSchemaExposed : IdTable<Int>(name = "users_with_id") {
    override val id: Column<EntityID<Int>> = integer("id").entityId()
    val name: Column<String> = varchar("name", length = 50)
    val age: Column<Int> = integer("age")
    val dateCreated: Column<LocalDateTime> = datetime("date_created")

//    override val primaryKey = PrimaryKey(id)
}