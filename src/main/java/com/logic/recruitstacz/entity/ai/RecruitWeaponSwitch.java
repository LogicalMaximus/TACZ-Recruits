package com.logic.recruitstacz.entity.ai;

import com.logic.recruitstacz.TACZRecruitsUtils;
import com.logic.recruitstacz.bridge.ISwapper;
import com.logic.recruitstacz.config.TACZRecruitsConfig;
import com.tacz.guns.api.TimelessAPI;
import com.tacz.guns.api.entity.IGunOperator;
import com.tacz.guns.api.item.GunTabType;
import com.tacz.guns.api.item.IGun;
import com.tacz.guns.api.item.gun.AbstractGunItem;
import com.tacz.guns.api.item.nbt.AmmoItemDataAccessor;
import com.tacz.guns.resource.index.CommonGunIndex;
import com.tacz.guns.resource.pojo.data.gun.BulletData;
import com.tacz.guns.resource.pojo.data.gun.GunData;
import com.talhanation.recruits.config.RecruitsServerConfig;
import com.talhanation.recruits.entities.AbstractRecruitEntity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
//TODO
public class RecruitWeaponSwitch<T extends AbstractRecruitEntity> extends Goal {
    private final T recruit;

    private int gunSlot = 0;

    private ItemStack itemStack;

    public RecruitWeaponSwitch(T mob) {
        this.recruit = mob;
    }

    @Override
    public boolean canUse() {
        if(!TACZRecruitsConfig.SHOULD_RECRUITS_WEAPON_SWITCH.get())
            return false;

        if(((ISwapper)this.recruit).getWeaponSwitchCooldown() > 0)
            return false;

        LivingEntity target = this.recruit.getTarget();

        ItemStack mainHandItem = this.recruit.getMainHandItem();

        if(!(mainHandItem.getItem() instanceof AbstractGunItem)) {
            this.gunSlot = this.findGunItem();

            if(gunSlot != -1) {
                itemStack = this.recruit.inventory.getItem(gunSlot);

                return true;
            }
        } else if(mainHandItem.getItem() instanceof AbstractGunItem gunItem) {
            Optional<CommonGunIndex> optCommonGunIndex = TimelessAPI.getCommonGunIndex(gunItem.getGunId(mainHandItem));

            if(optCommonGunIndex.isPresent()) {
                CommonGunIndex commonGunIndex = optCommonGunIndex.get();
                BulletData bulletData = commonGunIndex.getBulletData();

                if(target != null && !target.isDeadOrDying()) {
                    float distance = this.recruit.distanceTo(target);

                    if(TACZRecruitsConfig.SHOULD_RECRUITS_VEHICLE_WEAPON_SWITCH.get() && target.getVehicle() != null || TACZRecruitsConfig.SHOULD_RECRUITS_CLUSTER_WEAPON_SWITCH.get() && this.isEnemiesClusteredNearTarget(target, TACZRecruitsConfig.WEAPON_SWITCH_CLUSTER_RADIUS.get(), TACZRecruitsConfig.WEAPON_SWITCH_CLUSTER_MINIMUM_TARGETS.get())) {

                        if(bulletData.getExplosionData() == null || bulletData.getExplosionData() != null && !bulletData.getExplosionData().isExplode()) {
                            this.gunSlot = this.findATGunItemWithAmmo();

                            if(gunSlot != -1) {
                                itemStack = this.recruit.inventory.getItem(gunSlot);

                                return true;
                            }
                        } else {
                            return false;
                        }
                    }

                    this.gunSlot = this.findLongRangedGunItemWithAmmo(distance);

                    if(gunSlot != -1) {
                        itemStack = this.recruit.inventory.getItem(gunSlot);

                        return true;
                    }
                    else {
                        this.gunSlot = this.findGunItem();

                        if(gunSlot != -1) {
                            itemStack = this.recruit.inventory.getItem(gunSlot);

                            return true;
                        }
                    }

                }
            }

        }

        return false;
    }

