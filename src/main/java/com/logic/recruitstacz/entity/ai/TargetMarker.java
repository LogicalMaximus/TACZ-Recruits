package com.logic.recruitstacz.entity.ai;

import net.minecraft.world.phys.Vec3;

import java.util.UUID;

public record TargetMarker(UUID target, Vec3 lastKnownLocation) {


}
