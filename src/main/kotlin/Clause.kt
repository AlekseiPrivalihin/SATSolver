class Clause {
    val positive: Set<Int>
    val negative: Set<Int>

    constructor(rawClause: List<Int>) {
        positive = rawClause.filter { it > 0 }.toSet()
        negative = rawClause.filter { it < 0 }.map { -it }.toSet()
    }

    constructor(positive: Set<Int>, negative: Set<Int>) {
        this.positive = positive
        this.negative = negative
    }

    fun isEmpty(): Boolean {
        return positive.isEmpty() && negative.isEmpty()
    }

    fun copy(): Clause {
        return Clause(positive, negative)
    }
}
