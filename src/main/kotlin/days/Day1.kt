package days

import DayInterface
import kotlin.math.floor

object Day1 : DayInterface() {
    override fun dayNumber() = 1

    override fun step1(): String {
        return data.split("\n").map { floor(it.toInt() / 3.0).toInt() - 2 }.sum().toString()
    }

    private fun calcFuel(mass: Int): Int {
        val fuel = floor(mass / 3.0).toInt() - 2
        return if (fuel <= 0) 0 else fuel + calcFuel(fuel)
    }

    override fun step2(): String {
        return data.split("\n").map { calcFuel(it.toInt()) }.sum().toString()
    }
}