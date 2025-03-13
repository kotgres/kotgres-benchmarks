package io.kotgres.benchmarks.runner

import io.kotgres.benchmarks.hibernate.HibernateBenchmark
import io.kotgres.benchmarks.jooq.JooqBenchmark
import io.kotgres.benchmarks.common.AbstractBenchmark
import io.kotgres.benchmarks.common.BenchmarkResult
import io.kotgres.benchmarks.ebean.EbeanBenchmark
import io.kotgres.benchmarks.exposed.ExposedBenchmark
import io.kotgres.benchmarks.kotgres.KotgresBenchmark
import io.kotgres.benchmarks.ktorm.KtormBenchmark
import io.kotgres.benchmarks.ormlite.OrmliteBenchmark

enum class Mode {
    ALL,
    JAVA,
    KOTLIN,
}

class BenchmarkRunner(private val entityCount: Int, private val mode: Mode) {

    private val availableBenchmarks = listOf<AbstractBenchmark<*>>(
        // Java
        OrmliteBenchmark(entityCount),
        HibernateBenchmark(entityCount),
        EbeanBenchmark(entityCount),
        JooqBenchmark(entityCount),
        // Kotlin
        ExposedBenchmark(entityCount),
        KtormBenchmark(entityCount),
    ).shuffled()

    // TODO extract these 3 repeated blocks
    fun runAll(printAllResults: Boolean = false) {
        val benchmarksToRun = (when (mode) {
            Mode.ALL -> availableBenchmarks
            Mode.JAVA -> availableBenchmarks.filter { !it.isKotlinLibrary }
            Mode.KOTLIN -> availableBenchmarks.filter { it.isKotlinLibrary }
        } + KotgresBenchmark(entityCount)).shuffled()

        printHeader()

        val runInsertSequentiallyWithReturnResults =
            benchmarksToRun.map(AbstractBenchmark<*>::runInsertSequentiallyWithResult)
        if (printAllResults) {
            printResult(runInsertSequentiallyWithReturnResults, "INSERT SEQUENTIALLY w/ RETURNED ENTITY")
            printSeparator()
        }


        val runInsertSequentiallyWithoutReturnResults =
            benchmarksToRun.map(AbstractBenchmark<*>::runInsertSequentiallyWithoutResult)
        if (printAllResults) {
            printResult(runInsertSequentiallyWithoutReturnResults, "INSERT SEQUENTIALLY w/o RETURNED ENTITY")
            printSeparator()
        }


        val runGetBachResults = benchmarksToRun.map(AbstractBenchmark<*>::runGetBatch)
        if (printAllResults) {
            printResult(runGetBachResults, "GET BATCH")
            printSeparator()
        }


        val runGetSequentiallyResults = benchmarksToRun.map(AbstractBenchmark<*>::runGetSequentially)
        if (printAllResults) {
            printResult(runGetSequentiallyResults, "GET SEQUENTIALLY")
            printSeparator()
        }


        val totalResults =
            runInsertSequentiallyWithReturnResults + runInsertSequentiallyWithoutReturnResults + runGetBachResults + runGetSequentiallyResults
        val totalResultsGrouped = totalResults.groupBy { it.libraryName }
        val totalResultsFinal = totalResultsGrouped.mapValues {
            it.value.reduce { acc, benchmarkResult ->
                BenchmarkResult(
                    acc.libraryName,
                    acc.entityCount,
                    acc.executionTimeMsTotal + benchmarkResult.executionTimeMsTotal
                )
            }
        }.values.toList()
        printResult(totalResultsFinal, "OVERALL RESULTS ACROSS 4 BENCHMARKS")
    }

    private fun printHeader() {
        val separator = "=".repeat(44)
        println(separator)
        println("RUNNING ALL TESTS WITH $entityCount ENTITIES FOR MODE ${mode}:")
        println(separator)
    }

    private fun printSeparator() {
        println("")
    }


    private fun printResult(benchmarkResults: List<BenchmarkResult>, name: String) {
        println("RESULTS FOR TEST $name (fastest to slowest):")
        val maxExecutionTime = benchmarkResults.maxOfOrNull { it.executionTimeMsTotal }!!

        benchmarkResults.sortedBy { it.executionTimeMsTotal }.forEach {
            val speedUpRatio = (maxExecutionTime / it.executionTimeMsTotal)
            println("${it.libraryName}: ${it.executionTimeSecondsTotal.roundTo(2)}s (${speedUpRatio.roundTo(2)}x speed-up)")
        }
    }
}

fun main() {
//    BenchmarkRunner(500, Mode.JAVA).runAll()
//    BenchmarkRunner(100, Mode.KOTLIN).runAll()
    BenchmarkRunner(200, Mode.ALL).runAll()
}