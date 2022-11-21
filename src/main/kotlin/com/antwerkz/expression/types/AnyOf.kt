package com.antwerkz.expression.types

import com.antwerkz.expression.SuperExpressive

class AnyOf : Type("anyOf") {
    init {
        containsChildren = true
    }

    override fun copy() = AnyOf().copy(this)

    override fun evaluate(): String {
        var (fused, rest) = SuperExpressive.fuseElements(value as List<Type>)

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
