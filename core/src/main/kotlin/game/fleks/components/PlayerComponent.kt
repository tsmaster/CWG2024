package game.fleks.components

import com.github.quillraven.fleks.Component
import com.github.quillraven.fleks.ComponentType

// TODO: update to Fleks

/*
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
*/


class PlayerComponent : Component<PlayerComponent> {
    override fun type() = PlayerComponent

    companion object : ComponentType<PlayerComponent>()
}