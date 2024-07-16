package game.fleks.systems

import com.badlogic.gdx.scenes.scene2d.Stage
import com.github.quillraven.fleks.Entity
import com.github.quillraven.fleks.IteratingSystem
import com.github.quillraven.fleks.World.Companion.family
import com.github.quillraven.fleks.World.Companion.inject
import game.fleks.components.RemoveComponent
import game.fleks.components.SpriteComponent
import game.fleks.components.TransformComponent

// Dummy class used in tutorials, do not use
class DummyTransformSystem (
    private val stage: Stage = inject("world-stage")
) : IteratingSystem (
    family = family { all(TransformComponent).none(RemoveComponent)}
) {
    override fun onTickEntity(entity: Entity) {
        entity[TransformComponent].xy.add(1.0f, 1.0f)
        if (entity has SpriteComponent) {
            entity[SpriteComponent].rotation = 90.0f;
        }
    }
}