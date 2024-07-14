package game.fleks.components

import com.github.quillraven.fleks.Component
import com.github.quillraven.fleks.ComponentType

import com.badlogic.gdx.math.Vector2

// TODO: update to Fleks

/*
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
}*/

class ProjectileComponent : Component<ProjectileComponent> {
    override fun type() = ProjectileComponent

    companion object : ComponentType<ProjectileComponent>()
}
