@file:Suppress("UNUSED_PARAMETER")

package lesson8.task1

import lesson1.task1.sqr
import java.lang.IllegalArgumentException
import kotlin.math.*

// Урок 8: простые классы
// Максимальное количество баллов = 40 (без очень трудных задач = 11)

/**
 * Точка на плоскости
 */
data class Point(val x: Double, val y: Double) {
    /**
     * Пример
     *
     * Рассчитать (по известной формуле) расстояние между двумя точками
     */
    fun distance(other: Point): Double = sqrt(sqr(x - other.x) + sqr(y - other.y))
}

/**
 * Треугольник, заданный тремя точками (a, b, c, см. constructor ниже).
 * Эти три точки хранятся в множестве points, их порядок не имеет значения.
 */
@Suppress("MemberVisibilityCanBePrivate")
class Triangle private constructor(private val points: Set<Point>) {

    private val pointList = points.toList()

    val a: Point get() = pointList[0]

    val b: Point get() = pointList[1]

    val c: Point get() = pointList[2]

    constructor(a: Point, b: Point, c: Point) : this(linkedSetOf(a, b, c))

    /**
     * Пример: полупериметр
     */
    fun halfPerimeter() = (a.distance(b) + b.distance(c) + c.distance(a)) / 2.0

    /**
     * Пример: площадь
     */
    fun area(): Double {
        val p = halfPerimeter()
        return sqrt(p * (p - a.distance(b)) * (p - b.distance(c)) * (p - c.distance(a)))
    }

    /**
     * Пример: треугольник содержит точку
     */
    fun contains(p: Point): Boolean {
        val abp = Triangle(a, b, p)
        val bcp = Triangle(b, c, p)
        val cap = Triangle(c, a, p)
        return abp.area() + bcp.area() + cap.area() <= area()
    }

    override fun equals(other: Any?) = other is Triangle && points == other.points

    override fun hashCode() = points.hashCode()

    override fun toString() = "Triangle(a = $a, b = $b, c = $c)"
}

/**
 * Окружность с заданным центром и радиусом
 */
data class Circle(val center: Point, val radius: Double) {
    /**
     * Простая (2 балла)
     *
     * Рассчитать расстояние между двумя окружностями.
     * Расстояние между непересекающимися окружностями рассчитывается как
     * расстояние между их центрами минус сумма их радиусов.
     * Расстояние между пересекающимися окружностями считать равным 0.0.
     */
    fun distance(other: Circle): Double = TODO()

    /**
     * Тривиальная (1 балл)
     *
     * Вернуть true, если и только если окружность содержит данную точку НА себе или ВНУТРИ себя
     */
    fun contains(p: Point): Boolean = TODO()
}

/**
 * Отрезок между двумя точками
 */
data class Segment(val begin: Point, val end: Point) {
    override fun equals(other: Any?) =
        other is Segment && (begin == other.begin && end == other.end || end == other.begin && begin == other.end)

    override fun hashCode() =
        begin.hashCode() + end.hashCode()
}

/**
 * Средняя (3 балла)
 *
 * Дано множество точек. Вернуть отрезок, соединяющий две наиболее удалённые из них.
 * Если в множестве менее двух точек, бросить IllegalArgumentException
 */
fun diameter(vararg points: Point): Segment = TODO()

/**
 * Простая (2 балла)
 *
 * Построить окружность по её диаметру, заданному двумя точками
 * Центр её должен находиться посередине между точками, а радиус составлять половину расстояния между ними
 */
fun circleByDiameter(diameter: Segment): Circle = TODO()

/**
 * Прямая, заданная точкой point и углом наклона angle (в радианах) по отношению к оси X.
 * Уравнение прямой: (y - point.y) * cos(angle) = (x - point.x) * sin(angle)
 * или: y * cos(angle) = x * sin(angle) + b, где b = point.y * cos(angle) - point.x * sin(angle).
 * Угол наклона обязан находиться в диапазоне от 0 (включительно) до PI (исключительно).
 */
class Line private constructor(val b: Double, val angle: Double) {
    init {
        require(angle >= 0 && angle < PI) { "Incorrect line angle: $angle" }
    }

    constructor(point: Point, angle: Double) : this(point.y * cos(angle) - point.x * sin(angle), angle)

    /**
     * Средняя (3 балла)
     *
     * Найти точку пересечения с другой линией.
     * Для этого необходимо составить и решить систему из двух уравнений (каждое для своей прямой)
     */
    fun crossPoint(other: Line): Point = TODO()

    override fun equals(other: Any?) = other is Line && angle == other.angle && b == other.b

    override fun hashCode(): Int {
        var result = b.hashCode()
        result = 31 * result + angle.hashCode()
        return result
    }

    override fun toString() = "Line(${cos(angle)} * y = ${sin(angle)} * x + $b)"
}

/**
 * Средняя (3 балла)
 *
 * Построить прямую по отрезку
 */
