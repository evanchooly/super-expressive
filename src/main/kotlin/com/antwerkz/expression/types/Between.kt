package com.antwerkz.expression.types

class Between(private val x: Int, private val y: Int) :
    Type(
        "between",
        options = {
            times = listOf(x, y)
            containsChildren = true
        }
    ) {}
