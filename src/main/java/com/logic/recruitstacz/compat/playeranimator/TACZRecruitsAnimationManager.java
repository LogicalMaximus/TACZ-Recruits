package com.logic.recruitstacz.compat.playeranimator;

import com.logic.recruitstacz.mixin.tacz.AccessorPlayerAnimatorAssetManager;
import com.tacz.guns.api.TimelessAPI;
import com.tacz.guns.api.entity.IGunOperator;
import com.tacz.guns.api.event.common.GunDrawEvent;
import com.tacz.guns.api.event.common.GunMeleeEvent;
import com.tacz.guns.api.event.common.GunReloadEvent;
import com.tacz.guns.api.event.common.GunShootEvent;
import com.tacz.guns.api.item.IGun;
import com.tacz.guns.client.resource.GunDisplayInstance;
import com.tacz.guns.compat.playeranimator.animation.PlayerAnimatorAssetManager;
import dev.kosmx.playerAnim.api.layered.IAnimation;
import dev.kosmx.playerAnim.api.layered.KeyframeAnimationPlayer;
import dev.kosmx.playerAnim.api.layered.ModifierLayer;
import dev.kosmx.playerAnim.api.layered.modifier.AbstractFadeModifier;
import dev.kosmx.playerAnim.core.data.KeyframeAnimation;
import dev.kosmx.playerAnim.core.util.Ease;
import me.Thelnfamous1.mobplayeranimator.api.MobAnimationAccess;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.HashMap;
import java.util.Optional;

public class TACZRecruitsAnimationManager {

    public static boolean isFlying(Mob mob) {
        return !mob.onGround();
    }

    public static boolean hasPlayerAnimator3rd(GunDisplayInstance display) {
        ResourceLocation location = display.getPlayerAnimator3rd();
        return location == null ? false : PlayerAnimatorAssetManager.get().containsKey(location);
    }

    public static void playRotationAnimation(Mob mob, GunDisplayInstance display) {
        String animationName = "empty";
        ResourceLocation dataId = TACZRecruitAnimatorCompat.ROTATION_ANIMATION;
        ResourceLocation animator3rd = display.getPlayerAnimator3rd();
        if (animator3rd != null) {
            if (PlayerAnimatorAssetManager.get().containsKey(animator3rd)) {
                getAnimations(((AccessorPlayerAnimatorAssetManager)PlayerAnimatorAssetManager.get()).getAnimations(), animator3rd, animationName).ifPresent((keyframeAnimation) -> {
                    MobAnimationAccess.MobAssociatedAnimationData associatedData = MobAnimationAccess.getMobAssociatedData(mob);
                    ModifierLayer<IAnimation> modifierLayer = (ModifierLayer)associatedData.get(dataId);
                    if (modifierLayer != null) {
                        AbstractFadeModifier fadeModifier = AbstractFadeModifier.standardFadeIn(8, Ease.INOUTSINE);
                        modifierLayer.replaceAnimationWithFade(fadeModifier, new KeyframeAnimationPlayer(keyframeAnimation));
                    }
                });
            }
        }
    }

    private static Optional<KeyframeAnimation> getAnimations(HashMap<ResourceLocation, HashMap<String, KeyframeAnimation>> animations, ResourceLocation id, String name) {
        HashMap<String, KeyframeAnimation> animationHashMap = (HashMap)animations.get(id);
        return animationHashMap == null ? Optional.empty() : Optional.ofNullable((KeyframeAnimation)animationHashMap.get(name));
    }

