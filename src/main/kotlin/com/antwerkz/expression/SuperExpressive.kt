package com.antwerkz.expression

import com.antwerkz.expression.types.BackReference
import com.antwerkz.expression.types.Capture
import com.antwerkz.expression.types.EndOfInput
import com.antwerkz.expression.types.NamedBackReference
import com.antwerkz.expression.types.NamedCapture
import com.antwerkz.expression.types.StartOfInput
import com.antwerkz.expression.types.Type
import com.antwerkz.expression.types.Types
import java.util.regex.Pattern

internal class SuperExpressive : RegularExpression {
    private var state = State()

    internal constructor()

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

    override fun anyChar() = matchElement(Types.anyChar())

    override fun anyOf(body: RegularExpression.() -> RegularExpression) =
        frameCreatingElement(Types.anyOf(), body)

    override fun assertAhead(body: RegularExpression.() -> RegularExpression) =
        frameCreatingElement(Types.assertAhead(), body)

    override fun assertBehind(body: RegularExpression.() -> RegularExpression) =
        frameCreatingElement(Types.assertBehind(), body)

    override fun assertNotAhead(body: RegularExpression.() -> RegularExpression) =
        frameCreatingElement(Types.assertNotAhead(), body)

    override fun assertNotBehind(body: RegularExpression.() -> RegularExpression) =
        frameCreatingElement(Types.assertNotBehind(), body)

    override fun backreference(index: Int) = matchElement(Types.backreference(index))

    override fun capture(body: RegularExpression.() -> RegularExpression): SuperExpressive {
        return (with {
                    val newFrame = StackFrame(Types.capture())
                    state.stack.push(newFrame)
                    state.totalCaptureGroups++
                }
                .body() as SuperExpressive)
            .end()
    }

    override fun carriageReturn() = matchElement(Types.carriageReturn())
    override fun ignoreCase() = with { state.flags.ignoreCase() }
    override fun multiLine() = with { state.flags.multiLine() }
    override fun allowComments() = with { state.flags.allowComments() }
    override fun canonicalEquivalence() = with { state.flags.canonicalEquivalance() }
    override fun dotAll() = with { state.flags.dotAll() }
    override fun literal() = with { state.flags.literal() }
    override fun unixLines() = with { state.flags.unixLines() }

    override fun char(c: Char): SuperExpressive {
        return with {
            val currentFrame = getCurrentFrame()
            currentFrame.elements.push(applyQuantifier(Types.char(c.toString().escapeSpecial())))
        }
    }

    override fun digit() = matchElement(Types.digit())

