package days

import DayInterface

object Day4 : DayInterface() {
    override fun dayNumber() = 4

    private fun filterRange(op: (x: Int) -> Boolean): Int {
        val (from, to) = data.split("-").map { it.toInt() }
        return (from..to)
            .map { pwd -> pwd.toString().toList().map { it.toInt() } }
            .filter { numbers ->
                (numbers.count() == 6) &&
                        (numbers.groupingBy { it }.eachCount().filter { op(it.value) }.count() != 0) &&
                        (numbers.sorted() == numbers)
            }.count()
    }

    override fun step1() = filterRange { x -> x >= 2 }.toString()

    override fun step2() = filterRange { x -> x == 2 }.toString()
}