    @Override
    public boolean isInterruptable() {
        return false;
    }

    @Override
    public void start() {
        if(itemStack != null) {
            ItemStack beforeItem = this.recruit.inventory.getItem(5);
            this.recruit.getInventory().setItem(gunSlot, beforeItem);

            this.recruit.getInventory().setItem(5, itemStack);
            this.recruit.setItemSlot(EquipmentSlot.MAINHAND, itemStack);
            ((IGunOperator)this.recruit).draw(this.recruit::getMainHandItem);
            ((ISwapper)this.recruit).setWeaponSwitchCooldown(TACZRecruitsConfig.WEAPON_SWITCH_COOLDOWN.get());
        }

        super.start();
    }

    @Override
    public void stop() {
        this.gunSlot = -1;

        super.stop();
    }

    private int findGunItem() {
        for(int i = 0; i < this.recruit.inventory.getContainerSize(); i++) {
            if(this.recruit.inventory.getItem(i).getItem() instanceof IGun) {
                return i;
            }
        }

        return -1;
    }

    private int findLongRangedGunItemWithAmmo(double distance) {
        for(int i = 0; i < this.recruit.inventory.getContainerSize(); i++) {
            ItemStack item = this.recruit.inventory.getItem(i);

            if(item.getItem() instanceof IGun) {
                if(TACZRecruitsUtils.heldGunType(item) == GunTabType.SNIPER || TACZRecruitsUtils.heldGunType(item) == GunTabType.RIFLE || TACZRecruitsUtils.heldGunType(item) == GunTabType.MG) {
                    return i;
                }
            }
        }

        return -1;
    }

    private int findATGunItemWithAmmo() {
        for(int i = 0; i < this.recruit.inventory.getContainerSize(); i++) {
            ItemStack item = this.recruit.inventory.getItem(i);

            if(TACZRecruitsUtils.heldGunType(item) == GunTabType.RPG) {
                return i;
            }
        }

        return -1;
    }

    /*
    private boolean shouldUseExplosive(AbstractRecruitEntity recruit, LivingEntity target, boolean isGrenadeLauncher) {
        double distance = recruit.distanceTo(target);
        boolean canSee = !recruit.hasLineOfSight(target) && !this.isLineOfFireBlocked(target.getEyePosition());

        boolean isVehicle = target.getVehicle() != null;
        boolean isBehindCover = !canSee;
        boolean isClustered = isEnemyClusteredNear(target, 5.0, 3);
        boolean isTooClose = distance < 12.0;

        boolean shouldUse = false;

        if (isVehicle) shouldUse = true;
        else if (isClustered) shouldUse = true;
        else if (isBehindCover && distance < 12 && isGrenadeLauncher) shouldUse = true;

        if (isTooClose) shouldUse = false;

        return shouldUse;
    }


     */
    private boolean isEnemiesClusteredNearTarget(LivingEntity target, double radius, int minimumTargets) {
        List<LivingEntity> entities = this.recruit.level().getEntitiesOfClass(LivingEntity.class, target.getBoundingBox().inflate(radius));

        for(LivingEntity entity: entities) {
            if(!this.recruit.shouldAttack(entity)) {
                return false;
            }
        }

        List<LivingEntity> targets = new ArrayList<>(entities.stream().filter((e) -> recruit.canAttack(e) && recruit.shouldAttack(e)).toList());

        return targets.size() >= minimumTargets;
    }

    private boolean isLineOfFireBlocked(Vec3 targetPos) {
        Vec3 eye = this.recruit.getEyePosition();
        Level level = this.recruit.level();

        ClipContext context = new ClipContext(eye, targetPos, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this.recruit);
        HitResult result = level.clip(context);

        if (result.getType() == HitResult.Type.BLOCK) {
            double hitDist = result.getLocation().distanceTo(eye);
            double totalDist = targetPos.distanceTo(eye);
            return hitDist < totalDist * 0.2;
        }

        return false;
    }
}
