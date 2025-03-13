package io.kotgres.benchmarks.common

import kotlin.system.measureTimeMillis

data class BenchmarkResult(
    val libraryName: String,
    val entityCount: Int,
    val executionTimeMsTotal: Float,
) {
    val executionTimeMsPerEntity: Float = entityCount / executionTimeMsTotal
    val executionTimeSecondsTotal: Float = executionTimeMsTotal / 1_000f

    fun printResult() {
        val executionTimeSeconds = executionTimeMsTotal / 1_000f

        println("RESULTS FOR ${libraryName}:")
        println("-> Entities inserted: $entityCount")
        println("-> Execution time: ${executionTimeSeconds}s")
        println("-> Time per entity: ${executionTimeMsPerEntity}ms")
    }
}

const val DEFAULT_ITERATIONS = 10

abstract class AbstractBenchmark<E>(private val entityCount: Int) {

    protected val dbUrl = "jdbc:postgresql://localhost:54330/kotgresbench"
    protected val user = "kotgresbench"
    protected val password = "kotgresbench123"

    /**
     * ABSTRACT FIELDS
     */
    abstract val isKotlinLibrary: Boolean
    abstract val name: String

    /**
     * ABSTRACT METHODS
     */
    abstract fun insertAndGetSimpleEntity(): E
    abstract fun insertSimpleEntity()
    abstract fun getFirstEntities(limit: Int): List<E>

    private val libraryName: String
        get() = name + " " + getLanguageName()

    fun runInsertSequentiallyWithResult(iterations: Int = DEFAULT_ITERATIONS): BenchmarkResult {
        println("Running benchmark for $name...")
        warmCache()

        val averageExecutionTime = getExecutionTimeAverage(::runInsertSequantiallyWithResultIteration, iterations)

        return BenchmarkResult(libraryName, entityCount, averageExecutionTime)
    }

    private fun runInsertSequantiallyWithResultIteration() {
        for (j in 1..entityCount) {
            insertAndGetSimpleEntity()
        }
    }

    fun runInsertSequentiallyWithoutResult(iterations: Int = DEFAULT_ITERATIONS): BenchmarkResult {
        warmCache()

        val averageExecutionTime = getExecutionTimeAverage(::runInsertSequantiallyWithoutResultIteration, iterations)

        return BenchmarkResult(libraryName, entityCount, averageExecutionTime)
    }

    private fun runInsertSequantiallyWithoutResultIteration() {
        for (i in 1..entityCount) {
            insertSimpleEntity()
        }
    }


    fun runGetSequentially(iterations: Int = DEFAULT_ITERATIONS): BenchmarkResult {
        warmCache()

        val averageExecutionTime = getExecutionTimeAverage(::runGetSequantiallyIteration, iterations)

        return BenchmarkResult(libraryName, entityCount, averageExecutionTime)
    }

    private fun runGetSequantiallyIteration() {
        for (i in 1..entityCount) {
            getFirstEntities(1)
        }
    }

    fun runGetBatch(iterations: Int = DEFAULT_ITERATIONS): BenchmarkResult {
        warmCache()

        val averageExecutionTime = getExecutionTimeAverage(::runGetBactchIteration, iterations)

        return BenchmarkResult(libraryName, entityCount, averageExecutionTime)
    }

    private fun runGetBactchIteration() {
        // increase entity count bs other tests to make it slower
        // if it's too fast the comparisons are not fair
        val boostedEntityCount = entityCount * 100
        val list = getFirstEntities(boostedEntityCount)
        if (list.size != boostedEntityCount) throw Error("getFirstEntities did not return $boostedEntityCount for $name")
    }

    // TODO add update benchmark
    // TODO add parallel tests (multiple threads)

    // warm internal caches (i.e. for reflection)
    private fun warmCache() {
        getFirstEntities(1)
        insertSimpleEntity()
        Thread.sleep(100)
    }

    protected fun getLanguageName(): String {
        return "(${if (isKotlinLibrary) "K" else "J"})"
    }

    private fun getExecutionTimeAverage(function: () -> Unit, iterations: Int): Float {
        var totalExecutionMs = 0L

        for (i in 1..iterations) {
            val executionTimeMs = measureTimeMillis(function)
            totalExecutionMs += executionTimeMs
        }

        return totalExecutionMs / iterations.toFloat()
    }


}