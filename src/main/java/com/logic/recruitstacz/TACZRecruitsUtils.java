package com.logic.recruitstacz;

import com.logic.recruitstacz.config.TACZRecruitsConfig;
import com.tacz.guns.api.TimelessAPI;
import com.tacz.guns.api.entity.IGunOperator;
import com.tacz.guns.api.entity.ShootResult;
import com.tacz.guns.api.item.GunTabType;
import com.tacz.guns.api.item.gun.AbstractGunItem;
import com.tacz.guns.item.ModernKineticGunItem;
import com.tacz.guns.resource.index.CommonGunIndex;
import com.tacz.guns.resource.pojo.data.gun.GunData;
import com.talhanation.recruits.entities.AbstractRecruitEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

import java.util.Optional;

public class TACZRecruitsUtils {

    @javax.annotation.Nullable
    public static double getGunInaccuracy(ItemStack gunStack) {
        Item var2 = gunStack.getItem();
        if (var2 instanceof ModernKineticGunItem) {
            ModernKineticGunItem gun = (ModernKineticGunItem)var2;
            switch (((CommonGunIndex)TimelessAPI.getCommonGunIndex(gun.getGunId(gunStack)).get()).getType()) {
                case "pistol" -> {
                    return TACZRecruitsConfig.PISTOL_INACCURACY.get();
                }
                case "rifle" -> {
                    return TACZRecruitsConfig.RIFLE_INACCURACY.get();
                }
                case "sniper" -> {
                    return TACZRecruitsConfig.SNIPER_INACCURACY.get();
                }
                case "smg" -> {
                    return TACZRecruitsConfig.SMG_INACCURACY.get();
                }
                case "rpg" -> {
                    return TACZRecruitsConfig.RPG_INACCURACY.get();
                }
                case "shotgun" -> {
                    return TACZRecruitsConfig.SHOTGUN_INACCURACY.get();
                }
                case "mg" -> {
                    return TACZRecruitsConfig.MG_INACCURACY.get();
                }
            }
        }

        return 0;
    }

    @javax.annotation.Nullable
    public static int getGunTargetAmmo(ItemStack gunStack) {
        Item var2 = gunStack.getItem();
        if (var2 instanceof ModernKineticGunItem) {
            ModernKineticGunItem gun = (ModernKineticGunItem)var2;
            switch (((CommonGunIndex)TimelessAPI.getCommonGunIndex(gun.getGunId(gunStack)).get()).getType()) {
                case "pistol" -> {
                    return TACZRecruitsConfig.TARGET_AMMO_PISTOL.get();
                }
                case "rifle" -> {
                    return TACZRecruitsConfig.TARGET_AMMO_RIFLE.get();
                }
                case "sniper" -> {
                    return TACZRecruitsConfig.TARGET_AMMO_SNIPER.get();
                }
                case "smg" -> {
                    return TACZRecruitsConfig.TARGET_AMMO_SMG.get();
                }
                case "rpg" -> {
                    return TACZRecruitsConfig.TARGET_AMMO_RPG.get();
                }
                case "shotgun" -> {
                    return TACZRecruitsConfig.TARGET_AMMO_SHOTGUN.get();
                }
                case "mg" -> {
                    return TACZRecruitsConfig.TARGET_AMMO_MG.get();
                }
            }
        }

        return 0;
    }

    @javax.annotation.Nullable
    public static boolean shouldSuppressiveFire(ItemStack gunStack) {
        Item var2 = gunStack.getItem();
        if (var2 instanceof ModernKineticGunItem) {
            ModernKineticGunItem gun = (ModernKineticGunItem)var2;
            switch (((CommonGunIndex)TimelessAPI.getCommonGunIndex(gun.getGunId(gunStack)).get()).getType()) {
                case "pistol" -> {
                    return TACZRecruitsConfig.SHOULD_PISTOL_SUPPRESSION.get();
                }
                case "rifle" -> {
                    return TACZRecruitsConfig.SHOULD_RIFLE_SUPPRESSION.get();
                }
                case "sniper" -> {
                    return TACZRecruitsConfig.SHOULD_SNIPER_SUPPRESSION.get();
                }
                case "smg" -> {
                    return TACZRecruitsConfig.SHOULD_SMG_SUPPRESSION.get();
                }
                case "rpg" -> {
                    return TACZRecruitsConfig.SHOULD_RPG_SUPPRESSION.get();
                }
                case "shotgun" -> {
                    return TACZRecruitsConfig.SHOULD_SHOTGUN_SUPPRESSION.get();
                }
                case "mg" -> {
                    return TACZRecruitsConfig.SHOULD_MG_SUPPRESSION.get();
                }
            }
        }

        return false;
    }

    public static int recruitShootGun(AbstractRecruitEntity recruit) {
        ((IGunOperator)recruit).aim(true);
        ShootResult result = ((IGunOperator)recruit).shoot(() -> {
            return recruit.getViewXRot((float) Math.random());
        }, () -> {
            return recruit.getViewYRot((float) Math.random());
        });

        if(result == ShootResult.SUCCESS){
            return TACZRecruitsConfig.WEAPON_SHOOT_COOLDOWN.get();
        } else if(result == ShootResult.NOT_DRAW) {
            ((IGunOperator)recruit).draw(recruit::getMainHandItem);
        } else if (result == ShootResult.NEED_BOLT) {
            ((IGunOperator)recruit).bolt();
        } else if (result == ShootResult.NO_AMMO) {
            ((IGunOperator)recruit).reload();
        }

        return 0;
    }

    public static Vec3 randomizeVector(Vec3 vec3, double radius) {
        double r = radius * Math.sqrt(Math.random());
        double theta = Math.random() * 2 * Math.PI;
        double phi = Math.acos(2 * Math.random() - 1);

        return vec3.add(r * Math.sin(phi) * Math.cos(theta), r * Math.sin(phi) * Math.sin(theta),r * Math.cos(theta));
    }

    @javax.annotation.Nullable
    public static GunTabType heldGunType(ItemStack gunStack) {
        Item var2 = gunStack.getItem();
        if (var2 instanceof ModernKineticGunItem) {
            ModernKineticGunItem gun = (ModernKineticGunItem)var2;
            switch (((CommonGunIndex)TimelessAPI.getCommonGunIndex(gun.getGunId(gunStack)).get()).getType()) {
                case "pistol" -> {
                    return GunTabType.PISTOL;
                }
                case "rifle" -> {
                    return GunTabType.RIFLE;
                }
                case "sniper" -> {
                    return GunTabType.SNIPER;
                }
                case "smg" -> {
                    return GunTabType.SMG;
                }
                case "rpg" -> {
                    return GunTabType.RPG;
                }
                case "shotgun" -> {
                    return GunTabType.SHOTGUN;
                }
                case "mg" -> {
                    return GunTabType.MG;
                }
            }
        }

        return null;
    }
}
