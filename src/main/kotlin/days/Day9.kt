package days

import DayInterface
import IntCodeComputer

object Day9 : DayInterface() {
    override fun dayNumber() = 9

    private val code = data.split(",").map { it.toLong() }

    private fun runWithMode(mode: Long) = IntCodeComputer(code).apply {
        input.add(mode)
        run()
    }.output.last().toString()

    override fun step1() = runWithMode(1)

    // USe with -Xss500m JVM option
    override fun step2() = runWithMode(2)
}