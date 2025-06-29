package impl

/*
3
FFLF
FFRRFF
FFFBBBRFFFBBB

2
0
9
*/

private data class Position(var x: Int, var y: Int) {
    fun next(command: Command, direction: Direction1) {
        when(command) {
            Command.F -> {
                when(direction) {
                    Direction1.LEFT -> x -= 1
                    Direction1.RIGHT -> x += 1
                    Direction1.UP -> y += 1
                    Direction1.DOWN -> y -= 1
                }

            }
            Command.B -> {
                when(direction) {
                    Direction1.LEFT -> x += 1
                    Direction1.RIGHT -> x -= 1
                    Direction1.UP -> y -= 1
                    Direction1.DOWN -> y += 1
                }
            }
            Command.L,
            Command.R -> {}
        }
    }
}

private enum class Command {
    F, B, L, R;
    companion object {
        fun fromChar(c: Char): Command = when (c) {
            'F' -> F
            'B' -> B
            'L' -> L
            'R' -> R
            else -> throw IllegalArgumentException("Unknown direction: $c")
        }
    }
}

private enum class Direction1 {
    LEFT, RIGHT, UP, DOWN;

    fun next(command: Command) = when (command) {
        Command.F,
        Command.B -> {
            this
        }
        Command.L -> when (this) {
            UP -> LEFT
            LEFT -> DOWN
            DOWN -> RIGHT
            RIGHT -> UP
        }
        Command.R -> when (this) {
            UP -> RIGHT
            RIGHT -> DOWN
            DOWN -> LEFT
            LEFT -> UP
        }
    }
}

fun main() = with(System.`in`.bufferedReader()) {
    val bw = System.out.bufferedWriter()

    val n = readLine().toInt()
    repeat(n) {
        val pos = Position(0, 0)
        val commands = readLine().map { Command.fromChar(it) }
        var curDirection = Direction1.UP

        var (minX, maxX, minY, maxY) = listOf(0, 0, 0, 0)
        commands.forEach { c ->
            curDirection = curDirection.next(c)
            pos.next(c, curDirection)

            minX = minOf(minX, pos.x)
            maxX = maxOf(maxX, pos.x)
            minY = minOf(minY, pos.y)
            maxY = maxOf(maxY, pos.y)
        }

        bw.write("${(maxX - minX) * (maxY - minY)}")
        bw.newLine()
    }

    bw.flush()
}