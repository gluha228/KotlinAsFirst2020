@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson4.task1

import lesson1.task1.discriminant
import kotlin.math.sqrt

/**
 * Пример
 *
 * Найти все корни уравнения x^2 = y
 */
fun sqRoots(y: Double) =
    when {
        y < 0 -> listOf()
        y == 0.0 -> listOf(0.0)
        else -> {
            val root = sqrt(y)
            // Результат!
            listOf(-root, root)
        }
    }

/**
 * Пример
 *
 * Найти все корни биквадратного уравнения ax^4 + bx^2 + c = 0.
 * Вернуть список корней (пустой, если корней нет)
 */
fun biRoots(a: Double, b: Double, c: Double): List<Double> {
    if (a == 0.0) {
        return if (b == 0.0) listOf()
        else sqRoots(-c / b)
    }
    val d = discriminant(a, b, c)
    if (d < 0.0) return listOf()
    if (d == 0.0) return sqRoots(-b / (2 * a))
    val y1 = (-b + sqrt(d)) / (2 * a)
    val y2 = (-b - sqrt(d)) / (2 * a)
    return sqRoots(y1) + sqRoots(y2)
}

/**
 * Пример
 *
 * Выделить в список отрицательные элементы из заданного списка
 */
fun negativeList(list: List<Int>): List<Int> {
    val result = mutableListOf<Int>()
    for (element in list) {
        if (element < 0) {
            result.add(element)
        }
    }
    return result
}

/**
 * Пример
 *
 * Изменить знак для всех положительных элементов списка
 */
fun invertPositives(list: MutableList<Int>) {
    for (i in 0 until list.size) {
        val element = list[i]
        if (element > 0) {
            list[i] = -element
        }
    }
}

/**
 * Пример
 *
 * Из имеющегося списка целых чисел, сформировать список их квадратов
 */
fun squares(list: List<Int>) = list.map { it * it }

/**
 * Пример
 *
 * Из имеющихся целых чисел, заданного через vararg-параметр, сформировать массив их квадратов
 */
fun squares(vararg array: Int) = squares(array.toList()).toTypedArray()

/**
 * Пример
 *
 * По заданной строке str определить, является ли она палиндромом.
 * В палиндроме первый символ должен быть равен последнему, второй предпоследнему и т.д.
 * Одни и те же буквы в разном регистре следует считать равными с точки зрения данной задачи.
 * Пробелы не следует принимать во внимание при сравнении символов, например, строка
 * "А роза упала на лапу Азора" является палиндромом.
 */
fun isPalindrome(str: String): Boolean {
    val lowerCase = str.toLowerCase().filter { it != ' ' }
    for (i in 0..lowerCase.length / 2) {
        if (lowerCase[i] != lowerCase[lowerCase.length - i - 1]) return false
    }
    return true
}

/**
 * Пример
 *
 * По имеющемуся списку целых чисел, например [3, 6, 5, 4, 9], построить строку с примером их суммирования:
 * 3 + 6 + 5 + 4 + 9 = 27 в данном случае.
 */
fun buildSumExample(list: List<Int>) = list.joinToString(separator = " + ", postfix = " = ${list.sum()}")

/**
 * Простая
 *
 * Найти модуль заданного вектора, представленного в виде списка v,
 * по формуле abs = sqrt(a1^2 + a2^2 + ... + aN^2).
 * Модуль пустого вектора считать равным 0.0.
 */
fun abs(v: List<Double>): Double = sqrt(v.fold(0.0) { squareSum, element ->
    squareSum + (element * element)
})

/**
 * Простая
 *
 * Рассчитать среднее арифметическое элементов списка list. Вернуть 0.0, если список пуст
 */
fun mean(list: List<Double>): Double = when {
    list.isEmpty() -> 0.0
    else -> list.sum() / list.size
}

/**
 * Средняя
 *
 * Центрировать заданный список list, уменьшив каждый элемент на среднее арифметическое всех элементов.
 * Если список пуст, не делать ничего. Вернуть изменённый список.
 *
 * Обратите внимание, что данная функция должна изменять содержание списка list, а не его копии.
 */
fun center(list: MutableList<Double>): MutableList<Double> {
    val mean = mean(list)
    for (i in 0 until list.size) {
        list[i] -= mean
    }
    return list//.map { it - mean }.toMutableList()
}

/**
 * Средняя
 *
 * Найти скалярное произведение двух векторов равной размерности,
 * представленные в виде списков a и b. Скалярное произведение считать по формуле:
 * C = a1b1 + a2b2 + ... + aNbN. Произведение пустых векторов считать равным 0.
 */
fun times(a: List<Int>, b: List<Int>): Int {
    var c = 0
    for (i in 0 until (a.size)) {
        c += a[i] * b[i]
    }
    return c
}

/**
 * Средняя
 *
 * Рассчитать значение многочлена при заданном x:
 * p(x) = p0 + p1*x + p2*x^2 + p3*x^3 + ... + pN*x^N.
 * Коэффициенты многочлена заданы списком p: (p0, p1, p2, p3, ..., pN).
 * Значение пустого многочлена равно 0 при любом x.
 */
