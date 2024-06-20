package com.calebjares.opensudoku.integration.fpuzzle

import com.calebjares.opensudoku.integration.fpuzzle.data.Puzzle
import kotlinx.serialization.json.Json

object FPuzzleDecoder {
    /**
     * Decodes the standard f-puzzle string format into a domain object.
     *
     * @param input a custom string format from f-puzzle. This string is compressed to base64 format
     *   using a version of the JS lz-string package. See
     *   https://npmdoc.github.io/node-npmdoc-lz-string/build/apidoc.html.
     */
    fun decode(input: String): Puzzle {
        val decompressed = LZString.decompressFromBase64(input)
        checkNotNull(decompressed) { "Error while decompressing puzzle input" }
        return Json.decodeFromString<Puzzle>(decompressed)
    }
}