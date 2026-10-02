package com.strikey.compat.module;

import com.strikey.compat.LetsDoEveryCompat;
import com.strikey.compat.PieceOwnership;
import net.mehvahdjukaar.every_compat.api.RenderLayer;
import net.mehvahdjukaar.every_compat.api.SimpleEntrySet;
import net.mehvahdjukaar.every_compat.modules.EveryCompatModule;
import net.mehvahdjukaar.moonlight.api.set.wood.VanillaWoodTypes;
import net.mehvahdjukaar.moonlight.api.set.wood.WoodType;
import net.mehvahdjukaar.moonlight.api.util.Utils;
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

    private static ResourceLocation maskRes(String path) {
        return ResourceLocation.fromNamespaceAndPath(LetsDoEveryCompat.MODID, path);
    }

    public SimpleEntrySet<WoodType, CabinetBlock> cabinets;
    public SimpleEntrySet<WoodType, CabinetBlock> drawers;
    public SimpleEntrySet<WoodType, TableBlock> tables;
    public SimpleEntrySet<WoodType, ChairBlock> chairs;
    public SimpleEntrySet<WoodType, ShelfBlock> shelves;
    public SimpleEntrySet<WoodType, LargeTableBlock> bigTables;

    public LetsDoCandlelightModule(String modId) {
        super(modId, "ldc", LetsDoEveryCompat.MODID);
        Supplier<CreativeModeTab> tab = getTab(modRes("candlelight"));
        Supplier<WoodType> oak = () -> VanillaWoodTypes.OAK;

        cabinets = SimpleEntrySet.builder(WoodType.class, "cabinet",
                        getModBlock("oak_cabinet", CabinetBlock.class), oak,
                        w -> new CabinetBlock(Utils.copyPropertySafe(w.planks).strength(2.0F, 3.0F).sound(SoundType.WOOD),
                                SoundEventRegistry.CABINET_OPEN.get(), SoundEventRegistry.CABINET_CLOSE.get()))
                .addTextureM(modRes("block/oak_cabinet_front"), maskRes("block/ldc/masks/oak_cabinet_front_m"))
                .addTexture(modRes("block/oak_cabinet_side"))
                .addTexture(modRes("block/oak_cabinet_top"))
                .addTexture(modRes("block/oak_cabinet_open"))
                .addTile(EntityTypeRegistry.CABINET_BLOCK_ENTITY)
                .addTag(BlockTags.MINEABLE_WITH_AXE, Registries.BLOCK)
                .setTab(tab)
                .defaultRecipe()
                .build();
        if (PieceOwnership.owns("candlelight", "cabinet")) {
            this.addEntry(cabinets);
        }

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
        if (PieceOwnership.owns("candlelight", "drawer")) {
            this.addEntry(drawers);
        }

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
        if (PieceOwnership.owns("candlelight", "table")) {
            this.addEntry(tables);
        }

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
        if (PieceOwnership.owns("candlelight", "chair")) {
            this.addEntry(chairs);
        }

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
        if (PieceOwnership.owns("candlelight", "shelf")) {
            this.addEntry(shelves);
        }

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
        if (PieceOwnership.owns("candlelight", "big_table")) {
            this.addEntry(bigTables);
        }
    }
}
