package search

fun main() {
    println("Enter the number of people:")
    val qtyOfPeople = readLine()!!.toInt()
    val people = arrayOfNulls<String>(qtyOfPeople)

    println("Enter all people:")
    for (i in 0 until qtyOfPeople) {
        people[i] = readLine()!!
    }

    while (true) {
        println()
        println("=== Menu ===")
        println("1. Find a person")
        println("2. Print all people")
        println("0. Exit")
        val menuItemNumber = readLine()!!.toInt()

        when (menuItemNumber) {
            0 -> {
                break
            }
            1 -> {
                println()
                println("Enter a name or email to search all suitable people.")
                val dataToSearch = readLine()!!.toUpperCase()
                var indexes = mutableListOf<Int>()
                for (j in people.indices) {
                    if (people[j]!!.toUpperCase().contains(dataToSearch)) {
                        indexes.add(j)
                    }
                }
                if (!indexes.isEmpty()) {
                    for (k in indexes) {
                        println(people[k])
                    }
                } else {
                    println("No matching people found.")
                }
            }
            2 -> {
                println("=== List of people ===")
                for (line in people) {
                    println(line)
                }
            }
            else -> {
                println()
                println("Incorrect option! Try again.")
            }
        }
    }
    println()
    println("Bye!")
}