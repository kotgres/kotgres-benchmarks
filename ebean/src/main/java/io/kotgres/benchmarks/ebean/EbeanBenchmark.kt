package io.kotgres.benchmarks.ebean

import io.ebean.Database
import io.ebean.DatabaseFactory
import io.ebean.config.DatabaseConfig
import io.ebean.datasource.DataSourceConfig
import io.kotgres.benchmarks.common.AbstractBenchmark
import io.kotgres.benchmarks.common.randomNumber
import io.kotgres.benchmarks.common.randomString
import io.kotgres.benchmarks.ebean.query.QUserEbean
import java.time.LocalDateTime

class EbeanBenchmark(entityCount: Int) : AbstractBenchmark<UserEbean>(entityCount) {
    override val isKotlinLibrary: Boolean
        get() = false
    override val name: String
        get() = "Ebean"

    private val db: Database

    init {
        // datasource
        val dataSourceConfig = DataSourceConfig()
        dataSourceConfig.setUsername(user)
        dataSourceConfig.setPassword(password)
        dataSourceConfig.setUrl(dbUrl)


        val config = DatabaseConfig();
        config.setDataSourceConfig(dataSourceConfig)

        db = DatabaseFactory.create(config)

    }

    override fun insertAndGetSimpleEntity(): UserEbean {
        val user = UserEbean(null, randomString(), randomNumber(), LocalDateTime.now())
        db.insert(user)

        return QUserEbean().where().id.eq(user.id).findOne()!!
    }

    override fun insertSimpleEntity() {
        val user = UserEbean(null, randomString(), randomNumber(), LocalDateTime.now())
        db.insert(user)
    }

    override fun getFirstEntities(limit: Int): List<UserEbean> {
        return QUserEbean().setMaxRows(limit).findList()
    }
}