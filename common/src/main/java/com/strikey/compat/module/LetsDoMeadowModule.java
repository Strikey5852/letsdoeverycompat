package com.strikey.compat.module;

import com.strikey.compat.LetsDoEveryCompat;
import dev.architectury.platform.Platform;
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
import net.satisfy.meadow.core.block.CheeseRackBlock;
import net.satisfy.meadow.core.block.PineWallCabinetBlock;
import net.satisfy.meadow.core.block.SeatBenchBlock;
import net.satisfy.meadow.core.registry.EntityTypeRegistry;

import java.util.function.Supplier;

public class LetsDoMeadowModule extends EveryCompatModule {

    private static ResourceLocation maskRes(String path) {
        return ResourceLocation.fromNamespaceAndPath(LetsDoEveryCompat.MODID, path);
    }

    // cabinet art is candlelight's
    private static ResourceLocation cabinetRes(String path) {
        return ResourceLocation.fromNamespaceAndPath("candlelight", path);
    }

    public final SimpleEntrySet<WoodType, CheeseRackBlock> cheeseRacks;
    public final SimpleEntrySet<WoodType, SeatBenchBlock> benches;
    public final SimpleEntrySet<WoodType, PineWallCabinetBlock> wallCabinets;

    public LetsDoMeadowModule(String modId) {
        super(modId, "ldm", LetsDoEveryCompat.MODID);
        Supplier<CreativeModeTab> tab = getTab(modRes("meadow"));
        Supplier<WoodType> pine = WoodTypeRegistry.INSTANCE.makeFutureHolder(modRes("pine"));

        // rack side names lack a wood prefix, so re-point them ourselves
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
                .addTile(() -> EntityTypeRegistry.STORAGE_ENTITY.get())
                .addTag(BlockTags.MINEABLE_WITH_AXE, Registries.BLOCK)
                .setTab(tab)
                .defaultRecipe()
                .build();
        this.addEntry(cheeseRacks);

        benches = SimpleEntrySet.builder(WoodType.class, "bench",
                        getModBlock("pine_bench", SeatBenchBlock.class), pine,
                        w -> new SeatBenchBlock(BlockBehaviour.Properties.of().strength(2.0F, 3.0F).sound(SoundType.WOOD)))
                .addTexture(modRes("block/pine_log_side"))
                .addTexture(modRes("block/pine_log_top"))
                .addTexture(modRes("block/stripped_pine_log_side"))
                .addTag(BlockTags.MINEABLE_WITH_AXE, Registries.BLOCK)
                .setTab(tab)
                .defaultRecipe()
                .build();
        this.addEntry(benches);

        wallCabinets = SimpleEntrySet.builder(WoodType.class, "wall_cabinet",
                        getModBlock("pine_wall_cabinet", PineWallCabinetBlock.class), pine,
                        w -> new PineWallCabinetBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS),
                                () -> EntityTypeRegistry.CABINET_BLOCK_ENTITY.get(),
                                SoundEvents.WOODEN_TRAPDOOR_OPEN, SoundEvents.WOODEN_TRAPDOOR_CLOSE))
                .addTexture(modRes("block/pine_wall_cabinet"))
                .addTextureMC(cabinetRes("block/oak_cabinet_front"), maskRes("block/ldc/masks/oak_cabinet_front_m"),
                        PaletteStrategies.MAIN_CHILD, "block/pine_cabinet_front_closed")
                .addTextureC(cabinetRes("block/oak_cabinet_open"), "block/pine_cabinet_front_open")
                .addTile(() -> EntityTypeRegistry.CABINET_BLOCK_ENTITY.get())
                .addTag(BlockTags.MINEABLE_WITH_AXE, Registries.BLOCK)
                .setTab(tab)
                .defaultRecipe()
                .build();
        // needs candlelight for the door texture
        if (Platform.isModLoaded("candlelight")) {
            this.addEntry(wallCabinets);
        }
    }

    // clone vanilla woods meadow doesn't ship
    @Override
    public boolean isEntryAlreadyRegistered(String entrySetId, ResourceLocation blockId, BlockType blockType, Registry<?> registry) {
        if (blockType.isVanilla() && !registry.containsKey(blockId) && !meadowHasBlock(blockId)) {
            return false;
        }
        return super.isEntryAlreadyRegistered(entrySetId, blockId, blockType, registry);
    }

    private static boolean meadowHasBlock(ResourceLocation blockId) {
        String path = blockId.getPath();
        String blockName = path.substring(path.lastIndexOf('/') + 1);
        return BuiltInRegistries.BLOCK.containsKey(ResourceLocation.fromNamespaceAndPath("meadow", blockName));
    }
}
