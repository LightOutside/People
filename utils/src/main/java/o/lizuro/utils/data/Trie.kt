package o.lizuro.utils.data

data class Trie<T>(
    private val char: Char,
    private val indices: MutableList<T> = mutableListOf(),
    private val children: MutableList<Trie<T>> = mutableListOf()
) {

    fun add(word: String, index: T) {
        val first = word.first()
        val trie = findOrCreate(first)

        when (word.length) {
            1 -> trie.indices.add(index)
            else -> trie.add(word.drop(1), index)
        }
    }

    fun get(word: String): List<T> {
        val first = word.first()

        return find(first)?.run {
            when (word.length) {
                1 -> indices
                else -> get(word.drop(1))
            }
        } ?: listOf()
    }

    fun complete(word: String): List<T> {
        val first = word.first()

        return find(first)?.run {
            when (word.length) {
                1 -> getAllFrom(this)
                else -> get(word.drop(1))
            }
        } ?: listOf()
    }

    private fun getAllFrom(trie: Trie<T>) : List<T> {
        val result = arrayListOf<T>()
        collectValues(trie, result)
        return result
    }

    private fun collectValues(trie: Trie<T>, result: ArrayList<T>) {
        result.addAll(trie.indices)
        trie.children.forEach {
            collectValues(it, result)
        }
    }

    private fun findOrCreate(char: Char) =
        find(char) ?: Trie<T>(char).apply { this@Trie.children.add(this) }

    private fun find(char: Char) =
        children.find { it.char == char }
}

object TrieBuilder {
    fun <T> empty() = Trie<T>(char = ' ')
}