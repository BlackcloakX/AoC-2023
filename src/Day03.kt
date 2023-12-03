fun main() {
    fun part1(input: List<String>): Int {
        var sum = 0

        val num = StringBuilder()
        for(y in input.indices){
            for(x in input[y].indices){
                val c = input[y][x]
                if(c.isDigit()){
                    num.append(c)
                }
                else if(num.isNotEmpty()){
                    var isValid = false
                    for(n in num.indices){
                        isValid = isValid || hasAdjacentSymbol(input, x - 1 - n, y)
                    }
                    if(isValid){
                        sum += num.toString().toInt()
                    }
                    num.clear()
                }
            }

            if(num.isNotEmpty()){
                var isValid = false
                for(n in num.indices){
                    isValid = isValid || hasAdjacentSymbol(input, input[y].length - 1 - n, y)
                }
                if(isValid){
                    sum += num.toString().toInt()
                }
                num.clear()
            }
        }

        return sum
    }


    fun part2(input: List<String>): Int {
        var sum = 0

        for(y in input.indices){
            for(x in input[y].indices){
                if(input[y][x] == '*'){
                    val list = getAdjacentNumbers(input, x, y)
                    if(list.size == 2){
                        sum += list[0] * list[1]
                    }
                }
            }
        }

        return sum
    }

    val input = part1(readInput("../resources/Day03"))
    val input2 = part2(readInput("../resources/Day03"))
    println(input)
    println(input2)
}

fun hasAdjacentSymbol(input: List<String>, x: Int, y: Int): Boolean {
    if(x - 1 >= 0 && y - 1 >= 0){
        if(input[y-1][x-1] != '.' && !input[y-1][x-1].isDigit()) return true
    }
    if(x - 1 >= 0){
        if(input[y][x-1] != '.' && !input[y][x-1].isDigit()) return true
    }
    if(x - 1 >= 0 && y + 1 < input.size){
        if(input[y+1][x-1] != '.' && !input[y+1][x-1].isDigit()) return true
    }
    if(y - 1 >= 0){
        if(input[y-1][x] != '.' && !input[y-1][x].isDigit()) return true
    }
    if(y + 1 < input.size){
        if(input[y+1][x] != '.' && !input[y+1][x].isDigit()) return true
    }
    if(x + 1 < input[y].length && y - 1 >= 0){
        if(input[y-1][x+1] != '.' && !input[y-1][x+1].isDigit()) return true
    }
    if(x + 1 < input[y].length){
        if(input[y][x+1] != '.' && !input[y][x+1].isDigit()) return true
    }
    if(x + 1 < input[y].length && y + 1 < input.size){
        if(input[y+1][x+1] != '.' && !input[y+1][x+1].isDigit()) return true
    }

    return false
}

fun getAdjacentNumbers(input: List<String>, x: Int, y: Int): List<Int>{
    val list = ArrayList<Int>()

    for(j in y-1..y+1){
        for(i in x-1..x+1){
            if(j < 0 || j >= input.size || i < 0 || i >= input[j].length){
                continue
            }
            val num = constructNumString(input[j], i)
            if(num.isNotEmpty() && !list.contains(num.toInt())){
                list.add(num.toInt())
            }
        }
    }

    return list
}
fun constructNumString(s: String, x: Int): String{
    if(x >= s.length || x < 0) throw IndexOutOfBoundsException()
    if(!s[x].isDigit()) return ""

    val sb = StringBuilder()
    var pos = x
    while(pos >= 0 && s[pos].isDigit()){
        pos--
    }
    pos++

    while(pos < s.length && s[pos].isDigit()){
        sb.append(s[pos])
        pos ++
    }

    return sb.toString()
}
