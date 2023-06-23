package com.antwerkz.expression.types

internal class AnyOf : Type("anyOf") {
    companion object {
        private fun fuseElements(elements: List<Type>): Pair<String, List<Type>> {
            val (fusables, rest) = partition(elements)
            val fused =
                fusables.joinToString("") { el ->
                    if (el is CharType || el is AnyOfChars) {
                        el.value.toString()
                    } else if (el !is RangeType) {
                        el.evaluate()
                    } else {
                        val value = el.value as List<*>
                        "${value[0]}-${value[1]}"
                    }
                }
            return fused to rest
        }

        private fun partition(elements: List<Type>): Pair<List<Type>, List<Type>> {
            val fused = mutableListOf<Type>()
            val rest = mutableListOf<Type>()

            elements.forEach {
                if (it.fusible) {
                    fused += it
                } else {
                    rest += it
                }
            }

            return fused to rest
        }
    }
    init {
        containsChildren = true
    }

    override fun copy() = AnyOf().copy(this)

    override fun evaluate(): String {
        val (fused, rest) = fuseElements(value as List<Type>)

        if (rest.isEmpty()) {
            return "[${fused}]"
        }
        val evaluatedRest = rest.map { it.evaluate() }
        val separator = if (evaluatedRest.isNotEmpty() && fused.isNotEmpty()) "|" else ""
        val restJoined = evaluatedRest.joinToString("|")
        val fusedJoined = if (fused.isNotEmpty()) "[${fused}]" else ""
        return "(?:$restJoined${separator}${fusedJoined})"
    }
}
