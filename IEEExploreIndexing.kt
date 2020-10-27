/**
 * @author Jacob Huebner
 * 
 * Scored 19/19 (100%)
 */

fun main() = (System.`in`.bufferedReader().run {
    val bw = System.out.bufferedWriter()
    val stopWords = readLine().split(";")
    val indexTerms = readLine().split(";")

    val scores = IntArray(indexTerms.size)
    var curState = 0
    var l = 0


    fun readTag(): String {
        val tag = StringBuilder()
        var r = read()
        if (r == '/'.toInt()) {
            r = read()
        }
        while (r != '>'.toInt()) {
            tag.append(r.toChar())
            r = read()
        }
        return tag.toString()
    }

    fun String.toWord(): String {
        return this.toLowerCase().filter { (it in 'a'..'z') || it == '\'' }
    }


    fun dealWithWord(s: String) {
        val word = s.toWord()
        if (word.length < 4) return
        if (curState == 0) return
        if (word in stopWords) return
        l++
        val idx = indexTerms.indexOf(word)
        if (idx != -1) {
            scores[idx] += curState
        }
    }

    val sb = StringBuilder()
    var last = read()
    while (last != -1) {
        when (last) {
            '<'.toInt() -> {
                dealWithWord(sb.toString())
                sb.clear()
                when (readTag()) {
                    "title" -> {
                        curState = 5 - curState
                    }
                    "abstract" -> {
                        curState = 3 - curState
                    }
                    "body" -> {
                        curState = 1 - curState
                    }
                }
            }
            ' '.toInt(), '\n'.toInt() -> {
                dealWithWord(sb.toString())
                sb.clear()
            }

            else -> {
                sb.append(last.toChar())
            }
        }
        last = read()
    }

    val comp = Comparator<Int> { a, b ->
        if (scores[a] == scores[b]) {
            indexTerms[a].compareTo(indexTerms[b])
        } else {
            scores[b].compareTo(scores[a])
        }
    }
    var info = IntArray(indexTerms.size) { it }.sortedWith(comp)
    if (info.size >= 3) {
        val cutoff = info[2]
        info = info.takeWhile {
            scores[it] >= scores[cutoff]
        }
    }

    info.filter { scores[it] > 0 }.forEach {
        bw.write(indexTerms[it])
        bw.write(": ")
        val score = 100.0 * scores[it] / l
        bw.write(score.toString())
        bw.newLine()
    }
    bw.close()
} to Unit).second