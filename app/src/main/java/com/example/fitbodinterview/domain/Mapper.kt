package com.example.fitbodinterview.domain

abstract class Mapper<I, O> {

    abstract fun from(from: I): O

    open fun from(from: List<I>): List<O> {
        return from.map { from(it) }
    }
}