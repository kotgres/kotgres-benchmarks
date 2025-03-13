package io.kotgres.benchmarks.hibernate

import io.kotgres.benchmarks.common.AbstractBenchmark
import io.kotgres.benchmarks.common.randomNumber
import io.kotgres.benchmarks.common.randomString
import org.hibernate.SessionFactory
import org.hibernate.boot.MetadataSources
import org.hibernate.boot.registry.StandardServiceRegistryBuilder
import java.time.LocalDateTime


class HibernateBenchmark(entityCount: Int) : AbstractBenchmark<UserHibernate>(entityCount) {
    override val isKotlinLibrary: Boolean
        get() = false
    override val name: String
        get() = "Hibernate"

    private lateinit var sessionFactory: SessionFactory

    init {
        val registry = StandardServiceRegistryBuilder()
            .build()
        try {
            sessionFactory = MetadataSources(registry)
                .addAnnotatedClass(UserHibernate::class.java)
                .buildMetadata()
                .buildSessionFactory()
        } catch (e: Exception) {
            // The registry would be destroyed by the SessionFactory, but we
            // had trouble building the SessionFactory so destroy it manually.
            StandardServiceRegistryBuilder.destroy(registry)
            throw e
        }
    }

    override fun insertAndGetSimpleEntity(): UserHibernate {
        val entity = UserHibernate(-1, randomString(), randomNumber(), LocalDateTime.now())

        val session = sessionFactory.openStatelessSession()
        val id = session.insert(entity)
        val newEntity = session.get(UserHibernate::class.java, id)
        session.close()

        return newEntity
    }

    override fun insertSimpleEntity() {
        val entity = UserHibernate(-1, randomString(), randomNumber(), LocalDateTime.now())

        val session = sessionFactory.openStatelessSession()
        session.insert(entity)
        session.close()
    }

    override fun getFirstEntities(limit: Int): List<UserHibernate> {
        val session = sessionFactory.openStatelessSession()

        val result = session.createSelectionQuery(
            "from UserHibernate",
            UserHibernate::class.java
        )
            .setMaxResults(limit)
            .resultList

        session.close()

        return result
    }
}