    public static void playLowerAnimation(Mob mob, GunDisplayInstance display, float limbSwingAmount) {
        if (!isPlayerLie(mob)) {
            if (mob.getVehicle() != null) {
                playLoopAnimation(mob, display, TACZRecruitAnimatorCompat.LOWER_ANIMATION, "ride_lower");
            } else if (isFlying(mob)) {
                playLoopAnimation(mob, display, TACZRecruitAnimatorCompat.LOWER_ANIMATION, "hold_lower");
            } else if (mob.isSprinting()) {
                if (mob.getPose() == Pose.CROUCHING) {
                    playLoopAnimation(mob, display, TACZRecruitAnimatorCompat.LOWER_ANIMATION, "crouch_walk_lower");
                } else {
                    playLoopAnimation(mob, display, TACZRecruitAnimatorCompat.LOWER_ANIMATION, "run_lower");
                }

            } else if ((double)limbSwingAmount > 0.05) {
                if (mob.getPose() == Pose.CROUCHING) {
                    playLoopAnimation(mob, display, TACZRecruitAnimatorCompat.LOWER_ANIMATION, "crouch_walk_lower");
                } else {
                    playLoopAnimation(mob, display, TACZRecruitAnimatorCompat.LOWER_ANIMATION, "walk_lower");
                }

            } else {
                if (mob.getPose() == Pose.CROUCHING) {
                    playLoopAnimation(mob, display, TACZRecruitAnimatorCompat.LOWER_ANIMATION, "crouch_lower");
                } else {
                    playLoopAnimation(mob, display, TACZRecruitAnimatorCompat.LOWER_ANIMATION, "hold_lower");
                }

            }
        }
    }

    public static void playLoopUpperAnimation(Mob mob, GunDisplayInstance display, float limbSwingAmount) {
        IGunOperator operator = IGunOperator.fromLivingEntity(mob);
        float aimingProgress = operator.getSynAimingProgress();
        if (aimingProgress <= 0.0F) {
            if (!isFlying(mob) && mob.isSprinting()) {
                if (isPlayerLie(mob)) {
                    playLoopAnimation(mob, display, TACZRecruitAnimatorCompat.LOOP_UPPER_ANIMATION, "lie_move");
                } else if (mob.getPose() == Pose.CROUCHING) {
                    playLoopAnimation(mob, display, TACZRecruitAnimatorCompat.LOOP_UPPER_ANIMATION, "crouch_walk_upper");
                } else {
                    playLoopAnimation(mob, display, TACZRecruitAnimatorCompat.LOOP_UPPER_ANIMATION, "run_upper");
                }

                return;
            }

            if (!isFlying(mob) && (double)limbSwingAmount > 0.05) {
                if (isPlayerLie(mob)) {
                    playLoopAnimation(mob, display, TACZRecruitAnimatorCompat.LOOP_UPPER_ANIMATION, "lie_move");
                } else if (mob.getPose() == Pose.CROUCHING) {
                    playLoopAnimation(mob, display, TACZRecruitAnimatorCompat.LOOP_UPPER_ANIMATION, "crouch_walk_upper");
                } else {
                    playLoopAnimation(mob, display, TACZRecruitAnimatorCompat.LOOP_UPPER_ANIMATION, "walk_upper");
                }

                return;
            }

            if (isPlayerLie(mob)) {
                playLoopAnimation(mob, display, TACZRecruitAnimatorCompat.LOOP_UPPER_ANIMATION, "lie");
            } else {
                playLoopAnimation(mob, display, TACZRecruitAnimatorCompat.LOOP_UPPER_ANIMATION, "hold_upper");
            }
        } else if (isPlayerLie(mob)) {
            if (!isFlying(mob) && (double)limbSwingAmount > 0.05) {
                playLoopAnimation(mob, display, TACZRecruitAnimatorCompat.LOOP_UPPER_ANIMATION, "lie_move");
            } else {
                playLoopAnimation(mob, display, TACZRecruitAnimatorCompat.LOOP_UPPER_ANIMATION, "lie_aim");
            }
        } else {
            playLoopAnimation(mob, display, TACZRecruitAnimatorCompat.LOOP_UPPER_ANIMATION, "aim_upper");
        }

    }

