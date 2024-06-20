package com.calebjares.opensudoku.integration.fpuzzle

import com.calebjares.opensudoku.integration.fpuzzle.data.Puzzle
import kotlinx.serialization.json.Json
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

object FPuzzleDecoder {
    /**
     * Decodes the standard f-puzzle string format into a domain object.
     *
     * @param input a base-64 encoded JSON string
     */
    @OptIn(ExperimentalEncodingApi::class)
    fun decode(input: String): Puzzle {
        // TODO: this needs to be decompressed first
        val json = Base64.decode(input).decodeToString()
        return Json.decodeFromString<Puzzle>(json)
    }
}