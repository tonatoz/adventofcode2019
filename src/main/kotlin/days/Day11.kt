package days

import DayInterface
import IntCodeComputer

object Day11 : DayInterface() {
    override fun dayNumber() = 11

    private val code = data.split(",").map { it.toLong() }

    enum class Directions { Left, Up, Right, Down }

    class Robot(initialPanelColor: Long = 0L) {
        private val computer = IntCodeComputer(code)
        private var coords = Pair(0, 0)
        private var direction = Directions.Up

        val field = mutableMapOf<Pair<Int, Int>, Long>(coords to initialPanelColor)

        private fun getDirection(newDirection: Long) = when (direction) {
            Directions.Up -> if (newDirection == 0L) Directions.Left else Directions.Right
            Directions.Left -> if (newDirection == 0L) Directions.Down else Directions.Up
            Directions.Right -> if (newDirection == 0L) Directions.Up else Directions.Down
            Directions.Down -> if (newDirection == 0L) Directions.Right else Directions.Left
        }

        private fun getCoords() = when (direction) {
            Directions.Left -> Pair(coords.first.dec(), coords.second)
            Directions.Up -> Pair(coords.first, coords.second.inc())
            Directions.Right -> Pair(coords.first.inc(), coords.second)
            Directions.Down -> Pair(coords.first, coords.second.dec())
        }

        private fun nextMove() {
            val color = field.getOrDefault(coords, 0L)
            val (newColor, newDirection) = computer.apply {
                input.add(color)
                run()
            }.output.takeLast(2)

            field[coords] = newColor
            direction = getDirection(newDirection)
            coords = getCoords()
        }

        fun draw() {
            while (computer.status != Status.Stopped) nextMove()
        }
    }

    override fun step1() = Robot().apply { draw() }.field.keys.count().toString()

    override fun step2(): String {
        val field = Robot(1L).apply { draw() }.field
        val maxY = field.keys.map { it.second }.max() ?: 50
        val minY = field.keys.map { it.second }.min() ?: -50
        val maxX = field.keys.map { it.first }.max() ?: 50
        val minxX = field.keys.map { it.first }.min() ?: -50
        (maxY downTo minY).forEach { y ->
            (minxX..maxX).forEach { x ->
                when (field.getOrDefault(Pair(x, y), 0L)) {
                    0L -> print(" ")
                    1L -> print("#")
                }
            }
            println()
        }
        return "^See up^"
    }
}
