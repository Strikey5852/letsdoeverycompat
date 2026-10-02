package com.strikey.compat.module;

import com.berksire.furniture.core.block.BenchBlock;
import com.berksire.furniture.core.block.CabinetBlock;
import com.berksire.furniture.core.block.ClockBlock;
import com.berksire.furniture.core.block.DeskBlock;
import com.berksire.furniture.core.block.DeskChairBlock;
import com.berksire.furniture.core.block.DresserBlock;
import com.berksire.furniture.core.block.GrandfatherClockBlock;
import com.berksire.furniture.core.block.MirrorBlock;
import com.berksire.furniture.core.block.ShutterBlock;
import com.berksire.furniture.core.block.WardrobeBlock;
import com.berksire.furniture.core.registry.EntityTypeRegistry;
import com.berksire.furniture.core.registry.SoundRegistry;
import com.strikey.compat.LetsDoEveryCompat;
import net.mehvahdjukaar.every_compat.api.RenderLayer;
import net.mehvahdjukaar.every_compat.api.SimpleEntrySet;
import net.mehvahdjukaar.every_compat.api.TextureInfo;
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
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.PushReaction;

import java.util.function.Supplier;

public class LetsDoFurnitureModule extends EveryCompatModule {

    private static ResourceLocation maskRes(String path) {
        return ResourceLocation.fromNamespaceAndPath(LetsDoEveryCompat.MODID, path);
    }

    public final SimpleEntrySet<WoodType, BenchBlock> benches;
    public final SimpleEntrySet<WoodType, DeskBlock> desks;
    public final SimpleEntrySet<WoodType, DeskChairBlock> deskChairs;
    public final SimpleEntrySet<WoodType, MirrorBlock> mirrors;
    public final SimpleEntrySet<WoodType, ShutterBlock> shutters;
    public final SimpleEntrySet<WoodType, CabinetBlock> cabinets;
    public final SimpleEntrySet<WoodType, ClockBlock> clocks;
    public final SimpleEntrySet<WoodType, GrandfatherClockBlock> grandfatherClocks;
    public final SimpleEntrySet<WoodType, DresserBlock> dressers;
    public final SimpleEntrySet<WoodType, WardrobeBlock> wardrobes;

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

        mirrors = SimpleEntrySet.builder(WoodType.class, "mirror",
                        getModBlock("oak_mirror", MirrorBlock.class), () -> VanillaWoodTypes.OAK,
                        w -> new MirrorBlock(Utils.copyPropertySafe(w.planks).pushReaction(PushReaction.IGNORE)))
                .addTexture(modRes("block/oak_mirror_back"))
                .addTextureM(modRes("block/oak_mirror_single"), maskRes("block/ldf/masks/oak_mirror_single_m"))
                .addTextureM(modRes("block/oak_mirror_top"), maskRes("block/ldf/masks/oak_mirror_top_m"))
                .addTextureM(modRes("block/oak_mirror_middle"), maskRes("block/ldf/masks/oak_mirror_middle_m"))
                .addTextureM(modRes("block/oak_mirror_bottom"), maskRes("block/ldf/masks/oak_mirror_bottom_m"))
                .addTextureM(modRes("item/oak_mirror"), maskRes("item/ldf/masks/oak_mirror_m"))
                .addTag(BlockTags.MINEABLE_WITH_AXE, Registries.BLOCK)
                .setTab(tab)
                .defaultRecipe()
                .build();
        this.addEntry(mirrors);

        shutters = SimpleEntrySet.builder(WoodType.class, "shutter",
                        getModBlock("oak_shutter", ShutterBlock.class), () -> VanillaWoodTypes.OAK,
                        w -> new ShutterBlock(Utils.copyPropertySafe(w.planks).pushReaction(PushReaction.IGNORE)))
                .addTexture(modRes("block/oak_shutter_main"))
                .addTexture(modRes("block/oak_shutter_top"))
                .addTexture(modRes("block/oak_shutter_middle"))
                .addTexture(modRes("block/oak_shutter_bottom"))
                .addTag(BlockTags.MINEABLE_WITH_AXE, Registries.BLOCK)
                .setTab(tab)
                .defaultRecipe()
                .build();
        this.addEntry(shutters);

        // shared BE; keep the supplier lazy, the String overload throws
        cabinets = SimpleEntrySet.builder(WoodType.class, "cabinet",
                        getModBlock("oak_cabinet", CabinetBlock.class), () -> VanillaWoodTypes.OAK,
                        w -> new CabinetBlock(BlockBehaviour.Properties.of().strength(2.0F, 3.0F).sound(SoundType.WOOD),
                                SoundRegistry.CABINET_OPEN, SoundRegistry.CABINET_CLOSE))
                .addTextureM(modRes("block/oak_cabinet"), maskRes("block/ldf/masks/oak_cabinet_m"))
                .addTexture(modRes("block/oak_cabinet_front"))
                .addTexture(modRes("block/oak_cabinet_side"))
                .addTexture(modRes("block/oak_cabinet_top"))
                .addTexture(modRes("block/oak_cabinet_bottom"))
                .addTile(EntityTypeRegistry.CABINET_BLOCK_ENTITY)
                .addTag(BlockTags.MINEABLE_WITH_AXE, Registries.BLOCK)
                .setTab(tab)
                .defaultRecipe()
                .build();
        this.addEntry(cabinets);

