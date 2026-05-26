package com.logic.recruitstacz.mixin;

import com.logic.recruitstacz.bridge.ISpotter;
import com.logic.recruitstacz.entity.ai.TargetMarker;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.sensing.Sensing;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Sensing.class)
public class MixinSensing {
    @Shadow
    private Mob mob;

    @Shadow
    private final IntSet seen = new IntOpenHashSet();

    @Shadow
    private final IntSet unseen = new IntOpenHashSet();

    /**
     * @author
     * @reason
     */
    @Overwrite
    public boolean hasLineOfSight(Entity entity) {
        int i = entity.getId();
        if (this.seen.contains(i)) {
            return true;
        } else if (this.unseen.contains(i)) {
            return false;
        } else {
            this.mob.level().getProfiler().push("hasLineOfSight");
            boolean flag = this.mob.hasLineOfSight(entity);
            this.mob.level().getProfiler().pop();
            if (flag) {
                this.seen.add(i);

                if(mob instanceof ISpotter spotter && entity == this.mob.getTarget()) {
                    spotter.setLastKnownEnemyPos(new TargetMarker(entity.getUUID(), entity.getEyePosition()));
                }
            } else {
                this.unseen.add(i);
            }

            return flag;
        }
    }
}
