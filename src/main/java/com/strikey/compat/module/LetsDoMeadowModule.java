package com.strikey.compat.module;

import com.strikey.compat.LetsDoEveryCompat;
import net.mehvahdjukaar.every_compat.api.PaletteStrategies;
import net.mehvahdjukaar.every_compat.api.SimpleEntrySet;
import net.mehvahdjukaar.every_compat.modules.EveryCompatModule;
import net.mehvahdjukaar.moonlight.api.set.BlockType;
import net.mehvahdjukaar.moonlight.api.set.wood.WoodType;
import net.mehvahdjukaar.moonlight.api.set.wood.WoodTypeRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.fml.ModList;
import net.satisfy.meadow.core.block.BenchBlock;
import net.satisfy.meadow.core.block.CabinetBlock;
import net.satisfy.meadow.core.block.CheeseRackBlock;
import net.satisfy.meadow.core.registry.EntityTypeRegistry;

import java.util.function.Supplier;

public class LetsDoMeadowModule extends EveryCompatModule {

    // masks sit in our jar, so not modRes()
    private static ResourceLocation maskRes(String path) {
        return ResourceLocation.fromNamespaceAndPath(LetsDoEveryCompat.MODID, path);
    }

    // candlelight's art: modRes() resolves against this module's mod, which is meadow
    private static ResourceLocation cabinetRes(String path) {
        return ResourceLocation.fromNamespaceAndPath("candlelight", path);
    }

    public final SimpleEntrySet<WoodType, CheeseRackBlock> cheeseRacks;
    public final SimpleEntrySet<WoodType, BenchBlock> benches;
    public final SimpleEntrySet<WoodType, CabinetBlock> wallCabinets;

    public LetsDoMeadowModule(String modId) {
        super(modId, "ldm", LetsDoEveryCompat.MODID);
        Supplier<CreativeModeTab> tab = getTab(modRes("meadow"));
        // meadow only ships pine, so pine is the base for the rest
        Supplier<WoodType> pine = WoodTypeRegistry.INSTANCE.makeFutureHolder(modRes("pine"));

        // rack sides are wood but named without the wood in front, so ec leaves that ref at meadow.
        // give it our own name and point the model at it, else the rack stays pine coloured
        cheeseRacks = SimpleEntrySet.builder(WoodType.class, "cheese_rack",
                        getModBlock("pine_cheese_rack", CheeseRackBlock.class), pine,
                        w -> new CheeseRackBlock(BlockBehaviour.Properties.of().strength(2.0F, 3.0F)
                                .sound(SoundType.WOOD).noOcclusion()))
                .addTexture(modRes("block/pine_table_middle"))
                .addTexture(modRes("block/pine_table_top"))
                .addTextureC(modRes("block/cheese_rack_side"), "block/pine_cheese_rack_side")
                .addModelTransform(t -> t.addModifier((s, id, wood) ->
                        s.replace("meadow:block/cheese_rack_side", LetsDoEveryCompat.MODID + ":block/ldm/"
                                + wood.getNamespace() + "/" + wood.getTypeName() + "_cheese_rack_side")))
                // the rack makes meadow's StorageBlockEntity, and that one is hardcoded to meadow:storage
                .addTile(EntityTypeRegistry.STORAGE_ENTITY)
                .addTag(BlockTags.MINEABLE_WITH_AXE, Registries.BLOCK)
                .setTab(tab)
                .defaultRecipe()
                .build();
        this.addEntry(cheeseRacks);

        // the bench is log art, and meadow doesn't cut it out like its tables/chairs
        benches = SimpleEntrySet.builder(WoodType.class, "bench",
                        getModBlock("pine_bench", BenchBlock.class), pine,
                        w -> new BenchBlock(BlockBehaviour.Properties.of().strength(2.0F, 3.0F).sound(SoundType.WOOD)))
                .addTexture(modRes("block/pine_log_side"))
                .addTexture(modRes("block/pine_log_top"))
                .addTexture(modRes("block/stripped_pine_log_side"))
                .addTag(BlockTags.MINEABLE_WITH_AXE, Registries.BLOCK)
                .setTab(tab)
                .defaultRecipe()
                .build();
        this.addEntry(benches);

        wallCabinets = SimpleEntrySet.builder(WoodType.class, "wall_cabinet",
                        getModBlock("pine_wall_cabinet", CabinetBlock.class), pine,
                        w -> new CabinetBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS),
                                () -> SoundEvents.WOODEN_TRAPDOOR_OPEN, () -> SoundEvents.WOODEN_TRAPDOOR_CLOSE,
                                () -> true))
                .addTexture(modRes("block/pine_wall_cabinet"))
                // ec names the generated file after the base wood, so the custom paths keep meadow's
                // names and the models need no rewriting
                .addTextureMC(cabinetRes("block/oak_cabinet_front"), maskRes("block/ldc/masks/oak_cabinet_front_m"),
                        PaletteStrategies.MAIN_CHILD, "block/pine_cabinet_front_closed")
                .addTextureC(cabinetRes("block/oak_cabinet_open"), "block/pine_cabinet_front_open")
                .addTile(EntityTypeRegistry.CABINET_BLOCK_ENTITY)
                .addTag(BlockTags.MINEABLE_WITH_AXE, Registries.BLOCK)
                .setTab(tab)
                .defaultRecipe()
                .build();
        // the door is candlelight's cabinet art, so without candlelight there's no texture for it
        // and the whole entry would be missing one
        if (ModList.get().isLoaded("candlelight")) {
            this.addEntry(wallCabinets);
        }
    }

    // ec skips vanilla woods assuming the mod ships them itself. meadow only ships pine
    @Override
    public boolean isEntryAlreadyRegistered(String entrySetId, ResourceLocation blockId, BlockType blockType, Registry<?> registry) {
        if (blockType.isVanilla() && !registry.containsKey(blockId) && !meadowHasBlock(blockId)) {
            return false;
        }
        return super.isEntryAlreadyRegistered(entrySetId, blockId, blockType, registry);
    }

    // entry names are <wood>_<piece>, same as meadow's own pine_ blocks
    private static boolean meadowHasBlock(ResourceLocation blockId) {
        String path = blockId.getPath();
        String blockName = path.substring(path.lastIndexOf('/') + 1);
        return BuiltInRegistries.BLOCK.containsKey(ResourceLocation.fromNamespaceAndPath("meadow", blockName));
    }
}
