package days

import DayInterface
import IntCodeComputer

object Day11 : DayInterface() {
    override fun dayNumber() = 11

    private val code = data.split(",").map { it.toLong() }

    override fun step1(): String {
        val input = mutableListOf(0L)
//        IntCodeComputer(input, code) { out ->
//            println(out)
//            input.add(0)
//        }.run()
        return "?"
    }

    override fun step2(): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
