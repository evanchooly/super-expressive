package com.antwerkz.expression

import java.util.regex.Pattern

class SuperExpressive() {
    private var state = State()

    companion object {
        operator fun invoke(body: SuperExpressive.() -> Unit): SuperExpressive {
            val superExpressive = SuperExpressive()
            superExpressive.body()

            return superExpressive
        }

        private fun evaluate(el: StackFrame): String {
            return when (el.type) {
                Types.noop -> ""
                Types.anyChar -> "."
//                Types.whitespaceChar': return '\\s'
//                Types.nonWhitespaceChar': return '\\S'
//                Types.digit': return '\\d'
//                Types.nonDigit': return '\\D'
//                Types.word': return '\\w'
//                Types.nonWord': return '\\W'
//                Types.wordBoundary': return '\\b'
//                Types.nonWordBoundary': return '\\B'
//                Types.startOfInput': return '^'
//                Types.endOfInput': return '$'
//                Types.newline': return '\\n'
//                Types.carriageReturn': return '\\r'
//                Types.tab': return '\\t'
//                Types.nullByte': return '\\0'
//                Types.string': return el.value
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
//                case 'anyOf': {
//                const [fused, rest] = fuseElements(el.value)
//
//                if (!rest.length) {
//                    return `[${fused}]`
//                }
//
//                const evaluatedRest = rest.map(SuperExpressive[evaluate])
//                const separator = (evaluatedRest.length > 0 && fused.length > 0) ? '|' : ''
//                return `(?:${evaluatedRest.join('|')}${separator}${fused ? `[${fused}]` : ''})`
//            }
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

    fun anyChar(): SuperExpressive {
        TODO("Not yet implemented")
    }

    fun anyOf(): SuperExpressive {
        TODO("Not yet implemented")
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

    fun carriageReturn(): SuperExpressive {
        TODO("Not yet implemented")
    }

    fun caseInsensitive(): SuperExpressive {
        return with {
            state.flags.i = true
        }
    }

    fun char(c: Char): SuperExpressive {

//        const next = this[clone]()
//        const currentFrame = next[getCurrentFrame]()
//        currentFrame.elements.push(next[applyQuantifier](t.char(escapeSpecial(c))))
//
//        return next
        TODO("Not yet implemented")
    }

    fun digit(): SuperExpressive {
        TODO("Not yet implemented")
    }

    fun end() {
        TODO("Not yet implemented")
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

    fun newline(): SuperExpressive {
        TODO("Not yet implemented")
    }

    fun nonDigit(): SuperExpressive {
        TODO("Not yet implemented")
    }

    fun nonWhitespaceChar(): SuperExpressive {
        TODO("Not yet implemented")
    }

    fun nonWord(): SuperExpressive {
        TODO("Not yet implemented")
    }

    fun nonWordBoundary(): SuperExpressive {
        TODO("Not yet implemented")
    }

    fun nullByte(): SuperExpressive {
        TODO("Not yet implemented")
    }

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

//        const next = this[clone]()
//        const elementValue = s.length > 1 ? t.string(escapeSpecial(s)) : t.char(escapeSpecial(s))
//        const currentFrame = next[getCurrentFrame]()
//        currentFrame.elements.push(next[applyQuantifier](elementValue))
//        return next
        TODO("Not yet implemented")
    }

    fun tab(): SuperExpressive {
        TODO("Not yet implemented")
    }

    fun toRegex(): Regex {
        val (pattern, flags) = getRegexPatternAndFlags()
        return Regex(pattern, flags.toRegexOptions())
    }

    fun toPattern(): Pattern {
        return toRegex().toPattern()
    }

    fun unixLines(): SuperExpressive {
        return with {
            state.flags.u = true
        }
    }

    fun whitespaceChar(): SuperExpressive {
        TODO("Not yet implemented")
    }

    fun word(): SuperExpressive {
        TODO("Not yet implemented")
    }

    fun wordBoundary(): SuperExpressive {
        TODO("Not yet implemented")
    }

    private fun getCurrentElementArray(): List<StackFrame> {
        return getCurrentFrame().elements
    }

    private fun getCurrentFrame(): StackFrame {
        return state.stack.last()
    }

    private fun getRegexPatternAndFlags(): Pair<String, Flags> {
        val pattern: String = getCurrentElementArray()
            .joinToString("") { evaluate(it) }

        return pattern.ifBlank { "(?:)" } to this.state.flags
    }
}
