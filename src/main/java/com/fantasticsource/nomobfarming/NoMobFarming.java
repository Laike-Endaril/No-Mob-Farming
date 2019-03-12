package com.fantasticsource.nomobfarming;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod(modid = NoMobFarming.MODID, name = NoMobFarming.NAME, version = NoMobFarming.VERSION)
public class NoMobFarming
{
    public static final String MODID = "nomobfarming";
    public static final String NAME = "No Mob Farming";
    public static final String VERSION = "1.12.2.001";

    @Mod.EventHandler
    public static void preInit(FMLPreInitializationEvent event)
    {
        MinecraftForge.EVENT_BUS.register(NoMobFarming.class);
    }

    @SubscribeEvent
    public static void saveConfig(ConfigChangedEvent.OnConfigChangedEvent event)
    {
        if (event.getModID().equals(MODID)) ConfigManager.sync(MODID, Config.Type.INSTANCE);
    }

    @SubscribeEvent(priority = EventPriority.LOWEST, receiveCanceled = true)
    public static void drops(LivingDropsEvent event)
    {
        //Players always drop items
        if (event.getEntityLiving().getClass() == EntityPlayerMP.class) return;

        //If a player killed it, drop items
        DamageSource source = event.getSource();
        Entity entity = source.getTrueSource();
        if (entity != null && entity.getClass() == EntityPlayerMP.class) return;
        entity = source.getImmediateSource();
        if (entity != null && entity.getClass() == EntityPlayerMP.class) return;

        //Otherwise, hard-cancel drops
        event.getDrops().clear();
        event.setCanceled(true);
    }
}