fun polynom(p: List<Int>, x: Int): Int {
    var res = 0
    var xPart = 1
    for (i in 0 until (p.size)) {
        res += p[i] * xPart
        xPart *= x
    }
    return res
}

/**
 * Средняя
 *
 * В заданном списке list каждый элемент, кроме первого, заменить
 * суммой данного элемента и всех предыдущих.
 * Например: 1, 2, 3, 4 -> 1, 3, 6, 10.
 * Пустой список не следует изменять. Вернуть изменённый список.
 *
 * Обратите внимание, что данная функция должна изменять содержание списка list, а не его копии.
 */
fun accumulate(list: MutableList<Int>): MutableList<Int> {
    var currentSumBefore = 0
    var currentSumAfter = 0
    for (i in 0 until list.size) {
        currentSumBefore += list[i]
        list[i] += currentSumAfter
        currentSumAfter = currentSumBefore
    }
    return list
}

/**
 * Средняя
 *
 * Разложить заданное натуральное число n > 1 на простые множители.
 * Результат разложения вернуть в виде списка множителей, например 75 -> (3, 5, 5).
 * Множители в списке должны располагаться по возрастанию.
 */
fun factorize(n1: Int): List<Int> {
    var n = n1
    var flag: Int
    val multiplierList: MutableList<Int> = mutableListOf()
    while (n != 1) {
        flag = 0
        for (i in 2..(n / 2)) {
            if ((n % i) == 0) {
                multiplierList.add(i)
                n /= i
                flag = 1
                break
            }
        }
        if (flag == 0) {
            multiplierList.add(n)
            n = 1
        }
    }
    return multiplierList
}

/**
 * Сложная
 *
 * Разложить заданное натуральное число n > 1 на простые множители.
 * Результат разложения вернуть в виде строки, например 75 -> 3*5*5
 * Множители в результирующей строке должны располагаться по возрастанию.
 */
fun factorizeToString(n1: Int): String {
    var n = n1
    var flag: Boolean
    var multiplierStr = ""
    while (n != 1) {
        flag = true
        for (i in 2..(n / 2)) {
            if ((n % i) == 0) {
                multiplierStr += "*$i"
                n /= i
                flag = false
                break
            }
        }
        if (flag) {
            multiplierStr += "*$n"
            n = 1
        }
    }
    return multiplierStr.removeRange(0..0)
}
/**
 * Средняя
 *
 * Перевести заданное целое число n >= 0 в систему счисления с основанием base > 1.
 * Результат перевода вернуть в виде списка цифр в base-ичной системе от старшей к младшей,
 * например: n = 100, base = 4 -> (1, 2, 1, 0) или n = 250, base = 14 -> (1, 3, 12)
 */
fun convert(n: Int, base: Int): List<Int> {
    if (n == 0) return listOf()
    return convert(n / base, base) + listOf(n % base)
}

/**
 * Сложная
 *
 * Перевести заданное целое число n >= 0 в систему счисления с основанием 1 < base < 37.
 * Результат перевода вернуть в виде строки, цифры более 9 представлять латинскими
 * строчными буквами: 10 -> a, 11 -> b, 12 -> c и так далее.
 * Например: n = 100, base = 4 -> 1210, n = 250, base = 14 -> 13c
 *
 * Использовать функции стандартной библиотеки, напрямую и полностью решающие данную задачу
 * (например, n.toString(base) и подобные), запрещается.
 */
fun convertToString(n: Int, base: Int): String {
    if (n == 0) return "0"
    val list = convert(n, base).toMutableList()
    var anotherCountSystem = ""
    val alphabet = "abcdefghijklmnopqrstuvwxyz"
    for (i in 0 until list.size) {
        if (list[i] in 0..9) {
            anotherCountSystem += "${list[i]}"
        } else {
            anotherCountSystem += alphabet[list[i] - 10]
        }
    }
    return anotherCountSystem
}

/**
 * Средняя
 *
 * Перевести число, представленное списком цифр digits от старшей к младшей,
 * из системы счисления с основанием base в десятичную.
 * Например: digits = (1, 3, 12), base = 14 -> 250
 */
fun decimal(digits: List<Int>, base: Int): Int = digits.fold(0) { initial, element ->
    (initial * base + element)
}

/**
 * Сложная
 *
 * Перевести число, представленное цифровой строкой str,
 * из системы счисления с основанием base в десятичную.
 * Цифры более 9 представляются латинскими строчными буквами:
 * 10 -> a, 11 -> b, 12 -> c и так далее.
 * Например: str = "13c", base = 14 -> 250
 *
 * Использовать функции стандартной библиотеки, напрямую и полностью решающие данную задачу
 * (например, str.toInt(base)), запрещается.
 */
fun decimalFromString(str: String, base: Int): Int {
    val alphabet = "0123456789abcdefghijklmnopqrstuvwxyz"
    val numberForm = mutableListOf<Int>()
    for (element in str) {
        numberForm += alphabet.indexOf(element)
    }
    return decimal(numberForm, base)
}

