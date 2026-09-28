package com.strikey.compat;

import com.mojang.logging.LogUtils;
import com.strikey.compat.module.LetsDoBeachpartyModule;
import com.strikey.compat.module.LetsDoCandlelightModule;
import com.strikey.compat.module.LetsDoFurnitureModule;
import com.strikey.compat.module.LetsDoHearthAndTimberModule;
import com.strikey.compat.module.LetsDoMeadowModule;
import com.strikey.compat.module.LetsDoVineryModule;
import net.mehvahdjukaar.every_compat.api.EveryCompatAPI;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.Mod;
import org.slf4j.Logger;

@Mod(LetsDoEveryCompat.MODID)
public class LetsDoEveryCompat {

    public static final String MODID = "letsdoeverycompat";
    public static final Logger LOGGER = LogUtils.getLogger();

    public LetsDoEveryCompat(IEventBus modEventBus, ModContainer modContainer) {
        // one guard per Let's Do mod so they stay mix-and-match
        // meadow goes first: its bench shares the piece name with furniture's, and the pine bench
        // has to stay meadow's child or the whole bench entry's assets get skipped
        if (ModList.get().isLoaded("everycomp") && ModList.get().isLoaded("meadow")) {
            EveryCompatAPI.registerOptionalModule("meadow", () -> LetsDoMeadowModule.class);
        }
        if (ModList.get().isLoaded("everycomp") && ModList.get().isLoaded("vinery")) {
            EveryCompatAPI.registerOptionalModule("vinery", () -> LetsDoVineryModule.class);
        }
        if (ModList.get().isLoaded("everycomp") && ModList.get().isLoaded("beachparty")) {
            EveryCompatAPI.registerOptionalModule("beachparty", () -> LetsDoBeachpartyModule.class);
        }
        if (ModList.get().isLoaded("everycomp") && ModList.get().isLoaded("furniture")) {
            EveryCompatAPI.registerOptionalModule("furniture", () -> LetsDoFurnitureModule.class);
        }
        if (ModList.get().isLoaded("everycomp") && ModList.get().isLoaded("candlelight")) {
            EveryCompatAPI.registerOptionalModule("candlelight", () -> LetsDoCandlelightModule.class);
        }
        if (ModList.get().isLoaded("everycomp") && ModList.get().isLoaded("hearth_and_timber")) {
            EveryCompatAPI.registerOptionalModule("hearth_and_timber", () -> LetsDoHearthAndTimberModule.class);
        }
    }
}
