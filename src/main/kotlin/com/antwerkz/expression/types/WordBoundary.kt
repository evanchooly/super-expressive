package com.antwerkz.expression.types

object WordBoundary : Type("wordBoundary") {
    override fun copy() = WordBoundary

    override fun evaluate() = "\\b"
}
