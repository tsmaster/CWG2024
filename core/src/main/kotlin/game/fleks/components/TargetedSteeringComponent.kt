package game.fleks.components

import com.github.quillraven.fleks.Component
import com.github.quillraven.fleks.ComponentType
import com.badlogic.gdx.math.Vector2

class TargetedSteeringComponent : Component<TargetedSteeringComponent> {
    override fun type() = TargetedSteeringComponent

    var headingDegrees = 0.0f
    var speed = 0.0f
    var targetWorldPos = Vector2(0.0f, 0.0f)
    var hasTarget = false
    var turnRate = 0.0f
    var maxSpeed = 100.0f;
    var maxAccel = 20.0f;
    var maxBrake = 10.0f;

    companion object : ComponentType<TargetedSteeringComponent>()
}
