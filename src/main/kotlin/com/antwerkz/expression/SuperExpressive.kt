package com.antwerkz.expression

import com.antwerkz.expression.types.AnyOfChars
import com.antwerkz.expression.types.BackReference
import com.antwerkz.expression.types.Capture
import com.antwerkz.expression.types.CharType
import com.antwerkz.expression.types.EndOfInput
import com.antwerkz.expression.types.NamedBackReference
import com.antwerkz.expression.types.NamedCapture
import com.antwerkz.expression.types.RangeType
import com.antwerkz.expression.types.StartOfInput
import com.antwerkz.expression.types.Type
import com.antwerkz.expression.types.Types
import java.util.function.Predicate

class SuperExpressive() {
    private var state = State()

    companion object {
        operator fun invoke(body: SuperExpressive.() -> Unit): SuperExpressive {
            val superExpressive = SuperExpressive()
            superExpressive.body()

            return superExpressive
        }

        internal fun fuseElements(elements: List<Type>): Pair<String, List<Type>> {
            val (fusables, rest) = partition(elements)
            val fused =
                fusables.joinToString("") { el ->
                    if (el is CharType || el is AnyOfChars) {
                        el.value.toString()
                    } else {
                        val value = el.value as List<*>
                        "${value[0]}-${value[1]}"
                    }
                }
            return fused to rest
        }

        private fun partition(elements: List<Type>): Pair<List<Type>, List<Type>> {
            val predicate =
                Predicate<Type> { type ->
                    type is RangeType || type is CharType || type is AnyOfChars
                }
            val fused = mutableListOf<Type>()
            val rest = mutableListOf<Type>()

            elements.forEach {
                if (predicate.test(it)) {
                    fused += it
                } else {
                    rest += it
                }
            }

            return fused to rest
        }
    }

    private constructor(expressive: SuperExpressive) : this() {
        state = State(expressive.state)
    }

    private fun with(updates: SuperExpressive.() -> Unit): SuperExpressive {
        val next = SuperExpressive(this)
        next.updates()
        return next
    }

    private fun applyQuantifier(element: Type): Type {
        val currentFrame = getCurrentFrame()
        val quantifier = currentFrame.quantifier
        if (quantifier != null) {
            quantifier.value = element
            currentFrame.quantifier = null
            return quantifier
        }
        return element
    }

    private fun matchElement(type: Type) = with {
        getCurrentElementArray().push(applyQuantifier(type))
    }

    fun anyChar() = matchElement(Types.anyChar())

    fun anyOf(body: SuperExpressive.() -> SuperExpressive) = frameCreatingElement(Types.anyOf(), body)

    fun assertAhead(body: SuperExpressive.() -> SuperExpressive) = frameCreatingElement(Types.assertAhead(), body)

    fun assertBehind(body: SuperExpressive.() -> SuperExpressive) = frameCreatingElement(Types.assertBehind(), body)

    fun assertNotAhead(body: SuperExpressive.() -> SuperExpressive) = frameCreatingElement(Types.assertNotAhead(), body)

    fun assertNotBehind(body: SuperExpressive.() -> SuperExpressive) = frameCreatingElement(Types.assertNotBehind(), body)

    fun backreference(index: Int) = matchElement(Types.backreference(index))

    fun capture(): SuperExpressive {
        return with {
            val newFrame = StackFrame(Types.capture())
            state.stack.push(newFrame)
            state.totalCaptureGroups++
        }
    }

    fun carriageReturn() = matchElement(Types.carriageReturn())
    fun ignoreCase() = with { state.flags.ignoreCase() }
    fun multiLine() = with { state.flags.multiLine() }
    fun allowComments() = with { state.flags.allowComments() }
    fun canonicalEquivalance() = with { state.flags.canonicalEquivalance() }
    fun dotAll() = with { state.flags.dotAll() }
    fun literal() = with { state.flags.literal() }
    fun unixLines() = with { state.flags.unixLines() }

    fun char(c: Char): SuperExpressive {
        return with {
            val currentFrame = getCurrentFrame()
            currentFrame.elements.push(applyQuantifier(Types.char(c.toString().escapeSpecial())))
        }
    }

    fun digit() = matchElement(Types.digit())

    @Deprecated(message = "making this private")
    fun end(): SuperExpressive {
        return with {
            val oldFrame = state.stack.pop()
            if (state.stack.isEmpty()) {
                throw IllegalStateException("Cannot call end() here")
            }
            val currentFrame = getCurrentFrame()
            val value = oldFrame.type.value(oldFrame.elements)
            currentFrame.elements.push(applyQuantifier(value))
        }
    }

    fun group(body: SuperExpressive.() -> SuperExpressive) = frameCreatingElement(Types.group(), body)

