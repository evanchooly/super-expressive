package com.antwerkz.expression

import java.util.Collections.replaceAll
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
                if (el.type == "char" || el.type == "anyOfChars") el.value.toString() else
                    "${el.value/*[0]*/}-${el.value/*[1]*/}"
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
                "wordBoundary" ->  "\\b"
                "nonWordBoundary" ->  "\\B"
                "startOfInput" ->  "^"
                "endOfInput" ->  "$"
                "newline" ->  "\\n"
                "carriageReturn" ->  "\\r"
                "tab" ->  "\\t"
//                "nullByte" -> return "\\0"
                "string" -> el.value as String
//                Types.char': return el.value
//                Types.range': return `[${el.value[0]}-${el.value[1]}]`
//                Types.anythingButRange': return `[^${el.value[0]}-${el.value[1]}]`
//                Types.anyOfChars': return `[${el.value}]`
//                Types.anythingButChars': return `[^${el.value}]`
//                Types.namedBackreference': return `\\k<${el.name}>`
//                Types.backreference': return `\\${el.index}`
//                Types.subexpression': return el.value.map(SuperExpressive[evaluate]).join('')
//                Types.
//                Types.optional':
//                Types.zeroOrMore':
//                Types.zeroOrMoreLazy':
//                Types.oneOrMore':
//                Types.oneOrMoreLazy': {
//                const inner = SuperExpressive[evaluate](el.value)
//                const withGroup = el.value.quantifierRequiresGroup
//                ? `(?:${inner})`
//                : inner
//                const symbol = quantifierTable[el.type]
//                return `${withGroup}${symbol}`
//            }
//
//                case 'betweenLazy':
//                case 'between':
//                case 'atLeast':
//                case 'exactly': {
//                const inner = SuperExpressive[evaluate](el.value)
//                const withGroup = el.value.quantifierRequiresGroup
//                ? `(?:${inner})`
//                : inner
//                return `${withGroup}${quantifierTable[el.type](el.times)}`
//            }
//
//                case 'anythingButString': {
//                const chars = el.value.split('').map(c => `[^${c}]`).join('')
//                return `(?:${chars})`
//            }
//
//                case 'assertAhead': {
//                const evaluated = el.value.map(SuperExpressive[evaluate]).join('')
//                return `(?=${evaluated})`
//            }
//
//                case 'assertBehind': {
//                const evaluated = el.value.map(SuperExpressive[evaluate]).join('')
//                return `(?<=${evaluated})`
//            }
//
//                case 'assertNotAhead': {
//                const evaluated = el.value.map(SuperExpressive[evaluate]).join('')
//                return `(?!${evaluated})`
//            }
//
//                case 'assertNotBehind': {
//                const evaluated = el.value.map(SuperExpressive[evaluate]).join('')
//                return `(?<!${evaluated})`
//            }
//
            "anyOf" -> {
                var (fused, rest) = fuseElements(el.value as List<Type>)

                if (rest.isEmpty()) {
                    return "[${fused}]"
                }

                val evaluatedRest = rest.map { evaluate(it) }
                val separator = if (evaluatedRest.isNotEmpty() && fused.isNotEmpty()) "|" else ""
                val restJoined = evaluatedRest.joinToString("|")
                val fusedJoined = if(fused.isNotEmpty()) "[${fused}]" else ""
                return "(?:$restJoined${separator}${fusedJoined})"
            }
//
//                case 'capture': {
//                const evaluated = el.value.map(SuperExpressive[evaluate])
//                return `(${evaluated.join('')})`
//            }
//
//                case 'namedCapture': {
//                const evaluated = el.value.map(SuperExpressive[evaluate])
//                return `(?<${el.name}>${evaluated.join('')})`
//            }
//
//                case 'group': {
//                const evaluated = el.value.map(SuperExpressive[evaluate])
//                return `(?:${evaluated.join('')})`
//            }
//
                else -> throw IllegalArgumentException("Can't process unsupported element type: ${el.type}")
            }
        }
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
        if (currentFrame.quantifier != null) {
//            var wrapped = currentFrame.quantifier.value(element)
//            currentFrame.quantifier = null
//            return wrapped
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
        TODO("Not yet implemented")
    }

    fun assertBehind(): SuperExpressive {
        TODO("Not yet implemented")
    }

    fun assertNotAhead(): SuperExpressive {
        TODO("Not yet implemented")
    }

    fun assertNotBehind(): SuperExpressive {
        TODO("Not yet implemented")
    }

    fun backreference(i: Int) {
        TODO("Not yet implemented")
    }

    fun capture(): SuperExpressive {
        TODO("Not yet implemented")
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
            val oldFrame = state.stack.pop();
            val currentFrame = getCurrentFrame();
            val value = oldFrame.type.value(oldFrame.elements)
            currentFrame.elements.push(applyQuantifier(value));
        }
    }

    fun group(): SuperExpressive {
        TODO("Not yet implemented")
    }

    fun multiline(): SuperExpressive {
        return with {
            state.flags.m = true
        }
    }

    fun namedBackreference(s: String) {
        TODO("Not yet implemented")
    }

    fun namedCapture(s: String): SuperExpressive {
        TODO("Not yet implemented")
    }

    fun newline() = matchElement(Types.newline())

    fun nonDigit() = matchElement(Types.nonDigit())

    fun nonWhitespaceChar() = matchElement(Types.nonWhitespaceChar())

    fun nonWord() = matchElement(Types.nonWord())

    fun nonWordBoundary() = matchElement(Types.nonWordBoundary())

//    fun nullByte() = matchElement(Types.nullByte())

    fun optional(): Any {
        TODO("Not yet implemented")
    }

    fun singleLine(): SuperExpressive {
        TODO("Not yet implemented")
    }

    fun startOfInput(): SuperExpressive {
        TODO("Not yet implemented")
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
            state.stack.add(StackFrame(type));
        };
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
}
