fun main() {
    fun part1(input: List<String>): Int {
        val list = input.map { l -> run{
            val tmp = l.split(" ")
            val pair = MyPair(tmp[0], tmp[1].toInt())
            pair
        } }

        var leftmost = 0
        var rightmost = 0
        var botmost= 0
        var topmost= 0

        var x = 0
        var y = 0

        for(i in list.indices){
            var dx = 0
            var dy = 0

            when(list[i].first){
                "U" -> dy = -1
                "D" -> dy = 1
                "L" -> dx = -1
                "R" -> dx = 1
            }

            x += list[i].second * dx
            y += list[i].second * dy

            if(x > rightmost) rightmost = x
            if(x < leftmost) leftmost = x
            if(y > botmost) botmost = y
            if(x < topmost) topmost = x
        }

        val width = rightmost - leftmost + 1
        val height = botmost - topmost + 1

        println("$width $height")

        val array = Array<Array<Char>>(height) { Array<Char>(width) { '.' } }
        x = -leftmost
        y = -topmost

        for(i in list.indices){
            var dx = 0
            var dy = 0

            when(list[i].first){
                "U" -> dy = -1
                "D" -> dy = 1
                "L" -> dx = -1
                "R" -> dx = 1
            }

            //x += list[i].second * dx
            //y += list[i].second * dy
            for(j in 0..<list[i].second){
                x += dx
                y += dy
                array[y][x] = '#'
            }
        }
        var sum = 0
        //array.forEach { e -> e.forEach { print(it) };println("") }
        for(line in array){
            var count = 0
            var last = '.'
            for(c in line){
                if(c == '#' && last != '#') count++
                if(count % 2 == 1 || count % 2 == 0 && c == '#') sum++
                last = c
            }
        }

        return sum
    }

    fun part2(input: List<String>): Int {
        return 0
    }


    val output = part1(readInput("../resources/Day18"))
    val output2 = part2(readInput("../resources/Day18"))
    println(output)
    println(output2)
}
