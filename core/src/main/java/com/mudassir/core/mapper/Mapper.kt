package com.mudassir.core.mapper

interface Mapper <in T, out R> {
    suspend operator fun invoke (input: T): R
}