abstract class DayInterface {
    abstract fun step1(): String?
    abstract fun step2(): String?

    abstract fun dayNumber(): Int
    val data = DayInterface::class.java.getResource("day${dayNumber()}.txt").readText()
}