package com.strikey.compat.module;

import com.strikey.compat.LetsDoEveryCompat;
import net.mehvahdjukaar.every_compat.api.RenderLayer;
import net.mehvahdjukaar.every_compat.api.SimpleEntrySet;
import net.mehvahdjukaar.every_compat.modules.EveryCompatModule;
import net.mehvahdjukaar.moonlight.api.set.BlockType;
import net.mehvahdjukaar.moonlight.api.set.wood.VanillaWoodTypes;
import net.mehvahdjukaar.moonlight.api.set.wood.WoodType;
import net.mehvahdjukaar.moonlight.api.util.Utils;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.PushReaction;
import net.satisfy.candlelight.core.block.CabinetBlock;
import net.satisfy.candlelight.core.block.LargeTableBlock;
import net.satisfy.candlelight.core.block.ShelfBlock;
import net.satisfy.candlelight.core.block.TableBlock;
import net.satisfy.candlelight.core.registry.EntityTypeRegistry;
import net.satisfy.candlelight.core.registry.SoundEventRegistry;
import net.satisfy.farm_and_charm.core.block.ChairBlock;

import java.util.function.Supplier;

public class LetsDoCandlelightModule extends EveryCompatModule {

    // masks sit in our jar, so not modRes()
    private static ResourceLocation maskRes(String path) {
        return ResourceLocation.fromNamespaceAndPath(LetsDoEveryCompat.MODID, path);
    }

    public final SimpleEntrySet<WoodType, CabinetBlock> cabinets;
    public final SimpleEntrySet<WoodType, CabinetBlock> drawers;
    public final SimpleEntrySet<WoodType, TableBlock> tables;
    public final SimpleEntrySet<WoodType, ChairBlock> chairs;
    public final SimpleEntrySet<WoodType, ShelfBlock> shelves;
    public final SimpleEntrySet<WoodType, LargeTableBlock> bigTables;

    public LetsDoCandlelightModule(String modId) {
        super(modId, "ldc", LetsDoEveryCompat.MODID);
        Supplier<CreativeModeTab> tab = getTab(modRes("candlelight"));
        // candlelight ships all 11 vanilla woods, so oak is the base for the rest
        Supplier<WoodType> oak = () -> VanillaWoodTypes.OAK;

        // cabinet + drawer are the same block, only the sounds differ, so they share the be
        cabinets = SimpleEntrySet.builder(WoodType.class, "cabinet",
                        getModBlock("oak_cabinet", CabinetBlock.class), oak,
                        w -> new CabinetBlock(Utils.copyPropertySafe(w.planks).strength(2.0F, 3.0F).sound(SoundType.WOOD),
                                SoundEventRegistry.CABINET_OPEN.get(), SoundEventRegistry.CABINET_CLOSE.get()))
                // front handle isn't wood, masked to keep its colour
                .addTextureM(modRes("block/oak_cabinet_front"), maskRes("block/ldc/masks/oak_cabinet_front_m"))
                .addTexture(modRes("block/oak_cabinet_side"))
                .addTexture(modRes("block/oak_cabinet_top"))
                .addTexture(modRes("block/oak_cabinet_open"))
                .addTile(EntityTypeRegistry.CABINET_BLOCK_ENTITY)
                .addTag(BlockTags.MINEABLE_WITH_AXE, Registries.BLOCK)
                .setTab(tab)
                .defaultRecipe()
                .build();
        this.addEntry(cabinets);

        drawers = SimpleEntrySet.builder(WoodType.class, "drawer",
                        getModBlock("oak_drawer", CabinetBlock.class), oak,
                        w -> new CabinetBlock(Utils.copyPropertySafe(w.planks).strength(2.0F, 3.0F).sound(SoundType.WOOD),
                                SoundEventRegistry.DRAWER_OPEN.get(), SoundEventRegistry.DRAWER_CLOSE.get()))
                .addTextureM(modRes("block/oak_drawer"), maskRes("block/ldc/masks/oak_drawer_m"))
                .addTexture(modRes("block/oak_cabinet_side"))
                .addTexture(modRes("block/oak_cabinet_top"))
                .addTexture(modRes("block/oak_cabinet_open"))
                .addTile(EntityTypeRegistry.CABINET_BLOCK_ENTITY)
                .addTag(BlockTags.MINEABLE_WITH_AXE, Registries.BLOCK)
                .setTab(tab)
                .defaultRecipe()
                .build();
        this.addEntry(drawers);

        // cutout like the mod
        tables = SimpleEntrySet.builder(WoodType.class, "table",
                        getModBlock("oak_table", TableBlock.class), oak,
                        w -> new TableBlock(Utils.copyPropertySafe(w.planks)))
                .addTexture(modRes("block/oak_table"))
                .addTexture(modRes("block/oak_table_mid"))
                .addTexture(modRes("block/oak_table_connected"))
                .addTexture(modRes("block/oak_cabinet_top"))
                .setRenderType(RenderLayer.CUTOUT)
                .addTag(BlockTags.MINEABLE_WITH_AXE, Registries.BLOCK)
                .setTab(tab)
                .defaultRecipe()
                .build();
        this.addEntry(tables);

        // chair's decor_2 cushion is shared, left at candlelight
        chairs = SimpleEntrySet.builder(WoodType.class, "chair",
                        getModBlock("oak_chair", ChairBlock.class), oak,
                        w -> new ChairBlock(Utils.copyPropertySafe(w.planks).strength(2.0F, 3.0F).sound(SoundType.WOOD)))
                .addTexture(modRes("block/oak_cabinet_top"))
                .addTexture(modRes("block/oak_cabinet_open"))
                .addTexture(modRes("block/oak_cabinet_side"))
                .setRenderType(RenderLayer.CUTOUT)
                .addTag(BlockTags.MINEABLE_WITH_AXE, Registries.BLOCK)
                .setTab(tab)
                .defaultRecipe()
                .build();
        this.addEntry(chairs);

        // shelf reuses the table's connected planks, its stone top stays at candlelight
        shelves = SimpleEntrySet.builder(WoodType.class, "shelf",
                        getModBlock("oak_shelf", ShelfBlock.class), oak,
                        w -> new ShelfBlock(Utils.copyPropertySafe(w.planks).strength(2.0F, 3.0F).sound(SoundType.WOOD).noOcclusion()))
                .addTexture(modRes("block/oak_table_connected"))
                .addTextureM(modRes("item/oak_shelf"), maskRes("item/ldc/masks/oak_shelf_m"))
                .addTile(EntityTypeRegistry.STORAGE_BLOCK_ENTITY)
                .addTag(BlockTags.MINEABLE_WITH_AXE, Registries.BLOCK)
                .setTab(tab)
                .defaultRecipe()
                .build();
        this.addEntry(shelves);

        // two blocks long, cloth top masked
        bigTables = SimpleEntrySet.builder(WoodType.class, "big_table",
                        getModBlock("oak_big_table", LargeTableBlock.class), oak,
                        w -> new LargeTableBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(2.0F, 2.0F)
                                .pushReaction(PushReaction.IGNORE)))
                .addTextureM(modRes("block/oak_big_table"), maskRes("block/ldc/masks/oak_big_table_m"))
                .addTextureM(modRes("item/oak_big_table"), maskRes("item/ldc/masks/oak_big_table_m"))
                .addTag(BlockTags.MINEABLE_WITH_AXE, Registries.BLOCK)
                .setTab(tab)
                .defaultRecipe()
                .build();
        this.addEntry(bigTables);
    }
}
