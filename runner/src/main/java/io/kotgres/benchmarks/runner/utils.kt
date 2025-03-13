package io.kotgres.benchmarks.runner

import kotlin.math.pow
import kotlin.math.roundToInt

fun Float.roundTo(decimals: Int): Double {
    val divider = 10.0.pow(decimals)
    return (this * divider).roundToInt() / divider
}