package io.kotgres.benchmarks.jooq

import io.kotgres.benchmarks.common.AbstractBenchmark
import io.kotgres.benchmarks.common.randomNumber
import io.kotgres.benchmarks.common.randomString
import io.kotgres.benchmarks.jooq.gen.publi.Public
import io.kotgres.benchmarks.jooq.gen.publi.tables.records.UsersWithIdRecord
import org.jooq.*
import org.jooq.impl.DSL
import org.jooq.impl.DefaultConfiguration
import java.sql.Connection
import java.sql.DriverManager
import java.time.LocalDateTime


class JooqBenchmark(entityCount: Int) : AbstractBenchmark<UsersWithIdRecord>(entityCount) {
    override val isKotlinLibrary: Boolean
        get() = false
    override val name: String
        get() = "JOOQ"

    private val conn: Connection = DriverManager.getConnection(dbUrl, user, password)

    private val configuration: Configuration = DefaultConfiguration().set(conn).set(SQLDialect.POSTGRES)
    override fun insertAndGetSimpleEntity(): UsersWithIdRecord {
        val usersWithIdRecord = UsersWithIdRecord(null, randomString(), randomNumber(), LocalDateTime.now())
        usersWithIdRecord.attach(configuration)
        usersWithIdRecord.insert()

        val create: DSLContext = DSL.using(conn, SQLDialect.POSTGRES)

        val record = create.select()
            .from(Public.PUBLIC.USERS_WITH_ID)
            .where(Public.PUBLIC.USERS_WITH_ID.ID.eq(usersWithIdRecord.id))
            .fetchOne()

        return record!!.into(UsersWithIdRecord::class.java)
    }

    override fun insertSimpleEntity() {
        val usersWithIdRecord = UsersWithIdRecord(null, randomString(), randomNumber(), LocalDateTime.now())
        usersWithIdRecord.attach(configuration)
        usersWithIdRecord.insert()
    }

    override fun getFirstEntities(limit: Int): List<UsersWithIdRecord> {
        val create: DSLContext = DSL.using(conn, SQLDialect.POSTGRES)
        val result: Result<Record> = create.select()
            .from(Public.PUBLIC.USERS_WITH_ID)
            .limit(limit)
            .fetch()

        return result.into(UsersWithIdRecord::class.java)

    }
}

