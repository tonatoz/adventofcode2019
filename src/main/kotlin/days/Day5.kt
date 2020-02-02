package days

import DayInterface
import IntCodeComputer

object Day5 : DayInterface() {
    override fun dayNumber() = 5

    private val clearData = data.split(",").map { it.toLong() }

    private fun calcWithInput(inputVal: Long) = IntCodeComputer(clearData).apply {
        input.add(inputVal)
        run()
    }.output.last().toString()

    override fun step1() = calcWithInput(1L)
    override fun step2() = calcWithInput(5L)
}