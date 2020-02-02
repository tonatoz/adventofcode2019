package days

import DayInterface

object Day8 : DayInterface() {
    override fun dayNumber() = 8

    private val layers = data.map { it.toString().toInt() }.chunked(25 * 6) { it.chunked(25) }

    private fun numberOf(arr: List<List<Int>>, n: Int) = arr.flatten().filter { v -> v == n }.count()

    override fun step1(): String {
        val biggest = layers.minBy { numberOf(it, 0) }!!
        return (numberOf(biggest, 1) * numberOf(biggest, 2)).toString()
    }

    override fun step2(): String {
        (0 until 6).forEach { x ->
            (0 until 25).forEach loop@{ y ->
                layers.forEach { l ->
                    when (l[x][y]) {
                        0 -> {
                            print(" ")
                            return@loop
                        }
                        1 -> {
                            print("#")
                            return@loop
                        }
                    }
                }
            }
            println()
        }
        return "^See up^"
    }
}