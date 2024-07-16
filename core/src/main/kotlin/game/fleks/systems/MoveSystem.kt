package game.fleks.systems

import com.badlogic.gdx.math.MathUtils
import com.github.quillraven.fleks.Entity
import com.github.quillraven.fleks.World.Companion.family
import game.fleks.components.TransformComponent
import game.fleks.components.PlayerComponent
import game.fleks.components.TargetedSteeringComponent
import kotlin.math.abs
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin

class MoveSystem () : com.github.quillraven.fleks.IteratingSystem (
    family = family { all(TargetedSteeringComponent, game.fleks.components.TransformComponent)}
) {
    override fun onTickEntity(entity: Entity) {
        val transform = entity[TransformComponent]
        val steeringComponent = entity[TargetedSteeringComponent]

        if (entity.has(PlayerComponent)) {
            // do player movement
            val reachedTarget = moveEntity(transform, steeringComponent, deltaTime)
            if (reachedTarget) {
                steeringComponent.hasTarget = false
            }
        } else {
            // do AI movement
            val reachedTarget = moveEntity(transform, steeringComponent, deltaTime)
            if (reachedTarget) {
                pickTarget(steeringComponent)
            }
        }
    }

    private fun moveEntity(
        transformComponent: TransformComponent,
        steeringComponent: TargetedSteeringComponent,
        deltaTime: Float) : Boolean {
        val movementThisTick = steeringComponent.speed * deltaTime
        val headingRadians = bdg.Math.mapval(steeringComponent.headingDegrees, 0.0f, 360.0f, 0.0f, 2*Math.PI.toFloat()).toDouble()

        val dispX = movementThisTick * cos(headingRadians).toFloat()
        val dispY = movementThisTick * sin(headingRadians).toFloat()

        transformComponent.position.x += dispX
        transformComponent.position.y += dispY

        if (transformComponent.position.x < 0.0f || transformComponent.position.x > 16.0f ||
            transformComponent.position.y < 0.0f || transformComponent.position.y > 9.0f) {
            steeringComponent.speed = 0.0f
            transformComponent.position.x = bdg.Math.clamp(transformComponent.position.x, 0.0f, 16.0f)
            transformComponent.position.y = bdg.Math.clamp(transformComponent.position.y, 0.0f, 9.0f)
        }

        if (!steeringComponent.hasTarget) {
            steeringComponent.speed = bdg.Math.clamp(steeringComponent.speed - steeringComponent.maxBrake * deltaTime, 0.0f, steeringComponent.maxSpeed)

            if (steeringComponent.speed <= 0.0f) {
                return false
            }
        }

        val deltaX = steeringComponent.targetWorldPos.x - transformComponent.position.x
        val deltaY = steeringComponent.targetWorldPos.y - transformComponent.position.y

        val distSqr = deltaX * deltaX + deltaY * deltaY
        if (distSqr < 1.5f) {
            return true
        }

        val angleToTargetRad = atan2(deltaY.toDouble(), deltaX.toDouble()).toFloat()
        val angleToTargetDeg = bdg.Math.radiansToDegrees(angleToTargetRad)

        val maxTurn = steeringComponent.turnRate * deltaTime

        val relAngle = bdg.Math.wrap(angleToTargetDeg - steeringComponent.headingDegrees, 180.0f)

        val clampedRelAngle = bdg.Math.clamp(relAngle, -maxTurn, maxTurn)

        steeringComponent.headingDegrees += clampedRelAngle

        transformComponent.rotationDeg = steeringComponent.headingDegrees - 90

        if (abs(relAngle) < 90.0f) {
            if (steeringComponent.speed < steeringComponent.maxSpeed)
            {
                steeringComponent.speed = bdg.Math.clamp(steeringComponent.speed + steeringComponent.maxAccel * deltaTime, 0.0f, steeringComponent.maxSpeed)
            }
        } else {
            steeringComponent.speed = bdg.Math.clamp(steeringComponent.speed - steeringComponent.maxBrake * deltaTime, 0.0f, steeringComponent.maxSpeed)
        }
        return false
    }

    private fun pickTarget(steeringComponent: TargetedSteeringComponent) {
        steeringComponent.targetWorldPos.set(
            MathUtils.random(0.0f, 16.0f),
            MathUtils.random(0.0f, 9.0f)
        )
    }
}