fun lineBySegment(s: Segment): Line = TODO()

/**
 * Средняя (3 балла)
 *
 * Построить прямую по двум точкам
 */
fun lineByPoints(a: Point, b: Point): Line = TODO()

/**
 * Сложная (5 баллов)
 *
 * Построить серединный перпендикуляр по отрезку или по двум точкам
 */
fun bisectorByPoints(a: Point, b: Point): Line = TODO()

/**
 * Средняя (3 балла)
 *
 * Задан список из n окружностей на плоскости.
 * Найти пару наименее удалённых из них; расстояние между окружностями
 * рассчитывать так, как указано в Circle.distance.
 *
 * При наличии нескольких наименее удалённых пар,
 * вернуть первую из них по порядку в списке circles.
 *
 * Если в списке менее двух окружностей, бросить IllegalArgumentException
 */
fun findNearestCirclePair(vararg circles: Circle): Pair<Circle, Circle> = TODO()

/**
 * Сложная (5 баллов)
 *
 * Дано три различные точки. Построить окружность, проходящую через них
 * (все три точки должны лежать НА, а не ВНУТРИ, окружности).
 * Описание алгоритмов см. в Интернете
 * (построить окружность по трём точкам, или
 * построить окружность, описанную вокруг треугольника - эквивалентная задача).
 */
fun circleByThreePoints(a: Point, b: Point, c: Point): Circle {
    val p1 = a
    var p2 = b
    var p3 = c
    val p: Point
    if (sqr(p2.y - p1.y) > sqr(p3.y - p1.y)) { //защита ОДЗ
        p = p3
        p3 = p2
        p2 = p
    }
    val x =
        (sqr(p2.x) + sqr(p2.y) - sqr(p3.x) - sqr(p3.y) - (p3.y - p2.y) * (sqr(p1.x) + sqr(p1.y) - sqr(p3.x) - sqr(p3.y)) / (p3.y - p1.y)) /
                (-2 * (p3.x - p2.x - (p3.x - p1.x) * (p3.y - p2.y) / (p3.y - p1.y)))
    val y = ((sqr(p1.x) + sqr(p1.y) - sqr(p3.x) - sqr(p3.y)) / (-2) - x * (p3.x - p1.x)) / (p3.y - p1.y)
    //формулы выведены из системы трех уравнений для точек и центра окружности
    return Circle(Point(x, y), dist(Point(x, y), p3))
}

/**
 * Очень сложная (10 баллов)
 *
 * Дано множество точек на плоскости. Найти круг минимального радиуса,
 * содержащий все эти точки. Если множество пустое, бросить IllegalArgumentException.
 * Если множество содержит одну точку, вернуть круг нулевого радиуса с центром в данной точке.
 *
 * Примечание: в зависимости от ситуации, такая окружность может либо проходить через какие-либо
 * три точки данного множества, либо иметь своим диаметром отрезок,
 * соединяющий две самые удалённые точки в данном множестве.
 */
fun dist(p1: Point, p2: Point): Double = sqrt(sqr(p1.x - p2.x) + sqr(p1.y - p2.y))

fun minContainingCircle(vararg points: Point): Circle {
    if (points.isNullOrEmpty()) throw IllegalArgumentException()
    var mostStrPts = Pair(points[0], points[0])
    var maxR = 0.0
    var r: Double
    for (p1 in points)
        for (p2 in points) {
            r = dist(p1, p2)
            if (dist(p1, p2) > maxR) {
                mostStrPts = Pair(p1, p2)
                maxR = r
            }
        }
    val center = Point((mostStrPts.first.x + mostStrPts.second.x) / 2, (mostStrPts.first.y + mostStrPts.second.y) / 2)
    var finalR = maxR / 2
    var mostStrPt3 = mostStrPts.first
    for (p in points) if ((p != mostStrPts.first) && (p != mostStrPts.second)) {
        r = dist(p, center)
        if (r > finalR) {
            mostStrPt3 = p
            finalR = r
        }
    }
    if (finalR == maxR / 2) return Circle(center, finalR)
    return circleByThreePoints(mostStrPts.first, mostStrPts.second, mostStrPt3)
}

fun con(s: String): MutableList<Point> {
    var sh = ""
    for (ch in s)
        if ((ch in '0'..'9') || (ch in "e-.,")) sh += ch
    val shh = sh.split(",")
    val ret = mutableListOf<Point>()
    for (x in shh.indices step 2) ret += Point(shh[x].toDouble(), shh[x + 1].toDouble())
    return ret
}

