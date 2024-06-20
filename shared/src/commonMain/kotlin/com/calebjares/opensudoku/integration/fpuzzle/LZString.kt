/**
 * LZString4Java By Rufus Huang
 * https://github.com/rufushuang/lz-string4java
 * MIT License
 *
 * Port from original JavaScript version by pieroxy
 * https://github.com/pieroxy/lz-string
 *
 * MIT License
 *
 * Copyright (c) 2016 rufushuang
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.calebjares.opensudoku.integration.fpuzzle

import kotlin.jvm.JvmStatic

/**
 * Implements the original JS library lz-string compression/decompression algorithm, which is an
 * LZ-based compression algorithm.
 *
 * Also see http://pieroxy.net/blog/pages/lz-string/index.html.
 *
 * Copied from https://github.com/rufushuang/lz-string4java/blob/master/src/rufus/lzstring4java/LZString.java
 * then converted to Kotlin using IntelliJ, and cleaned up manually.
 */
object LZString {
    private val keyStrBase64 =
        "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=".toCharArray()
    private val keyStrUriSafe =
        "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+-$".toCharArray()
    private val baseReverseDic: MutableMap<CharArray, MutableMap<Char, Int>?> = mutableMapOf()

    private fun getBaseValue(alphabet: CharArray, character: Char): Char {
        var map = baseReverseDic[alphabet]
        if (map == null) {
            map = mutableMapOf()
            baseReverseDic[alphabet] = map
            for (i in alphabet.indices) {
                map[alphabet[i]] = i
            }
        }
        return map[character]!!.toChar()
    }

    fun compressToBase64(input: String?): String {
        if (input == null) return ""
        val res = _compress(input, 6) { a -> keyStrBase64[a] }
        return when (res.length % 4) {
            0 -> res
            1 -> "$res==="
            2 -> "$res=="
            3 -> "$res="
            else -> res
        }
    }

    fun decompressFromBase64(inputStr: String?): String? {
        if (inputStr == null) return ""
        if (inputStr == "") return null
        return _decompress(inputStr.length, 32) { index -> getBaseValue(keyStrBase64, inputStr[index]) }
    }

    fun compressToUTF16(input: String?): String {
        if (input == null) return ""
        return _compress(input, 15) { a -> fc(a + 32) } + " "
    }

    fun decompressFromUTF16(compressedStr: String?): String? {
        if (compressedStr == null) return ""
        if (compressedStr.isEmpty()) return null
        return _decompress(compressedStr.length, 16384) { index -> (compressedStr[index].code - 32).toChar() }
    }

    //TODO: java has no Uint8Array type, what can we do?
    fun compressToEncodedURIComponent(input: String?): String {
        if (input == null) return ""
        return _compress(input, 6) { a -> keyStrUriSafe[a] }
    }

    fun decompressFromEncodedURIComponent(inputStr: String?): String? {
        if (inputStr == null) return ""
        if (inputStr.isEmpty()) return null
        val urlEncodedInputStr = inputStr.replace(' ', '+')
        return _decompress(urlEncodedInputStr.length, 32) { index -> getBaseValue(keyStrUriSafe, urlEncodedInputStr[index]) }
    }

    fun compress(uncompressed: String?): String {
        return _compress(uncompressed, 16) { a -> fc(a) }
    }

