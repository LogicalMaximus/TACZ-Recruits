package com.logic.recruitstacz.entity.ai;

import com.logic.recruitstacz.bridge.IPoser;
import com.logic.recruitstacz.config.TACZRecruitsConfig;
import com.tacz.guns.api.item.IGun;
import com.talhanation.recruits.entities.AbstractRecruitEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.goal.Goal;

public class RecruitsChangePoseGoal<T extends AbstractRecruitEntity> extends Goal {
    private final T recruit;
    private LivingEntity target;

    public RecruitsChangePoseGoal(T mob) {
        this.recruit = mob;
    }

    @Override
    public boolean canUse() {
        if(TACZRecruitsConfig.SHOULD_RECRUITS_CHANGE_POSE.get() && this.recruit.getMainHandItem().getItem() instanceof IGun) {
            this.target = this.recruit.getTarget();

            return true;
        }

        return false;
    }

    @Override
    public void tick() {
        if(target != null) {
            if(this.recruit.distanceTo(this.target) >= 16) {
                if(this.recruit.getRandom().nextFloat() < TACZRecruitsConfig.RECRUIT_POSE_CHANCE.get()) {
                    if(((IPoser)this.recruit).getPoseCooldown() <= 0) {
                        ((IPoser)this.recruit).changePose();

                        ((IPoser)this.recruit).setPoseCooldown(TACZRecruitsConfig.RECRUIT_POSE_COOLDOWN.get());
                    }
                }
            }
        } else {
            this.recruit.setPose(Pose.STANDING);
        }

        super.start();
    }

    @Override
    public void stop() {
        this.recruit.setPose(Pose.STANDING);
        super.stop();
    }
}
