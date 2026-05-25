package com.logic.recruitstacz.mixin.tacz;

import com.tacz.guns.entity.shooter.LivingEntityAmmoCheck;
import com.talhanation.recruits.config.RecruitsServerConfig;
import com.talhanation.recruits.entities.AbstractRecruitEntity;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntityAmmoCheck.class)
public class MixinLivingEntityAmmoCheck {
    @Shadow(remap = false)
    private LivingEntity shooter;

    @Inject(at=@At("HEAD"), method = "needCheckAmmo", cancellable = true, remap = false)
    public void needCheckAmmo(CallbackInfoReturnable<Boolean> cir) {
        if(shooter instanceof AbstractRecruitEntity && !RecruitsServerConfig.RangedRecruitsNeedArrowsToShoot.get()) {
            cir.setReturnValue(false);
        }
    }

}
