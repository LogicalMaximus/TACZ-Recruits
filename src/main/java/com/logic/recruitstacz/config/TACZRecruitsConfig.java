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
    public static final ForgeConfigSpec.ConfigValue<Boolean> SHOULD_RECRUITS_ADVANCE_ON_TARGET;
    public static final ForgeConfigSpec.ConfigValue<Integer> BULLET_SUPPRESSION_RADIUS;
    public static final ForgeConfigSpec.ConfigValue<Integer> WEAPON_SHOOT_COOLDOWN;

    public static final ForgeConfigSpec.ConfigValue<Boolean> SHOULD_RECRUITS_CHANGE_POSE;
    public static final ForgeConfigSpec.ConfigValue<Boolean> SHOULD_RECRUITS_USE_SUPPRESSIVE_FIRE;
    public static final ForgeConfigSpec.ConfigValue<Integer> RECRUIT_POSE_COOLDOWN;
    public static final ForgeConfigSpec.ConfigValue<Float> RECRUIT_POSE_CHANCE;
    public static final ForgeConfigSpec.ConfigValue<Boolean> SHOULD_RECRUIT_PRONE;

    public static final ForgeConfigSpec.ConfigValue<Integer> ADVANCE_DISTANCE;
    public static final ForgeConfigSpec.ConfigValue<Double> RECRUIT_TARGET_RADIUS;
    public static final ForgeConfigSpec.ConfigValue<Boolean> SHOULD_RECRUITS_WEAPON_SWITCH;
    public static final ForgeConfigSpec.ConfigValue<Boolean> SHOULD_RECRUITS_CLUSTER_WEAPON_SWITCH;
    public static final ForgeConfigSpec.ConfigValue<Boolean> SHOULD_RECRUITS_VEHICLE_WEAPON_SWITCH;
    public static final ForgeConfigSpec.ConfigValue<Integer> WEAPON_SWITCH_COOLDOWN;
    public static final ForgeConfigSpec.ConfigValue<Boolean> SHOULD_RECRUITS_USE_WEAPON_WHEN_MOUNTED;
    public static final ForgeConfigSpec.ConfigValue<Integer> WEAPON_SWITCH_CLUSTER_RADIUS;
    public static final ForgeConfigSpec.ConfigValue<Integer> WEAPON_SWITCH_CLUSTER_MINIMUM_TARGETS;

    public static final ForgeConfigSpec.ConfigValue<Boolean> SHOULD_PISTOL_SUPPRESSION;
    public static final ForgeConfigSpec.ConfigValue<Boolean> SHOULD_RIFLE_SUPPRESSION;
    public static final ForgeConfigSpec.ConfigValue<Boolean> SHOULD_SNIPER_SUPPRESSION;
    public static final ForgeConfigSpec.ConfigValue<Boolean> SHOULD_SMG_SUPPRESSION;
    public static final ForgeConfigSpec.ConfigValue<Boolean> SHOULD_RPG_SUPPRESSION;
    public static final ForgeConfigSpec.ConfigValue<Boolean> SHOULD_SHOTGUN_SUPPRESSION;
    public static final ForgeConfigSpec.ConfigValue<Boolean> SHOULD_MG_SUPPRESSION;

    public static final ForgeConfigSpec.ConfigValue<Integer> TARGET_AMMO_PISTOL;
    public static final ForgeConfigSpec.ConfigValue<Integer> TARGET_AMMO_RIFLE;
    public static final ForgeConfigSpec.ConfigValue<Integer> TARGET_AMMO_SNIPER;
    public static final ForgeConfigSpec.ConfigValue<Integer> TARGET_AMMO_SMG;
    public static final ForgeConfigSpec.ConfigValue<Integer> TARGET_AMMO_RPG;
    public static final ForgeConfigSpec.ConfigValue<Integer> TARGET_AMMO_SHOTGUN;
    public static final ForgeConfigSpec.ConfigValue<Integer> TARGET_AMMO_MG;

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

        BUILDER.push("Suppression Behaviour");
        SHOULD_RECRUITS_USE_SUPPRESSIVE_FIRE = BUILDER.comment("\n Whether Recruits Should Use Suppressive Fire \n\t(takes effect after restart)").define("ShouldUseSuppressiveFire", true);
        BULLET_SUPPRESSION_RADIUS = BUILDER.comment("\nThe Radius At Which A Bullet Will Suppressive Recruits \n\t(takes effect after restart)").define("BulletSuppressionRadius", 4);

        SHOULD_PISTOL_SUPPRESSION = BUILDER.comment("\n Whether Recruits Should Use A Pistol For Suppressive Fire \n\t(takes effect after restart)").define("ShouldSuppressiveFirePistol", true);
        SHOULD_RIFLE_SUPPRESSION = BUILDER.comment("\n Whether Recruits Should Use A Rifle For Suppressive Fire \n\t(takes effect after restart)").define("ShouldSuppressiveFireRifle", true);
        SHOULD_SNIPER_SUPPRESSION = BUILDER.comment("\n Whether Recruits Should Use A Sniper For Suppressive Fire \n\t(takes effect after restart)").define("ShouldSuppressiveFireSniper", false);
        SHOULD_SMG_SUPPRESSION = BUILDER.comment("\n Whether Recruits Should Use A SMG For Suppressive Fire \n\t(takes effect after restart)").define("ShouldSuppressiveFireSMG", true);
        SHOULD_RPG_SUPPRESSION = BUILDER.comment("\n Whether Recruits Should Use A RPG/Heavy Weapon For Suppressive Fire \n\t(takes effect after restart)").define("ShouldSuppressiveFireRPG", false);
        SHOULD_SHOTGUN_SUPPRESSION = BUILDER.comment("\n Whether Recruits Should Use A Shotgun For Suppressive Fire \n\t(takes effect after restart)").define("ShouldSuppressiveFireShotgun", false);
        SHOULD_MG_SUPPRESSION = BUILDER.comment("\n Whether Recruits Should Use A Machine Gun For Suppressive Fire \n\t(takes effect after restart)").define("ShouldSuppressiveFireMG", true);

        BUILDER.pop();

        BUILDER.push("General Behaviour");

        SHOULD_RECRUITS_USE_WEAPON_WHEN_MOUNTED = BUILDER.comment("\nWhether Recruits Should Use Their Weapons While Mounted \n\t(Recommend You Turn Off If Using Vehicle Mod Like Superb Warfare)").define("ShouldRecruitUseWeaponWhileMounted", true);
        WEAPON_SHOOT_COOLDOWN = BUILDER.comment("\n The Cooldown In Ticks In Between Each Shot \n\t(takes effect after restart)").define("WeaponShootCooldown", 0);
        SHOULD_RECRUIT_PRONE = BUILDER.comment("\n Whether Recruits Should Go Prone \n\t(takes effect after restart)").define("ShouldProneWhilePosing", true);
        RECRUIT_TARGET_RADIUS = BUILDER.comment("\n The Radius At Which Recruits Will Search For Targets \n\t(takes effect after restart)").define("RecruitsTargetRadius", 64.0);

        BUILDER.pop();

        BUILDER.push("AI Cover Behaviour");

        RECRUIT_COVER_RADIUS = BUILDER.comment("How Far Recruits Will Run For Cover").define("RecruitCoverRadius", 16);
        SHOULD_RECRUITS_RUN_TO_COVER = BUILDER.comment("Whether Recruits Should Run To Cover From Enemies").define("ShouldRecruitsRunToCover", true);
        SHOULD_RECRUITS_ADVANCE_ON_TARGET = BUILDER.comment("Whether Recruits Should Move Toward Targets If Not Seen").define("ShouldRecruitsAdvanceOnTargets", true);
        ADVANCE_DISTANCE  = BUILDER.comment("How Far Will A Recruit Attempt To Advance At Once").define("RecruitsAdvanceDistance", 8);

        BUILDER.pop();

        BUILDER.push("Posing Behaviour");

        SHOULD_RECRUITS_CHANGE_POSE = BUILDER.comment("\nWhether Recruits Should Attempt Crouch And Prone In A Gunfight \n\t(takes effect after restart)").define("ShouldRecruitPose", true);
        RECRUIT_POSE_COOLDOWN = BUILDER.comment("\n The Cooldown On How Often A Recruit Can Change It's Pose In Ticks\n\t(takes effect after restart)").define("RecruitPoseCooldown", 200);
        RECRUIT_POSE_CHANCE = BUILDER.comment("\n The Chance At Which A Recruit Changes It's Pose \n\t(takes effect after restart)").define("RecruitPoseChance", 0.1F);

        BUILDER.pop();

        BUILDER.push("Weapon Switching Behaviour");

        SHOULD_RECRUITS_WEAPON_SWITCH = BUILDER.comment("\nWhether Recruits Should Switch Weapons Based On The Target And Weapon Stats \n\t(takes effect after restart)").define("ShouldRecruitsWeaponSwitch", true);
        SHOULD_RECRUITS_CLUSTER_WEAPON_SWITCH = BUILDER.comment("\nWhether Recruits Should Switch To Explosive Weapons For Large Groups Of Enemies \n\t(takes effect after restart)").define("ShouldRecruitsClusterWeaponSwitch", true);
        SHOULD_RECRUITS_VEHICLE_WEAPON_SWITCH = BUILDER.comment("\nWhether Recruits Should Switch To Explosive Weapons For Enemies In Vehicles \n\t(takes effect after restart)").define("ShouldRecruitsVehicleWeaponSwitch", true);
        WEAPON_SWITCH_COOLDOWN = BUILDER.comment("\nThe Weapon Switch Cooldown On Recruits In Ticks\n\t(takes effect after restart)").define("WeaponSwitchCooldown", 100);
        WEAPON_SWITCH_CLUSTER_RADIUS = BUILDER.comment("The Radius For How Close Enemies Must Be To Be Considered Clustered For Weapon Switching").define("WeaponSwitchClusterRadius", 6);
        WEAPON_SWITCH_CLUSTER_MINIMUM_TARGETS = BUILDER.comment("The Minimum Number Of Enemies In A Cluster To Be Considered For Weapon Switching").define("WeaponSwitchClusterMinimumTargets", 6);

        BUILDER.pop();

        BUILDER.push("Upkeep Behaviour");

        TARGET_AMMO_PISTOL = BUILDER.comment("\nThe Amount Of Ammo A Recruit Will Aim For When Doing Upkeep For A Pistol \n\t(takes effect after restart)").define("TargetPistolAmmo", 60);
        TARGET_AMMO_RIFLE = BUILDER.comment("\nThe Amount Of Ammo A Recruit Will Aim For When Doing Upkeep For A Rifle \n\t(takes effect after restart)").define("TargetRifleAmmo", 210);
        TARGET_AMMO_SNIPER = BUILDER.comment("\nThe Amount Of Ammo A Recruit Will Aim For When Doing Upkeep For A Sniper \n\t(takes effect after restart)").define("TargetSniperAmmo", 120);
        TARGET_AMMO_SMG = BUILDER.comment("\nThe Amount Of Ammo A Recruit Will Aim For When Doing Upkeep For A SMG \n\t(takes effect after restart)").define("TargetSMGAmmo", 300);
        TARGET_AMMO_RPG = BUILDER.comment("\nThe Amount Of Ammo A Recruit Will Aim For When Doing Upkeep For A RPG/Heavy Weapon \n\t(takes effect after restart)").define("TargetRPGAmmo", 3);
        TARGET_AMMO_SHOTGUN = BUILDER.comment("\nThe Amount Of Ammo A Recruit Will Aim For When Doing Upkeep For A Shotgun \n\t(takes effect after restart)").define("TargetShotgunAmmo", 15);
        TARGET_AMMO_MG = BUILDER.comment("\nThe Amount Of Ammo A Recruit Will Aim For When Doing Upkeep For A MG \n\t(takes effect after restart)").define("TargetMGAmmo", 600);

        BUILDER.pop();

        SPEC = BUILDER.build();
    }
}
