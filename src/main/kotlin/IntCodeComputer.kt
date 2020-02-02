import kotlin.math.pow

enum class State { Running, Stopped, Paused }

class IntCodeComputer(code: List<Long>) {
    class InfinityMemory(private val list: MutableList<Long>) : MutableList<Long> by list {
        override operator fun get(index: Int): Long = list.getOrElse(index) { 0L }

        override operator fun set(index: Int, element: Long): Long {
            if (index >= list.size) list.addAll(Array(index - list.size + 1) { 0L })
            return list.set(index, element)
        }
    }

    private var pointer = 0
    private var relativeBase = 0

    val input = mutableListOf<Long>()
    val output = mutableListOf<Long>()
    val memory = InfinityMemory(code.toMutableList())
    var state = State.Running

    private fun mode(offset: Int) = memory[pointer]
        .div(10.0.pow(1.0 + offset))
        .toInt()
        .rem(10)

    private fun getPosition(offset: Int) = memory[getImmediate(offset).toInt()]
    private fun getImmediate(offset: Int) = memory[pointer + offset]
    private fun getRelative(offset: Int) = memory[relativeBase + getImmediate(offset).toInt()]

    private fun get(offset: Int) = when (mode(offset)) {
        0 -> getPosition(offset)
        1 -> getImmediate(offset)
        2 -> getRelative(offset)
        else -> throw Exception("Wong get mode")
    }

    private fun set(offset: Int, getVal: () -> Long) {
        val realPointer = when (mode(offset)) {
            0 -> getImmediate(offset).toInt()
            1 -> offset
            2 -> relativeBase + getImmediate(offset).toInt()
            else -> throw Exception("Wong set mode")
        }
        memory[realPointer] = getVal()
    }

    fun run(nextPointer: Int = 0) {
        if (state == State.Running) pointer = nextPointer // Avoid replacing pointer form Paused state
        state = State.Running

        return when (val operation = memory[pointer].rem(100).toInt()) {
            1 -> {
                set(3) { get(1) + get(2) }
                run(pointer + 4)
            }
            2 -> {
                set(3) { get(1) * get(2) }
                run(pointer + 4)
            }
            3 -> {
                if (input.isEmpty())
                    state = State.Paused
                else {
                    set(1) { input.removeAt(0) }
                    run(pointer + 2)
                }
            }
            4 -> {
                output.add(get(1))
                run(pointer + 2)
            }
            5 -> {
                run(if (get(1) != 0L) get(2).toInt() else pointer + 3)
            }
            6 -> {
                run(if (get(1) == 0L) get(2).toInt() else pointer + 3)
            }
            7 -> {
                set(3) { if (get(1) < get(2)) 1L else 0L }
                run(pointer + 4)
            }
            8 -> {
                set(3) { if (get(1) == get(2)) 1L else 0L }
                run(pointer + 4)
            }
            9 -> {
                relativeBase += get(1).toInt()
                run(pointer + 2)
            }
            99 -> state = State.Stopped
            else -> throw Exception("Wrong opcode $operation at mem ${memory[pointer]}")
        }
    }
}