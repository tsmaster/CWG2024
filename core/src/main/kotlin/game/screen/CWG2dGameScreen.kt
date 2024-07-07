package game.screen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.utils.viewport.FitViewport
import game.CWGGame
import game.ecs.components.*
import ktx.ashley.entity
import ktx.ashley.get
import ktx.ashley.with
import ktx.graphics.use
import ktx.log.logger
import kotlin.math.min

private val LOG = logger<CWG2dGameScreen>()
private const val MAX_DELTA_TIME = 1/20.0f

class CWG2dGameScreen(game: CWGGame) : CWGScreen(game) {

    //private val car_0_texture = Texture(Gdx.files.internal("sprites/car-white.png"))
    //private val car_1_texture = Texture(Gdx.files.internal("sprites/car-red.png"))
    //private val car_2_texture = Texture(Gdx.files.internal("sprites/car-blue.png"))

    private val carBlueTexture = Texture(Gdx.files.internal("sprites/car_blue_1.png"))
    private val carRedTexture = Texture(Gdx.files.internal("sprites/car_red_1.png"))
    private val carYellowTexture = Texture(Gdx.files.internal("sprites/car_yellow_1.png"))
    private val carGreenTexture = Texture(Gdx.files.internal("sprites/car_green_1.png"))
    private val carBlackTexture = Texture(Gdx.files.internal("sprites/car_black_1.png"))

    // ecs for now


    fun makeCarEntity(isPlayer:Boolean)
    {
        if (isPlayer)
        {
            engine.entity {
                with<TransformComponent> {
                    setInitialPosition(
                        MathUtils.random(0.0f, 16.0f),
                        MathUtils.random(0.0f, 9.0f),
                        0.0f
                    )
                    size.set(0.5f, 1.0f)
                }
                with<GraphicComponent> {
                    sprite.run {
                        setRegion(carBlueTexture)
                        setSize(0.5f, 1.0f)
                        setOriginCenter()
                    }
                }
                with<DamageableComponent> {
                    dmgTeamIndex = 0
                }
                with<TargetedSteeringComponent> {
                    headingDegrees = MathUtils.random(0.0f, 360.0f)
                    turnRate = 35.0f
                    maxAccel = 0.25f
                    maxSpeed = 5.0f
                    hasTarget = false
                }
                with<PlayerComponent> {

                }
                with<WeaponComponent> {
                    weaponType = WeaponType.MachineGun
                    weaponTeamIndex = 0
                    timeToReload = 0.2f;
                }
            }
        }
        else
        {
            val colorPickerRnd = MathUtils.random(0.0f, 1.0f)

            var carTexture  = when {
                (colorPickerRnd < 0.3f) -> carRedTexture
                (colorPickerRnd < 0.6f) -> carYellowTexture
                (colorPickerRnd < 0.9f) -> carGreenTexture
                else -> carBlackTexture
            }

            engine.entity {
                with<TransformComponent> {
                    position.set(
                        MathUtils.random(0.0f, 16.0f),
                        MathUtils.random(0.0f, 9.0f),
                        0.0f)
                    size.set(0.5f, 1.0f)
                }
                with<GraphicComponent> {
                    sprite.run {
                        setRegion(carTexture)
                        setSize(0.5f, 1.0f)
                        setOriginCenter()
                    }
                }
                with<DamageableComponent> {
                    dmgTeamIndex = 1
                }
                with<TargetedSteeringComponent> {
                    headingDegrees = MathUtils.random(0.0f, 360.0f)
                    targetWorldPos.set(
                        MathUtils.random(0.0f, 16.0f),
                        MathUtils.random(0.0f, 9.0f)
                        //8.0f, 4.5f
                    )
                    turnRate = 25.0f
                    maxAccel = 0.25f
                    maxSpeed = 5.0f
                    hasTarget = true
                }
                with<AIControlledComponent> {

                }
                with<WeaponComponent> {
                    weaponType = WeaponType.MachineGun
                    weaponTeamIndex = 1
                    timeToReload = 0.3f;
                }
            }
        }
    }

    override fun show() {
        LOG.debug{ "showing CWG2dGameScreen" }

        makeCarEntity(true)

        repeat(8) {
            makeCarEntity(false)
        }
    }


    override fun render(delta: Float) {
        super.render(delta)

        // avoid "spiral of death"
        engine.update(min(MAX_DELTA_TIME, delta))


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

        super.dispose()
    }

    companion object {
        public val bullet_texture by lazy {Texture(Gdx.files.internal("sprites/bullet.png"))}
    }
}
