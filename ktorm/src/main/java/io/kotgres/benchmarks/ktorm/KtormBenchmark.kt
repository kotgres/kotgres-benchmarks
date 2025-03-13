package io.kotgres.benchmarks.ktorm

import io.kotgres.benchmarks.common.AbstractBenchmark
import io.kotgres.benchmarks.common.randomNumber
import io.kotgres.benchmarks.common.randomString
import org.ktorm.database.Database
import org.ktorm.dsl.*
import org.ktorm.support.postgresql.PostgreSqlDialect
import org.ktorm.support.postgresql.insertReturning
import java.time.LocalDate

class KtormBenchmark(entityCount: Int) : AbstractBenchmark<UserKtorm>(entityCount) {
    override val isKotlinLibrary: Boolean = true
    override val name: String
        get() = "Ktorm"

    private val database = Database.connect(dbUrl, user = user, password = password, dialect = PostgreSqlDialect())

    override fun insertAndGetSimpleEntity(): UserKtorm {
        val insertedId: Int? = database.insertReturning(UserSchemaKtorm, UserSchemaKtorm.id) {
            set(it.name, randomString())
            set(it.age, randomNumber())
            set(it.dateCreated, LocalDate.now())
        }

        val result = mutableListOf<UserKtorm>()
        for (row in database.from(UserSchemaKtorm).select().where(UserSchemaKtorm.id eq insertedId!!)) {
            result.add(mapRowToInstance(row))
        }

        return result.first()
    }

    override fun insertSimpleEntity() {
        database.insert(UserSchemaKtorm) {
            set(it.name, randomString())
            set(it.age, randomNumber())
            set(it.dateCreated, LocalDate.now())
        }
    }

    override fun getFirstEntities(limit: Int): List<UserKtorm> {
        val result = mutableListOf<UserKtorm>()
        for (row in database.from(UserSchemaKtorm).select().limit(limit)) {
            result.add(mapRowToInstance(row))
        }
        return result
    }


    private fun mapRowToInstance(row: QueryRowSet): UserKtorm {
        return UserKtorm(
            row[UserSchemaKtorm.id]!!,
            row[UserSchemaKtorm.name],
            row[UserSchemaKtorm.age],
            row[UserSchemaKtorm.dateCreated]
        )
    }
}