fun main() {
    /* print(minContainingCircle(con("[\n" +
            "  {\n" +
            "    \"x\": -2.220446049250313e-16,\n" +
            "    \"y\": 2.220446049250313e-16\n" +
            "  },\n" +
            "  {\n" +
            "    \"x\": -632,\n" +
            "    \"y\": 0.7802601395662847\n" +
            "  },\n" +
            "  {\n" +
            "    \"x\": -5e-324,\n" +
            "    \"y\": 0.9546041603582007\n" +
            "  },\n" +
            "  {\n" +
            "    \"x\": -632,\n" +
            "    \"y\": 0.7722503285898465\n" +
            "  },\n" +
            "  {\n" +
            "    \"x\": -2.220446049250313e-16,\n" +
            "    \"y\": 0.9598083430685647\n" +
            "  },\n" +
            "  {\n" +
            "    \"x\": 0.0033576005753688243,\n" +
            "    \"y\": 0.4520361790923416\n" +
            "  },\n" +
            "  {\n" +
            "    \"x\": 0,\n" +
            "    \"y\": -632\n" +
            "  },\n" +
            "  {\n" +
            "    \"x\": 0.8196105819007525,\n" +
            "    \"y\": 0.013202821330625447\n" +
            "  },\n" +
            "  {\n" +
            "    \"x\": -632,\n" +
            "    \"y\": 0.8995499482346984\n" +
            "  },\n" +
            "  {\n" +
            "    \"x\": 0.6698706754503193,\n" +
            "    \"y\": 0.8875565644394442\n" +
            "  },\n" +
            "  {\n" +
            "    \"x\": 0.3620340155852404,\n" +
            "    \"y\": 0.4444920106748661\n" +
            "  },\n" +
            "  {\n" +
            "    \"x\": -632,\n" +
            "    \"y\": 0.5874488698205582\n" +
            "  },\n" +
            "  {\n" +
            "    \"x\": 0.17337994168179083,\n" +
            "    \"y\": -632\n" +
            "  },\n" +
            "  {\n" +
            "    \"x\": -632,\n" +
            "    \"y\": 0.7195823582779275\n" +
            "  },\n" +
            "  {\n" +
            "    \"x\": -632,\n" +
            "    \"y\": 0\n" +
            "  },\n" +
            "  {\n" +
            "    \"x\": -632,\n" +
            "    \"y\": -2.220446049250313e-16\n" +
            "  },\n" +
            "  {\n" +
            "    \"x\": 0.3044424818984959,\n" +
            "    \"y\": 0.9100454047394838\n" +
            "  },\n" +
            "  {\n" +
            "    \"x\": 0.7424358707793838,\n" +
            "    \"y\": -632\n" +
            "  },\n" +
            "  {\n" +
            "    \"x\": 0.46224870503712523,\n" +
            "    \"y\": 0.10919330435861407\n" +
            "  },\n" +
            "  {\n" +
            "    \"x\": -632,\n" +
            "    \"y\": -632\n" +
            "  },\n" +
            "  {\n" +
            "    \"x\": 0.4990363812978523,\n" +
            "    \"y\": 0.11978480871722885\n" +
            "  },\n" +
            "  {\n" +
            "    \"x\": 0.028294930123268003,\n" +
            "    \"y\": -632\n" +
            "  },\n" +
            "  {\n" +
            "    \"x\": 0.22636422788070254,\n" +
            "    \"y\": 0.1509239791458633\n" +
            "  },\n" +
            "  {\n" +
            "    \"x\": 0.4231280655360379,\n" +
            "    \"y\": 0.7200937789967167\n" +
            "  },\n" +
            "  {\n" +
            "    \"x\": 0.9537564959468353,\n" +
            "    \"y\": 0.11633282479489948\n" +
            "  },\n" +
            "  {\n" +
            "    \"x\": 0,\n" +
            "    \"y\": 5e-324\n" +
            "  },\n" +
            "  {\n" +
            "    \"x\": -632,\n" +
            "    \"y\": -632\n" +
            "  },\n" +
            "  {\n" +
            "    \"x\": 0,\n" +
            "    \"y\": -632\n" +
            "  },\n" +
            "  {\n" +
            "    \"x\": 0.5674345873553633,\n" +
            "    \"y\": -2.220446049250313e-16\n" +
            "  },\n" +
            "  {\n" +
            "    \"x\": 0.33272024471601813,\n" +
            "    \"y\": -632\n" +
            "  },\n" +
            "  {\n" +
            "    \"x\": 0.17660975899153475,\n" +
            "    \"y\": -632\n" +
            "  },\n" +
            "  {\n" +
            "    \"x\": -632,\n" +
            "    \"y\": 2.220446049250313e-16\n" +
            "  },\n" +
            "  {\n" +
            "    \"x\": 0.6007276717256033,\n" +
            "    \"y\": -632\n" +
            "  },\n" +
            "  {\n" +
            "    \"x\": -632,\n" +
            "    \"y\": 0.029771790980096746\n" +
            "  },\n" +
            "  {\n" +
            "    \"x\": -632,\n" +
            "    \"y\": -2.220446049250313e-16\n" +
            "  },\n" +
            "  {\n" +
            "    \"x\": 0.8670635216309539,\n" +
            "    \"y\": -2.220446049250313e-16\n" +
            "  }")))*/
}