package com.logic.recruitstacz.mixin.tacz;

import com.tacz.guns.compat.playeranimator.animation.PlayerAnimatorAssetManager;
import dev.kosmx.playerAnim.core.data.KeyframeAnimation;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.HashMap;

@Mixin(PlayerAnimatorAssetManager.class)
public interface AccessorPlayerAnimatorAssetManager {

    @Accessor
    HashMap<ResourceLocation, HashMap<String, KeyframeAnimation>> getAnimations();
}
