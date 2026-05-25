package com.logic.recruitstacz.mixin;

import com.google.common.collect.ImmutableMap;
import com.logic.recruitstacz.bridge.IPoser;
import com.logic.recruitstacz.bridge.ISwapper;
import com.logic.recruitstacz.entity.ai.*;
import com.tacz.guns.api.entity.IGunOperator;
import com.tacz.guns.api.item.IGun;
import com.talhanation.recruits.entities.AbstractInventoryEntity;
import com.talhanation.recruits.entities.AbstractRecruitEntity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(AbstractRecruitEntity.class)
public abstract class MixinAbstractRecruitEntity extends AbstractInventoryEntity implements IPoser, ISwapper {

    @Unique
    private static final EntityDimensions STANDING_DIMENSIONS = EntityDimensions.scalable(0.6F, 1.8F);

    @Unique
    private static final Map<Pose, EntityDimensions> POSES = ImmutableMap.<Pose, EntityDimensions>builder().put(Pose.STANDING, STANDING_DIMENSIONS).put(Pose.SLEEPING, SLEEPING_DIMENSIONS).put(Pose.FALL_FLYING, EntityDimensions.scalable(0.6F, 0.6F)).put(Pose.SWIMMING, EntityDimensions.scalable(0.6F, 0.6F)).put(Pose.SPIN_ATTACK, EntityDimensions.scalable(0.6F, 0.6F)).put(Pose.CROUCHING, EntityDimensions.scalable(0.6F, 1.5F)).put(Pose.DYING, EntityDimensions.fixed(0.2F, 0.2F)).build();

    @Unique
    private int poseCooldown;

    @Unique
    private int weaponSwitchCooldown;

    public MixinAbstractRecruitEntity(EntityType<? extends AbstractInventoryEntity> entityType, Level world) {
        super(entityType, world);
    }

    @Inject(at=@At("TAIL"), method = "registerGoals")
    protected void registerGoals(CallbackInfo ci) {
        this.goalSelector.addGoal(2, new RecruitShootTACZGunGoal(((AbstractRecruitEntity)(Object)this)));
        this.goalSelector.addGoal(3, new RecruitsFindCoverFromTargetGoal<>(((AbstractRecruitEntity)(Object)this), 1.25));
        this.goalSelector.addGoal(3, new RecruitsChangePoseGoal<>(((AbstractRecruitEntity)(Object)this)));
        this.goalSelector.addGoal(3, new RecruitSuppressTACZGunGoal(((AbstractRecruitEntity)(Object)this)));
        this.goalSelector.addGoal(3, new RecruitWeaponSwitch(((AbstractRecruitEntity)(Object)this)));
    }

    /**
     * @author
     * @reason
     */
    @Overwrite(remap=false)
    public double getMeleeStartRange() {
        if(this.getMainHandItem().getItem() instanceof IGun) {
            return (double)3.0F;
        } else {
            return 32;
        }
    }

    /**
     * @author
     * @reason
     */
    @Overwrite(remap=false)
    public float getStandingEyeHeight(Pose p_36259_, EntityDimensions p_36260_) {
        switch (p_36259_) {
            case SWIMMING:
            case FALL_FLYING:
            case SPIN_ATTACK:
                return 0.4F;
            case CROUCHING:
                return 1.27F;
            default:
                return p_36260_.height * 0.98F;
        }
    }

    @Override
    public void setWeaponSwitchCooldown(int weaponSwitchCooldown) {
        this.weaponSwitchCooldown = weaponSwitchCooldown;
    }

    @Override
    public int getWeaponSwitchCooldown() {
        return weaponSwitchCooldown;
    }

    @Inject(method = "tick", at = @At("TAIL"))
    public void tick(CallbackInfo ci) {
        poseCooldown--;
        weaponSwitchCooldown--;
    }

    @Override
    public void changePose() {
        if(this.getPose() == Pose.STANDING) {
            this.setPose(Pose.CROUCHING);
        } else {
            this.setPose(Pose.STANDING);
        }
    }

    @Override
    public void setPoseCooldown(int poseCooldown) {
        this.poseCooldown = poseCooldown;
    }

    @Override
    public int getPoseCooldown() {
        return poseCooldown;
    }
}