    private fun _compress(
        uncompressedStr: String?,
        bitsPerChar: Int,
        getCharFromInt: CompressFunctionWrapper
    ): String {
        if (uncompressedStr == null) return ""
        var i: Int
        var value: Int
        val context_dictionary= mutableMapOf<String, Int>()
        val context_dictionaryToCreate = mutableSetOf<String>()
        var context_c = ""
        var context_wc = ""
        var context_w = ""
        var context_enlargeIn = 2 // Compensate for the first entry which should not count
        var context_dictSize = 3
        var context_numBits = 2
        val context_data = StringBuilder(uncompressedStr.length / 3)
        var context_data_val = 0
        var context_data_position = 0

        var ii = 0
        while (ii < uncompressedStr.length) {
            context_c = uncompressedStr[ii].toString()
            if (!context_dictionary.containsKey(context_c)) {
                context_dictionary[context_c] = context_dictSize++
                context_dictionaryToCreate.add(context_c)
            }

            context_wc = context_w + context_c
            if (context_dictionary.containsKey(context_wc)) {
                context_w = context_wc
            } else {
                if (context_dictionaryToCreate.contains(context_w)) {
                    if (context_w[0].code < 256) {
                        i = 0
                        while (i < context_numBits) {
                            context_data_val = (context_data_val shl 1)
                            if (context_data_position == bitsPerChar - 1) {
                                context_data_position = 0
                                context_data.append(getCharFromInt.doFunc(context_data_val))
                                context_data_val = 0
                            } else {
                                context_data_position++
                            }
                            i++
                        }
                        value = context_w[0].code
                        i = 0
                        while (i < 8) {
                            context_data_val = (context_data_val shl 1) or (value and 1)
                            if (context_data_position == bitsPerChar - 1) {
                                context_data_position = 0
                                context_data.append(getCharFromInt.doFunc(context_data_val))
                                context_data_val = 0
                            } else {
                                context_data_position++
                            }
                            value = value shr 1
                            i++
                        }
                    } else {
                        value = 1
                        i = 0
                        while (i < context_numBits) {
                            context_data_val = (context_data_val shl 1) or value
                            if (context_data_position == bitsPerChar - 1) {
                                context_data_position = 0
                                context_data.append(getCharFromInt.doFunc(context_data_val))
                                context_data_val = 0
                            } else {
                                context_data_position++
                            }
                            value = 0
                            i++
                        }
                        value = context_w[0].code
                        i = 0
                        while (i < 16) {
                            context_data_val = (context_data_val shl 1) or (value and 1)
                            if (context_data_position == bitsPerChar - 1) {
                                context_data_position = 0
                                context_data.append(getCharFromInt.doFunc(context_data_val))
                                context_data_val = 0
                            } else {
                                context_data_position++
                            }
                            value = value shr 1
                            i++
                        }
                    }
                    context_enlargeIn--
                    if (context_enlargeIn == 0) {
                        context_enlargeIn = powerOf2(context_numBits)
                        context_numBits++
                    }
                    context_dictionaryToCreate.remove(context_w)
                } else {
                    value = context_dictionary[context_w]!!
                    i = 0
                    while (i < context_numBits) {
                        context_data_val = (context_data_val shl 1) or (value and 1)
                        if (context_data_position == bitsPerChar - 1) {
                            context_data_position = 0
                            context_data.append(getCharFromInt.doFunc(context_data_val))
                            context_data_val = 0
                        } else {
                            context_data_position++
                        }
                        value = value shr 1
                        i++
                    }
                }
                context_enlargeIn--
                if (context_enlargeIn == 0) {
                    context_enlargeIn = powerOf2(context_numBits)
                    context_numBits++
                }
                // Add wc to the dictionary.
                context_dictionary[context_wc] = context_dictSize++
                context_w = context_c
            }
            ii += 1
        }


        // Output the code for w.
        if (!context_w.isEmpty()) {
            if (context_dictionaryToCreate.contains(context_w)) {
                if (context_w[0].code < 256) {
                    i = 0
                    while (i < context_numBits) {
                        context_data_val = (context_data_val shl 1)
                        if (context_data_position == bitsPerChar - 1) {
                            context_data_position = 0
                            context_data.append(getCharFromInt.doFunc(context_data_val))
                            context_data_val = 0
                        } else {
                            context_data_position++
                        }
                        i++
                    }
                    value = context_w[0].code
                    i = 0
                    while (i < 8) {
                        context_data_val = (context_data_val shl 1) or (value and 1)
                        if (context_data_position == bitsPerChar - 1) {
                            context_data_position = 0
                            context_data.append(getCharFromInt.doFunc(context_data_val))
                            context_data_val = 0
                        } else {
                            context_data_position++
                        }
                        value = value shr 1
                        i++
                    }
                } else {
                    value = 1
                    i = 0
                    while (i < context_numBits) {
                        context_data_val = (context_data_val shl 1) or value
                        if (context_data_position == bitsPerChar - 1) {
                            context_data_position = 0
                            context_data.append(getCharFromInt.doFunc(context_data_val))
                            context_data_val = 0
                        } else {
                            context_data_position++
                        }
                        value = 0
                        i++
                    }
                    value = context_w[0].code
                    i = 0
                    while (i < 16) {
                        context_data_val = (context_data_val shl 1) or (value and 1)
                        if (context_data_position == bitsPerChar - 1) {
                            context_data_position = 0
                            context_data.append(getCharFromInt.doFunc(context_data_val))
                            context_data_val = 0
                        } else {
                            context_data_position++
                        }
                        value = value shr 1
                        i++
                    }
                }
                context_enlargeIn--
                if (context_enlargeIn == 0) {
                    context_enlargeIn = powerOf2(context_numBits)
                    context_numBits++
                }
                context_dictionaryToCreate.remove(context_w)
            } else {
                value = context_dictionary[context_w]!!
                i = 0
                while (i < context_numBits) {
                    context_data_val = (context_data_val shl 1) or (value and 1)
                    if (context_data_position == bitsPerChar - 1) {
                        context_data_position = 0
                        context_data.append(getCharFromInt.doFunc(context_data_val))
                        context_data_val = 0
                    } else {
                        context_data_position++
                    }
                    value = value shr 1
                    i++
                }
            }
            context_enlargeIn--
            if (context_enlargeIn == 0) {
                context_enlargeIn = powerOf2(context_numBits)
                context_numBits++
            }
        }

        // Mark the end of the stream
        value = 2
        i = 0
        while (i < context_numBits) {
            context_data_val = (context_data_val shl 1) or (value and 1)
            if (context_data_position == bitsPerChar - 1) {
                context_data_position = 0
                context_data.append(getCharFromInt.doFunc(context_data_val))
                context_data_val = 0
            } else {
                context_data_position++
            }
            value = value shr 1
            i++
        }

        // Flush the last char
        while (true) {
            context_data_val = (context_data_val shl 1)
            if (context_data_position == bitsPerChar - 1) {
                context_data.append(getCharFromInt.doFunc(context_data_val))
                break
            } else context_data_position++
        }
        return context_data.toString()
    }