/**
 * Сложная
 *
 * Перевести натуральное число n > 0 в римскую систему.
 * Римские цифры: 1 = I, 4 = IV, 5 = V, 9 = IX, 10 = X, 40 = XL, 50 = L,
 * 90 = XC, 100 = C, 400 = CD, 500 = D, 900 = CM, 1000 = M.
 * Например: 23 = XXIII, 44 = XLIV, 100 = C
 */
fun roman(n1: Int): String {
    var n = n1
    var str = ""
    for (i in 1..(n / 1000)) {
        str += "M"
    }
    n %= 1000
    when (n / 100) {
        1 -> str += "C"
        2 -> str += "CC"
        3 -> str += "CCC"
        4 -> str += "CD"
        5 -> str += "D"
        6 -> str += "DC"
        7 -> str += "DCC"
        8 -> str += "DCCC"
        9 -> str += "CM"
    }
    n %= 100
    when (n / 10) {
        1 -> str += "X"
        2 -> str += "XX"
        3 -> str += "XXX"
        4 -> str += "XL"
        5 -> str += "L"
        6 -> str += "LX"
        7 -> str += "LXX"
        8 -> str += "LXXX"
        9 -> str += "XC"
    }
    n %= 10
    when (n) {
        1 -> str += "I"
        2 -> str += "II"
        3 -> str += "III"
        4 -> str += "IV"
        5 -> str += "V"
        6 -> str += "VI"
        7 -> str += "VII"
        8 -> str += "VIII"
        9 -> str += "IX"
    }
    return str
}

/**
 * Очень сложная
 *
 * Записать заданное натуральное число 1..999999 прописью по-русски.
 * Например, 375 = "триста семьдесят пять",
 * 23964 = "двадцать три тысячи девятьсот шестьдесят четыре"
 */
fun russianOverThousand(n1: Int): String {
    if (n1 == 0) return ""
    val n = n1
    var str = ""
    when (n / 100) {
        1 -> str += "сто "
        2 -> str += "двести "
        3 -> str += "триста "
        4 -> str += "четыреста "
        5 -> str += "пятьсот "
        6 -> str += "шестьсот "
        7 -> str += "семьсот "
        8 -> str += "восемьсот "
        9 -> str += "девятьсот "
    }

    when (n % 100) {
        11 -> return str + "одиннадцать тысяч "
        12 -> return str + "двенадцать тысяч "
        13 -> return str + "тринадцать тысяч "
        14 -> return str + "четырнадцать тысяч "
        15 -> return str + "пятнадцать тысяч "
        16 -> return str + "шестнадцать тысяч "
        17 -> return str + "семнадцать тысяч "
        18 -> return str + "восемнадцать тысяч "
        19 -> return str + "девятнадцать тысяч "
    }
    when ((n / 10) % 10) {
        2 -> str += "двадцать "
        3 -> str += "тридцать "
        4 -> str += "сорок "
        5 -> str += "пятьдесят "
        6 -> str += "шестьдесят "
        7 -> str += "семьдесят "
        8 -> str += "восемьдесят "
        9 -> str += "девяносто "
    }
    when (n % 10) {
        1 -> str += "одна тысяча "
        2 -> str += "две тысячи "
        3 -> str += "три тысячи "
        4 -> str += "четыре тысячи "
        5 -> str += "пять тысяч "
        6 -> str += "шесть тысяч "
        7 -> str += "семь тысяч "
        8 -> str += "восемь тысяч "
        9 -> str += "девять тысяч "
        0 -> str += "тысяч "
    }
    return str
}

fun russianUnderThousand(n1: Int): String {
    if (n1 == 0) return ""
    var n = n1
    var str = ""
    when (n / 100) {
        1 -> str += "сто "
        2 -> str += "двести "
        3 -> str += "триста "
        4 -> str += "четыреста "
        5 -> str += "пятьсот "
        6 -> str += "шестьсот "
        7 -> str += "семьсот "
        8 -> str += "восемьсот "
        9 -> str += "девятьсот "
    }
    n %= 100

    when (n) {
        11 -> return str + "одиннадцать"
        12 -> return str + "двенадцать"
        13 -> return str + "тринадцать"
        14 -> return str + "четырнадцать"
        15 -> return str + "пятнадцать"
        16 -> return str + "шестнадцать"
        17 -> return str + "семнадцать"
        18 -> return str + "восемнадцать"
        19 -> return str + "девятнадцать"
    }
    when (n / 10) {
        2 -> str += "двадцать "
        3 -> str += "тридцать "
        4 -> str += "сорок "
        5 -> str += "пятьдесят "
        6 -> str += "шестьдесят "
        7 -> str += "семьдесят "
        8 -> str += "восемьдесят "
        9 -> str += "девяносто "
    }
    when (n % 10) {
        1 -> str += "один"
        2 -> str += "два"
        3 -> str += "три"
        4 -> str += "четыре"
        5 -> str += "пять"
        6 -> str += "шесть"
        7 -> str += "семь"
        8 -> str += "восемь"
        9 -> str += "девять"
    }
    return str
}

fun russian(n: Int): String = (russianOverThousand(n / 1000) + russianUnderThousand(n % 1000)).trim(' ')