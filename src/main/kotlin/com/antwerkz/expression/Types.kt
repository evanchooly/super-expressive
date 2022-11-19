package com.antwerkz.expression

import kotlin.text.Typography.times

@Suppress("unused")
class Types {
    companion object {
        fun root() = RealizedType("root")
        fun noop() = RealizedType("noop")
        fun startOfInput() = RealizedType("startOfInput")
        fun endOfInput() = RealizedType("endOfInput")
        fun anyChar() = RealizedType("anyChar")
        fun whitespaceChar() = RealizedType("whitespaceChar")
        fun nonWhitespaceChar() = RealizedType("nonWhitespaceChar")
        fun digit() = RealizedType("digit")
        fun nonDigit() = RealizedType("nonDigit")
        fun word() = RealizedType("word")
        fun nonWord() = RealizedType("nonWord")
        fun wordBoundary() = RealizedType("wordBoundary")
        fun nonWordBoundary() = RealizedType("nonWordBoundary")
        fun newline() = RealizedType("newline")
        fun carriageReturn() = RealizedType("carriageReturn")
        fun tab() = RealizedType("tab")
        fun nullByte() = RealizedType("nullByte")
        fun anyOfChars(chars: String) = RealizedType("anyOfChars", chars)
        fun anythingButString() = RealizedType("anythingButString")
        fun anythingButChars(chars: String) = RealizedType("anythingButChars", chars)
        fun anythingButRange(start: Char, end: Char) = RealizedType("anythingButRange", listOf(start, end))
        fun char(value: String): RealizedType = RealizedType("char", value)
        fun range(start: Char, end: Char) = RealizedType("range", listOf(start, end))
        fun string(value: String) = RealizedType("string", value) { quantifierRequiresGroup = true }

        fun anyOf() = DeferredType("anyOf") { containsChildren = true }
        fun namedBackreference(backReference: String) = DeferredType("namedBackreference") { name = backReference }

        fun backreference(refIndex: Int) = DeferredType("backreference") { index = refIndex }

        fun capture() = DeferredType("capture") { containsChildren = true }

        fun namedCapture(captureName: String) = DeferredType("namedCapture") {
            name = captureName
            containsChildren = true
        }
        fun group() = DeferredType("group") { containsChildren = true }

        fun assertAhead() = DeferredType("assertAhead") { containsChildren = true }
        fun assertNotAhead() = DeferredType("assertNotAhead") { containsChildren = true }
        fun assertBehind() = DeferredType("assertBehind") { containsChildren = true }
        fun assertNotBehind() = DeferredType("assertNotBehind") { containsChildren = true }

        fun subexpression() = RealizedType("subexpression") {
            containsChildren = true
            quantifierRequiresGroup = true
        }
        fun exactly(count: Int) = DeferredType("exactly") {
            times = listOf(count)
            containsChildren = true
        }

        fun atLeast(count: Int) = DeferredType("atLeast") {
            times = listOf(count)
            containsChildren = true
        }
                       fun between(x: Int, y: Int) = DeferredType("between") {
                           times = listOf(x, y)
                           containsChildren = true
                       }
                       fun betweenLazy(x: Int, y: Int) = DeferredType("betweenLazy") {
                           times = listOf(x, y)
                           containsChildren = true
                       }
                       fun zeroOrMore() = DeferredType("zeroOrMore") { containsChildren = true }
                       fun zeroOrMoreLazy() = DeferredType("zeroOrMoreLazy") { containsChildren = true }
                       fun oneOrMore() = DeferredType("oneOrMore") { containsChildren = true }
                       fun oneOrMoreLazy() = DeferredType("oneOrMoreLazy") { containsChildren = true }
        fun optional() = DeferredType("optional") { containsChildren = true }
    }
}

interface Type {
    val type: String
    var value: Any?
    fun value(elements: MutableList<Type>): Type
}

open class RealizedType(override val type: String, options: RealizedType.() -> Unit = {}): Type {
    var containsChildren: Boolean = false
    var quantifierRequiresGroup: Boolean = false
    override var value: Any? = null

    constructor(type: String, value: Any, options: RealizedType.() -> Unit = {}): this(type, options) {
        this.value = value
    }

    init {
        this.options()
    }

    override fun toString(): String {
        return "Type(name='$type', quantifierRequiresGroup=$quantifierRequiresGroup)"
    }

    override fun value(elements: MutableList<Type>): RealizedType {
        val requiresGroup = quantifierRequiresGroup
        return RealizedType(type, elements) { quantifierRequiresGroup = requiresGroup }
    }
}

class DeferredType(override val type: String, options: DeferredType.() -> Unit = {}): Type {
    var times: List<Int> = emptyList()
    var name: String? = null
    var index: Int = 0

    override var value: Any? = null
    var containsChildren: Boolean = false
    constructor(type: String, value: Any?, options: DeferredType.() -> Unit = {}): this(type, options) {
        this.value = value
    }

    constructor(original: DeferredType): this(original.type, original.value) {
        this.name = original.name
        this.index = original.index
        this.times = original.times
        this.containsChildren = original.containsChildren
    }

    init {
        this.options()
    }

    override fun value(elements: MutableList<Type>): Type {
        return DeferredType(this).also {
            it.value = elements
        }
    }

    override fun toString(): String {
        return "DeferredType(name='$type', containsChildren=$containsChildren)"
    }

}