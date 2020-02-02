package days

import DayInterface
import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.atan2

object Day10 : DayInterface() {
    override fun dayNumber() = 10

    data class Asteroid(val x: Double, val y: Double) {
        fun angle(a: Asteroid) = atan2(a.x - x, a.y - y)
        fun dist(a: Asteroid) = abs(x - a.x) + abs(y - a.y)
    }

    private val asteroids = sequence {
        data.split("\n").forEachIndexed { y, line ->
            line.forEachIndexed { x, a ->
                if (a.toString() != ".") yield(Asteroid(x.toDouble(), y.toDouble()))
            }
        }
    }.toList()

    private fun directCount(a: Asteroid) = asteroids.filter { it != a }.map { a.angle(it) }.groupBy { it }.count()

    override fun step1() = asteroids.map(::directCount).max().toString()

    override fun step2(): String {
        val laser = asteroids.maxBy(::directCount) ?: throw Exception("No laser")
        val targets =
            asteroids.filter { it != laser }
                .groupBy { laser.angle(it) + PI }
                .map { (k, v) -> k to v.sortedBy(laser::dist).toMutableList() }
                .sortedByDescending { it.first }

        var p = Asteroid(0.0, 0.0)
        repeat(200) { i ->
            targets[i % targets.size].second.let { p = it.removeAt(0) }
        }

        return p.let { it.x * 100 + it.y }.toInt().toString()
    }
}