package com.strikey.compat.module;

import com.strikey.compat.LetsDoEveryCompat;
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
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.satisfy.hearth_and_timber.core.block.PillarBlock;
import net.satisfy.hearth_and_timber.core.block.RailingBlock;
import net.satisfy.hearth_and_timber.core.block.SupportBlock;
import net.satisfy.hearth_and_timber.core.block.WoodenBoardBlock;

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

    public LetsDoHearthAndTimberModule(String modId) {
        super(modId, "ldh", LetsDoEveryCompat.MODID);
        Supplier<CreativeModeTab> tab = getTab(modRes("hearth_and_timber"));
        // hearth ships 9 of 12 vanilla woods (not bamboo/crimson/warped) and self-comps
        // palm/pine/dark_cherry/arolla_pine, so oak is the base for the gaps we fill
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

        // stairs + slab read the shingles art and parent vanilla stairs/slab
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
        // the board model's spruce_* parent is shared and stays at hearth, the oak_board override
        // is what carries the wood
        boards = SimpleEntrySet.builder(WoodType.class, "board",
                        getModBlock("oak_board", WoodenBoardBlock.class), oak,
                        w -> new WoodenBoardBlock())
                .addTexture(modRes("block/oak_board"))
                .addTag(BlockTags.MINEABLE_WITH_AXE, Registries.BLOCK)
                .setTab(tab)
                .defaultRecipe()
                .build();
        this.addEntry(boards);

        // support/pillar/railing share the window casing plank art, same shared spruce templates
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
    }

    // ec skips vanilla woods assuming the mod ships them itself. hearth ships 9 of 12
    @Override
    public boolean isEntryAlreadyRegistered(String entrySetId, ResourceLocation blockId, BlockType blockType, Registry<?> registry) {
        if (blockType.isVanilla() && !registry.containsKey(blockId) && !htHasBlock(blockId)) {
            return false;
        }
        boolean r = super.isEntryAlreadyRegistered(entrySetId, blockId, blockType, registry);
        return r;
    }

    // entry names are <wood>_<piece>, same as hearth's own oak_ blocks
    private static boolean htHasBlock(ResourceLocation blockId) {
        String path = blockId.getPath();
        String blockName = path.substring(path.lastIndexOf('/') + 1);
        return BuiltInRegistries.BLOCK.containsKey(ResourceLocation.fromNamespaceAndPath("hearth_and_timber", blockName));
    }
}