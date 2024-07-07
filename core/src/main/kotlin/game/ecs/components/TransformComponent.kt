package game.ecs.components

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.ComponentMapper
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.utils.Pool
import ktx.ashley.mapperFor

class TransformComponent : Component, Pool.Poolable, Comparable<TransformComponent> {
    var position = Vector3()
    var previousPosition = Vector3()
    var interpolatedPosition = Vector3()
    var size = Vector2(1.0f, 1.0f)
    var rotationDeg = 0.0f

    override fun reset() {
        position.set(Vector3.Zero)
        previousPosition.set(Vector3.Zero)
        interpolatedPosition.set(Vector3.Zero)
        size.set(1.0f, 1.0f)
        rotationDeg = 0.0f
    }

    fun setInitialPosition(x: Float, y: Float, z: Float) {
        position.set(x, y, z)
        previousPosition.set(x, y, z)
        interpolatedPosition.set(x, y, z)
    }

    override fun compareTo(other: TransformComponent): Int {
        val zDiff = other.position.z.compareTo(position.z)
        val yDiff = other.position.y.compareTo(position.y)
        return if (zDiff == 0) yDiff else zDiff
    }

    companion object {
        val mapper:ComponentMapper<TransformComponent> = mapperFor<TransformComponent>()
    }
}

