package com.strikey.compat.module;

import com.berksire.furniture.core.block.BenchBlock;
import com.strikey.compat.LetsDoEveryCompat;
import net.mehvahdjukaar.every_compat.api.RenderLayer;
import net.mehvahdjukaar.every_compat.api.SimpleEntrySet;
import net.mehvahdjukaar.every_compat.modules.EveryCompatModule;
import net.mehvahdjukaar.moonlight.api.set.wood.VanillaWoodTypes;
import net.mehvahdjukaar.moonlight.api.set.wood.WoodType;
import net.mehvahdjukaar.moonlight.api.util.Utils;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.material.PushReaction;

import java.util.function.Supplier;

public class LetsDoFurnitureModule extends EveryCompatModule {

    public final SimpleEntrySet<WoodType, BenchBlock> benches;

    public LetsDoFurnitureModule(String modId) {
        super(modId, "ldf", LetsDoEveryCompat.MODID);
        Supplier<CreativeModeTab> tab = getTab(modRes("furniture"));

        benches = SimpleEntrySet.builder(WoodType.class, "bench",
                        getModBlock("oak_bench", BenchBlock.class), () -> VanillaWoodTypes.OAK,
                        w -> new BenchBlock(Utils.copyPropertySafe(w.planks).pushReaction(PushReaction.IGNORE)))
                .addTexture(modRes("block/oak_bench_front"))
                .addTexture(modRes("block/oak_bench_front_middle"))
                .addTexture(modRes("block/oak_bench_front_side"))
                .addTexture(modRes("block/oak_bench_top"))
                .setRenderType(RenderLayer.CUTOUT)
                .addTag(BlockTags.MINEABLE_WITH_AXE, Registries.BLOCK)
                .setTab(tab)
                .defaultRecipe()
                .build();
        this.addEntry(benches);
    }
}
