package com.antwerkz.expression.types

internal object WordBoundary : Type("wordBoundary") {
    override fun copy() = WordBoundary

    override fun evaluate() = "\\b"
}
