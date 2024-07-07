package game.ecs.components

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.utils.Pool
import ktx.ashley.mapperFor

const val MAX_LIFE = 100.0f;
const val MAX_SHIELD = 100.0f;

class PlayerComponent : Component, Pool.Poolable {
    var life = MAX_LIFE
    var maxLife = MAX_LIFE
    var shield = 0.0f
    var maxShield = MAX_SHIELD
    var distanceTravelled = 0.0f

    override fun reset() {
        life = MAX_LIFE
        maxLife = MAX_LIFE
        shield = 0.0f
        maxShield = MAX_SHIELD
        distanceTravelled = 0.0f
    }

    companion object {
        val mapper = mapperFor<PlayerComponent>()
    }
}
