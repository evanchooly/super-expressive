package com.antwerkz.expression

import java.util.function.Predicate
import java.util.regex.Pattern

class SuperExpressive() {
    private var state = State()

    companion object {
        operator fun invoke(body: SuperExpressive.() -> Unit): SuperExpressive {
            val superExpressive = SuperExpressive()
            superExpressive.body()

            return superExpressive
        }

        private fun fuseElements(elements: List<Type>): Pair<String, List<Type>> {
            val (fusables, rest) = partition(elements)
            val fused = fusables.joinToString("") { el ->
                if (el.type == "char" || el.type == "anyOfChars") {
                    el.value.toString()
                } else {
                    val value = el.value as List<*>
                    "${value[0]}-${value[1]}"
                }
            }
            return fused to rest
        }

        private fun partition(elements: List<Type>): Pair<List<Type>, List<Type>> {
            val predicate = Predicate<Type> { type -> type.type in listOf("range", "char", "anyOfChars") }
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

        private fun evaluate(el: Type): String {
            return when (el.type) {
                "noop" -> ""
                "anyChar" -> "."
                "whitespaceChar" -> "\\s"
                "nonWhitespaceChar" -> "\\S"
                "digit" -> "\\d"
                "nonDigit" -> "\\D"
                "word" -> "\\w"
                "nonWord" -> "\\W"
                "wordBoundary" -> "\\b"
                "nonWordBoundary" -> "\\B"
                "startOfInput" -> "^"
                "endOfInput" -> "$"
                "newline" -> "\\n"
                "carriageReturn" -> "\\r"
                "tab" -> "\\t"
//                "nullByte" -> return "\\0"
                "string" -> el.value as String
                "char" -> el.value as String
                "range" -> {
                    val list = el.value as List<*>
                    "[${list[0]}-${list[1]}]"
                }
//                "anythingButRange" -> "[^${el.value[0]}-${el.value[1]}]"
                "anyOfChars" -> "[${el.value}]"
                "anythingButChars" -> "[^${el.value}]"
                "namedBackreference" -> "\\k<${(el as DeferredType).name}>"
                "backreference" -> "\\${(el as DeferredType).index}"
//                "subexpression" ->  el.value.map(SuperExpressive[evaluate]).join('')
                "optional",
                "zeroOrMore",
                "zeroOrMoreLazy",
                "oneOrMore",
                "oneOrMoreLazy",
                -> {
                    val inner = evaluate(el.value as Type)
                    val withGroup = if ((el.value as RealizedType).quantifierRequiresGroup) "(?:${inner})" else inner
                    val symbol = quantifierTable[el.type]
                    return "${withGroup}${symbol}"
                }

                "betweenLazy",
                "between",
                "atLeast",
                "exactly"
                -> {
                    val inner = evaluate(el.value as Type)
                    val withGroup = if ((el.value as RealizedType).quantifierRequiresGroup) "(?:${inner})" else inner

                    val func = times[el.type]!!
                    return "${withGroup}${func((el as DeferredType).times)}"
                }
//
//                case 'anythingButString': {
//                const chars = el.value.split('').map(c => `[^${c}]`).join('')
//                return `(?:${chars})`
//            }
//
                "assertAhead" -> {
                    val list = el.value as List<Type>
                    val evaluated = list.joinToString("") { evaluate(it) }
                    return "(?=${evaluated})"
                }

                "assertBehind" -> {
                    val list = el.value as List<Type>
                    val evaluated = list.joinToString("") { evaluate(it) }
                    return "(?<=${evaluated})"
                }

                "assertNotAhead" -> {
                    val list = el.value as List<Type>
                    val evaluated = list.joinToString("") { evaluate(it) }
                    return "(?!${evaluated})"
                }

                "assertNotBehind" -> {
                    val list = el.value as List<Type>
                    val evaluated = list.joinToString("") { evaluate(it) }
                    return "(?<!${evaluated})"
                }

                "anyOf" -> {
                    var (fused, rest) = fuseElements(el.value as List<Type>)

                    if (rest.isEmpty()) {
                        return "[${fused}]"
                    }
                    val evaluatedRest = rest.map { evaluate(it) }
                    val separator = if (evaluatedRest.isNotEmpty() && fused.isNotEmpty()) "|" else ""
                    val restJoined = evaluatedRest.joinToString("|")
                    val fusedJoined = if (fused.isNotEmpty()) "[${fused}]" else ""
                    return "(?:$restJoined${separator}${fusedJoined})"
                }

                "capture" -> {
                    val list = el.value as List<Type>
                    val evaluated = list.joinToString("") { evaluate(it) }
                    return "(${evaluated})"
                }

                "namedCapture" -> {
                    el as DeferredType
                    val list = el.value as List<Type>
                    val evaluated = list.joinToString("") { evaluate(it) }
                    return "(?<${el.name}>${evaluated})"
                }

                "group" -> {
                    val value = el.value as List<Type>
                    val evaluated = value.joinToString("") { evaluate(it) }
                    return "(?:${evaluated})"
                }

                else -> throw IllegalArgumentException("Can't process unsupported element type: ${el.type}")
            }
        }

        private val quantifierTable = mapOf(
            "oneOrMore" to "+",
            "oneOrMoreLazy" to "+?",
            "zeroOrMore" to "*",
            "zeroOrMoreLazy" to "*?",
            "optional" to "?",
        )

        private val times = mapOf<String, (List<Int>) -> String>(
            "exactly" to { times: List<Int> -> "{${times}}" },
            "atLeast" to { times: List<Int> -> "{${times},}" },
            "between" to { times: List<Int> -> "{${times[0]},${times[1]}}" },
            "betweenLazy" to { times: List<Int> -> "{${times[0]},${times[1]}}?" },
        )
    }

    private constructor(expressive: SuperExpressive): this() {
        state = State(expressive.state)
    }

    private fun with(updates: SuperExpressive.() -> Unit): SuperExpressive {
        val next = SuperExpressive(this)
        next.updates()
        return next
    }

    fun dotMatchesAll(): SuperExpressive {
        return with {
            state.flags.d = true
        }
    }

    private fun applyQuantifier(element: Type): Type {
        val currentFrame = getCurrentFrame()
        val quantifier = currentFrame.quantifier
        if (quantifier != null) {
            quantifier.value = element
            currentFrame.quantifier = null
            return element
        }
        return element
    }

    private fun matchElement(type: Type): SuperExpressive {
        return with {
            getCurrentElementArray().push(applyQuantifier(type))
        }
    }

    fun anyChar(): SuperExpressive = matchElement(Types.anyChar())

    fun anyOf(): SuperExpressive {
        return frameCreatingElement(Types.anyOf())
    }

    fun assertAhead(): SuperExpressive {
        return frameCreatingElement(Types.assertAhead());
    }

    fun assertBehind(): SuperExpressive {
        return frameCreatingElement(Types.assertBehind());
    }

    fun assertNotAhead(): SuperExpressive {
        return frameCreatingElement(Types.assertNotAhead());
    }

    fun assertNotBehind(): SuperExpressive {
        return frameCreatingElement(Types.assertNotBehind());
    }

    fun backreference(index: Int): SuperExpressive {
        return matchElement(Types.backreference(index));
    }

    fun capture(): SuperExpressive {
        return with {
            val newFrame = StackFrame(Types.capture())
            state.stack.push(newFrame)
            state.totalCaptureGroups++
        }
    }

    fun carriageReturn() = matchElement(Types.carriageReturn())

    fun caseInsensitive(): SuperExpressive {
        return with {
            state.flags.i = true
        }
    }

    fun char(c: Char): SuperExpressive {
        return with {
            val currentFrame = getCurrentFrame()
            currentFrame.elements.push(applyQuantifier(Types.char(escapeSpecial(c.toString()))))
        }
    }

    fun digit() = matchElement(Types.digit())

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

    fun group(): SuperExpressive {
        return frameCreatingElement(Types.group());
    }

    fun multiline(): SuperExpressive {
        return with {
            state.flags.m = true
        }
    }

    fun namedBackreference(name: String): SuperExpressive {
        if(!this.state.namedGroups.contains(name)) {
            throw IllegalArgumentException("no capture group called '${name}' exists (create one with .namedCapture())")
        };
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
        this.state.namedGroups.push(name)
    }

    fun newline() = matchElement(Types.newline())

    fun nonDigit() = matchElement(Types.nonDigit())

    fun nonWhitespaceChar() = matchElement(Types.nonWhitespaceChar())

    fun nonWord() = matchElement(Types.nonWord())

    fun nonWordBoundary() = matchElement(Types.nonWordBoundary())

//    fun nullByte() = matchElement(Types.nullByte())

    fun oneOrMore(): SuperExpressive {
        return quantifierElement(Types.oneOrMore());
    }
    fun oneOrMoreLazy(): SuperExpressive {
        return quantifierElement(Types.oneOrMoreLazy());
    }

    fun optional(): SuperExpressive {
        return quantifierElement(Types.optional());
    }

    fun range(start: Char, end: Char): SuperExpressive {
        if (start >= end) {
            throw java.lang.IllegalArgumentException("start ($start) must be smaller than end (${end})")
        }

        return with {
            getCurrentFrame().elements.push(applyQuantifier(Types.range(start, end)))
        }
    }

    fun singleLine(): SuperExpressive {
        TODO("Not yet implemented")
    }

    fun startOfInput(): SuperExpressive {
        return with {
            state.hasDefinedStart = true;
            getCurrentElementArray().push(Types.startOfInput());
            
        }    
    }

    fun endOfInput(): SuperExpressive {
        return with {
            state.hasDefinedEnd = true;
            getCurrentElementArray().push(Types.endOfInput());
            
        }    
    }

    fun string(s: String): SuperExpressive {
        assert(s.isNotEmpty()) { "s cannot be an empty string" }

        return with {
            val elementValue = if(s.length > 1) Types.string(escapeSpecial(s)) else Types.char(escapeSpecial(s))
            val currentFrame = getCurrentFrame()
            currentFrame.elements.push(applyQuantifier(elementValue))
        }
    }

    fun tab() = matchElement(Types.tab())

    fun toRegex(): Regex {
        val (pattern, flags) = getRegexPatternAndFlags()
        return Regex(pattern, flags.toRegexOptions())
    }

    fun toPattern(): Pattern = toRegex().toPattern()

    fun unixLines(): SuperExpressive {
        return with {
            state.flags.u = true
        }
    }

    fun whitespaceChar() = matchElement(Types.whitespaceChar())

    fun word() = matchElement(Types.word())

    fun wordBoundary() = matchElement(Types.wordBoundary())

    private fun getCurrentElementArray(): MutableList<Type> = getCurrentFrame().elements

    private fun getCurrentFrame(): StackFrame = state.stack.last()

    private fun getRegexPatternAndFlags(): Pair<String, Flags> {
        val pattern: String = getCurrentElementArray()
            .joinToString("") { evaluate(it) }

        return pattern.ifBlank { "(?:)" } to this.state.flags
    }

    private fun frameCreatingElement(type: DeferredType): SuperExpressive {
        return with {
            state.stack.add(StackFrame(type))
        }
    }

    private fun escapeSpecial(s: String): String {
        val specialChars = "\\.^$|?*+()[]{}-".toCharArray().map { it.toString() }
        var escaped = s
        specialChars.forEach { char ->
            escaped = escaped.replace(char, "\\${char}")
        }
        return escaped
    }
    private fun <E> MutableList<E>.push(element: E) {
        add(element)
    }

    private fun <E> MutableList<E>.pop(): E {
        return removeLast()
    }

    fun zeroOrMore(): SuperExpressive {
        return quantifierElement(Types.zeroOrMore());
    }
    fun zeroOrMoreLazy(): SuperExpressive {
        return quantifierElement(Types.zeroOrMoreLazy());
    }
    private fun quantifierElement(type: Type): SuperExpressive {
        return with {
            getCurrentFrame().quantifier(type)
        }
    }

    fun exactly(count: Int): SuperExpressive {
        return with {
            getCurrentFrame().quantifier(Types.exactly(count))
        }
    }
    fun atLeast(count: Int): SuperExpressive {
        return with {
            getCurrentFrame().quantifier(Types.atLeast(count))
        }
    }

    fun between(x: Int, y: Int): SuperExpressive {
        if(x >= y) {
            throw IllegalArgumentException("x ($x) must be less than y ($y)")
        }

        return with {
            getCurrentFrame().quantifier(Types.between(x, y))
        };
    }
    fun betweenLazy(x: Int, y: Int): SuperExpressive {
        if(x >= y) {
            throw IllegalArgumentException("x ($x) must be less than y ($y)")
        }

        return with {
            getCurrentFrame().quantifier(Types.betweenLazy(x, y))
        };
    }

    fun anyOfChars(chars: String): SuperExpressive {
        return with {
            val elementValue = Types.anyOfChars(escapeSpecial(chars))
            val currentFrame = getCurrentFrame()

            currentFrame.elements.push(applyQuantifier(elementValue))

        }
    }
    fun anythingButChars(chars: String): SuperExpressive {
        return with {
            val elementValue = Types.anythingButChars(escapeSpecial(chars))
            val currentFrame = getCurrentFrame()

            currentFrame.elements.push(applyQuantifier(elementValue))

        }
    }

    fun anythingButRange(start: Char, end: Char): SuperExpressive {
        if (start >= end) {
            throw java.lang.IllegalArgumentException("start ($start) must be smaller than end (${end})")
        }

        return with {
            getCurrentFrame().elements.push(applyQuantifier(Types.anythingButRange(start, end)))
        }

    }
}
