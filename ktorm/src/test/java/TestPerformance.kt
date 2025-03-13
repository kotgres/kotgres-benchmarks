//import io.koworm.printTestResults
//import io.koworm.randomNumber
//import io.koworm.randomString
//import org.junit.jupiter.api.AfterAll
//import org.junit.jupiter.api.Assertions.assertEquals
//import org.junit.jupiter.api.BeforeAll
//import org.junit.jupiter.api.Test
//import org.junit.jupiter.api.TestInstance
//import org.ktorm.database.Database
//import org.ktorm.dsl.*
//import org.ktorm.support.postgresql.PostgreSqlDialect
//import java.time.LocalDate
//import kotlin.system.measureTimeMillis
//
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//class TestPerformance {
//
//    lateinit var database: Database
//
//    @BeforeAll
//    fun beforeAll() {
//        val dbUrl = "jdbc:postgresql://localhost:54330/kow"
//        database = Database.connect(dbUrl, user = "kow", password = "kotgresbench123", dialect = PostgreSqlDialect())
//    }
//
//    @AfterAll
//    fun afterAll() {
//        println("Closing connection...")
//
//    }
//
//    val ENTITIES_COUNT = 1
//
//    @Test
//    fun `inserting simple entities with 4 columns sequentially`() {
//        // warm internal caches (i.e. for reflection)
//        insertRandomUserWithId()
//
//        Thread.sleep(1_000)
//
//        val executionTimeMs = measureTimeMillis {
//            for (i in 1..ENTITIES_COUNT) {
//                insertRandomUserWithId()
//            }
//        }
//
//        printTestResults(executionTimeMs, ENTITIES_COUNT)
//    }
//
//    @Test
//    fun `getting simple entities with 4 columns sequentially`() {
//        // warm internal caches (i.e. for reflection)
//        for (row in database.from(UserWithIdSchema).select().limit(1)) {
//            mapRowToInstance(row)
//        }
//
//        Thread.sleep(1_000)
//
//        val executionTimeMs = measureTimeMillis {
//            for (i in 1..ENTITIES_COUNT) {
//                for (row in database.from(UserWithIdSchema).select().limit(1)) {
//                    mapRowToInstance(row)
//                }
//            }
//        }
//
//        printTestResults(executionTimeMs, ENTITIES_COUNT)
//    }
//
//    @Test
//    fun `getting simple entities with 4 columns in one query`() {
//        // warm internal caches (i.e. for reflection)
//        for (row in database.from(UserWithIdSchema).select().limit(1)) {
//            mapRowToInstance(row)
//        }
//
//        Thread.sleep(1_000)
//
//        val executionTimeMs = measureTimeMillis {
//            val users = mutableListOf<UserWithId>()
//            for (row in database.from(UserWithIdSchema).select().limit(ENTITIES_COUNT)) {
//                users.add(mapRowToInstance(row))
//            }
//            assertEquals(ENTITIES_COUNT, users.size)
//        }
//
//        printTestResults(executionTimeMs, ENTITIES_COUNT)
//    }
//
//    private fun mapRowToInstance(row: QueryRowSet) = UserWithId(
//        row[UserWithIdSchema.id]!!,
//        row[UserWithIdSchema.name],
//        row[UserWithIdSchema.age],
//        row[UserWithIdSchema.dateCreated]
//    )
//
//    private fun insertRandomUserWithId() {
//        val result = database.insert(UserWithIdSchema) {
//            set(it.name, randomString())
//            set(it.age, randomNumber())
//            set(it.dateCreated, LocalDate.now())
//        }
//        println(result)
//        // TODO missing get after, like koworm and ormlite do
//    }
//}