package io.kotgres.benchmarks.ormlite

import com.j256.ormlite.dao.Dao
import com.j256.ormlite.dao.DaoManager
import com.j256.ormlite.jdbc.JdbcPooledConnectionSource
import io.kotgres.benchmarks.common.AbstractBenchmark
import io.kotgres.benchmarks.common.randomNumber
import io.kotgres.benchmarks.common.randomString
import java.util.*

class OrmliteBenchmark(entityCount: Int) : AbstractBenchmark<UserOrmlite>(entityCount) {
    override val isKotlinLibrary: Boolean = false
    override val name: String
        get() = "ORMLite"

    private val dao: Dao<UserOrmlite, Int>

    init {
        val database = JdbcPooledConnectionSource(dbUrl, user, password)
        dao = DaoManager.createDao(database, UserOrmlite::class.java)
    }

    override fun insertAndGetSimpleEntity(): UserOrmlite {
        val user = UserOrmlite(-1, randomString(), randomNumber(), Date())
        dao.create(user)
        return user // TODO does ormlite update in place for all values?
    }

    override fun insertSimpleEntity() {
        val user = UserOrmlite(-1, randomString(), randomNumber(), Date())
        dao.create(user)
    }

    override fun getFirstEntities(limit: Int): List<UserOrmlite> {
        return dao.queryBuilder()
            .limit(limit.toLong())
            .query()
    }
}