    private fun end(): SuperExpressive {
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

    override fun group(body: RegularExpression.() -> RegularExpression) =
        frameCreatingElement(Types.group(), body)

    override fun namedBackreference(name: String): SuperExpressive {
        if (!this.state.namedGroups.contains(name)) {
            throw IllegalArgumentException(
                "no capture group called '${name}' exists (create one with .namedCapture())"
            )
        }
        return matchElement(Types.namedBackreference(name))
    }

    override fun namedCapture(
        name: String,
        body: RegularExpression.() -> RegularExpression
    ): SuperExpressive {
        return (with {
                    trackNamedGroup(name)
                    state.stack.push(StackFrame(Types.namedCapture(name)))
                    state.totalCaptureGroups++
                }
                .body() as SuperExpressive)
            .end()
    }

    private fun trackNamedGroup(name: String) {
        if (this.state.namedGroups.contains(name))
            throw IllegalStateException("cannot use ${name} again for a capture group")
        this.state.namedGroups.push(name)
    }

    override fun newline() = matchElement(Types.newline())

    override fun nonDigit() = matchElement(Types.nonDigit())

    override fun nonWhitespaceChar() = matchElement(Types.nonWhitespaceChar())

    override fun nonWord() = matchElement(Types.nonWord())

    override fun nonWordBoundary() = matchElement(Types.nonWordBoundary())

    //    fun nullByte() = matchElement(Types.nullByte())

    override fun oneOrMore() = quantifierElement(Types.oneOrMore())
    override fun oneOrMoreLazy() = quantifierElement(Types.oneOrMoreLazy())

    override fun optional() = quantifierElement(Types.optional())

    override fun range(start: Char, end: Char): SuperExpressive {
        if (start >= end) {
            throw java.lang.IllegalArgumentException(
                "start ($start) must be smaller than end (${end})"
            )
        }

        return with { getCurrentFrame().elements.push(applyQuantifier(Types.range(start, end))) }
    }

    //    fun singleLine(): SuperExpressive = TODO("Not yet implemented")

    override fun startOfInput(): SuperExpressive {
        return with {
            state.hasDefinedStart = true
            getCurrentElementArray().push(Types.startOfInput())
        }
    }

    override fun endOfInput(): SuperExpressive {
        return with {
            state.hasDefinedEnd = true
            getCurrentElementArray().push(Types.endOfInput())
        }
    }

    override fun string(s: String): SuperExpressive {
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

    override fun tab() = matchElement(Types.tab())

    internal fun toRegex(): Regex {
        val (pattern, options) = getRegexPatternAndFlags()
        return Regex(pattern, options)
    }

    internal fun toPattern(): Pattern {
        val (pattern, options) = getRegexPatternAndFlags()
        return Pattern.compile(pattern, options.fold(1) { acc, option -> acc or option.value })
    }

    override fun whitespaceChar() = matchElement(Types.whitespaceChar())

    override fun word() = matchElement(Types.word())

    override fun wordBoundary() = matchElement(Types.wordBoundary())

    private fun getCurrentElementArray(): MutableList<Type> = getCurrentFrame().elements

    private fun getCurrentFrame(): StackFrame = state.stack.last()

    private fun getRegexPatternAndFlags(): Pair<String, Set<RegexOption>> {
        val pattern: String = getCurrentElementArray().joinToString("") { it.evaluate() }

        return pattern.ifBlank { "(?:)" } to this.state.flags.options
    }

    private fun frameCreatingElement(
        type: Type,
        body: RegularExpression.() -> RegularExpression
    ): SuperExpressive {
        return (with { state.stack.add(StackFrame(type)) }.body() as SuperExpressive).end()
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

    override fun zeroOrMore() = quantifierElement(Types.zeroOrMore())
    override fun zeroOrMoreLazy() = quantifierElement(Types.zeroOrMoreLazy())
    private fun quantifierElement(type: Type) = with { getCurrentFrame().quantifier(type) }

    override fun exactly(count: Int) = with { getCurrentFrame().quantifier(Types.exactly(count)) }
    override fun atLeast(count: Int) = with { getCurrentFrame().quantifier(Types.atLeast(count)) }

    override fun between(x: Int, y: Int): SuperExpressive {
        if (x >= y) {
            throw IllegalArgumentException("x ($x) must be less than y ($y)")
        }

        return with { getCurrentFrame().quantifier(Types.between(x, y)) }
    }
    override fun betweenLazy(x: Int, y: Int): SuperExpressive {
        if (x >= y) {
            throw IllegalArgumentException("x ($x) must be less than y ($y)")
        }

        return with { getCurrentFrame().quantifier(Types.betweenLazy(x, y)) }
    }

    override fun anyOfChars(chars: String): SuperExpressive {
        return with {
            val elementValue = Types.anyOfChars(chars.escapeSpecial())
            val currentFrame = getCurrentFrame()

            currentFrame.elements.push(applyQuantifier(elementValue))
        }
    }
    override fun anythingButChars(chars: String): SuperExpressive {
        return with {
            val elementValue = Types.anythingButChars(chars.escapeSpecial())
            val currentFrame = getCurrentFrame()

            currentFrame.elements.push(applyQuantifier(elementValue))
        }
    }

    override fun anythingButRange(start: Char, end: Char): SuperExpressive {
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

    override fun subexpression(
        expr: RegularExpression,
        optionsLambda: SubexpressionOptions.() -> Unit
    ): RegularExpression {
        if ((expr as SuperExpressive).state.stack.size != 1) {
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
