package game.fleks.components

import com.github.quillraven.fleks.Component
import com.github.quillraven.fleks.ComponentType

import com.badlogic.gdx.math.Vector2

class ProjectileComponent : Component<ProjectileComponent> {
    override fun type() = ProjectileComponent

    var velocity: Vector2 = Vector2(0f,0f)
    var damagePayload: Float = 0.0f
    var lifetimeLeftSeconds: Float = 0.0f
    var teamIndex: Int = -1

    companion object : ComponentType<ProjectileComponent>()
}
