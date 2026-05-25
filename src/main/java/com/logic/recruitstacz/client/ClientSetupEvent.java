package com.logic.recruitstacz.client;

import com.logic.recruitstacz.TACZRecruits;
import com.logic.recruitstacz.compat.playeranimator.TACZRecruitAnimatorCompat;
import com.tacz.guns.GunMod;
import com.tacz.guns.client.resource.ClientAssetsManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterClientReloadListenersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT, modid = TACZRecruits.MODID)
public class ClientSetupEvent {

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(TACZRecruitAnimatorCompat::init);
    }

    @SubscribeEvent
    public static void onClientResourceReload(RegisterClientReloadListenersEvent event) {
        TACZRecruitAnimatorCompat.init();
        ClientAssetsManager.INSTANCE.reloadAndRegister(event::registerReloadListener);
        if (TACZRecruitAnimatorCompat.isInstalled()) {
            TACZRecruitAnimatorCompat.registerReloadListener(event::registerReloadListener);
        }
    }
}
