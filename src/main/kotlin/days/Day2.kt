package days

import DayInterface
import IntCodeComputer

object Day2 : DayInterface() {
    override fun dayNumber() = 2

    private val clearData = data.split(",").map { it.toLong() }

    override fun step1(): String {
        val updData = clearData.toMutableList().apply {
            set(1, 12)
            set(2, 2)
        }
        return IntCodeComputer(updData).apply { run() }.memory[0].toString()
    }

    override fun step2(): String {
        (0L..99L).forEach { noun ->
            (0L..99L).forEach { verb ->
                val updData = clearData.toMutableList().apply {
                    set(1, noun)
                    set(2, verb)
                }
                if (IntCodeComputer(updData).apply { run() }.memory[0] == 19690720L) return (100 * noun + verb).toString()
            }
        }
        return "None"
    }
}