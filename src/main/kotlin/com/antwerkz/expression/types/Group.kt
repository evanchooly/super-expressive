package com.antwerkz.expression.types

import com.antwerkz.expression.types.Root.containsChildren

class Group : Type("group", { containsChildren = true }) {}
