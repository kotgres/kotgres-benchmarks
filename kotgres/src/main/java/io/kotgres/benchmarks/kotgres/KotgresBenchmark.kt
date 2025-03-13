package io.kotgres.benchmarks.kotgres

import io.kotgres.benchmarks.common.AbstractBenchmark
import io.kotgres.benchmarks.common.randomNumber
import io.kotgres.benchmarks.common.randomString
import io.kotgres.orm.connection.AbstractKotgresConnectionPool
import io.kotgres.orm.connection.KotgresConnectionPool
import io.kotgres.orm.connection.KotgresConnectionPoolConfig
import io.kotgres.orm.manager.DaoManager
import java.time.LocalDateTime

class KotgresBenchmark(entityCount: Int) : AbstractBenchmark<UserKotgres>(entityCount) {

    override val isKotlinLibrary: Boolean = true
    override val name: String
        get() = "Kotgres"

    private fun createKowConnection(): AbstractKotgresConnectionPool {
        return KotgresConnectionPool.build(
            KotgresConnectionPoolConfig(
                "localhost", "kotgresbench", 54330, "kotgresbench", "kotgresbench123"
            )
        )
    }

    private val conn = createKowConnection()

    private val dao = DaoManager.getPrimaryKeyDao<UserKotgres, Int>(UserKotgres::class, conn)

    override fun insertAndGetSimpleEntity(): UserKotgres {
        val user = UserKotgres(-1, randomString(), randomNumber(), LocalDateTime.now())
        return dao.insert(user)
    }

    override fun insertSimpleEntity() {
        val user = UserKotgres(-1, randomString(), randomNumber(), LocalDateTime.now())
        dao.insertVoid(user)
    }

    override fun getFirstEntities(limit: Int): List<UserKotgres> {
        return dao.runSelect(dao.selectQuery().limit(limit))
    }
}
