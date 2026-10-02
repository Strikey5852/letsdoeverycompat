package com.strikey.compat;

import com.mojang.logging.LogUtils;
import com.strikey.compat.module.LetsDoBeachpartyModule;
import com.strikey.compat.module.LetsDoCandlelightModule;
import com.strikey.compat.module.LetsDoFurnitureModule;
import com.strikey.compat.module.LetsDoHearthAndTimberModule;
import com.strikey.compat.module.LetsDoMeadowModule;
import com.strikey.compat.module.LetsDoVineryModule;
import dev.architectury.platform.Platform;
import net.mehvahdjukaar.every_compat.api.EveryCompatAPI;
import org.slf4j.Logger;

public class LetsDoEveryCompat {

    public static final String MODID = "letsdoeverycompat";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static void registerModules() {
        PieceOwnership.init();
        if (loaded("everycomp") && loaded("meadow")) {
            EveryCompatAPI.registerOptionalModule("meadow", () -> LetsDoMeadowModule.class);
        }
        if (loaded("everycomp") && loaded("vinery")) {
            EveryCompatAPI.registerOptionalModule("vinery", () -> LetsDoVineryModule.class);
        }
        if (loaded("everycomp") && loaded("beachparty")) {
            EveryCompatAPI.registerOptionalModule("beachparty", () -> LetsDoBeachpartyModule.class);
        }
        if (loaded("everycomp") && loaded("furniture")) {
            EveryCompatAPI.registerOptionalModule("furniture", () -> LetsDoFurnitureModule.class);
        }
        if (loaded("everycomp") && loaded("candlelight")) {
            EveryCompatAPI.registerOptionalModule("candlelight", () -> LetsDoCandlelightModule.class);
        }
        if (loaded("everycomp") && loaded("hearth_and_timber")) {
            EveryCompatAPI.registerOptionalModule("hearth_and_timber", () -> LetsDoHearthAndTimberModule.class);
        }
    }

    private static boolean loaded(String modId) {
        return Platform.isModLoaded(modId);
    }
}
