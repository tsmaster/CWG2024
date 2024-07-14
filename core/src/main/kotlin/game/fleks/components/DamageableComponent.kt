package game.fleks.components

import com.github.quillraven.fleks.Component
import com.github.quillraven.fleks.ComponentType

// TODO: update to Fleks

/*
const val MAX_HEALTH = 100.0f

class DamageableComponent: Component, Pool.Poolable{
    var curHealth = MAX_HEALTH
    var maxHealth = MAX_HEALTH
    var dmgTeamIndex = -1

    override fun reset() {
        curHealth = MAX_HEALTH
        maxHealth = MAX_HEALTH
        dmgTeamIndex = -1
    }

    companion object {
        val mapper = mapperFor<DamageableComponent>()
    }
}*/


class DamageableComponent : Component<DamageableComponent> {
    override fun type() = DamageableComponent

    companion object : ComponentType<DamageableComponent>()
}