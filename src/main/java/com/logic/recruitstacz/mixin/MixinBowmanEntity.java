package com.logic.recruitstacz.mixin;

import com.logic.recruitstacz.entity.ai.RecruitsTACZStrategicFire;
import com.talhanation.recruits.entities.*;
import com.talhanation.recruits.entities.ai.RecruitStrategicFire;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BowmanEntity.class)
public abstract class MixinBowmanEntity extends AbstractRecruitEntity implements IRangedRecruit, IStrategicFire {

    public MixinBowmanEntity(EntityType<? extends AbstractInventoryEntity> entityType, Level world) {
        super(entityType, world);
    }

    @Inject(at=@At("TAIL"), method = "registerGoals")
    protected void registerGoals(CallbackInfo ci) {
        this.goalSelector.addGoal(2, new RecruitsTACZStrategicFire(((BowmanEntity)(Object)this)));
    }
}
