package com.logic.recruitstacz.mixin.client;

import com.logic.recruitstacz.compat.playeranimator.TACZRecruitAnimatorCompat;
import com.tacz.guns.api.item.gun.AbstractGunItem;
import com.talhanation.recruits.client.render.RecruitHumanRenderer;
import com.talhanation.recruits.entities.AbstractRecruitEntity;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(RecruitHumanRenderer.class)
public abstract class MixinRecruitHumanRenderer extends MobRenderer<AbstractRecruitEntity, HumanoidModel<AbstractRecruitEntity>> {
    public MixinRecruitHumanRenderer(EntityRendererProvider.Context p_174304_, HumanoidModel<AbstractRecruitEntity> p_174305_, float p_174306_) {
        super(p_174304_, p_174305_, p_174306_);
    }

    @Inject(method = "getArmPose", at =@At("HEAD"), cancellable = true, remap = false)
    private static void getArmPose(AbstractRecruitEntity recruit, InteractionHand hand, CallbackInfoReturnable<HumanoidModel.ArmPose> cir) {
        if(!TACZRecruitAnimatorCompat.isInstalled()) {
            ItemStack stack = recruit.getItemInHand(hand);

            if(stack != null) {
                if(stack.getItem() instanceof AbstractGunItem) {
                    cir.setReturnValue(HumanoidModel.ArmPose.CROSSBOW_HOLD);
                }
            }
        }
    }
}
