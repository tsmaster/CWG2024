package game.screen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.viewport.ExtendViewport
import com.github.quillraven.fleks.World
import com.github.quillraven.fleks.configureWorld
import game.CWGGame
import game.fleks.components.*
import game.fleks.systems.*
import ktx.log.logger

private const val MAX_DELTA_TIME = 1/20.0f


class CWG2dGameScreenFleks(game: CWGGame) : CWGScreen(game) {

    //private val car_0_texture = Texture(Gdx.files.internal("sprites/car-white.png"))
    //private val car_1_texture = Texture(Gdx.files.internal("sprites/car-red.png"))
    //private val car_2_texture = Texture(Gdx.files.internal("sprites/car-blue.png"))

    private val carBlueTexture = Texture(Gdx.files.internal("sprites/car_blue_1.png"))
    private val carRedTexture = Texture(Gdx.files.internal("sprites/car_red_1.png"))
    private val carYellowTexture = Texture(Gdx.files.internal("sprites/car_yellow_1.png"))
    private val carGreenTexture = Texture(Gdx.files.internal("sprites/car_green_1.png"))
    private val carBlackTexture = Texture(Gdx.files.internal("sprites/car_black_1.png"))

    private val stoneTileTexture = Texture(Gdx.files.internal("sprites/MapTiles/tile_stone.png"))

    private val worldStage = Stage(ExtendViewport(16.0f, 9.0f))
    private val uiStage = Stage(ExtendViewport(1280.0f, 768.0f))

    // see https://github.com/Quillraven/Fleks/wiki/World
    private val fleksWorld:World = configureWorld {
        injectables {
            add("world-stage", worldStage)
            add("ui-stage", uiStage)
            add("spritebatch", batch)
            add("world-viewport", gameViewport)
            add("ui-viewport", uiViewport)
        }

        systems {
            add(PlayerInputSystem())
            add(RenderSystem())
            add(MoveSystem())
            add(ProjectileSystem())
            add(WeaponCooldownSystem())
            add(RemoveSystem())
        }
    }

    // fleks ecs for now

    private fun makeStoneTileEntity(x: Int, y: Int)
    {
        fleksWorld.entity {
            it += TransformComponent(Vector2(x * 1.0f, y * 1.0f)).apply {
                size = Vector2(2.0f, 2.0f)
            }
            it += GraphicComponent(sprite = Sprite(stoneTileTexture)).apply {
                sprite.setSize(2.0f, 2.0f)
            }
        }
    }

    private fun makeCarEntity(isPlayer:Boolean)
    {
        if (isPlayer)
        {
            fleksWorld.entity {
                it += TransformComponent(Vector2(
                    com.badlogic.gdx.math.MathUtils.random(0.0f, 16.0f),
                    com.badlogic.gdx.math.MathUtils.random(0.0f, 9.0f),
                )).apply {
                    size.set(0.5f, 1.0f)
                }
                it += GraphicComponent(Sprite(carBlueTexture)).apply {
                    sprite.setRegion(carBlueTexture)
                    sprite.setSize(0.5f, 1.0f)
                    sprite.setOriginCenter()
                }
                it += DamageableComponent().apply {
                    dmgTeamIndex = 0
                }
                it += TargetedSteeringComponent().apply {
                    headingDegrees = com.badlogic.gdx.math.MathUtils.random(0.0f, 360.0f)
                    turnRate = 35.0f
                    maxAccel = 0.25f
                    maxSpeed = 5.0f
                    hasTarget = false
                }
                it += PlayerComponent()
                it += WeaponComponent().apply {
                    weaponType = WeaponType.MachineGun
                    weaponTeamIndex = 0
                    timeToReload = 0.2f
                }
            }
        }
        else
        {
            val colorPickerRnd = MathUtils.random(0.0f, 1.0f)

            val carTexture  = when {
                (colorPickerRnd < 0.3f) -> carRedTexture
                (colorPickerRnd < 0.6f) -> carYellowTexture
                (colorPickerRnd < 0.9f) -> carGreenTexture
                else -> carBlackTexture
            }

            fleksWorld.entity {
                it += TransformComponent(Vector2(
                    MathUtils.random(0.0f, 16.0f),
                    MathUtils.random(0.0f, 9.0f))
                ).apply {
                    size.set(0.5f, 1.0f)
                }
                it += GraphicComponent(Sprite(carTexture)).apply {
                    sprite.setSize(0.5f, 1.0f)
                    sprite.setOriginCenter()
                }
                it += DamageableComponent().apply {
                    dmgTeamIndex = 1
                }
                it += TargetedSteeringComponent().apply {
                    headingDegrees = MathUtils.random(0.0f, 360.0f)
                    targetWorldPos.set(
                        MathUtils.random(0.0f, 16.0f),
                        MathUtils.random(0.0f, 9.0f)
                    )
                    turnRate = 25.0f
                    maxAccel = 0.25f
                    maxSpeed = 5.0f
                    hasTarget = true
                }
                it += AIControlledComponent()
                it += WeaponComponent().apply {
                    weaponType = WeaponType.MachineGun
                    weaponTeamIndex = 1
                    timeToReload = 0.3f
                }
            }
        }
    }

    override fun show() {
        log.info { "showing CWG2dGameScreenFleks" }

        for (xi in 0..16 step 2) {
            for (yi in 0 .. 9 step 2) {
                makeStoneTileEntity(xi, yi)
            }
        }
        makeCarEntity(true)

        repeat(8) {
            makeCarEntity(false)
        }
    }

    override fun render(delta: Float) {
        super.render(delta)

        fleksWorld.update(delta)

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            game.setScreen<CWGMenuScreen>()
        }
    }

    override fun dispose() {
        carBlueTexture.dispose()
        carRedTexture.dispose()
        carYellowTexture.dispose()
        carGreenTexture.dispose()
        carBlackTexture.dispose()
        bullet_texture.dispose()
        stoneTileTexture.dispose()

        fleksWorld.dispose()
        super.dispose()
    }

    companion object {
        val bullet_texture by lazy { Texture(Gdx.files.internal("sprites/bullet.png")) }
        private val log = logger<CWG2dGameScreenFleks>()
    }
}