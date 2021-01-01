/*
 *  Copyright 2021 Steven Smith kana-tutor.com
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *
 *  You may obtain a copy of the License at
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 *  either express or implied.
 *
 *  See the License for the specific language governing permissions
 *  and limitations under the License.
 */

class RpnParserException(message: String) : Exception(message)

@Throws(RpnParserException::class)
fun rpnCalculate(rpnIn: MutableList<String>, previous: List<Double>? = null): MutableList<Double> {
    val rv = mutableListOf<Double>()
    var trace = false
    var inComment = false
    if (previous != null) rv.addAll(previous)
    nextToken@while (rpnIn.isNotEmpty()) {
        val token = rpnIn.removeAt(0)
        if (token == "trace") {
            trace = true
            println("Trace start ${rpnIn}:$rv")
            continue@nextToken
        }
        // comment starts with #_ token and ends with _#
        if (token.startsWith("#_") || inComment) {
            inComment = token.startsWith("#_")
            if (inComment && trace) println("<$token>")
            inComment = !token.endsWith("_#")
            continue@nextToken
        }
        if (trace) println("Trace > ${rpnIn}, token=\"$token\", $rv")
        val numIn = token.toDoubleOrNull()
        if (numIn != null) rv.add(numIn)
        else if ("^[+\\-*/]$".toRegex().matches(token)) {
            if (rv.size >= 2) {
                val lVal = rv.removeLast()
                val rVal = rv.removeLast()
                rv.add(
                    when (token) {
                        "+" -> rVal + lVal
                        "-" -> rVal - lVal
                        "*" -> rVal * lVal
                        "/" -> rVal / lVal
                        else -> throw RpnParserException("Unrecognized operation: $token")
                    }
                )
            }
            else throw RpnParserException("Stack underflow at $token")
        }
        else throw RpnParserException("unrecognized token:\"$token\"")
        if (trace) println("Trace < ${rpnIn}, $rv")
    }
    return rv
}

@Throws(RpnParserException::class)
fun rpnCalculate(rpnIn: String, previous: List<Double>? = null): MutableList<Double> =
    rpnCalculate(rpnIn.split("\\s+".toRegex()).toMutableList(), previous)

fun main() {
    var lineIn:String?
    do {
        print("> ")
        lineIn = readLine()
        if (lineIn != null) {
            if (lineIn == "q") System.exit(0)
            try {
                println("${rpnCalculate(lineIn)}")
            }
            catch (e:RpnParserException) {
                println(e)
            }
        }
    } while (lineIn != null)
}
