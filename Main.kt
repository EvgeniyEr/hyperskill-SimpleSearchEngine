package search

fun main() {
    val words = readLine()!!.split(" ")
    val word = readLine()!!
    var index = -1
    for (i in words.indices) {
        if (words[i] == word) {
            index = i
            break
        }
    }
    if (index >= 0) {
        println(index + 1)
    } else {
        println("Not Found")
    }
}
