package game.fleks.components

import com.github.quillraven.fleks.Component
import com.github.quillraven.fleks.ComponentType

const val MAX_HEALTH = 100.0f

class DamageableComponent : Component<DamageableComponent> {
    override fun type() = DamageableComponent

    var curHealth = MAX_HEALTH
    var maxHealth = MAX_HEALTH
    var dmgTeamIndex = -1

    companion object : ComponentType<DamageableComponent>()
}