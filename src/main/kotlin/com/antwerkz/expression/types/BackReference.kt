package com.antwerkz.expression.types

class BackReference(private val refIndex: Int) :
    Type("backreference", options = { index = refIndex }) {}
