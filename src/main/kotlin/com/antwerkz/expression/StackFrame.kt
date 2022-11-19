package com.antwerkz.expression

import java.util.Stack

data class StackFrame(
    val type: Type,
    var quantifier: String? = null,
    val elements: MutableList<Type> = mutableListOf()
)
