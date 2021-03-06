@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson5.task1

// Урок 5: ассоциативные массивы и множества
// Максимальное количество баллов = 14
// Рекомендуемое количество баллов = 9
// Вместе с предыдущими уроками = 33/47

/**
 * Пример
 *
 * Для заданного списка покупок `shoppingList` посчитать его общую стоимость
 * на основе цен из `costs`. В случае неизвестной цены считать, что товар
 * игнорируется.
 */
fun shoppingListCost(
    shoppingList: List<String>,
    costs: Map<String, Double>
): Double {
    var totalCost = 0.0

    for (item in shoppingList) {
        val itemCost = costs[item]
        if (itemCost != null) {
            totalCost += itemCost
        }
    }

    return totalCost
}

/**
 * Пример
 *
 * Для набора "имя"-"номер телефона" `phoneBook` оставить только такие пары,
 * для которых телефон начинается с заданного кода страны `countryCode`
 */
fun filterByCountryCode(
    phoneBook: MutableMap<String, String>,
    countryCode: String
) {
    val namesToRemove = mutableListOf<String>()

    for ((name, phone) in phoneBook) {
        if (!phone.startsWith(countryCode)) {
            namesToRemove.add(name)
        }
    }

    for (name in namesToRemove) {
        phoneBook.remove(name)
    }
}

/**
 * Пример
 *
 * Для заданного текста `text` убрать заданные слова-паразиты `fillerWords`
 * и вернуть отфильтрованный текст
 */
fun removeFillerWords(
    text: List<String>,
    vararg fillerWords: String
): List<String> {
    val fillerWordSet = setOf(*fillerWords)

    val res = mutableListOf<String>()
    for (word in text) {
        if (word !in fillerWordSet) {
            res += word
        }
    }
    return res
}

/**
 * Пример
 *
 * Для заданного текста `text` построить множество встречающихся в нем слов
 */
fun buildWordSet(text: List<String>): MutableSet<String> {
    val res = mutableSetOf<String>()
    for (word in text) res.add(word)
    return res
}


/**
 * Простая (2 балла)
 *
 * По заданному ассоциативному массиву "студент"-"оценка за экзамен" построить
 * обратный массив "оценка за экзамен"-"список студентов с этой оценкой".
 *
 * Например:
 *   buildGrades(mapOf("Марат" to 3, "Семён" to 5, "Михаил" to 5))
 *     -> mapOf(5 to listOf("Семён", "Михаил"), 3 to listOf("Марат"))
 */
fun buildGrades(grades: Map<String, Int>): Map<Int, List<String>> {
    val reBuild = mutableMapOf<Int, MutableList<String>>(0 to mutableListOf())
    for ((name, grade) in grades) {
        if (reBuild[grade].isNullOrEmpty()) reBuild[grade] = mutableListOf(name) else reBuild[grade]?.add(name)
    }
    return reBuild - 0
}

/**
 * Простая (2 балла)
 *
 * Определить, входит ли ассоциативный массив a в ассоциативный массив b;
 * это выполняется, если все ключи из a содержатся в b с такими же значениями.
 *
 * Например:
 *   containsIn(mapOf("a" to "z"), mapOf("a" to "z", "b" to "sweet")) -> true
 *   containsIn(mapOf("a" to "z"), mapOf("a" to "zee", "b" to "sweet")) -> false
 */
fun containsIn(a: Map<String, String>, b: Map<String, String>): Boolean = (b + a) == b

/**
 * Простая (2 балла)
 *
 * Удалить из изменяемого ассоциативного массива все записи,
 * которые встречаются в заданном ассоциативном массиве.
 * Записи считать одинаковыми, если и ключи, и значения совпадают.
 *
 * ВАЖНО: необходимо изменить переданный в качестве аргумента
 *        изменяемый ассоциативный массив
 *
 * Например:
 *   subtractOf(a = mutableMapOf("a" to "z"), mapOf("a" to "z"))
 *     -> a changes to mutableMapOf() aka becomes empty
 */
fun subtractOf(a: MutableMap<String, String>, b: Map<String, String>) {
    for ((s1, _) in b) {
        if (a[s1] == b[s1]) a -= s1
    }
}

/**
 * Простая (2 балла)
 *
 * Для двух списков людей найти людей, встречающихся в обоих списках.
 * В выходном списке не должно быть повторяюихся элементов,
 * т. е. whoAreInBoth(listOf("Марат", "Семён, "Марат"), listOf("Марат", "Марат")) == listOf("Марат")
 */
fun whoAreInBoth(a: List<String>, b: List<String>): List<String> = a.toSet().intersect(b.toSet()).toList()

