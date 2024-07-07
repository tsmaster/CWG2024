package game.ecs.components

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.ComponentMapper
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.Pool
import ktx.ashley.mapperFor

class TargetedSteeringComponent : Component, Pool.Poolable {
    var headingDegrees = 0.0f
    var speed = 0.0f
    var targetWorldPos = Vector2(0.0f, 0.0f)
    var hasTarget = false
    var turnRate = 0.0f
    var maxSpeed = 100.0f;
    var maxAccel = 20.0f;
    var maxBrake = 10.0f;

    override fun reset() {
        headingDegrees = 0.0f
        speed = 0.0f
        targetWorldPos = Vector2(0.0f, 0.0f)
        hasTarget = false
        turnRate = 0.0f
        maxSpeed = 100.0f
        maxAccel = 20.0f
        maxBrake = 50.0f
    }

    companion object {
        val mapper:ComponentMapper<TargetedSteeringComponent> = mapperFor<TargetedSteeringComponent>()
    }
}
