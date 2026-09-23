package com.strikey.compat.module;

import com.berksire.furniture.core.block.BenchBlock;
import com.berksire.furniture.core.block.DeskBlock;
import com.berksire.furniture.core.block.DeskChairBlock;
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
    public final SimpleEntrySet<WoodType, DeskBlock> desks;
    public final SimpleEntrySet<WoodType, DeskChairBlock> deskChairs;

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

        desks = SimpleEntrySet.builder(WoodType.class, "desk",
                        getModBlock("oak_desk", DeskBlock.class), () -> VanillaWoodTypes.OAK,
                        w -> new DeskBlock(Utils.copyPropertySafe(w.planks).pushReaction(PushReaction.IGNORE)))
                .addTexture(modRes("block/oak_desk"))
                .addTexture(modRes("block/oak_desk_support"))
                .addTexture(modRes("block/oak_desk_connected_side"))
                .addTexture(modRes("block/oak_desk_connected_middle"))
                .addTexture(modRes("block/oak_cabinet_top"))
                .setRenderType(RenderLayer.CUTOUT)
                .addTag(BlockTags.MINEABLE_WITH_AXE, Registries.BLOCK)
                .setTab(tab)
                .defaultRecipe()
                .build();
        this.addEntry(desks);

        // furniture:block/desk_chair_parts is a shared, wood-independent texture, so it is
        // deliberately not declared: the models keep pointing at Furniture's own copy.
        deskChairs = SimpleEntrySet.builder(WoodType.class, "desk_chair",
                        getModBlock("oak_desk_chair", DeskChairBlock.class), () -> VanillaWoodTypes.OAK,
                        w -> new DeskChairBlock(Utils.copyPropertySafe(w.planks)))
                .addTexture(modRes("block/oak_desk_chair"))
                .setRenderType(RenderLayer.CUTOUT)
                .addTag(BlockTags.MINEABLE_WITH_AXE, Registries.BLOCK)
                .setTab(tab)
                .defaultRecipe()
                .build();
        this.addEntry(deskChairs);
    }
}
