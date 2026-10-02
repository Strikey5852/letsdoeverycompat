package com.strikey.compat.neoforge;

import com.strikey.compat.LetsDoEveryCompat;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;

@Mod(LetsDoEveryCompat.MODID)
public class LetsDoEveryCompatNeoForge {

    public LetsDoEveryCompatNeoForge(IEventBus modEventBus, ModContainer modContainer) {
        LetsDoEveryCompat.registerModules();
    }
}