    fun namedBackreference(name: String): SuperExpressive {
        if (!this.state.namedGroups.contains(name)) {
            throw IllegalArgumentException(
                "no capture group called '${name}' exists (create one with .namedCapture())"
            )
        }
        return matchElement(Types.namedBackreference(name))
    }

    fun namedCapture(name: String): SuperExpressive {
        return with {
            trackNamedGroup(name)
            state.stack.push(StackFrame(Types.namedCapture(name)))
            state.totalCaptureGroups++
        }
    }

    private fun trackNamedGroup(name: String) {
        if (this.state.namedGroups.contains(name))
            throw IllegalStateException("cannot use ${name} again for a capture group")
        this.state.namedGroups.push(name)
    }

    fun newline() = matchElement(Types.newline())

    fun nonDigit() = matchElement(Types.nonDigit())

    fun nonWhitespaceChar() = matchElement(Types.nonWhitespaceChar())

    fun nonWord() = matchElement(Types.nonWord())

    fun nonWordBoundary() = matchElement(Types.nonWordBoundary())

    //    fun nullByte() = matchElement(Types.nullByte())

    fun oneOrMore() = quantifierElement(Types.oneOrMore())
    fun oneOrMoreLazy() = quantifierElement(Types.oneOrMoreLazy())

    fun optional() = quantifierElement(Types.optional())

    fun range(start: Char, end: Char): SuperExpressive {
        if (start >= end) {
            throw java.lang.IllegalArgumentException(
                "start ($start) must be smaller than end (${end})"
            )
        }

        return with { getCurrentFrame().elements.push(applyQuantifier(Types.range(start, end))) }
    }

    //    fun singleLine(): SuperExpressive = TODO("Not yet implemented")

    fun startOfInput(): SuperExpressive {
        return with {
            state.hasDefinedStart = true
            getCurrentElementArray().push(Types.startOfInput())
        }
    }

    fun endOfInput(): SuperExpressive {
        return with {
            state.hasDefinedEnd = true
            getCurrentElementArray().push(Types.endOfInput())
        }
    }

    fun string(s: String): SuperExpressive {
        if (s.isEmpty()) {
            throw IllegalArgumentException("s cannot be an empty string")
        }

        return with {
            val elementValue =
                if (s.length > 1) Types.string(s.escapeSpecial()) else Types.char(s.escapeSpecial())
            val currentFrame = getCurrentFrame()
            currentFrame.elements.push(applyQuantifier(elementValue))
        }
    }

    fun tab() = matchElement(Types.tab())

    fun toRegex(): Regex {
        val (pattern, options) = getRegexPatternAndFlags()
        return Regex(pattern, options)
    }

    fun whitespaceChar() = matchElement(Types.whitespaceChar())

    fun word() = matchElement(Types.word())

    fun wordBoundary() = matchElement(Types.wordBoundary())

    private fun getCurrentElementArray(): MutableList<Type> = getCurrentFrame().elements

    private fun getCurrentFrame(): StackFrame = state.stack.last()

    private fun getRegexPatternAndFlags(): Pair<String, Set<RegexOption>> {
        val pattern: String = getCurrentElementArray().joinToString("") { it.evaluate() }

        return pattern.ifBlank { "(?:)" } to this.state.flags.options
    }

    private fun frameCreatingElement(type: Type, body: SuperExpressive.() -> SuperExpressive): SuperExpressive {
        return with {
            state.stack.add(StackFrame(type))
        }
            .body()
            .end()
    }

    private fun String.escapeSpecial(): String {
        val specialChars = "\\.^$|?*+()[]{}-".toCharArray().map { it.toString() }
        var escaped = this
        specialChars.forEach { char -> escaped = escaped.replace(char, "\\${char}") }
        return escaped
    }
    private fun <E> MutableList<E>.push(element: E) {
        add(element)
    }

    private fun <E> MutableList<E>.pop(): E = removeLast()

    fun zeroOrMore() = quantifierElement(Types.zeroOrMore())
    fun zeroOrMoreLazy() = quantifierElement(Types.zeroOrMoreLazy())
    private fun quantifierElement(type: Type) = with { getCurrentFrame().quantifier(type) }

    fun exactly(count: Int) = with { getCurrentFrame().quantifier(Types.exactly(count)) }
    fun atLeast(count: Int) = with { getCurrentFrame().quantifier(Types.atLeast(count)) }

    fun between(x: Int, y: Int): SuperExpressive {
        if (x >= y) {
            throw IllegalArgumentException("x ($x) must be less than y ($y)")
        }

        return with { getCurrentFrame().quantifier(Types.between(x, y)) }
    }
    fun betweenLazy(x: Int, y: Int): SuperExpressive {
        if (x >= y) {
            throw IllegalArgumentException("x ($x) must be less than y ($y)")
        }

        return with { getCurrentFrame().quantifier(Types.betweenLazy(x, y)) }
    }

