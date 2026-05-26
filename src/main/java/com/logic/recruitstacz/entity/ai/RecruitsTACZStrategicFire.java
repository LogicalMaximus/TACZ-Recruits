package com.logic.recruitstacz.entity.ai;

import com.logic.recruitstacz.TACZRecruitsUtils;
import com.logic.recruitstacz.config.TACZRecruitsConfig;
import com.tacz.guns.api.entity.IGunOperator;
import com.tacz.guns.api.item.GunTabType;
import com.tacz.guns.api.item.IGun;
import com.talhanation.recruits.config.RecruitsServerConfig;
import com.talhanation.recruits.entities.BowmanEntity;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

import java.util.Random;

public class RecruitsTACZStrategicFire extends Goal {
    private BlockPos pos;
    private BowmanEntity bowman;
    private int cooldown = 0;

    public RecruitsTACZStrategicFire(BowmanEntity bowman) {
        this.bowman = bowman;
    }

    public boolean canUse() {
        if(!this.bowman.getShouldRanged())
            return false;

        if(this.bowman.getVehicle() != null && !TACZRecruitsConfig.SHOULD_RECRUITS_USE_WEAPON_WHEN_MOUNTED.get())
            return false;

        if(this.bowman.getMainHandItem().getItem() instanceof IGun) {
            if (this.bowman.getTarget() == null && this.bowman.getShouldStrategicFire()  && this.bowman.getFollowState() != 5 && !this.bowman.getShouldMount()) {
                return true;
            } else {
                this.pos = null;
            }
        }

        return false;
    }

    public boolean canContinueToUse() {
        return this.canUse();
    }

    public void stop() {
        super.stop();
        this.bowman.stopUsingItem();
        this.bowman.clearArrowsPos();
        ((IGunOperator)this.bowman).aim(false);
    }

    public boolean requiresUpdateEveryTick() {
        return true;
    }

    public void tick() {
        this.pos = this.bowman.StrategicFirePos();

        if(this.pos == null)return;

        Vec3 vec3 = new Vec3((double) this.pos.getX(), (double) (this.pos.getY()), (double) this.pos.getZ());

        this.bowman.lookAt(EntityAnchorArgument.Anchor.EYES, TACZRecruitsUtils.randomizeVector(vec3, TACZRecruitsUtils.getGunInaccuracy(this.bowman.getMainHandItem()) * TACZRecruitsConfig.SUPPRESSIVE_FIRE_INACCURACY_MULTIPLIER.get()));

        if(this.cooldown <= 0) {
            this.cooldown = TACZRecruitsUtils.recruitShootGun(this.bowman);
        }

        this.cooldown--;
    }
}
