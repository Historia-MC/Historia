package dev.boooiil.historia.core.util.json

import dev.boooiil.historia.core.util.JSONSerializable

class JSONHolder : JSONSerializable {

    private var members: MutableSet<JSONComponent> = HashSet()

    fun add(component: JSONComponent): JSONHolder {
        members.add(component)
        return this
    }

    fun pretty() {
        TODO("Print with spaces.")
    }

    override fun toJSON(): String {
        TODO("Not yet implemented")
    }
}