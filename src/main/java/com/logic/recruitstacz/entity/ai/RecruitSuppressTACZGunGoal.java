package com.logic.recruitstacz.entity.ai;

import com.logic.recruitstacz.TACZRecruits;
import com.logic.recruitstacz.TACZRecruitsUtils;
import com.logic.recruitstacz.bridge.ISpotter;
import com.logic.recruitstacz.config.TACZRecruitsConfig;
import com.tacz.guns.api.entity.IGunOperator;
import com.tacz.guns.api.entity.ShootResult;
import com.tacz.guns.api.item.GunTabType;
import com.tacz.guns.api.item.IGun;
import com.talhanation.recruits.entities.AbstractRecruitEntity;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class RecruitSuppressTACZGunGoal extends Goal {
    private static final double MIN_ANGLE_RAD = Math.toRadians(3.0);   // tight when close
    private static final double MAX_ANGLE_RAD = Math.toRadians(7.5);  // laxer when far
    private static final double MIN_DISTANCE  = 2.0;
    private static final double MAX_DISTANCE  = 20.0;

    private final AbstractRecruitEntity recruit;
    private LivingEntity target;
    private int cooldown = 0;
    private TargetMarker lastKnownEnemyPos;
    private Path path;

    public RecruitSuppressTACZGunGoal(AbstractRecruitEntity recruit) {
        this.recruit = recruit;
    }

    @Override
    public boolean canUse() {
        if(!(TACZRecruitsConfig.SHOULD_RECRUITS_USE_SUPPRESSIVE_FIRE.get()))
            return false;

        if(this.recruit.getVehicle() != null && !TACZRecruitsConfig.SHOULD_RECRUITS_USE_WEAPON_WHEN_MOUNTED.get())
            return false;

        ItemStack mainHandItem = this.recruit.getMainHandItem();

        if(!(mainHandItem.getItem() instanceof IGun))
            return false;

        if(!TACZRecruitsUtils.shouldSuppressiveFire(mainHandItem))
            return false;

        this.target = this.recruit.getTarget();

        if(this.target != null) {
            boolean shouldRanged = this.recruit.getShouldRanged();
            boolean canAttack = this.recruit.canAttack(this.target);
            boolean notPassive = this.recruit.getState() != 3;

            if(shouldRanged && canAttack && notPassive && !this.recruit.getSensing().hasLineOfSight(this.target)) {
                TargetMarker lastKnownEnemyPos = ((ISpotter) this.recruit).getLastKnownEnemyPos();

                if(lastKnownEnemyPos != null && lastKnownEnemyPos.target() == this.target.getUUID()) {
                    this.lastKnownEnemyPos = lastKnownEnemyPos;
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public void start() {
        super.start();
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

    @Override
    public void stop() {
        ((IGunOperator)recruit).aim(false);
        super.stop();
    }

    @Override
    public void tick() {
        Vec3 lastKnownLocation = this.lastKnownEnemyPos.lastKnownLocation();

        if (this.hasLineOfSight(lastKnownLocation)) {
            if(this.path != null) {
                this.recruit.getNavigation().stop();
                this.path = null;
            }

            this.recruit.lookAt(EntityAnchorArgument.Anchor.EYES, TACZRecruitsUtils.randomizeVector(lastKnownLocation, TACZRecruitsUtils.getGunInaccuracy(this.recruit.getMainHandItem()) * TACZRecruitsConfig.SUPPRESSIVE_FIRE_INACCURACY_MULTIPLIER.get()));

            if(this.cooldown <= 0) {
                this.cooldown = TACZRecruitsUtils.recruitShootGun(this.recruit);
            }
        } else {
            BlockPos blockPos = new BlockPos((int) lastKnownLocation.x(), (int) lastKnownLocation.y(), (int) lastKnownLocation.z());

            if(this.path == null || !this.path.getTarget().equals(blockPos)) {
                this.path = this.recruit.getNavigation().createPath(blockPos, 1);
            }

            this.recruit.getNavigation().moveTo(this.path, 0.85);
        }

        this.cooldown--;
        super.tick();
    }

    private boolean hasLineOfSight(Vec3 target) {
        Vec3 vec3 = new Vec3(this.recruit.getX(), this.recruit.getEyeY(), this.recruit.getZ());
        Vec3i vec31 = new Vec3i((int) target.x(), (int) target.y(), (int) target.z());

        return Math.sqrt(this.recruit.level().clip(new ClipContext(vec3, target, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this.recruit)).getBlockPos().distSqr(vec31)) <= 5;
    }


}
