package days

import DayInterface
import kotlin.math.abs

object Day3 : DayInterface() {
    override fun dayNumber() = 3

    private fun wireCoords(num: Int): List<Pair<Int, Int>> {
        val coords = mutableListOf<Pair<Int, Int>>()
        var x = 0
        var y = 0
        data.split("\n")[num].split(",").forEach { cmd ->
            val dist = cmd.substring(1).toInt()
            when (cmd[0]) {
                'U' -> repeat(dist) {
                    y += 1
                    coords.add(Pair(x, y))
                }
                'R' -> repeat(dist) {
                    x += 1
                    coords.add(Pair(x, y))
                }
                'D' -> repeat(dist) {
                    y -= 1
                    coords.add(Pair(x, y))
                }
                'L' -> repeat(dist) {
                    x -= 1
                    coords.add(Pair(x, y))
                }
            }
        }
        return coords
    }

    private val wireOne = wireCoords(0)
    private val wireTwo = wireCoords(1)

    override fun step1() = wireOne.intersect(wireTwo).map { abs(it.first) + abs(it.second) }.min().toString() ?: "None"

    override fun step2() =
        ((wireOne.intersect(wireTwo).map { wireOne.indexOf(it) + wireTwo.indexOf(it) }.min() ?: 0) + 2).toString()
}