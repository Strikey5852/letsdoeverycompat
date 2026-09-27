package com.strikey.compat.module;

import com.strikey.compat.LetsDoEveryCompat;
import net.mehvahdjukaar.every_compat.api.PaletteStrategies;
import net.mehvahdjukaar.every_compat.api.PaletteStrategy;
import net.mehvahdjukaar.every_compat.api.RenderLayer;
import net.mehvahdjukaar.every_compat.api.SimpleEntrySet;
import net.mehvahdjukaar.every_compat.api.TextureInfo;
import net.mehvahdjukaar.every_compat.modules.EveryCompatModule;
import net.mehvahdjukaar.moonlight.api.resources.textures.Palette;
import net.mehvahdjukaar.moonlight.api.set.BlockType;
import net.mehvahdjukaar.moonlight.api.set.wood.WoodType;
import net.mehvahdjukaar.moonlight.api.set.wood.WoodTypeRegistry;
import net.mehvahdjukaar.moonlight.api.util.Utils;
import net.mehvahdjukaar.moonlight.api.util.math.colors.RGBColor;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.fml.ModList;
import net.satisfy.beachparty.core.block.PalmBarBlock;
import net.satisfy.beachparty.core.block.PalmBarStoolBlock;
import net.satisfy.beachparty.core.block.PalmCabinetBlock;
import net.satisfy.beachparty.core.block.PalmChairBlock;
import net.satisfy.beachparty.core.block.PalmTableBlock;
import net.satisfy.beachparty.core.registry.EntityTypeRegistry;

import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

public class LetsDoBeachpartyModule extends EveryCompatModule {

    // masks sit in our jar, so not modRes()
    private static ResourceLocation maskRes(String path) {
        return ResourceLocation.fromNamespaceAndPath(LetsDoEveryCompat.MODID, path);
    }

    public final SimpleEntrySet<WoodType, PalmBarStoolBlock> barStools;
    public final SimpleEntrySet<WoodType, PalmBarBlock> palmBars;
    // null unless vinery is absent, so not final
    public SimpleEntrySet<WoodType, PalmChairBlock> chairs;
    public SimpleEntrySet<WoodType, PalmTableBlock> tables;
    public SimpleEntrySet<WoodType, PalmCabinetBlock> cabinets;

