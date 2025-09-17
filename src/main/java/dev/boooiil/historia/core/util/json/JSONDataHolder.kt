package dev.boooiil.historia.core.util.json

import dev.boooiil.historia.core.util.JSONSerializable

class JSONDataHolder<T>(val data: T) : JSONSerializable {

    override fun toJSON(): String {
        TODO("Not yet implemented")
    }
}