    fun f(i: Int): String {
        return i.toChar().toString()
    }

    fun fc(i: Int): Char {
        return i.toChar()
    }

    fun decompress(compressed: String?): String? {
        if (compressed == null) return ""
        if (compressed.isEmpty()) return null
        return _decompress(compressed.length, 32768) { i -> compressed[i] }
    }

    private fun _decompress(
        length: Int,
        resetValue: Int,
        getNextValue: DecompressFunctionWrapper
    ): String? {
        val dictionary = mutableListOf<String>()
        // TODO: is next an unused variable in original lz-string?
        @Suppress("unused") var next: Int
        var enlargeIn = 4
        var dictSize = 4
        var numBits = 3
        var entry: String? = ""
        val result = StringBuilder()
        var w: String?
        var bits: Int
        var resb: Int
        var maxpower: Int
        var power: Int
        var c: String? = null
        val data = DecData()
        data.`val` = getNextValue.doFunc(0)
        data.position = resetValue
        data.index = 1

        var i = 0
        while (i < 3) {
            dictionary.add(i, f(i))
            i += 1
        }

        bits = 0
        maxpower = powerOf2(2)
        power = 1
        while (power != maxpower) {
            resb = data.`val`.code and data.position
            data.position = data.position shr 1
            if (data.position == 0) {
                data.position = resetValue
                data.`val` = getNextValue.doFunc(data.index++)
            }
            bits = bits or (if (resb > 0) 1 else 0) * power
            power = power shl 1
        }

        when (bits.also { next = it }) {
            0 -> {
                bits = 0
                maxpower = powerOf2(8)
                power = 1
                while (power != maxpower) {
                    resb = data.`val`.code and data.position
                    data.position = data.position shr 1
                    if (data.position == 0) {
                        data.position = resetValue
                        data.`val` = getNextValue.doFunc(data.index++)
                    }
                    bits = bits or (if (resb > 0) 1 else 0) * power
                    power = power shl 1
                }
                c = f(bits)
            }

            1 -> {
                bits = 0
                maxpower = powerOf2(16)
                power = 1
                while (power != maxpower) {
                    resb = data.`val`.code and data.position
                    data.position = data.position shr 1
                    if (data.position == 0) {
                        data.position = resetValue
                        data.`val` = getNextValue.doFunc(data.index++)
                    }
                    bits = bits or (if (resb > 0) 1 else 0) * power
                    power = power shl 1
                }
                c = f(bits)
            }

            2 -> return ""
        }
        if (c != null) {
            dictionary.add(3, c)
        }
        w = c
        result.append(w)
        while (true) {
            if (data.index > length) {
                return ""
            }

            bits = 0
            maxpower = powerOf2(numBits)
            power = 1
            while (power != maxpower) {
                resb = data.`val`.code and data.position
                data.position = data.position shr 1
                if (data.position == 0) {
                    data.position = resetValue
                    data.`val` = getNextValue.doFunc(data.index++)
                }
                bits = bits or (if (resb > 0) 1 else 0) * power
                power = power shl 1
            }
            // TODO: very strange here, c above is as char/string, here further is a int, rename "c" in the switch as "cc"
            var cc: Int
            when (bits.also { cc = it }) {
                0 -> {
                    bits = 0
                    maxpower = powerOf2(8)
                    power = 1
                    while (power != maxpower) {
                        resb = data.`val`.code and data.position
                        data.position = data.position shr 1
                        if (data.position == 0) {
                            data.position = resetValue
                            data.`val` = getNextValue.doFunc(data.index++)
                        }
                        bits = bits or (if (resb > 0) 1 else 0) * power
                        power = power shl 1
                    }

                    dictionary.add(dictSize++, f(bits))
                    cc = dictSize - 1
                    enlargeIn--
                }

                1 -> {
                    bits = 0
                    maxpower = powerOf2(16)
                    power = 1
                    while (power != maxpower) {
                        resb = data.`val`.code and data.position
                        data.position = data.position shr 1
                        if (data.position == 0) {
                            data.position = resetValue
                            data.`val` = getNextValue.doFunc(data.index++)
                        }
                        bits = bits or (if (resb > 0) 1 else 0) * power
                        power = power shl 1
                    }
                    dictionary.add(dictSize++, f(bits))
                    cc = dictSize - 1
                    enlargeIn--
                }

                2 -> return result.toString()
            }
            if (enlargeIn == 0) {
                enlargeIn = powerOf2(numBits)
                numBits++
            }

            entry = if (cc < dictionary.size) {
                dictionary[cc]
            } else {
                if (cc == dictSize) {
                    w + w!![0]
                } else {
                    return null
                }
            }
            result.append(entry)

            // Add w+entry[0] to the dictionary.
            dictionary.add(dictSize++, w + entry[0])
            enlargeIn--

            w = entry

            if (enlargeIn == 0) {
                enlargeIn = powerOf2(numBits)
                numBits++
            }
        }
    }

    private fun powerOf2(power: Int): Int = 1 shl power

    fun interface CompressFunctionWrapper {
        fun doFunc(i: Int): Char
    }

    fun interface DecompressFunctionWrapper {
        fun doFunc(i: Int): Char
    }

    private class DecData {
        var `val`: Char = 0.toChar()
        var position: Int = 0
        var index: Int = 0
    }
}