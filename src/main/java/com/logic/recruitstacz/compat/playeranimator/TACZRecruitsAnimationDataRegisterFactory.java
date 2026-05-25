package com.logic.recruitstacz.compat.playeranimator;

import dev.kosmx.playerAnim.api.layered.ModifierLayer;
import me.Thelnfamous1.mobplayeranimator.api.MobAnimationFactory;

public class TACZRecruitsAnimationDataRegisterFactory {
    public static void registerData() {
        MobAnimationFactory.ANIMATION_DATA_FACTORY.registerFactory(TACZRecruitAnimatorCompat.LOWER_ANIMATION, 93, player -> new ModifierLayer<>());
        MobAnimationFactory.ANIMATION_DATA_FACTORY.registerFactory(TACZRecruitAnimatorCompat.LOOP_UPPER_ANIMATION, 94, player -> new ModifierLayer<>());
        MobAnimationFactory.ANIMATION_DATA_FACTORY.registerFactory(TACZRecruitAnimatorCompat.ONCE_UPPER_ANIMATION, 95, player -> new ModifierLayer<>());
        MobAnimationFactory.ANIMATION_DATA_FACTORY.registerFactory(TACZRecruitAnimatorCompat.ROTATION_ANIMATION, 96,
                player -> new ModifierLayer<>(null, TACZRecruitAdjustmentYRotModifier.getModifier(player)));
    }
}
