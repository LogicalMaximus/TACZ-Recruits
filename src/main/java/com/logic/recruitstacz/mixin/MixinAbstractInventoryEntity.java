package com.logic.recruitstacz.mixin;

import com.tacz.guns.api.item.gun.AbstractGunItem;
import com.tacz.guns.item.AmmoItem;
import com.talhanation.recruits.entities.AbstractInventoryEntity;
import com.talhanation.recruits.inventory.RecruitSimpleContainer;
import com.talhanation.recruits.pathfinding.AsyncPathfinderMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractInventoryEntity.class)
public abstract class MixinAbstractInventoryEntity extends AsyncPathfinderMob {
    @Shadow(remap = false)
    public RecruitSimpleContainer inventory;

    protected MixinAbstractInventoryEntity(EntityType<? extends PathfinderMob> p_21683_, Level p_21684_) {
        super(p_21683_, p_21684_);
    }

    @Inject(at=@At("HEAD"), method = "wantsToPickUp", cancellable = true)
    public void wantsToPickUp(ItemStack itemStack, CallbackInfoReturnable<Boolean> cir) {
        ItemStack mainHandItem = this.getMainHandItem();

        if(mainHandItem.getItem() instanceof AbstractGunItem) {
            Item item = itemStack.getItem();

            if(item instanceof AmmoItem ammoItem) {
                if(ammoItem.isAmmoOfGun(mainHandItem, itemStack)) {
                    cir.setReturnValue(true);
                }
            }
        }
    }
}
