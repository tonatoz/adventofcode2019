package days

import DayInterface
import kotlin.math.abs
import kotlin.math.sign

object Day12 : DayInterface() {
    private data class Moon(var pos: List<Int>) {
        var vel = List(pos.size) { 0 }

        fun applyGravity(other: Moon) {
            vel = pos.zip(other.pos) { a, b -> b.compareTo(a).sign }.zip(vel, Int::plus)
        }

        fun updatePos() {
            pos = pos.zip(vel, Int::plus)
        }

        fun getEnergy() = pos.sumBy { abs(it) } * vel.sumBy { abs(it) }

        fun toHistory() = Pair(pos.toList(), vel.toList())
    }

    override fun dayNumber() = 12

    private fun getMoons() = data.split("\n").map { line ->
        Regex("<x=(-?\\d+), y=(-?\\d+), z=(-?\\d+)>").find(line)!!.destructured.toList().map { it.toInt() }
    }.map { Moon(it) }

    private fun nextStep(moons: List<Moon>) {
        moons.forEach { moon -> moons.filter { it != moon }.forEach { moon.applyGravity(it) } }
        moons.forEach { it.updatePos() }
    }

    private fun findAxisPeriod(originalMoons: List<Moon>, axisIndex: Int): Int {
        val moons = originalMoons.map { it.pos.mapIndexed { i, p -> if (i == axisIndex) p else 0 } }.map { Moon(it) }
        val history = hashSetOf<List<Pair<List<Int>, List<Int>>>>()
        var i = 0
        while (history.contains(moons.map { it.toHistory() }).not()) {
            history.add(moons.map { it.toHistory() })
            nextStep(moons)
            i++
        }
        return i
    }

    private fun lcm(x: Long, y: Long): Long {
        var a = x
        var b = y
        while (a != 0L) a = (b % a).also { b = a }
        return x / b * y
    }

    override fun step1(): String? {
        val moons = getMoons()
        repeat(1000) { nextStep(moons) }
        return moons.sumBy { it.getEnergy() }.toString()
    }

    override fun step2(): String? {
        val moons = getMoons()
        return (0..2).map { findAxisPeriod(moons, it) }
            .fold(1L) { acc, i -> lcm(acc, i.toLong()) }
            .toString()
    }
}