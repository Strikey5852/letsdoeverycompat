package com.strikey.compat.fabric;

import com.strikey.compat.LetsDoEveryCompat;
import net.fabricmc.api.ModInitializer;

public class LetsDoEveryCompatFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        LetsDoEveryCompat.registerModules();
    }
}