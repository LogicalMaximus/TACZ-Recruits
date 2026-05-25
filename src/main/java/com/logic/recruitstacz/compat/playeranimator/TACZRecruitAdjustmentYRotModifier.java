package com.logic.recruitstacz.compat.playeranimator;

import com.tacz.guns.api.TimelessAPI;
import com.tacz.guns.client.resource.GunDisplayInstance;
import dev.kosmx.playerAnim.api.layered.modifier.AdjustmentModifier;
import dev.kosmx.playerAnim.core.util.Vec3f;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Pose;

import java.util.Optional;
import java.util.function.Function;

public class TACZRecruitAdjustmentYRotModifier implements Function<String, Optional<AdjustmentModifier.PartModifier>> {
    private final Mob mob;

    private TACZRecruitAdjustmentYRotModifier(Mob mob) {
        this.mob = mob;
    }

    @Override
    public Optional<AdjustmentModifier.PartModifier> apply(String partName) {
        Minecraft mc = Minecraft.getInstance();

        if (mob.getVehicle() != null && "body".equals(partName)) {
            return Optional.empty();
        }

        float partialTick = mc.getPartialTick();
        float yBodyRot = Mth.rotLerp(partialTick, mob.yBodyRotO, mob.yBodyRot);
        float yHeadRot = Mth.rotLerp(partialTick, mob.yHeadRotO, mob.yHeadRot);
        float xRot = Mth.lerp(partialTick, mob.xRotO, mob.getXRot());

        float yaw = yHeadRot - yBodyRot;
        yaw = Mth.wrapDegrees(yaw);
        yaw = Mth.clamp(yaw, -85f, 85f);

        float pitch = Mth.wrapDegrees(xRot);

        return switch (partName) {
            case "body" -> {
                if (!mob.isSwimming() && mob.getPose() == Pose.SWIMMING) {
                    yield Optional.of(new AdjustmentModifier.PartModifier(new Vec3f(0, 0, -yaw * Mth.DEG_TO_RAD), Vec3f.ZERO));
                }
                yield Optional.of(new AdjustmentModifier.PartModifier(new Vec3f(0, -yaw * Mth.DEG_TO_RAD, 0), Vec3f.ZERO));
            }
            case "head" -> Optional.of(new AdjustmentModifier.PartModifier(new Vec3f(pitch * Mth.DEG_TO_RAD, 0, 0), Vec3f.ZERO));
            case "leftArm", "rightArm" -> {
                if (TimelessAPI.getGunDisplay(mob.getMainHandItem()).map(GunDisplayInstance::is3rdFixedHand).orElse(false)) {
                    yield Optional.empty();
                }
                yield Optional.of(new AdjustmentModifier.PartModifier(new Vec3f(pitch * Mth.DEG_TO_RAD, 0, 0), Vec3f.ZERO));
            }
            default -> Optional.empty();
        };
    }

    public static AdjustmentModifier getModifier(Mob mob) {
        return new AdjustmentModifier(new TACZRecruitAdjustmentYRotModifier(mob));
    }
}
