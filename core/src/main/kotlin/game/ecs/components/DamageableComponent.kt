package game.ecs.components

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.utils.Pool
import ktx.ashley.mapperFor

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
}
