package com.antwerkz.expression.types

class AnythingButString : Type("anythingButString") {
    override fun copy() = AnythingButString().copy(this)

    override fun evaluate(): String {
        TODO()
        val chars = "" // el.value.split("").map(c => `[^${c}]`).join('')
        return "(?:${chars})"
    }
}
