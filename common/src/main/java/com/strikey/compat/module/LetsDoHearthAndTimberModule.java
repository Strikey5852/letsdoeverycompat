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
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.StairBlock;
import net.satisfy.hearth_and_timber.core.block.PillarBlock;
import net.satisfy.hearth_and_timber.core.block.RailingBlock;
import net.satisfy.hearth_and_timber.core.block.SupportBlock;
import net.satisfy.hearth_and_timber.core.block.WindowBlock;
import net.satisfy.hearth_and_timber.core.block.WindowCasingBlock;
import net.satisfy.hearth_and_timber.core.block.WoodenBoardBlock;
import net.satisfy.hearth_and_timber.core.registry.EntityTypeRegistry;

import java.util.function.Supplier;

public class LetsDoHearthAndTimberModule extends EveryCompatModule {

    public final SimpleEntrySet<WoodType, Block> shingles;
    public final SimpleEntrySet<WoodType, StairBlock> shingleStairs;
    public final SimpleEntrySet<WoodType, SlabBlock> shingleSlabs;
    public final SimpleEntrySet<WoodType, RotatedPillarBlock> beams;
    public final SimpleEntrySet<WoodType, WoodenBoardBlock> boards;
    public final SimpleEntrySet<WoodType, SupportBlock> supports;
    public final SimpleEntrySet<WoodType, PillarBlock> pillars;
    public final SimpleEntrySet<WoodType, RailingBlock> railings;
    public final SimpleEntrySet<WoodType, WindowCasingBlock> windowCasings;
    public final SimpleEntrySet<WoodType, Block> windows;
    public final SimpleEntrySet<WoodType, WindowBlock> windowPanes;

