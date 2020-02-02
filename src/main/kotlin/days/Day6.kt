package days

import DayInterface

object Day6 : DayInterface() {
    override fun dayNumber() = 6

    private val orbits =
        data.split("\n").map { line ->
            val (a, b) = line.split(")")
            b to a
        }.toMap()

    private fun findWay(o: String): List<String> {
        var k = orbits[o]
        val way = mutableListOf<String>()
        while (k != null) {
            way.add(k)
            k = orbits[k]
        }
        return way
    }

    override fun step1() = orbits.keys.map { findWay(it) }.map { it.size }.sum().toString()

    override fun step2(): String {
        val you = findWay("YOU")
        val san = findWay("SAN")
        val commonWay = you.intersect(san)
        return (you.subtract(commonWay).count() + san.subtract(commonWay).count()).toString()
    }
}