import java.util.*

class Solution {
    fun solution(players: IntArray, m: Int, k: Int): Int {
        var answer: Int = 0
        
        val server: Queue<Int> = LinkedList()
        
        players.forEachIndexed { index, player ->
            if (server.isNotEmpty()) { 
                while(true) {
                    if(server.peek() == index) {
                        server.poll()
                    } else break
                }
            }
            
            val n = server.size
            if (player >= m) {
                val add = scaleUp(n, m, player)
                for (i in 0 until add) {
                    server.add(index + k)
                    answer++    
                }
            }
        }
        
        return answer
    }
    
    private fun needServer(n: Int, m: Int, player: Int) = player !in (n * m) until (n + 1) * m
    
    private fun scaleUp(n: Int, m: Int, player: Int): Int {
        return if (needServer(n, m, player)) {
            (player / m) - n
        } else {
            0
        }
    }
    
    
}