        // flat model; be renderer draws the face from the keepNamespace texture
        clocks = SimpleEntrySet.builder(WoodType.class, "clock",
                        getModBlock("oak_clock", ClockBlock.class), () -> VanillaWoodTypes.OAK,
                        w -> new ClockBlock(Utils.copyPropertySafe(w.planks).noOcclusion().pushReaction(PushReaction.IGNORE),
                                ClockBlock.WoodType.OAK))
                .addTexture(TextureInfo.of(modRes("entity/oak_clock"))
                        .mask(maskRes("entity/ldf/masks/oak_clock_m")).keepNamespace())
                .addTextureM(modRes("item/oak_clock"), maskRes("item/ldf/masks/oak_clock_m"))
                .addTile(EntityTypeRegistry.CLOCK_BLOCK_ENTITY)
                .addTag(BlockTags.MINEABLE_WITH_AXE, Registries.BLOCK)
                .setTab(tab)
                .defaultRecipe()
                .build();
        this.addEntry(clocks);

        grandfatherClocks = SimpleEntrySet.builder(WoodType.class, "grandfather_clock",
                        getModBlock("oak_grandfather_clock", GrandfatherClockBlock.class), () -> VanillaWoodTypes.OAK,
                        w -> new GrandfatherClockBlock(Utils.copyPropertySafe(w.planks).noOcclusion().pushReaction(PushReaction.IGNORE)))
                .addTexture(TextureInfo.of(modRes("entity/oak_grandfather_clock"))
                        .mask(maskRes("entity/ldf/masks/oak_grandfather_clock_m")).keepNamespace())
                .addTextureM(modRes("item/oak_grandfather_clock"), maskRes("item/ldf/masks/oak_grandfather_clock_m"))
                .addTile(EntityTypeRegistry.GRANDFATHER_CLOCK_BLOCK_ENTITY)
                .addTag(BlockTags.MINEABLE_WITH_AXE, Registries.BLOCK)
                .setTab(tab)
                .defaultRecipe()
                .build();
        this.addEntry(grandfatherClocks);

        dressers = SimpleEntrySet.builder(WoodType.class, "dresser",
                        getModBlock("oak_dresser", DresserBlock.class), () -> VanillaWoodTypes.OAK,
                        w -> new DresserBlock(BlockBehaviour.Properties.of().strength(2.0F, 3.0F).sound(SoundType.WOOD),
                                SoundRegistry.CABINET_OPEN, SoundRegistry.CABINET_CLOSE))
                .addTextureM(modRes("block/oak_dresser_front"), maskRes("block/ldf/masks/oak_dresser_front_m"))
                .addTextureM(modRes("block/oak_dresser_frontside"), maskRes("block/ldf/masks/oak_dresser_frontside_m"))
                .addTexture(modRes("block/oak_dresser_side"))
                .addTexture(modRes("block/oak_dresser_side_inner"))
                .addTexture(modRes("block/oak_dresser_back"))
                .addTexture(modRes("block/oak_dresser_back_middle"))
                .addTextureM(modRes("block/oak_dresser_middle"), maskRes("block/ldf/masks/oak_dresser_middle_m"))
                .addTexture(modRes("block/oak_cabinet_top"))
                .addTexture(modRes("block/oak_desk_connected_side"))
                .addTexture(modRes("block/oak_desk_connected_middle"))
                .addTile(EntityTypeRegistry.DRESSER_BLOCK_ENTITY)
                .addTag(BlockTags.MINEABLE_WITH_AXE, Registries.BLOCK)
                .setTab(tab)
                .defaultRecipe()
                .build();
        this.addEntry(dressers);

        wardrobes = SimpleEntrySet.builder(WoodType.class, "wardrobe",
                        getModBlock("oak_wardrobe", WardrobeBlock.class), () -> VanillaWoodTypes.OAK,
                        w -> new WardrobeBlock(BlockBehaviour.Properties.of().strength(2.0F, 3.0F).sound(SoundType.WOOD)))
                .addTextureM(modRes("block/oak_wardrobe_front_bottom_closed"), maskRes("block/ldf/masks/oak_wardrobe_front_bottom_closed_m"))
                .addTextureM(modRes("block/oak_wardrobe_front_bottom_open"), maskRes("block/ldf/masks/oak_wardrobe_front_bottom_open_m"))
                .addTextureM(modRes("block/oak_wardrobe_front_top_closed"), maskRes("block/ldf/masks/oak_wardrobe_front_top_closed_m"))
                .addTextureM(modRes("block/oak_wardrobe_front_top_open"), maskRes("block/ldf/masks/oak_wardrobe_front_top_open_m"))
                .addTexture(modRes("block/oak_wardrobe_inside_bottom"))
                .addTexture(modRes("block/oak_wardrobe_inside_top"))
                .addTexture(modRes("block/oak_wardrobe_side_bottom"))
                .addTexture(modRes("block/oak_wardrobe_side_top"))
                .addTexture(modRes("block/oak_cabinet_top"))
                .setRenderType(RenderLayer.CUTOUT)
                .addTile(EntityTypeRegistry.WARDROBE_BLOCK_ENTITY)
                .addTag(BlockTags.MINEABLE_WITH_AXE, Registries.BLOCK)
                .setTab(tab)
                .defaultRecipe()
                .build();
        this.addEntry(wardrobes);
    }

    // clone vanilla woods furniture doesn't ship
    @Override
    public boolean isEntryAlreadyRegistered(String entrySetId, ResourceLocation blockId, BlockType blockType, Registry<?> registry) {
        if (blockType.isVanilla() && !registry.containsKey(blockId) && !furnitureHasBlock(blockId)) {
            return false;
        }
        return super.isEntryAlreadyRegistered(entrySetId, blockId, blockType, registry);
    }

    private static boolean furnitureHasBlock(ResourceLocation blockId) {
        String path = blockId.getPath();
        String blockName = path.substring(path.lastIndexOf('/') + 1);
        return BuiltInRegistries.BLOCK.containsKey(ResourceLocation.fromNamespaceAndPath("furniture", blockName));
    }
}
