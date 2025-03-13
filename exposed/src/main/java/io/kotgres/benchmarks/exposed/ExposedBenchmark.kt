package io.kotgres.benchmarks.exposed

import io.kotgres.benchmarks.common.AbstractBenchmark
import io.kotgres.benchmarks.common.randomNumber
import io.kotgres.benchmarks.common.randomString
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

// TODO can we do  this w/o transactions? May be quicker
class ExposedBenchmark(entityCount: Int) : AbstractBenchmark<UserExposed>(entityCount) {
    override val isKotlinLibrary: Boolean = true

    override val name: String
        get() = "Exposed"

    init {
        Database.connect(dbUrl, driver = "org.postgresql.Driver", user = user, password = password)
    }

    override fun insertAndGetSimpleEntity(): UserExposed {
        return transaction {
            val result = UserSchemaExposed.insert {
                it[age] = randomNumber()
                it[name] = randomString()
                it[dateCreated] = Clock.System.now().toLocalDateTime(TimeZone.UTC)
            }

            val resultRow = result.resultedValues!!.first()

            return@transaction userWithIdFromResultRow(resultRow)
        }
    }

    override fun insertSimpleEntity() {
        transaction {
            UserSchemaExposed.insert {
                it[age] = randomNumber()
                it[name] = randomString()
                it[dateCreated] = Clock.System.now().toLocalDateTime(TimeZone.UTC)
            }
        }
    }

    private fun userWithIdFromResultRow(resultRow: ResultRow) = UserExposed(
        resultRow[UserSchemaExposed.id].value,
        resultRow[UserSchemaExposed.name],
        resultRow[UserSchemaExposed.age],
        resultRow[UserSchemaExposed.dateCreated]
    )

    override fun getFirstEntities(limit: Int): List<UserExposed> {
        val users = mutableListOf<UserExposed>()

        transaction {
            for (userRow in UserSchemaExposed.selectAll().limit(limit)) {
                users.add(userWithIdFromResultRow(userRow))
            }
        }

        return users
    }
}