/**
 * Средняя (3 балла)
 *
 * Объединить два ассоциативных массива `mapA` и `mapB` с парами
 * "имя"-"номер телефона" в итоговый ассоциативный массив, склеивая
 * значения для повторяющихся ключей через запятую.
 * В случае повторяющихся *ключей* значение из mapA должно быть
 * перед значением из mapB.
 *
 * Повторяющиеся *значения* следует добавлять только один раз.
 *
 * Например:
 *   mergePhoneBooks(
 *     mapOf("Emergency" to "112", "Police" to "02"),
 *     mapOf("Emergency" to "911", "Police" to "02")
 *   ) -> mapOf("Emergency" to "112, 911", "Police" to "02")
 */
fun mergePhoneBooks(mapA: Map<String, String>, mapB: Map<String, String>): Map<String, String> {
    val merged = mapA.toMutableMap()
    for ((s1, s2) in mapB)
        if (s1 !in mapA) merged[s1] = s2
        else if ((!(mapA[s1]?.contains(s2))!!) && (s2 != "")) merged[s1] += ", $s2"
    return merged
}

/**
 * Средняя (4 балла)
 *
 * Для заданного списка пар "акция"-"стоимость" вернуть ассоциативный массив,
 * содержащий для каждой акции ее усредненную стоимость.
 *
 * Например:
 *   averageStockPrice(listOf("MSFT" to 100.0, "MSFT" to 200.0, "NFLX" to 40.0))
 *     -> mapOf("MSFT" to 150.0, "NFLX" to 40.0)
 */
fun averageStockPrice(stockPrices: List<Pair<String, Double>>): Map<String, Double> {
    val averagePrice = mutableMapOf<String, Double>()
    val stockNumber = mutableMapOf<String, Int>()
    for ((company, price) in stockPrices) {
        if (averagePrice[company] == null) {
            averagePrice[company] = price
            stockNumber[company] = 1
        } else {
            averagePrice[company] = averagePrice[company]?.plus(price)!!
            stockNumber[company] = stockNumber[company]?.plus(1)!!
        }
    }
    for ((company, num) in stockNumber) {
        averagePrice[company] = averagePrice[company]?.div(num)!!
    }
    return averagePrice
}

/**
 * Средняя (4 балла)
 *
 * Входными данными является ассоциативный массив
 * "название товара"-"пара (тип товара, цена товара)"
 * и тип интересующего нас товара.
 * Необходимо вернуть название товара заданного типа с минимальной стоимостью
 * или null в случае, если товаров такого типа нет.
 *
 * Например:
 *   findCheapestStuff(
 *     mapOf("Мария" to ("печенье" to 20.0), "Орео" to ("печенье" to 100.0)),
 *     "печенье"
 *   ) -> "Мария"
 */
fun findCheapestStuff(stuff: Map<String, Pair<String, Double>>, kind: String): String? {
    var mostCheapStuff = "Александр Глушков"
    var minPrice = 9999999.0
    for ((name, productKind) in stuff) {
        if ((productKind.first == kind) && (productKind.second < minPrice)) {
            minPrice = productKind.second
            mostCheapStuff = name
        }
    }
    if (mostCheapStuff == "Александр Глушков") return null
    return mostCheapStuff
}

/**
 * Средняя (3 балла)
 *
 * Для заданного набора символов определить, можно ли составить из него
 * указанное слово (регистр символов игнорируется)
 *
 * Например:
 *   canBuildFrom(listOf('a', 'b', 'o'), "baobab") -> true
 */
fun canBuildFrom(chars: List<Char>, word: String): Boolean {
    if ((chars.isEmpty()) && (word != "")) return false
    if (word == "") return true
    for (symbol in chars) {
        if (!word.contains(symbol)) return false
    }
    return true
}


/**
 * Средняя (4 балла)
 *
 * Найти в заданном списке повторяющиеся элементы и вернуть
 * ассоциативный массив с информацией о числе повторений
 * для каждого повторяющегося элемента.
 * Если элемент встречается только один раз, включать его в результат
 * не следует.
 *
 * Например:
 *   extractRepeats(listOf("a", "b", "a")) -> mapOf("a" to 2)
 */
fun extractRepeats(list: List<String>): Map<String, Int> {
    var repeat: Map<Any, List<String>>
    val foundElement = mutableListOf("Александр Глушков")
    val repeats = mutableMapOf<String, Int>()
    for (element in list) if (element !in foundElement) {
        repeat = list.groupBy { it.compareTo(element) }
        foundElement.add(element)
        if (repeat[0]?.size!! > 1) repeats[element] = repeat[0]?.size!!
    }
    return repeats
}

