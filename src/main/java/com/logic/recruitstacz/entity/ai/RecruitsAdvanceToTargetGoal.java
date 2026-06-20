package com.logic.recruitstacz.entity.ai;

import com.logic.recruitstacz.TACZRecruitsUtils;
import com.logic.recruitstacz.config.TACZRecruitsConfig;
import com.tacz.guns.item.ModernKineticGunItem;
import com.talhanation.recruits.entities.AbstractRecruitEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.Vec3;

public class RecruitsAdvanceToTargetGoal<T extends AbstractRecruitEntity> extends Goal {
    private static final int REVALUATION_DURATION = 10;
    private static final float MAX_SPREAD_DEGREES = 25.0F;

    private final T recruit;
    private LivingEntity target;

    private int revalTicks = 0;

    public RecruitsAdvanceToTargetGoal(T recruit) {
        this.recruit = recruit;
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

    @Override
    public boolean canUse() {
        if (!TACZRecruitsConfig.SHOULD_RECRUITS_ADVANCE_ON_TARGET.get()) return false;
        if (this.recruit.getFollowState() != 0 || this.recruit.getShouldFollow() || this.recruit.getShouldMovePos())  return false;
        if(!(this.recruit.getMainHandItem().getItem() instanceof ModernKineticGunItem)) return false;

        return targetValid();
    }

    private boolean targetValid() {
        this.target = this.recruit.getTarget();

        if(this.target == null || this.target.isDeadOrDying() || !this.recruit.canAttack(this.target)) return false;

        if(!TACZRecruitsUtils.targetHasLineOfSight(this.recruit, this.target)) {
            return true;
        }

        return false;
    }

    @Override
    public boolean canContinueToUse() {
        return targetValid();
    }

    @Override
    public void tick() {
        if(revalTicks <= 0) {
            Vec3 toTarget = this.target.position().subtract(this.recruit.position()).normalize();

            float randomAngle = (this.recruit.getRandom().nextFloat() * 2.0F - 1.0F) * MAX_SPREAD_DEGREES;
            Vec3 spreadDir = toTarget.yRot((float) Math.toRadians(randomAngle));

            Vec3 targetPosition = spreadDir.scale(TACZRecruitsConfig.ADVANCE_DISTANCE.get());

            this.recruit.getNavigation().moveTo((int) targetPosition.x, recruit.level().getHeight(Heightmap.Types.WORLD_SURFACE_WG, (int) targetPosition.x, (int) targetPosition.z), (int) targetPosition.z, 0.85);

            revalTicks = REVALUATION_DURATION;
        }

        revalTicks--;
    }

    @Override
    public void stop() {
        this.recruit.getNavigation().stop();
        revalTicks = 0;
        this.target = null;
        super.stop();
    }
}
