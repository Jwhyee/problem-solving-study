import java.util.StringTokenizer

private class Room(private val max: Int) {
    var isStarted = false
    val playerList = mutableListOf<Player>()

    fun canAddPlayer(player: Player): Boolean =
        !isStarted &&
        playerList.isNotEmpty() &&
        playerList[0].level in (player.level - 10)..(player.level + 10)

    fun addPlayer(player: Player): Boolean {
        if (playerList.size < max) {
            playerList += player
            isStarted = playerList.size == max
            return true
        }
        return false
    }
}

private data class Player(val nickname: String, val level: Int)

fun main() = with(System.`in`.bufferedReader()) {
    val (p, m) = StringTokenizer(readLine()).run {
        nextToken().toInt() to nextToken().toInt()
    }
    val roomList = mutableListOf<Room>()

    repeat(p) {
        val (level, nickname) = StringTokenizer(readLine()).run {
            nextToken().toInt() to nextToken()
        }
        val player = Player(nickname, level)

        val room = roomList.firstOrNull { it.canAddPlayer(player) }
            ?: Room(m).also { roomList += it }

        room.addPlayer(player)
    }

    roomList.forEach { room ->
        println(if (room.isStarted) "Started!" else "Waiting!")
        room.playerList
            .sortedBy { it.nickname }
            .forEach { println("${it.level} ${it.nickname}") }
    }
}
