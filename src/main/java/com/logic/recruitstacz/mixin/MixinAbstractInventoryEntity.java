package com.logic.recruitstacz.mixin;

import com.tacz.guns.api.TimelessAPI;
import com.tacz.guns.api.item.IGun;
import com.tacz.guns.api.item.gun.AbstractGunItem;
import com.talhanation.recruits.entities.AbstractInventoryEntity;
import com.talhanation.recruits.pathfinding.AsyncPathfinderMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractInventoryEntity.class)
public abstract class MixinAbstractInventoryEntity extends AsyncPathfinderMob {

    protected MixinAbstractInventoryEntity(EntityType<? extends PathfinderMob> p_21683_, Level p_21684_) {
        super(p_21683_, p_21684_);
    }

    @Inject(at=@At("HEAD"), method = "wantsToPickUp")
    public void wantsToPickUp(ItemStack itemStack, CallbackInfoReturnable<Boolean> cir) {
        if(this.getMainHandItem().getItem() instanceof AbstractGunItem gunItem) {
            //TimelessAPI.
        }
    }

}
