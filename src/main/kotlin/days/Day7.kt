package days

import DayInterface
import IntCodeComputer

object Day7 : DayInterface() {
    override fun dayNumber() = 7

    private val code = data.split(",").map { it.toLong() }

    // From https://code.sololearn.com/c24EP02YuQx3/#kt
    private fun <T> permute(list: List<T>): List<List<T>> {
        if (list.size == 1) return listOf(list)
        val perms = mutableListOf<List<T>>()
        val sub = list[0]
        for (perm in permute(list.drop(1)))
            for (i in 0..perm.size) {
                val newPerm = perm.toMutableList()
                newPerm.add(i, sub)
                perms.add(newPerm)
            }
        return perms.distinct()
    }

    private fun simpleChain(order: List<Long>, initOut: Long = 0L): Long {
        var lastOut = initOut
        order.forEach {
            lastOut = IntCodeComputer(code).apply {
                input.addAll(listOf(it, lastOut))
                run()
            }.output.last()
        }
        return lastOut
    }

    private fun cycledChain(order: List<Long>): Long {
        val chain = List(order.size) { IntCodeComputer(code) }
        var lastOut = 0L
        var i = 0
        while (chain.all { it.status == Status.Stopped }.not()) {
            lastOut = chain[i].apply {
                if (status != Status.Paused) input.add(order[i])
                input.add(lastOut)
                run()
            }.output.last()
            i = i.inc().rem(chain.size)
        }
        return lastOut
    }

    override fun step1() = permute((0L..4L).toList()).map { simpleChain(it) }.max().toString()
    override fun step2() = permute((5L..9L).toList()).map { cycledChain(it) }.max().toString()
}