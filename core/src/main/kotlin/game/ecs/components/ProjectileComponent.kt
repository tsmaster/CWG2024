package game.ecs.components

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.Pool
import ktx.ashley.mapperFor

class ProjectileComponent: Component, Pool.Poolable
{
    var velocity: Vector2 = Vector2(0f,0f)
    var damagePayload: Float = 0.0f
    var lifetimeLeftSeconds: Float = 0.0f
    var teamIndex: Int = -1

    override fun reset() {
        velocity = Vector2(0f,0f)
        damagePayload = 0.0f
        lifetimeLeftSeconds = 0.0f
        teamIndex = -1
    }

    companion object {
        val mapper = mapperFor<ProjectileComponent>()
    }
}
