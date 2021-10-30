/*
* @description: 146. LRU 缓存机制
* https://leetcode-cn.com/problems/lru-cache/
* @author: jixiaoyong
* @email: jixiaoyong1995@gmail.com
* @date: 2021/10/30
*/

// 用list保证put和get时候能保存key的顺序，用maps保存key对应的值
class LruCache(capacity: Int) {
    // 维持顺序
    var list: MutableList<Int>

    // 保存值
    var maps: MutableMap<Int, Int>
    val mCapacity: Int

    init {
        mCapacity = capacity
        list = ArrayList(capacity)
        maps = HashMap(capacity)
    }

    fun get(key: Int): Int {
        val result = if (list.contains(key)) {
            // 更新当前key的位置为最新（list最末尾）
            list.remove(key)
            list.add(key)
            maps[key] ?: -1
        } else {
            -1
        }
        println("get $key: $result")
        return result
    }

    fun put(key: Int, value: Int) {
        if (list.contains(key)) {
            list.remove(key)
        } else if (list.size >= mCapacity) {
            val removeKey = list.removeAt(0)
            maps.remove(removeKey)
        }
        // 新增的key在列表最末尾
        list.add(key)
        maps[key] = value
    }
}

// 按照上述思路使用系统LinkedHashMap实现
class LRUCacheLinkedHashMap(capacity: Int) {

    // 维持顺序
    var linkedHashMap: LinkedHashMap<Int, Int>

    val mCapacity: Int

    init {
        mCapacity = capacity
        linkedHashMap = LinkedHashMap(capacity)
    }

    fun get(key: Int): Int {
        val result = if (linkedHashMap.contains(key)) {
            val value = linkedHashMap.remove(key)
            linkedHashMap[key] = value!!
            value
        } else {
            -1
        }
        println("get $key: $result")
        return result
    }

    fun put(key: Int, value: Int) {
        if (linkedHashMap.contains(key)) {
            linkedHashMap.remove(key)
        } else if (linkedHashMap.size >= mCapacity) {
            linkedHashMap.keys.first().let {
                linkedHashMap.remove(it)
            }
        }
        linkedHashMap.put(key, value)
    }

}

// 性能最优
// LinkedHashMap介绍：https://www.cnblogs.com/xiaoxi/p/6170590.html
// 其内部维持了一个双向链表，可以通过accessOrder（true 访问顺序，false插入顺序）指定链表读写顺序
class LRUCacheExtLinkedHashMap(val capacity: Int) : LinkedHashMap<Int, Int>(capacity, 0.75F, true) {

    override fun get(key: Int): Int {
        return super.getOrDefault(key, -1)
    }

    override fun removeEldestEntry(eldest: MutableMap.MutableEntry<Int, Int>?): Boolean {
        // 超过容量的时候删除最老的key value
        return size > capacity
    }
}