    fun anyOfChars(chars: String): SuperExpressive {
        return with {
            val elementValue = Types.anyOfChars(chars.escapeSpecial())
            val currentFrame = getCurrentFrame()

            currentFrame.elements.push(applyQuantifier(elementValue))
        }
    }
    fun anythingButChars(chars: String): SuperExpressive {
        return with {
            val elementValue = Types.anythingButChars(chars.escapeSpecial())
            val currentFrame = getCurrentFrame()

            currentFrame.elements.push(applyQuantifier(elementValue))
        }
    }

    fun anythingButRange(start: Char, end: Char): SuperExpressive {
        if (start >= end) {
            throw java.lang.IllegalArgumentException(
                "start ($start) must be smaller than end (${end})"
            )
        }

        return with {
            getCurrentFrame().elements.push(applyQuantifier(Types.anythingButRange(start, end)))
        }
    }

    private fun mergeSubexpression(
        el: Type,
        options: SubexpressionOptions,
        parent: SuperExpressive,
        incrementCaptureGroups: () -> Int
    ): Type {
        val nextEl: Type = el.copy()

        if (nextEl is BackReference) {
            nextEl.index += parent.state.totalCaptureGroups
        }

        if (nextEl is Capture) {
            incrementCaptureGroups()
        }

        if (nextEl is NamedCapture) {
            val groupName =
                if (options.namespace != null) "${options.namespace}${nextEl.name}"
                else nextEl.name!!

            parent.trackNamedGroup(groupName)
            nextEl.name = groupName
        }

        if (nextEl is NamedBackReference) {
            nextEl.name = options.namespace?.let { "${it}${nextEl.name}" } ?: nextEl.name
        }

        if (nextEl.containsChildren) {
            if (nextEl.value !is List<*>) {
                nextEl.value =
                    mergeSubexpression(
                        nextEl.value as Type,
                        options,
                        parent,
                        incrementCaptureGroups
                    )
            } else {
                nextEl.value =
                    (nextEl.value as List<Type>).map { e ->
                        mergeSubexpression(e, options, parent, incrementCaptureGroups)
                    }
            }
        }

        if (nextEl is StartOfInput) {
            if (options.ignoreStartAndEnd) {
                return Types.noop()
            }

            if (parent.state.hasDefinedStart) {
                throw IllegalStateException(
                    "The parent regex already has a defined start of input. You can ignore a subexpressions " +
                        "startOfInput/endOfInput markers with the ignoreStartAndEnd option"
                )
            }

            if (parent.state.hasDefinedEnd) {
                throw IllegalStateException(
                    "The parent regex already has a defined end of input. You can ignore a subexpressions " +
                        "startOfInput/endOfInput markers with the ignoreStartAndEnd option"
                )
            }

            parent.state.hasDefinedStart = true
        }

        if (nextEl is EndOfInput) {
            if (options.ignoreStartAndEnd) {
                return Types.noop()
            }

            if (parent.state.hasDefinedEnd) {
                throw IllegalStateException(
                    "The parent regex already has a defined start of input. " +
                        "You can ignore a subexpressions startOfInput/endOfInput markers with the ignoreStartAndEnd option"
                )
            }

            parent.state.hasDefinedEnd = true
        }

        return nextEl
    }

    fun subexpression(
        expr: SuperExpressive,
        optionsLambda: SubexpressionOptions.() -> Unit = {}
    ): SuperExpressive {
        if (expr.state.stack.size != 1) {
            throw java.lang.IllegalArgumentException(
                "Cannot call subexpression with a not yet fully specified regex object. (Try " +
                    "adding a .end() call to match the \"${getCurrentFrame().type.javaClass.simpleName}\" on the subexpression)"
            )
        }

        val options = SubexpressionOptions(optionsLambda)

        val exprNext = expr.with {}
        val next = with {}
        var additionalCaptureGroups = 0

        val exprFrame = exprNext.getCurrentFrame()
        exprFrame.elements =
            exprFrame.elements
                .map { e -> mergeSubexpression(e, options, next) { additionalCaptureGroups++ } }
                .toMutableList()

        next.state.totalCaptureGroups += additionalCaptureGroups

        if (!options.ignoreFlags) {
            next.state.flags.options += exprNext.state.flags.options
        }

        val currentFrame = next.getCurrentFrame()
        currentFrame.elements.push(next.applyQuantifier(Types.subexpression(exprFrame.elements)))

        return next
    }
}
