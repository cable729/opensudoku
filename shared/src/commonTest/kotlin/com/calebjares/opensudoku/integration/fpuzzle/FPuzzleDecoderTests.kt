package com.calebjares.opensudoku.integration.fpuzzle

import com.calebjares.opensudoku.integration.fpuzzle.data.Puzzle
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class FPuzzleDecoderTests {
    private companion object {
        // From https://app.crackingthecryptic.com/api/puzzle/olig7yj3gj
        const val sampleCompressedPuzzle = "N4IgzglgXgpiBcBOANCAFhA5mgNltALgMID2AdgGZ4DGBYCFAhjmDKpgE4QAmCA2n2ABfZMNEixkidPEBdZIJlTxK5cPmLVEkADdmAVzjwAzOwg6YZBAQ6Gl9uQrUPQenIYQBWMxavwbdlqOms5BzhqhkroGRgBMPpbWtjAu0hGpYfbpqtHuRqYgmOaJ/skZ4U7lVbLyIADWEDg4MBzUjJhGgiDUME308HwgAEqxRMYgqCNEACwTw6Oec1MAbEujAOxLxmNL00SxuzuyQrUEMAAeBPygPX38w55Ei5OPs7VuHvDojAAOPwCeAEI5hRyMQECAAMQABlhcLmkFgCGhADpPNpbix7kMABxEVaTRD4kC1UFkcFfGFw2EI6BGVHo0TdXpYgYPfZLZYc94xCGMMDcCggsFECFU6m0pHwVE41CMMiYZoIabGdHHIA==";

        const val sampleJsonPuzzle = """{"size":9,"highlightConflicts":false,"grid":[[{},{},{},{},{},{},{},{},{}],[{},{},{},{},{},{},{},{},{}],[{},{},{},{"value":3,"given":true},{},{},{},{},{}],[{},{},{},{},{},{"value":5,"given":true},{},{},{}],[{},{},{},{},{},{},{},{},{}],[{},{},{},{},{"value":2,"given":true},{},{},{},{}],[{},{},{},{},{},{},{},{},{}],[{},{},{"value":3,"given":true},{},{},{},{},{},{}],[{},{},{},{},{},{},{},{},{}]],"killercage":[{"cells":["R2C3","R2C4","R2C5","R2C6","R2C7","R3C3","R4C2","R4C3"]}],"text":[{"cells":["R5C5","R5C4"],"value":"happy!","fontC":"#000000","size":0.5},{"cells":["R8C6","R9C6"],"fontC":"#000000","size":0.5},{"cells":["R5C2","R6C2"],"value":"asdf","fontC":"#000000","size":0.8,"angle":435}]}"""
    }

    @Test
    fun decodeJson() {
        val puzzle = Json.decodeFromString<Puzzle>(sampleJsonPuzzle)

        val reEncodedJson = Json.encodeToString(puzzle)

        assertEquals(sampleJsonPuzzle, reEncodedJson)
    }

    @Test
    fun decompress_ThenRecompress() {
        val decompressed = LZString.decompressFromBase64(sampleCompressedPuzzle)

        val recompressed = LZString.compressToBase64(decompressed)

        // TODO: this fails on wasm because floats have extra bits added. E.g. 0.80000001968
        // Tracked by https://youtrack.jetbrains.com/issue/KT-59118/WASM-floating-point-toString-inconsistencies
        // Rather than adjust for this, I'll wait for a bug fix. I don't think it affects anything.
        assertEquals(sampleCompressedPuzzle, recompressed)
    }

    @Test
    fun decompressToDomain() {
        val puzzle = FPuzzleDecoder.decode(sampleCompressedPuzzle)

        assertEquals(9, puzzle.size)
        assertNull(puzzle.title)
        assertEquals(1, puzzle.killerCage?.size)
        assertEquals("R2C3", puzzle.killerCage!!.single().cells?.first()?.address)
    }
}
