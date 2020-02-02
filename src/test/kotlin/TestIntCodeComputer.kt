import org.junit.Test
import kotlin.math.pow
import kotlin.test.assertEquals

class TestIntCodeComputer {
    @Test
    fun testMode() {
        fun mode(i: Int, offset: Int) = i
            .div(10.0.pow(1.0 + offset))
            .rem(10)
            .toInt()

        assertEquals(0, mode(1002, 1), "Wrong 1 param")
        assertEquals(1, mode(1002, 2), "Wrong 2 param")
        assertEquals(0, mode(1002, 3), "Wrong 3 param")
    }

    @Test
    fun testDay2() {
        assertEquals(
            listOf(2L, 0, 0, 0, 99),
            IntCodeComputer(listOf(1, 0, 0, 0, 99)).apply { run() }.memory.toList()
        )
        assertEquals(
            listOf(2L, 3, 0, 6, 99),
            IntCodeComputer(listOf(2, 3, 0, 3, 99)).apply { run() }.memory.toList()
        )
        assertEquals(
            listOf(2L, 4, 4, 5, 99, 9801),
            IntCodeComputer(listOf(2, 4, 4, 5, 99, 0)).apply { run() }.memory.toList()
        )
        assertEquals(
            listOf(30L, 1, 1, 4, 2, 5, 6, 0, 99),
            IntCodeComputer(listOf(1, 1, 1, 4, 99, 5, 6, 0, 99)).apply { run() }.memory.toList()
        )
    }

    @Test
    fun testDay5() {
        // @formatter:off
        val code = listOf(3L,21,1008,21,8,20,1005,20,22,107,8,21,20,1006,20,31,1106,0,36,98,0,0,1002,21,125,20,4,20,1105,1,46,104,999,1105,1,46,1101,1000,1,20,4,20,1105,1,46,98,99)
        // @formatter:on

        fun runWithInput(inputVal: Long) = IntCodeComputer(code).apply {
            input.add(inputVal)
            run()
        }.output.last()

        assertEquals(999, runWithInput(1L))
        assertEquals(1000, runWithInput(8L))
        assertEquals(1001, runWithInput(100L))
    }

    @Test
    fun testDay9() {
        val quine = listOf(109L, 1, 204, -1, 1001, 100, 1, 100, 1008, 100, 16, 101, 1006, 101, 0, 99)
        assertEquals(
            quine, IntCodeComputer(quine).apply { run() }.output
        )

        assertEquals(
            1219070632396864,
            IntCodeComputer(listOf(1102, 34915192, 34915192, 7, 4, 7, 99, 0)).apply { run() }.output.first()
        )

        assertEquals(
            1125899906842624,
            IntCodeComputer(listOf(104, 1125899906842624, 99)).apply { run() }.output.first()
        )
    }
}