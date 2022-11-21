package com.antwerkz.expression.types

class NamedCapture(private val captureName: String) : Type("namedCapture") {
    init {
        name = captureName
        containsChildren = true
    }
}
