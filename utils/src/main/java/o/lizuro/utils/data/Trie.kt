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

    fun get(word: String, isPrefix: Boolean): List<T> {
        val first = word.first()

        return find(first)?.run {
            when (word.length) {
                1 -> if(isPrefix) getAllIndicesFrom(this) else indices
                else -> get(word.drop(1), isPrefix)
            }
        } ?: listOf()
    }

    private fun getAllIndicesFrom(trie: Trie<T>) : ArrayList<T> {
        val result = ArrayList(trie.indices)

        trie.children.forEach {
            result.addAll(getAllIndicesFrom(it))
        }

        return result
    }

    private fun findOrCreate(char: Char) =
        find(char) ?: Trie<T>(char).apply { this@Trie.children.add(this) }

    private fun find(char: Char) =
        children.find { it.char == char }
}

object TrieBuilder {
    fun <T> empty() = Trie<T>(char = ' ')
}