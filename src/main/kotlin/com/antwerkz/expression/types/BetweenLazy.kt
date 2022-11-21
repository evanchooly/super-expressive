package com.antwerkz.expression.types

class BetweenLazy(private val x: Int, private val y: Int) :
    Type(
        "betweenLazy",
        options = {
            times = listOf(x, y)
            containsChildren = true
        }
    )
