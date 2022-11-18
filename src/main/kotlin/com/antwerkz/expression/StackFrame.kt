package com.antwerkz.expression

data class StackFrame(
    val type: Type,
    val quantifier: String? = null,
    val elements: MutableList<StackFrame> = mutableListOf()
)
