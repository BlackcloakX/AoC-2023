import kotlin.math.pow

fun main() {
    fun part1(input: List<String>): Int {
        var sum = 0

        for(s in input){
            val parts = s.split(":")[1].split("|")
            val winNum = HashSet<Int>()
            val myNum = ArrayList<Int>()

            for(n in parts[0].trim().split(" ")){
                if(n.isNotEmpty()) winNum.add(n.trim().toInt())
            }
            for(n in parts[1].trim().split(" ")){
                if(n.isNotEmpty()) myNum.add(n.trim().toInt())
            }

            var pow = -1
            for(num in myNum){
                if(winNum.contains(num)){
                    pow++
                }
            }

            if(pow > -1) sum += 2.0.pow(pow).toInt()
        }

        return sum
    }

    fun part2(inputs: List<String>): Int {
        val input = ArrayList(inputs)
        var sum = 0

        var j = 0
        while(j < input.size){
            val s = input[j]
            val parts = s.split(":")[1].split("|")
            val cardName = s.split(":")[0].split(" ")
            val cardNum = cardName[cardName.size - 1].toInt()

            val winNum = HashSet<Int>()
            val myNum = ArrayList<Int>()

            for(n in parts[0].trim().split(" ")){
                if(n.isNotEmpty()) winNum.add(n.trim().toInt())
            }
            for(n in parts[1].trim().split(" ")){
                if(n.isNotEmpty()) myNum.add(n.trim().toInt())
            }

            var hits = 0
            for(num in myNum){
                if(winNum.contains(num)){
                    hits++
                }
            }
            for(i in 1..hits){
                if(cardNum - 1 + i < inputs.size){
                    input.add(input[cardNum - 1 + i])
                }
            }

            j++
            sum++
        }

        return sum
    }


    val input = part1(readInput("../resources/Day04"))
    val input2 = readInput("../resources/Day04")
    val output = part2(input2)

    println(input)
    println(output)
}
