package com.snowyfox.framework.utils

typealias Fun<A, B> = (A) -> B

infix fun <A, B> A.map(fn: Fun <A, B>): B = fn(this)