    public LetsDoHearthAndTimberModule(String modId) {
        super(modId, "ldh", LetsDoEveryCompat.MODID);
        Supplier<CreativeModeTab> tab = getTab(modRes("hearth_and_timber"));
        Supplier<WoodType> oak = () -> VanillaWoodTypes.OAK;

        shingles = SimpleEntrySet.builder(WoodType.class, "shingles",
                        getModBlock("oak_shingles", Block.class), oak,
                        w -> new Block(Utils.copyPropertySafe(w.planks).strength(2.0F, 3.0F).sound(SoundType.WOOD)))
                .addTexture(modRes("block/oak_shingles"))
                .addTag(BlockTags.MINEABLE_WITH_AXE, Registries.BLOCK)
                .setTab(tab)
                .defaultRecipe()
                .build();
        this.addEntry(shingles);

        shingleStairs = SimpleEntrySet.builder(WoodType.class, "shingle_stairs",
                        getModBlock("oak_shingle_stairs", StairBlock.class), oak,
                        w -> new StairBlock(w.planks.defaultBlockState(), Utils.copyPropertySafe(w.planks)))
                .addTexture(modRes("block/oak_shingles"))
                .addTag(BlockTags.MINEABLE_WITH_AXE, Registries.BLOCK)
                .setTab(tab)
                .defaultRecipe()
                .build();
        this.addEntry(shingleStairs);

        shingleSlabs = SimpleEntrySet.builder(WoodType.class, "shingle_slab",
                        getModBlock("oak_shingle_slab", SlabBlock.class), oak,
                        w -> new SlabBlock(Utils.copyPropertySafe(w.planks)))
                .addTexture(modRes("block/oak_shingles"))
                .addTag(BlockTags.MINEABLE_WITH_AXE, Registries.BLOCK)
                .setTab(tab)
                .defaultRecipe()
                .build();
        this.addEntry(shingleSlabs);

        beams = SimpleEntrySet.builder(WoodType.class, "beam",
                        getModBlock("oak_beam", RotatedPillarBlock.class), oak,
                        w -> new RotatedPillarBlock(Utils.copyPropertySafe(w.planks).strength(2.0F, 3.0F).sound(SoundType.WOOD)))
                .addTexture(modRes("block/oak_beam_side"))
                .addTexture(modRes("block/oak_beam_top"))
                .addTag(BlockTags.MINEABLE_WITH_AXE, Registries.BLOCK)
                .setTab(tab)
                .defaultRecipe()
                .build();
        this.addEntry(beams);
        boards = SimpleEntrySet.builder(WoodType.class, "board",
                        getModBlock("oak_board", WoodenBoardBlock.class), oak,
                        w -> new WoodenBoardBlock())
                .addTexture(modRes("block/oak_board"))
                .addTag(BlockTags.MINEABLE_WITH_AXE, Registries.BLOCK)
                .setTab(tab)
                .defaultRecipe()
                .build();
        this.addEntry(boards);

        supports = SimpleEntrySet.builder(WoodType.class, "support",
                        getModBlock("oak_support", SupportBlock.class), oak,
                        w -> new SupportBlock(Utils.copyPropertySafe(w.planks)))
                .addTexture(modRes("block/oak_board"))
                .addTexture(modRes("block/oak_window_casing"))
                .addTag(BlockTags.MINEABLE_WITH_AXE, Registries.BLOCK)
                .setTab(tab)
                .defaultRecipe()
                .build();
        this.addEntry(supports);

        pillars = SimpleEntrySet.builder(WoodType.class, "pillar",
                        getModBlock("oak_pillar", PillarBlock.class), oak,
                        w -> new PillarBlock(Utils.copyPropertySafe(w.planks)))
                .addTexture(modRes("block/oak_window_casing"))
                .addTag(BlockTags.MINEABLE_WITH_AXE, Registries.BLOCK)
                .setTab(tab)
                .defaultRecipe()
                .build();
        this.addEntry(pillars);

        railings = SimpleEntrySet.builder(WoodType.class, "railing",
                        getModBlock("oak_railing", RailingBlock.class), oak,
                        w -> new RailingBlock(w.planks.defaultBlockState(),
                                Utils.copyPropertySafe(w.planks).noOcclusion()))
                .addTexture(modRes("block/oak_window_casing"))
                .addTag(BlockTags.MINEABLE_WITH_AXE, Registries.BLOCK)
                .setTab(tab)
                .defaultRecipe()
                .build();
        this.addEntry(railings);

        windowCasings = SimpleEntrySet.builder(WoodType.class, "window_casing",
                        getModBlock("oak_window_casing", WindowCasingBlock.class), oak,
                        w -> new WindowCasingBlock(Utils.copyPropertySafe(w.planks).noOcclusion()))
                .addTexture(modRes("block/oak_window_casing"))
                .addTile(EntityTypeRegistry.WINDOW_CASING_BLOCK_ENTITY)
                .setRenderType(RenderLayer.CUTOUT)
                .addTag(BlockTags.MINEABLE_WITH_AXE, Registries.BLOCK)
                .setTab(tab)
                .defaultRecipe()
                .build();
        this.addEntry(windowCasings);

        windows = SimpleEntrySet.builder(WoodType.class, "window",
                        getModBlock("oak_window", Block.class), oak,
                        w -> new Block(Utils.copyPropertySafe(Blocks.GLASS)))
                .addTextureM(modRes("block/oak_window_pane"), maskRes("block/ldh/masks/oak_window_pane_m"))
                .addTexture(modRes("block/oak_window_top"))
                .setRenderType(RenderLayer.CUTOUT)
                .addTag(BlockTags.MINEABLE_WITH_AXE, Registries.BLOCK)
                .setTab(tab)
                .defaultRecipe()
                .build();
        this.addEntry(windows);

        windowPanes = SimpleEntrySet.builder(WoodType.class, "window_pane",
                        getModBlock("oak_window_pane", WindowBlock.class), oak,
                        w -> new WindowBlock(Utils.copyPropertySafe(Blocks.GLASS_PANE).sound(SoundType.GLASS)))
                .addTextureM(modRes("block/oak_window_pane"), maskRes("block/ldh/masks/oak_window_pane_m"))
                .addTextureM(modRes("block/oak_window_pane_top"), maskRes("block/ldh/masks/oak_window_pane_top_m"))
                .addTextureM(modRes("block/oak_window_pane_bottom"), maskRes("block/ldh/masks/oak_window_pane_bottom_m"))
                .addTextureM(modRes("block/oak_window_pane_middle"), maskRes("block/ldh/masks/oak_window_pane_middle_m"))
                .addTexture(modRes("block/oak_window_pane_side"))
                .setRenderType(RenderLayer.CUTOUT)
                .addTag(BlockTags.MINEABLE_WITH_AXE, Registries.BLOCK)
                .setTab(tab)
                .defaultRecipe()
                .build();
        this.addEntry(windowPanes);
    }

    private static ResourceLocation maskRes(String path) {
        return ResourceLocation.fromNamespaceAndPath(LetsDoEveryCompat.MODID, path);
    }

    // clone vanilla woods hearth doesn't ship
    @Override
    public boolean isEntryAlreadyRegistered(String entrySetId, ResourceLocation blockId, BlockType blockType, Registry<?> registry) {
        // palm already ships glass, skip windows
        if (blockType.getNamespace().equals("beachparty") && blockType.getTypeName().equals("palm")
                && (entrySetId.endsWith(":window") || entrySetId.endsWith(":window_pane"))) {
            return true;
        }
        if (blockType.isVanilla() && !registry.containsKey(blockId) && !htHasBlock(blockId)) {
            return false;
        }
        return super.isEntryAlreadyRegistered(entrySetId, blockId, blockType, registry);
    }

    private static boolean htHasBlock(ResourceLocation blockId) {
        String path = blockId.getPath();
        String blockName = path.substring(path.lastIndexOf('/') + 1);
        return BuiltInRegistries.BLOCK.containsKey(ResourceLocation.fromNamespaceAndPath("hearth_and_timber", blockName));
    }
}