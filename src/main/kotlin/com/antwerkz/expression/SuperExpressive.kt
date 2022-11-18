package com.antwerkz.expression

class SuperExpressive {
    companion object {
        operator fun invoke(body: SuperExpressive.() -> Unit): SuperExpressive {
            val superExpressive = SuperExpressive()
            superExpressive.body()

            return superExpressive
        }
    }
    fun toRegexString(): String {
        TODO("Not yet implemented")
    }

    fun toRegex(): Regex {
        TODO("Not yet implemented")
    }

    fun namedCapture(s: String): SuperExpressive {
        TODO("Not yet implemented")
    }

    fun allowMultipleMatches(): SuperExpressive {
        TODO("Not yet implemented")
    }

    fun lineByLine(): SuperExpressive {
        TODO("Not yet implemented")
    }

    fun caseInsensitive(): SuperExpressive {
        TODO("Not yet implemented")
    }

    fun sticky(): SuperExpressive {
        TODO("Not yet implemented")
    }

    fun unicode(): SuperExpressive {
        TODO("Not yet implemented")
    }

    fun singleLine(): SuperExpressive {
    }

    fun anyChar(): SuperExpressive {
        TODO("Not yet implemented")
    }

    fun whitespaceChar(): SuperExpressive {
        TODO("Not yet implemented")
    }

    fun nonWhitespaceChar(): SuperExpressive {
        TODO("Not yet implemented")
    }

    fun digit(): SuperExpressive {
        TODO("Not yet implemented")
    }

    fun nonDigit(): SuperExpressive {
        TODO("Not yet implemented")
    }

    fun word(): SuperExpressive {
        TODO("Not yet implemented")
    }

    fun nonWord(): SuperExpressive {
        TODO("Not yet implemented")
    }

    fun wordBoundary(): SuperExpressive {
        TODO("Not yet implemented")
    }

    fun nonWordBoundary(): SuperExpressive {
        TODO("Not yet implemented")
    }

    fun newline(): SuperExpressive {
        TODO("Not yet implemented")
    }

    fun carriageReturn(): SuperExpressive {
        TODO("Not yet implemented")
    }

    fun tab(): SuperExpressive {
        TODO("Not yet implemented")
    }

    fun nullByte(): SuperExpressive {
        TODO("Not yet implemented")
    }

    fun anyOf(): SuperExpressive {
        TODO("Not yet implemented")
    }

    fun capture(): SuperExpressive {
        TODO("Not yet implemented")
    }

    fun group(): SuperExpressive {
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

    fun startOfInput(): SuperExpressive {
        TODO("Not yet implemented")
    }

    fun namedBackreference(s: String) {
        TODO("Not yet implemented")
    }

    fun backreference(i: Int) {
        TODO("Not yet implemented")
    }

    fun end() {
        TODO("Not yet implemented")
    }

    fun optional(): Any {
        TODO("Not yet implemented")
    }
}