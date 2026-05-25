package com.logic.recruitstacz.entity.ai;

import com.logic.recruitstacz.TACZRecruits;
import com.logic.recruitstacz.TACZRecruitsUtils;
import com.logic.recruitstacz.config.TACZRecruitsConfig;
import com.tacz.guns.api.entity.IGunOperator;
import com.tacz.guns.api.entity.ShootResult;
import com.tacz.guns.api.item.GunTabType;
import com.tacz.guns.api.item.IGun;
import com.talhanation.recruits.entities.AbstractRecruitEntity;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

public class RecruitSuppressTACZGunGoal extends Goal {
    private static final double MIN_ANGLE_RAD = Math.toRadians(3.0);   // tight when close
    private static final double MAX_ANGLE_RAD = Math.toRadians(7.5);  // laxer when far
    private static final double MIN_DISTANCE  = 2.0;
    private static final double MAX_DISTANCE  = 20.0;

    private final AbstractRecruitEntity recruit;
    private LivingEntity target;
    private int cooldown = 0;

    public RecruitSuppressTACZGunGoal(AbstractRecruitEntity recruit) {
        this.recruit = recruit;
    }

    @Override
    public boolean canUse() {
        if(!(TACZRecruitsConfig.SHOULD_RECRUITS_USE_SUPPRESSIVE_FIRE.get()))
            return false;

        if(!(this.recruit.getMainHandItem().getItem() instanceof IGun))
            return false;

        this.target = this.recruit.getTarget();

        if(this.target != null) {
            boolean shouldRanged = this.recruit.getShouldRanged();
            boolean canAttack = this.recruit.canAttack(this.target);
            boolean notPassive = this.recruit.getState() != 3;

            return shouldRanged && canAttack && notPassive && !this.recruit.getSensing().hasLineOfSight(this.target);
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
        if (this.target != null) {
            Entity targetVehicle = this.target.getVehicle();
            Entity aimTarget;

            if(targetVehicle == null) {
                aimTarget = this.target;
            }
            else {
                aimTarget = targetVehicle;
            }

            this.recruit.lookAt(EntityAnchorArgument.Anchor.EYES, TACZRecruitsUtils.randomizeVector(aimTarget.getEyePosition((float) Math.random()), TACZRecruitsUtils.getGunInaccuracy(this.recruit.getMainHandItem()) * TACZRecruitsConfig.SUPPRESSIVE_FIRE_INACCURACY_MULTIPLIER.get()));

            if(this.cooldown <= 0) {
                this.cooldown = TACZRecruitsUtils.recruitShootGun(this.recruit);
            }
        }

        this.cooldown--;
        super.tick();
    }




}
