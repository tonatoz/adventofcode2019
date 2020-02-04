val lastDay = arrayOf(
    days.Day1,
    days.Day2,
    days.Day3,
    days.Day4,
    days.Day5,
    days.Day6,
    days.Day7,
    days.Day8,
    days.Day9,
    days.Day10,
    days.Day11,
    days.Day12
).last()


fun main() {
    listOf(lastDay::step1, lastDay::step2).forEach {
        try {
            println("${it.name} = ${it.invoke()}")
        } catch (e: NotImplementedError) {
            println("${it.name}: ${e.message}")
        }
    }
}