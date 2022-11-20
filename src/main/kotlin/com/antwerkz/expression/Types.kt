package com.antwerkz.expression

import kotlin.text.Typography.times

@Suppress("unused")
class Types {
    companion object {
        fun root() = Type("root")
        fun noop() = Type("noop")
        fun startOfInput() = Type("startOfInput")
        fun endOfInput() = Type("endOfInput")
        fun anyChar() = Type("anyChar")
        fun whitespaceChar() = Type("whitespaceChar")
        fun nonWhitespaceChar() = Type("nonWhitespaceChar")
        fun digit() = Type("digit")
        fun nonDigit() = Type("nonDigit")
        fun word() = Type("word")
        fun nonWord() = Type("nonWord")
        fun wordBoundary() = Type("wordBoundary")
        fun nonWordBoundary() = Type("nonWordBoundary")
        fun newline() = Type("newline")
        fun carriageReturn() = Type("carriageReturn")
        fun tab() = Type("tab")
        fun nullByte() = Type("nullByte")
        fun anyOfChars(chars: String) = Type("anyOfChars", chars)
        fun anythingButString() = Type("anythingButString")
        fun anythingButChars(chars: String) = Type("anythingButChars", chars)
        fun anythingButRange(start: Char, end: Char) = Type("anythingButRange", listOf(start, end))
        fun char(value: String) = Type("char", value)
        fun range(start: Char, end: Char) = Type("range", listOf(start, end))
        fun string(value: String) = Type("string", value) { quantifierRequiresGroup = true }

        fun anyOf() = Type("anyOf") { containsChildren = true }
        fun namedBackreference(backReference: String) =
            Type("namedBackreference") { name = backReference }

        fun backreference(refIndex: Int) = Type("backreference") { index = refIndex }

        fun capture() = Type("capture") { containsChildren = true }

        fun namedCapture(captureName: String) =
            Type("namedCapture") {
                name = captureName
                containsChildren = true
            }
        fun group() = Type("group") { containsChildren = true }

        fun assertAhead() = Type("assertAhead") { containsChildren = true }
        fun assertNotAhead() = Type("assertNotAhead") { containsChildren = true }
        fun assertBehind() = Type("assertBehind") { containsChildren = true }
        fun assertNotBehind() = Type("assertNotBehind") { containsChildren = true }

        fun subexpression(list: List<Type>) = Type("subexpression", list) {
                containsChildren = true
                quantifierRequiresGroup = true
            }
        fun exactly(count: Int) =
            Type("exactly") {
                times = listOf(count)
                containsChildren = true
            }

        fun atLeast(count: Int) =
            Type("atLeast") {
                times = listOf(count)
                containsChildren = true
            }
        fun between(x: Int, y: Int) =
            Type("between") {
                times = listOf(x, y)
                containsChildren = true
            }
        fun betweenLazy(x: Int, y: Int) =
            Type("betweenLazy") {
                times = listOf(x, y)
                containsChildren = true
            }
        fun zeroOrMore() = Type("zeroOrMore") { containsChildren = true }
        fun zeroOrMoreLazy() = Type("zeroOrMoreLazy") { containsChildren = true }
        fun oneOrMore() = Type("oneOrMore") { containsChildren = true }
        fun oneOrMoreLazy() = Type("oneOrMoreLazy") { containsChildren = true }
        fun optional() = Type("optional") { containsChildren = true }
    }
}

open class Type(val type: String, var value: Any? = null, options: Type.() -> Unit = {}) {
    var containsChildren: Boolean = false
    var quantifierRequiresGroup: Boolean = false
    var times: List<Int> = emptyList()
    var name: String? = null
    var index: Int = 0

    constructor(original: Type): this(original.type, original.value) {
        this.name = original.name
        this.index = original.index
        this.times = original.times
        this.containsChildren = original.containsChildren
        this.quantifierRequiresGroup = original.quantifierRequiresGroup
    }

    init {
        this.options()
    }
    fun value(elements: MutableList<Type>): Type {
        return Type(this).also { it.value = elements }
    }

    open fun copy(): Type {
        return Type(this)
    }
}
