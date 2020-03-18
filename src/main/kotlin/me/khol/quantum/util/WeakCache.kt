package me.khol.quantum.util

import java.lang.ref.Reference
import java.lang.ref.WeakReference
import java.util.*

class WeakCache<Key, Value> {

    private val map: MutableMap<Key, Reference<Value>> = WeakHashMap()

    operator fun get(key: Key): Value? {
        val reference = map[key]

        return if (reference == null) {
            null
        } else {
            val value = reference.get()
            if (value == null) {
                map.remove(key)
            }
            value
        }
    }

    operator fun set(key: Key, value: Value?) {
        if (value != null) {
            map[key] = WeakReference(value)
        } else {
            map.remove(key)
        }
    }

    fun getOrPut(key: Key, defaultValue: () -> Value): Value {
        return this[key] ?: defaultValue().also {
            this[key] = it
        }
    }
}
