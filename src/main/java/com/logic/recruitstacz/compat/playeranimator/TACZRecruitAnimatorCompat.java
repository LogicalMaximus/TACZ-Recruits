package com.logic.recruitstacz.compat.playeranimator;

import com.tacz.guns.client.resource.GunDisplayInstance;
import com.tacz.guns.compat.playeranimator.animation.AnimationDataRegisterFactory;
import com.tacz.guns.compat.playeranimator.animation.AnimationManager;
import com.tacz.guns.compat.playeranimator.animation.PlayerAnimatorAssetManager;
import com.tacz.guns.compat.playeranimator.animation.PlayerAnimatorLoader;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModList;

import java.io.File;
import java.util.function.Consumer;
import java.util.zip.ZipFile;

public class TACZRecruitAnimatorCompat {
    public static ResourceLocation LOWER_ANIMATION = new ResourceLocation("tacz", "lower_animation");
    public static ResourceLocation LOOP_UPPER_ANIMATION = new ResourceLocation("tacz", "loop_upper_animation");
    public static ResourceLocation ONCE_UPPER_ANIMATION = new ResourceLocation("tacz", "once_upper_animation");
    public static ResourceLocation ROTATION_ANIMATION = new ResourceLocation("tacz", "rotation");
    private static final String MOD_ID = "mobplayeranimator";
    private static boolean INSTALLED = false;

    public static void init() {
        INSTALLED = ModList.get().isLoaded(MOD_ID);
        if (isInstalled()) {
            TACZRecruitsAnimationDataRegisterFactory.registerData();
            MinecraftForge.EVENT_BUS.register(new TACZRecruitsAnimationManager());
        }

    }

    public static boolean hasPlayerAnimator3rd(LivingEntity livingEntity, GunDisplayInstance display) {
        return isInstalled() && livingEntity instanceof Mob ? TACZRecruitsAnimationManager.hasPlayerAnimator3rd(display) : false;
    }

    public static void stopAllAnimation(LivingEntity livingEntity) {
        if (isInstalled() && livingEntity instanceof Mob mob) {
            TACZRecruitsAnimationManager.stopAllAnimation(mob);
        }
    }

    public static void stopAllAnimation(LivingEntity livingEntity, int fadeTime) {
        if (isInstalled() && livingEntity instanceof Mob mob) {
            TACZRecruitsAnimationManager.stopAllAnimation(mob, fadeTime);
        }

    }

    public static void playAnimation(LivingEntity livingEntity, GunDisplayInstance display, float limbSwingAmount) {
        if (isInstalled() && livingEntity instanceof Mob mob) {
            TACZRecruitsAnimationManager.playLowerAnimation(mob, display, limbSwingAmount);
            TACZRecruitsAnimationManager.playLoopUpperAnimation(mob, display, limbSwingAmount);
            TACZRecruitsAnimationManager.playRotationAnimation(mob, display);
        }

    }

    public static boolean isInstalled() {
        return INSTALLED;
    }

    public static void registerReloadListener(Consumer<PreparableReloadListener> register) {
        if (isInstalled()) {
            register.accept(PlayerAnimatorAssetManager.get());
        }
    }
}
