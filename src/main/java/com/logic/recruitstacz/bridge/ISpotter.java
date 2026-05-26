package com.logic.recruitstacz.bridge;

import com.logic.recruitstacz.entity.ai.TargetMarker;
import net.minecraft.world.phys.Vec3;

public interface ISpotter {


    TargetMarker getLastKnownEnemyPos();

    void setLastKnownEnemyPos(TargetMarker lastKnownEnemyPos);
}
