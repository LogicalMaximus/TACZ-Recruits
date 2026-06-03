package com.logic.recruitstacz.mixin;

import com.tacz.guns.api.entity.IGunOperator;
import com.tacz.guns.api.item.IGun;
import com.talhanation.recruits.entities.AbstractRecruitEntity;
import com.talhanation.recruits.util.AttackUtil;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AttackUtil.class)
public class MixinAttackUtil {

    @Inject(at=@At("HEAD"), method = "performAttack", remap = false, cancellable = true)
    private static void performAttack(AbstractRecruitEntity recruit, LivingEntity target, CallbackInfo ci) {
        if (recruit.attackCooldown == 0 && !recruit.swinging && recruit.getLookControl().isLookingAtTarget()) {
            if(recruit.getMainHandItem().getItem() instanceof IGun) {
                ((IGunOperator)recruit).melee();
                recruit.attackCooldown = getAttackCooldown(recruit);
                ci.cancel();
            }
        }
    }

    @Shadow(remap = false)
    public static int getAttackCooldown(AbstractRecruitEntity recruit) {
        throw new AssertionError();
    }
}
