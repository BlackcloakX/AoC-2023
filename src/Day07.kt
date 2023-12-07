fun main() {
    fun part1(input: List<String>): Int {
        val list = ArrayList<HandBid>()
        for(line in input){
            val split = line.split(" ")
            list.add(HandBid(Hand.parseString(split[0]), split[1].toInt()))
        }

        val res = list.sortedBy { it.hand }

        var sum = 0
        for(i in res.indices){
            sum += res[i].bid * (i+1)
        }
        return sum
    }

    fun part2(input: List<String>): Int {
        val list = ArrayList<HandBid2>()
        for(line in input){
            val split = line.split(" ")
            list.add(HandBid2(Hand2.parseString(split[0]), split[1].toInt()))
        }

        val res = list.sortedBy { it.hand }

        var sum = 0
        for(i in res.indices){
            sum += res[i].bid * (i+1)
        }
        return sum
    }


    val input = part1(readInput("../resources/Day07"))
    val input2 = part2(readInput("../resources/Day07"))
    println(input)
    println(input2)
}

class HandBid(val hand: Hand, val bid: Int){
    override fun toString(): String {
        return "Hand: $hand, Bid: $bid"
    }
}

class HandBid2(val hand: Hand2, val bid: Int){
    override fun toString(): String {
        return "Hand: $hand, Bid: $bid"
    }
}

enum class Card(val value: Char){
    TWO('2'),
    THREE('3'),
    FOUR('4'),
    FIVE('5'),
    SIX('6'),
    SEVEN('7'),
    EIGHT('8'),
    NINE('9'),
    TEN('T'),
    JACK('J'),
    QUEEN('Q'),
    KING('K'),
    ACE('A');

    companion object{
        fun from(i :Char): Card{
            return entries.first{ it.value == i}
        }
    }
}

enum class Card2(val value: Char){
    JACK('J'),
    TWO('2'),
    THREE('3'),
    FOUR('4'),
    FIVE('5'),
    SIX('6'),
    SEVEN('7'),
    EIGHT('8'),
    NINE('9'),
    TEN('T'),
    QUEEN('Q'),
    KING('K'),
    ACE('A');

    companion object{
        fun from(i :Char): Card2{
            return entries.first{ it.value == i}
        }
    }
}

//enum class Color(val value: Int){
//    DIAMOND(1),
//    HEARTS(2),
//    SPADES(3),
//    CLUBS(4)
//}

enum class Combo{
    HIGH,
    ONE_P,
    TWO_P,
    THREE_K,
    FULL_H,
    FOUR_K,
    FIVE_K
}

//class ColCard(val kind: Color, val value: Cards){}

class Hand(val hand: List<Card>):Comparable<Hand>{
    fun getCombo(): Combo{
        val matches = findMatches(hand)
        if(matches.first == 5){ return Combo.FIVE_K }
        if(matches.first == 4){ return Combo.FOUR_K }
        if(matches.first == 3){
            val tmp = hand.toMutableList()
            tmp.removeAll { e -> e == matches.second }
            return if(findMatches(tmp).first == 2) Combo.FULL_H
            else Combo.THREE_K
        }
        if(findMatches(hand).first == 2){
            val tmp = hand.toMutableList()
            tmp.removeAll { e -> e == matches.second }
            return if(findMatches(tmp).first == 2) Combo.TWO_P
            else Combo.ONE_P
        }
        else return Combo.HIGH
    }

    fun compareToSameCombo(other: Hand): Int{
        for(i in hand.indices){
            if(hand[i] > other.hand[i]) return 1
            if(hand[i] < other.hand[i]) return -1
        }
        throw IllegalArgumentException("All the same")
    }

    override fun compareTo(other: Hand): Int {
        return if(this.getCombo() > other.getCombo()) 1
        else if(this.getCombo() < other.getCombo()) -1
        else compareToSameCombo(other)
    }

    override fun toString(): String {
        return hand.toString()
    }

    companion object{
        fun findMatches(input: List<Card>): Pair<Int, Card>{
            var highest = 0
            var highCount = 1
            var highCard = findHighCard(input)
            for(i in input.indices){
                for(j in input.indices){
                    if(i != j){
                        if(input[i].value == input[j].value) highCount++
                    }
                }
                if(highCount > highest){
                    highest = highCount
                    highCard = input[i]
                }

                highCount = 1
            }

            return Pair(highest, highCard)
        }

        fun findHighCard(input: List<Card>): Card{
            val tmp = input.toMutableList()
            tmp.sortDescending()
            return tmp[0]
        }

        fun parseString(string: String): Hand{
            if(string.length != 5) throw IllegalArgumentException("Wrong length")
            val list = ArrayList<Card>()

            for(c in string){
                list.add(Card.from(c))
            }

            return Hand(list)
        }

        fun of(list: List<Card>): Hand{
            return Hand(list)
        }
    }
}

class Hand2(val hand: List<Card2>):Comparable<Hand2>{
    fun getCombo(): Combo{
        val matches = findMatches(hand)
        if(matches.first == 5){ return Combo.FIVE_K }
        if(matches.first == 4){ return Combo.FOUR_K }
        if(matches.first == 3){
            val tmp = hand.toMutableList()
            tmp.removeAll { e -> e == matches.second }
            tmp.removeAll { e -> e == Card2.JACK }
            return if(findMatches(tmp).first == 2) Combo.FULL_H
            else Combo.THREE_K
        }
        if(findMatches(hand).first == 2){
            val tmp = hand.toMutableList()
            tmp.removeAll { e -> e == matches.second }
            tmp.removeAll { e -> e == Card2.JACK }
            return if(findMatches(tmp).first == 2) Combo.TWO_P
            else Combo.ONE_P
        }
        else return Combo.HIGH
    }

    fun compareToSameCombo(other: Hand2): Int{
        for(i in hand.indices){
            if(hand[i] > other.hand[i]) return 1
            if(hand[i] < other.hand[i]) return -1
        }
        throw IllegalArgumentException("All the same")
    }

    override fun compareTo(other: Hand2): Int {
        return if(this.getCombo() > other.getCombo()) 1
        else if(this.getCombo() < other.getCombo()) -1
        else compareToSameCombo(other)
    }

    override fun toString(): String {
        return hand.toString()
    }

    companion object{
        fun findMatches(input: List<Card2>): Pair<Int, Card2>{
            var highest = 0
            var highCount = 1
            var highCard = findHighCard(input)
            for(i in input.indices){
                for(j in input.indices){
                    if(i != j){
                        if(input[j] == Card2.JACK || input[i].value == input[j].value) highCount++
                    }
                }
                if(highCount > highest){
                    highest = highCount
                    if(input[i] != Card2.JACK) highCard = input[i] // unsure
                }

                highCount = 1
            }

            return Pair(highest, highCard)
        }

        fun findHighCard(input: List<Card2>): Card2{
            val tmp = input.toMutableList()
            tmp.sortDescending()
            return tmp[0]
        }

        fun parseString(string: String): Hand2{
            if(string.length != 5) throw IllegalArgumentException("Wrong length")
            val list = ArrayList<Card2>()

            for(c in string){
                list.add(Card2.from(c))
            }

            return Hand2(list)
        }

        fun of(list: List<Card2>): Hand2{
            return Hand2(list)
        }
    }
}
