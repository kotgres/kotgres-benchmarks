package io.kotgres.benchmarks.ktorm

import org.ktorm.schema.Table
import org.ktorm.schema.date
import org.ktorm.schema.int
import org.ktorm.schema.varchar

object UserSchemaKtorm : Table<Nothing>("users_with_id") {
    val id = int("id").primaryKey()
    val age = int("age")
    val name = varchar("name")
    val dateCreated = date("date_created")
}