    public LetsDoBeachpartyModule(String modId) {
        super(modId, "ldb", LetsDoEveryCompat.MODID);
        Supplier<CreativeModeTab> tab = getTab(modRes("beachparty"));
        // the palm pieces are the beachparty furniture, so palm is their base wood
        Supplier<WoodType> palm = WoodTypeRegistry.INSTANCE.makeFutureHolder(modRes("palm"));

        // stool top is just the blue pad, no wood in it. copyTexture would fill with wood, so pin it transparent
        // its legs point at palm_planks, so that ref needs a generated texture too
        // the footrest reuses the beach chair frame art; no wood prefix in the name, so ec leaves it there
        barStools = SimpleEntrySet.builder(WoodType.class, "bar_stool",
                        getModBlock("palm_bar_stool", PalmBarStoolBlock.class), palm,
                        w -> new PalmBarStoolBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BAMBOO_PLANKS)))
                .addTexture(TextureInfo.of(modRes("block/palm_bar_stool_top"))
                        .copyTexture()
                        .setPalette((wood, manager) -> PaletteStrategy.PaletteAndAnimation.of(
                                List.of(Palette.ofColors(Set.of(new RGBColor(0f, 0f, 0f, 0f)))), null)))
                .addTexture(modRes("block/palm_planks"))
                .addTextureC(modRes("block/beach_chair_front"), "block/palm_bar_stool_ring")
                .addModelTransform(t -> t.addModifier((s, id, wood) ->
                        s.replace("beachparty:block/beach_chair_front",
                                LetsDoEveryCompat.MODID + ":block/ldb/" + wood.getNamespace() + "/" + wood.getTypeName() + "_bar_stool_ring")))
                .setRenderType(RenderLayer.CUTOUT)
                .addTag(BlockTags.MINEABLE_WITH_AXE, Registries.BLOCK)
                .setTab(tab)
                .defaultRecipe()
                .build();
        this.addEntry(barStools);

        // palm_bar is a storage block with its own BE; it has the bar storage menu
        // the back has the bottle shelf, those pixels are masked
        palmBars = SimpleEntrySet.builder(WoodType.class, "bar",
                        getModBlock("palm_bar", PalmBarBlock.class), palm,
                        w -> new PalmBarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BAMBOO_PLANKS)))
                .addTexture(modRes("block/palm_bar_front"))
                .addTexture(modRes("block/palm_bar_side"))
                .addTextureM(modRes("block/palm_bar_back"), maskRes("block/ldb/masks/palm_bar_back_m"))
                .addTexture(modRes("block/palm_bar_bottom"))
                .addTexture(modRes("block/palm_cabinet_top"))
                .addTile(EntityTypeRegistry.PALM_BAR_BLOCK_ENTITY)
                .addTag(BlockTags.MINEABLE_WITH_AXE, Registries.BLOCK)
                .setTab(tab)
                .defaultRecipe()
                .build();
        this.addEntry(palmBars);

        // chair/table/cabinet are the same archetypes as vinery's, so they only generate without vinery
        if (!ModList.get().isLoaded("vinery")) {
            // chair_wood is wood art but has no palm prefix, so it gets its own generated copy + a ref rewrite
            // chair_1 mixes wood and fabric, so its wood is tinted behind a mask while the fabric is kept
            chairs = SimpleEntrySet.builder(WoodType.class, "chair",
                            getModBlock("palm_chair", PalmChairBlock.class), palm,
                            w -> new PalmChairBlock(Utils.copyPropertySafe(w.planks).pushReaction(PushReaction.IGNORE)))
                    .addTexture(modRes("block/palm_planks"))
                    .addTextureC(modRes("block/chair_wood"), "block/palm_chair_wood")
                    .addTextureMC(modRes("block/chair_1"), maskRes("block/ldb/masks/chair_1_m"),
                            PaletteStrategies.MAIN_CHILD, "block/palm_chair_1")
                    .addModelTransform(t -> t.addModifier((s, id, wood) -> {
                        String dst = LetsDoEveryCompat.MODID + ":block/ldb/" + wood.getNamespace() + "/" + wood.getTypeName() + "_";
                        return s.replace("beachparty:block/chair_wood", dst + "chair_wood")
                                .replace("beachparty:block/chair_1", dst + "chair_1");
                    }))
                    .setRenderType(RenderLayer.CUTOUT)
                    .addTag(BlockTags.MINEABLE_WITH_AXE, Registries.BLOCK)
                    .setTab(tab)
                    .defaultRecipe()
                    .build();
            this.addEntry(chairs);

            // connected_support model pulls palm_planks for the leg
            tables = SimpleEntrySet.builder(WoodType.class, "table",
                            getModBlock("palm_table", PalmTableBlock.class), palm,
                            w -> new PalmTableBlock(Utils.copyPropertySafe(w.planks)))
                    .addTextureM(modRes("block/palm_table_top"), maskRes("block/ldb/masks/palm_table_top_m"))
                    .addTextureM(modRes("block/palm_table_side_1"), maskRes("block/ldb/masks/palm_table_side_1_m"))
                    .addTexture(modRes("block/palm_table_side_2"))
                    .addTexture(modRes("block/palm_table_bottom"))
                    .addTexture(modRes("block/palm_planks"))
                    .setRenderType(RenderLayer.CUTOUT)
                    .addTag(BlockTags.MINEABLE_WITH_AXE, Registries.BLOCK)
                    .setTab(tab)
                    .defaultRecipe()
                    .build();
            this.addEntry(tables);

            // cabinet has its own BE
            cabinets = SimpleEntrySet.builder(WoodType.class, "cabinet",
                            getModBlock("palm_cabinet", PalmCabinetBlock.class), palm,
                            w -> new PalmCabinetBlock(Utils.copyPropertySafe(w.planks),
                                    () -> SoundEvents.BAMBOO_WOOD_TRAPDOOR_OPEN, () -> SoundEvents.BAMBOO_WOOD_TRAPDOOR_CLOSE))
                    .addTexture(modRes("block/palm_cabinet_front"))
                    .addTexture(modRes("block/palm_cabinet_front_open"))
                    .addTexture(modRes("block/palm_cabinet_side"))
                    .addTexture(modRes("block/palm_cabinet_top"))
                    .addTile(EntityTypeRegistry.CABINET_BLOCK_ENTITY)
                    .addTag(BlockTags.MINEABLE_WITH_AXE, Registries.BLOCK)
                    .setTab(tab)
                    .defaultRecipe()
                    .build();
            this.addEntry(cabinets);
        }
    }

    // ec skips vanilla woods assuming the mod ships them itself. beachparty only ships palm
    @Override
    public boolean isEntryAlreadyRegistered(String entrySetId, ResourceLocation blockId, BlockType blockType, Registry<?> registry) {
        if (blockType.isVanilla() && !registry.containsKey(blockId) && !bcHasBlock(blockId)) {
            return false;
        }
        return super.isEntryAlreadyRegistered(entrySetId, blockId, blockType, registry);
    }

    // entry names are <wood>_<piece>, same as beachparty's own palm_ blocks
    private static boolean bcHasBlock(ResourceLocation blockId) {
        String path = blockId.getPath();
        String blockName = path.substring(path.lastIndexOf('/') + 1);
        return BuiltInRegistries.BLOCK.containsKey(ResourceLocation.fromNamespaceAndPath("beachparty", blockName));
    }
}