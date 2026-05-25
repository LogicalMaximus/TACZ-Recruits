package com.logic.recruitstacz.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class TACZRecruitsConfig {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.ConfigValue<Double> PISTOL_INACCURACY;
    public static final ForgeConfigSpec.ConfigValue<Double> RIFLE_INACCURACY;
    public static final ForgeConfigSpec.ConfigValue<Double> SNIPER_INACCURACY;
    public static final ForgeConfigSpec.ConfigValue<Double> SMG_INACCURACY;
    public static final ForgeConfigSpec.ConfigValue<Double> RPG_INACCURACY;
    public static final ForgeConfigSpec.ConfigValue<Double> SHOTGUN_INACCURACY;
    public static final ForgeConfigSpec.ConfigValue<Double> MG_INACCURACY;
    public static final ForgeConfigSpec.ConfigValue<Double> SUPPRESSIVE_FIRE_INACCURACY_MULTIPLIER;
    public static final ForgeConfigSpec.ConfigValue<Integer> RECRUIT_COVER_RADIUS;
    public static final ForgeConfigSpec.ConfigValue<Boolean> SHOULD_RECRUITS_RUN_TO_COVER;
    public static final ForgeConfigSpec.ConfigValue<Integer> BULLET_SUPPRESSION_RADIUS;

    public static final ForgeConfigSpec.ConfigValue<Boolean> SHOULD_RECRUITS_CHANGE_POSE;
    public static final ForgeConfigSpec.ConfigValue<Boolean> SHOULD_RECRUITS_USE_SUPPRESSIVE_FIRE;
    public static final ForgeConfigSpec.ConfigValue<Integer> RECRUIT_POSE_COOLDOWN;
    public static final ForgeConfigSpec.ConfigValue<Float> RECRUIT_POSE_CHANCE;
    public static final ForgeConfigSpec.ConfigValue<Boolean> SHOULD_RECRUITS_WEAPON_SWITCH;
    public static final ForgeConfigSpec.ConfigValue<Integer> WEAPON_SWITCH_COOLDOWN;

    static {
        BUILDER.comment("Recruit TACZ Server Config").push("Accuracy Values");

        PISTOL_INACCURACY = BUILDER.comment("\nThe Radius For The Aiming Cone For Pistol Category\n\t(Smaller Radius = More Accurate)").define("PistolInaccuracy", 1.25);
        RIFLE_INACCURACY = BUILDER.comment("\nThe Radius For The Aiming Cone For Rifle Category\n\t(Smaller Radius = More Accurate)").define("RifleInaccuracy", 1.5);
        SNIPER_INACCURACY = BUILDER.comment("\nThe Radius For The Aiming Cone For Sniper Category\n\t(Smaller Radius = More Accurate)").define("SniperRifleInaccuracy", 0.75);
        SMG_INACCURACY = BUILDER.comment("\nThe Radius For The Aiming Cone For SMG Category\n\t(Smaller Radius = More Accurate)").define("SMGInaccuracy", 1.75);
        RPG_INACCURACY = BUILDER.comment("\nThe Radius For The Aiming Cone For RPG Category\n\t(Smaller Radius = More Accurate)").define("RPGInaccuracy", 1.65);
        SHOTGUN_INACCURACY = BUILDER.comment("\nThe Radius For The Aiming Cone For Shotgun Category\n\t(Smaller Radius = More Accurate)").define("ShotgunInaccuracy", 1.85);
        MG_INACCURACY = BUILDER.comment("\nThe Radius For The Aiming Cone For Machine Gun Category\n\t(Smaller Radius = More Accurate)").define("MGInaccuracy", 2.25);
        SUPPRESSIVE_FIRE_INACCURACY_MULTIPLIER = BUILDER.comment("\nIncreases Aiming Cone When Recruits Are Using Suppressive Fire On Enemies\n\t(Smaller Multiplier = More Accurate)").define("SuppressiveFireInaccuracyMultiplier", 1.75);

        BUILDER.pop();
        BUILDER.push("AI Behaviour");

        RECRUIT_COVER_RADIUS = BUILDER.comment("How Far Recruits Will Run For Cover").define("RecruitCoverRadius", 16);
        SHOULD_RECRUITS_RUN_TO_COVER = BUILDER.comment("Whether Recruits Should Run To Cover From Enemies").define("ShouldRecruitsRunToCover", true);
        BULLET_SUPPRESSION_RADIUS = BUILDER.comment("\nThe Radius At Which A Bullet Will Suppressive Recruits \n\t(takes effect after restart)").define("BulletSuppressionRadius", 4);

        SHOULD_RECRUITS_CHANGE_POSE = BUILDER.comment("\nWhether Recruits Should Attempt Crouch And Prone In A Gunfight \n\t(takes effect after restart)").define("ShouldRecruitPose", true);
        RECRUIT_POSE_COOLDOWN = BUILDER.comment("\n The Cooldown On How Often A Recruit Can Change It's Pose In Ticks\n\t(takes effect after restart)").define("RecruitPoseCooldown", 200);
        RECRUIT_POSE_CHANCE = BUILDER.comment("\n The Chance At Which A Recruit Changes It's Pose \n\t(takes effect after restart)").define("RecruitPoseChance", 0.1F);
        SHOULD_RECRUITS_USE_SUPPRESSIVE_FIRE = BUILDER.comment("\n Whether Recruits Should Use Suppressive Fire \n\t(takes effect after restart)").define("ShouldUseSuppressiveFire", true);
        SHOULD_RECRUITS_WEAPON_SWITCH = BUILDER.comment("\nWhether Recruits Should Switch Weapons Based On The Target And Weapon Stats \n\t(takes effect after restart)").define("ShouldRecruitsWeaponSwitch", true);
        WEAPON_SWITCH_COOLDOWN = BUILDER.comment("\nThe Weapon Switch Cooldown On Recruits In Ticks\n\t(takes effect after restart)").define("WeaponSwitchCooldown", 100);

        BUILDER.pop();

        SPEC = BUILDER.build();
    }
}