/**
 * Средняя (3 балла)
 *
 * Для заданного списка слов определить, содержит ли он анаграммы.
 * Два слова здесь считаются анаграммами, если они имеют одинаковую длину
 * и одно можно составить из второго перестановкой его букв.
 * Скажем, тор и рот или роза и азор это анаграммы,
 * а поле и полено -- нет.
 *
 * Например:
 *   hasAnagrams(listOf("тор", "свет", "рот")) -> true
 */
fun hasAnagrams(words: List<String>): Boolean {
    val possibleAnagrams = mutableMapOf<Int, Set<Char>>()
    for (element in words) {
        if (possibleAnagrams[element.length] == element.toSet()) return true
        else possibleAnagrams[element.length] = element.toSet()
    }
    return false
}

/**
 * Сложная (5 баллов)
 *
 * Для заданного ассоциативного массива знакомых через одно рукопожатие `friends`
 * необходимо построить его максимальное расширение по рукопожатиям, то есть,
 * для каждого человека найти всех людей, с которыми он знаком через любое
 * количество рукопожатий.
 *
 * Считать, что все имена людей являются уникальными, а также что рукопожатия
 * являются направленными, то есть, если Марат знает Свету, то это не означает,
 * что Света знает Марата.
 *
 * Оставлять пустой список знакомых для людей, которые их не имеют (см. EvilGnome ниже),
 * в том числе для случая, когда данного человека нет в ключах, но он есть в значениях
 * (см. GoodGnome ниже).
 *
 * Например:
 *   propagateHandshakes(
 *     mapOf(
 *       "Marat" to setOf("Mikhail", "Sveta"),
 *       "Sveta" to setOf("Marat"),
 *       "Mikhail" to setOf("Sveta"),
 *       "Friend" to setOf("GoodGnome"),
 *       "EvilGnome" to setOf()
 *     )
 *   ) -> mapOf(
 *          "Marat" to setOf("Mikhail", "Sveta"),
 *          "Sveta" to setOf("Marat", "Mikhail"),
 *          "Mikhail" to setOf("Sveta", "Marat"),
 *          "Friend" to setOf("GoodGnome"),
 *          "EvilGnome" to setOf(),
 *          "GoodGnome" to setOf()
 *        )
 */
fun propagateHandshakes(friends: Map<String, Set<String>>): Map<String, Set<String>> {
    val alreadyCounted = mutableMapOf<String, MutableSet<String>>()

    fun betweenHandshakes(alreadyWas: MutableSet<String>, currentHuman: String): MutableSet<String> {
        if (alreadyCounted[currentHuman] != null) return alreadyCounted[currentHuman]!!
        if (friends[currentHuman].isNullOrEmpty()) {
            alreadyCounted[currentHuman] = mutableSetOf()
            return mutableSetOf()
        }
        val friendsNumber = mutableSetOf<String>()
        for (element in friends[currentHuman] ?: error("")) {
            if (element !in alreadyWas) {
                friendsNumber.add(element)
                friendsNumber += betweenHandshakes((alreadyWas + element) as MutableSet<String>, element)
            }
        }
        return friendsNumber
    }
    for ((element, _) in friends) {
        alreadyCounted[element] = betweenHandshakes(mutableSetOf(element), element)
        alreadyCounted[element]?.remove(element)
    }

    return alreadyCounted
}


/*fun main() {
    println(propagateHandshakes(mapOf("Marat" to setOf("Sveta"), "Sveta" to setOf("Mikhail"))))
    println("***")
    println(mapOf("Marat" to setOf("Mikhail", "Sveta"), "Sveta" to setOf("Mikhail"), "Mikhail" to setOf()))
}*/

/**
 * Сложная (6 баллов)
 *
 * Для заданного списка неотрицательных чисел и числа определить,
 * есть ли в списке пара чисел таких, что их сумма равна заданному числу.
 * Если да, верните их индексы в виде Pair<Int, Int>;
 * если нет, верните пару Pair(-1, -1).
 *
 * Индексы в результате должны следовать в порядке (меньший, больший).
 *
 * Постарайтесь сделать ваше решение как можно более эффективным,
 * используя то, что вы узнали в данном уроке.
 *
 * Например:
 *   findSumOfTwo(listOf(1, 2, 3), 4) -> Pair(0, 2)
 *   findSumOfTwo(listOf(1, 2, 3), 6) -> Pair(-1, -1)
 */
fun findSumOfTwo(list: List<Int>, number: Int): Pair<Int, Int> {
    for (position in 0 until list.size - 1)
        if (list.contains(number - list[position]))
            return Pair(position, (list - position).indexOf(number - list[position]))
    return Pair(-1, -1)
}