    public static void playLoopAnimation(Mob mob, GunDisplayInstance display, ResourceLocation dataId, String animationName) {
        ResourceLocation animator3rd = display.getPlayerAnimator3rd();
        if (animator3rd != null) {
            if (PlayerAnimatorAssetManager.get().containsKey(animator3rd)) {
                getAnimations(((AccessorPlayerAnimatorAssetManager)PlayerAnimatorAssetManager.get()).getAnimations(), animator3rd, animationName).ifPresent((keyframeAnimation) -> {
                    MobAnimationAccess.MobAssociatedAnimationData associatedData = MobAnimationAccess.getMobAssociatedData(mob);
                    ModifierLayer<IAnimation> modifierLayer = (ModifierLayer)associatedData.get(dataId);
                    if (modifierLayer != null) {
                        IAnimation patt8115$temp = modifierLayer.getAnimation();
                        if (patt8115$temp instanceof KeyframeAnimationPlayer) {
                            KeyframeAnimationPlayer animationPlayer = (KeyframeAnimationPlayer)patt8115$temp;
                            if (animationPlayer.isActive()) {
                                Object extraDataName = animationPlayer.getData().extraData.get("name");
                                if (extraDataName instanceof String) {
                                    String name = (String)extraDataName;
                                    if (!animationName.equals(name)) {
                                        AbstractFadeModifier fadeModifier = AbstractFadeModifier.standardFadeIn(8, Ease.INOUTSINE);
                                        modifierLayer.replaceAnimationWithFade(fadeModifier, new KeyframeAnimationPlayer(keyframeAnimation));
                                    }
                                }

                                return;
                            }
                        }

                        AbstractFadeModifier fadeModifier = AbstractFadeModifier.standardFadeIn(8, Ease.INOUTSINE);
                        modifierLayer.replaceAnimationWithFade(fadeModifier, new KeyframeAnimationPlayer(keyframeAnimation));
                    }
                });
            }
        }
    }

    public static void playOnceAnimation(Mob mob, GunDisplayInstance display, ResourceLocation dataId, String animationName) {
        ResourceLocation animator3rd = display.getPlayerAnimator3rd();
        if (animator3rd != null) {
            if (PlayerAnimatorAssetManager.get().containsKey(animator3rd)) {
                getAnimations(((AccessorPlayerAnimatorAssetManager)PlayerAnimatorAssetManager.get()).getAnimations(), animator3rd, animationName).ifPresent((keyframeAnimation) -> {
                    MobAnimationAccess.MobAssociatedAnimationData associatedData = MobAnimationAccess.getMobAssociatedData(mob);
                    ModifierLayer<IAnimation> modifierLayer = (ModifierLayer)associatedData.get(dataId);
                    if (modifierLayer != null) {
                        IAnimation animation = modifierLayer.getAnimation();
                        if (animation == null || !animation.isActive()) {
                            AbstractFadeModifier fadeModifier = AbstractFadeModifier.standardFadeIn(8, Ease.INOUTSINE);
                            modifierLayer.replaceAnimationWithFade(fadeModifier, new KeyframeAnimationPlayer(keyframeAnimation));
                        }

                    }
                });
            }
        }
    }

    public static void stopAllAnimation(Mob mob) {
        stopAllAnimation(mob, 8);
    }

    public static void stopAllAnimation(Mob mob, int fadeTime) {
        stopAnimation(mob, TACZRecruitAnimatorCompat.LOWER_ANIMATION, fadeTime);
        stopAnimation(mob, TACZRecruitAnimatorCompat.LOOP_UPPER_ANIMATION, fadeTime);
        stopAnimation(mob, TACZRecruitAnimatorCompat.ONCE_UPPER_ANIMATION, fadeTime);
        stopAnimation(mob, TACZRecruitAnimatorCompat.ROTATION_ANIMATION, fadeTime);
    }

    private static void stopAnimation(Mob mob, ResourceLocation dataId, int fadeTime) {
        MobAnimationAccess.MobAssociatedAnimationData associatedData = MobAnimationAccess.getMobAssociatedData(mob);
        ModifierLayer<IAnimation> modifierLayer = (ModifierLayer)associatedData.get(dataId);
        if (modifierLayer != null && modifierLayer.isActive()) {
            AbstractFadeModifier fadeModifier = AbstractFadeModifier.standardFadeIn(fadeTime, Ease.INOUTSINE);
            modifierLayer.replaceAnimationWithFade(fadeModifier, (IAnimation)null);
        }

    }

    private static boolean isPlayerLie(Mob mob) {
        return !mob.isSwimming() && mob.getPose() == Pose.SWIMMING;
    }

