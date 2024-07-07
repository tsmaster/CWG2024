package game.ecs.systems

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.math.MathUtils
import game.ecs.components.PlayerComponent
import game.ecs.components.RemoveComponent
import game.ecs.components.TargetedSteeringComponent
import game.ecs.components.TransformComponent
import ktx.ashley.allOf
import ktx.ashley.exclude
import ktx.ashley.get
import kotlin.math.abs
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin

const val UPDATE_RATE =  1 / 25.0f

class MoveSystem :
    IteratingSystem(allOf(
        TransformComponent::class,
        TargetedSteeringComponent::class).exclude(RemoveComponent::class).get()) {
    private var accumulator = 0.0f

    override fun update(deltaTime: Float) {
        accumulator += deltaTime
        while (accumulator >= UPDATE_RATE) {
            accumulator -= UPDATE_RATE

            entities.forEach { entity->
                entity[TransformComponent.mapper] ?.let { transform ->
                    transform.previousPosition.set(transform.position)


                }
            }
            super.update(UPDATE_RATE)
        }

        val alpha:Float = accumulator / UPDATE_RATE
        entities.forEach { entity ->
            entity[TransformComponent.mapper]?.let {transform ->
                transform.interpolatedPosition.set(
                    MathUtils.lerp(transform.previousPosition.x, transform.position.x, alpha ),
                    MathUtils.lerp(transform.previousPosition.y, transform.position.y, alpha ),
                    transform.position.z
                )
            }
        }
    }

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val transform = entity[TransformComponent.mapper]
        require(transform != null) { "Entity |entity| must have TransformComponent. entity=$entity" }
        val steeringComponent = entity[TargetedSteeringComponent.mapper]
        require(steeringComponent != null) {"Entity |entity| must have TargetedSteeringComponent. entity=$entity"}

        val playerComponent = entity[PlayerComponent.mapper]
        if (playerComponent != null) {
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