fun main() {
    println(findSumOfTwo(listOf(0, 0), 0))
}

/**
 * Очень сложная (8 баллов)
 *
 * Входными данными является ассоциативный массив
 * "название сокровища"-"пара (вес сокровища, цена сокровища)"
 * и вместимость вашего рюкзака.
 * Необходимо вернуть множество сокровищ с максимальной суммарной стоимостью,
 * которые вы можете унести в рюкзаке.
 *
 * Перед решением этой задачи лучше прочитать статью Википедии "Динамическое программирование".
 *
 * Например:
 *   bagPacking(
 *     mapOf("Кубок" to (500 to 2000), "Слиток" to (1000 to 5000)),
 *     850
 *   ) -> setOf("Кубок")
 *   bagPacking(
 *     mapOf("Кубок" to (500 to 2000), "Слиток" to (1000 to 5000)),
 *     450
 *   ) -> emptySet()
 */
fun bagPacking(treasures: Map<String, Pair<Int, Int>>, capacity: Int): Set<String> {
    /*var bestComplect = mutableSetOf<String>()
    var maxPrice = 0
    fun backPacking(ungotTreasures: Map<String, Pair<Int, Int>>, capacity: Int, totalPrice: Int) {
        if (totalPrice > maxPrice) {
            maxPrice = totalPrice
            bestComplect = mutableSetOf()
            for ((element, _) in treasures) if (element !in ungotTreasures) bestComplect.add(element)
        }
        for ((element, info) in ungotTreasures) if (capacity - info.first >= 0) backPacking(
            ungotTreasures - element,
            capacity - info.first,
            totalPrice + info.second
        )
    }
    backPacking(treasures, capacity, 0)
    return bestComplect*/
    val bestComplect = mutableMapOf<Int, List<String>>()
    val bestForLostCapacity = mutableMapOf<Int, Int>()
    var maxprice = 0
    fun backPacking(ungotTresures: List<String>, lostCapacity: Int, totalPrice: Int) {
        if (lostCapacity !in bestForLostCapacity) {
            bestForLostCapacity[lostCapacity] = totalPrice
            bestComplect[totalPrice] = ungotTresures
            if (totalPrice > maxprice) maxprice = totalPrice
        } else if (bestForLostCapacity[lostCapacity]!! >= totalPrice) return
        else {
            bestForLostCapacity[lostCapacity] = totalPrice
            bestComplect[totalPrice] = ungotTresures
            if (totalPrice > maxprice) maxprice = totalPrice
        }
        for (elements in ungotTresures) if ((lostCapacity - (treasures[elements]?.first!!)) >= 0)
            backPacking(
                ungotTresures - elements,
                lostCapacity - (treasures[elements]?.first!!),
                totalPrice + (treasures[elements]?.second!!)
            )
    }
    backPacking(treasures.keys.toList(), capacity, 0)
    return treasures.keys - (bestComplect[maxprice]?.toSet()!!)
}
/*
fun main() {
    println(
        bagPacking(
            mapOf(
                "1" to (33 to 655),
                "2" to (893 to 342),
                "3" to (185 to 297),
                "4" to (592 to 841),
                "5" to (753 to 335),
                "6" to (809 to 528),
                "7" to (566 to 271),
                "8" to (441 to 676),
                "9" to (902 to 692),
                "10" to (682 to 276),
                "11" to (575 to 497),
                "12" to (380 to 268),
                "13" to (252 to 902),
                "14" to (514 to 305),
                "15" to (704 to 88),
                "16" to (331 to 655),
                "17" to (522 to 95),
                "18" to (957 to 837),
                "19" to (847 to 212),
                "20" to (906 to 875),
                "21" to (363 to 976),
                "22" to (502 to 815),
                "23" to (257 to 703),
                "24" to (696 to 44),
                "25" to (925 to 244),
                "26" to (334 to 15),
                "27" to (408 to 246),
                "28" to (809 to 537),
                "29" to (276 to 625),
                "30" to (90 to 283),
                "31" to (783 to 287),
                "32" to (138 to 666),
                "33" to (5 to 154),
                "34" to (6 to 472),
                "35" to (315 to 604),
                "36" to (829 to 690),
                "37" to (787 to 808),
                "38" to (913 to 600),
                "39" to (995 to 565),
                "40" to (12 to 550),
                "41" to (206 to 471),
                "42" to (380 to 461),
                "43" to (754 to 316),
                "44" to (247 to 277),
                "45" to (286 to 420),
                "46" to (562 to 222),
                "47" to (123 to 623)
            ),
            47000
        )
    )
}*/