    @SubscribeEvent
    public void onFire(GunShootEvent event) {
        if (!event.getLogicalSide().isServer()) {
            LivingEntity shooter = event.getShooter();
            if (shooter instanceof Mob) {
                Mob mob = (Mob) shooter;
                ItemStack gunItemStack = event.getGunItemStack();
                IGun iGun = IGun.getIGunOrNull(gunItemStack);
                if (iGun != null) {
                    TimelessAPI.getGunDisplay(gunItemStack).ifPresent((index) -> {
                        IGunOperator operator = IGunOperator.fromLivingEntity(mob);
                        float aimingProgress = operator.getSynAimingProgress();
                        if (aimingProgress <= 0.0F) {
                            if (isPlayerLie(mob)) {
                                playOnceAnimation(mob, index, TACZRecruitAnimatorCompat.ONCE_UPPER_ANIMATION, "lie_normal_fire");
                            } else {
                                playOnceAnimation(mob, index, TACZRecruitAnimatorCompat.ONCE_UPPER_ANIMATION, "normal_fire_upper");
                            }
                        } else if (isPlayerLie(mob)) {
                            playOnceAnimation(mob, index, TACZRecruitAnimatorCompat.ONCE_UPPER_ANIMATION, "lie_aim_fire");
                        } else {
                            playOnceAnimation(mob, index, TACZRecruitAnimatorCompat.ONCE_UPPER_ANIMATION, "aim_fire_upper");
                        }

                    });
                }
            }
        }
    }

    @SubscribeEvent
    public void onReload(GunReloadEvent event) {
        if (!event.getLogicalSide().isServer()) {
            LivingEntity shooter = event.getEntity();
            if (shooter instanceof Mob) {
                Mob mob = (Mob) shooter;
                ItemStack gunItemStack = event.getGunItemStack();
                IGun iGun = IGun.getIGunOrNull(gunItemStack);
                if (iGun != null) {
                    TimelessAPI.getGunDisplay(gunItemStack).ifPresent((index) -> {
                        if (isPlayerLie(mob)) {
                            playOnceAnimation(mob, index, TACZRecruitAnimatorCompat.ONCE_UPPER_ANIMATION, "lie_reload");
                        } else {
                            playOnceAnimation(mob, index, TACZRecruitAnimatorCompat.ONCE_UPPER_ANIMATION, "reload_upper");
                        }

                    });
                }
            }
        }
    }

    @SubscribeEvent
    public void onMelee(GunMeleeEvent event) {
        if (!event.getLogicalSide().isServer()) {
            LivingEntity shooter = event.getShooter();
            if (shooter instanceof Mob) {
                Mob mob = (Mob) shooter;
                ItemStack gunItemStack = event.getGunItemStack();
                IGun iGun = IGun.getIGunOrNull(gunItemStack);
                if (iGun != null) {
                    int randomIndex = shooter.getRandom().nextInt(3);
                    String var10000;
                    switch (randomIndex) {
                        case 0 -> var10000 = "melee_upper";
                        case 1 -> var10000 = "melee_2_upper";
                        default -> var10000 = "melee_3_upper";
                    }

                    String animationName = var10000;
                    TimelessAPI.getGunDisplay(gunItemStack).ifPresent((index) -> playOnceAnimation(mob, index, TACZRecruitAnimatorCompat.ONCE_UPPER_ANIMATION, animationName));
                }
            }
        }
    }

    @SubscribeEvent
    public void onDraw(GunDrawEvent event) {
        if (!event.getLogicalSide().isServer()) {
            LivingEntity entity = event.getEntity();
            if (entity instanceof Mob) {
                Mob mob = (Mob) entity;
                ItemStack currentGunItem = event.getCurrentGunItem();
                ItemStack previousGunItem = event.getPreviousGunItem();
                if (currentGunItem.getItem() instanceof IGun && previousGunItem.getItem() instanceof IGun) {
                    stopAnimation(mob, TACZRecruitAnimatorCompat.LOOP_UPPER_ANIMATION, 8);
                    stopAnimation(mob, TACZRecruitAnimatorCompat.ONCE_UPPER_ANIMATION, 8);
                    stopAnimation(mob, TACZRecruitAnimatorCompat.LOWER_ANIMATION, 8);
                }
            }
        }
    }
}
