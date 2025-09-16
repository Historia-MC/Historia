package dev.boooiil.historia.core.proficiency.skills

class SkillSupplier<T>(private val data: T) {
    fun get(): T {
        return data
    }
}
