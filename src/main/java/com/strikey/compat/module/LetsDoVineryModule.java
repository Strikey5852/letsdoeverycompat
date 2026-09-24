package com.strikey.compat.module;

import com.strikey.compat.LetsDoEveryCompat;
import net.mehvahdjukaar.every_compat.api.PaletteStrategies;
import net.mehvahdjukaar.every_compat.api.RenderLayer;
import net.mehvahdjukaar.every_compat.api.SimpleEntrySet;
import net.mehvahdjukaar.every_compat.modules.EveryCompatModule;
import net.mehvahdjukaar.moonlight.api.set.BlockType;
import net.mehvahdjukaar.moonlight.api.set.wood.VanillaWoodTypes;
import net.mehvahdjukaar.moonlight.api.set.wood.WoodType;
import net.mehvahdjukaar.moonlight.api.set.wood.WoodTypeRegistry;
import net.mehvahdjukaar.moonlight.api.util.Utils;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.PushReaction;
import net.satisfy.vinery.core.block.BigBottleStorageBlock;
import net.satisfy.vinery.core.block.BigTableBlock;
import net.satisfy.vinery.core.block.CabinetBlock;
import net.satisfy.vinery.core.block.ChairBlock;
import net.satisfy.vinery.core.block.DarkCherryBarrelBlock;
import net.satisfy.vinery.core.block.FourBottleStorageBlock;
import net.satisfy.vinery.core.block.LatticeBlock;
import net.satisfy.vinery.core.block.NineBottleStorageBlock;
import net.satisfy.vinery.core.block.ShelfBlock;
import net.satisfy.vinery.core.block.TableBlock;
import net.satisfy.vinery.core.registry.EntityTypeRegistry;
import net.satisfy.vinery.core.registry.SoundEventRegistry;

import java.util.function.Supplier;

public class LetsDoVineryModule extends EveryCompatModule {

    // masks sit in our jar, so not modRes()
    private static ResourceLocation maskRes(String path) {
        return ResourceLocation.fromNamespaceAndPath(LetsDoEveryCompat.MODID, path);
    }

    public final SimpleEntrySet<WoodType, FourBottleStorageBlock> wineRackSmall;
    public final SimpleEntrySet<WoodType, BigBottleStorageBlock> wineRackMid;
    public final SimpleEntrySet<WoodType, NineBottleStorageBlock> wineRackBig;
    public final SimpleEntrySet<WoodType, LatticeBlock> lattices;
    public final SimpleEntrySet<WoodType, ChairBlock> chairs;
    public final SimpleEntrySet<WoodType, TableBlock> tables;
    public final SimpleEntrySet<WoodType, BigTableBlock> bigTables;
    public final SimpleEntrySet<WoodType, CabinetBlock> cabinets;
    public final SimpleEntrySet<WoodType, CabinetBlock> drawers;
    public final SimpleEntrySet<WoodType, ShelfBlock> shelves;
    public final SimpleEntrySet<WoodType, DarkCherryBarrelBlock> barrels;

    public LetsDoVineryModule(String modId) {
        super(modId, "ldv", LetsDoEveryCompat.MODID);
        Supplier<CreativeModeTab> tab = getTab(modRes("vinery"));
        // vinery's furniture only exists in dark_cherry, so it is the base wood for these entries
        Supplier<WoodType> darkCherry = WoodTypeRegistry.INSTANCE.makeFutureHolder(modRes("dark_cherry"));

        // racks are model-rendered, so the shared storage BE picks the bottle renderer from type()
        wineRackSmall = SimpleEntrySet.builder(WoodType.class, "wine_rack_small",
                        getModBlock("oak_wine_rack_small", FourBottleStorageBlock.class), () -> VanillaWoodTypes.OAK,
                        w -> new FourBottleStorageBlock(BlockBehaviour.Properties.of().strength(2.0F, 3.0F)
                                .sound(SoundType.WOOD).noOcclusion()))
                // rack sides use their own name, else they'd overwrite vinery's cabinet side
                .addTextureC(modRes("block/oak_cabinet_side"), "block/oak_wine_rack_side")
                .addTexture(modRes("block/oak_cabinet_top"))
                .addModelTransform(t -> t.replaceString("cabinet_side", "wine_rack_side"))
                .addTile(EntityTypeRegistry.STORAGE_ENTITY)
                .addTag(BlockTags.MINEABLE_WITH_AXE, Registries.BLOCK)
                .setTab(tab)
                .defaultRecipe()
                .build();
        this.addEntry(wineRackSmall);

        wineRackBig = SimpleEntrySet.builder(WoodType.class, "wine_rack_big",
                        getModBlock("oak_wine_rack_big", NineBottleStorageBlock.class), () -> VanillaWoodTypes.OAK,
                        w -> new NineBottleStorageBlock(BlockBehaviour.Properties.of().strength(2.0F, 3.0F)
                                .sound(SoundType.WOOD).noOcclusion()))
                .addTextureC(modRes("block/oak_cabinet_side"), "block/oak_wine_rack_side")
                .addTexture(modRes("block/oak_cabinet_top"))
                .addModelTransform(t -> t.replaceString("cabinet_side", "wine_rack_side"))
                .addTile(EntityTypeRegistry.STORAGE_ENTITY)
                .addTag(BlockTags.MINEABLE_WITH_AXE, Registries.BLOCK)
                .setTab(tab)
                .defaultRecipe()
                .build();
        this.addEntry(wineRackBig);

        // the mid rack has a door, so vinery registers it as cutout; its handle is hand tinted per wood
        wineRackMid = SimpleEntrySet.builder(WoodType.class, "wine_rack_mid",
                        getModBlock("oak_wine_rack_mid", BigBottleStorageBlock.class), () -> VanillaWoodTypes.OAK,
                        w -> new BigBottleStorageBlock(BlockBehaviour.Properties.of().strength(2.0F, 3.0F)
                                .sound(SoundType.WOOD).noOcclusion()))
                // the rack door is its own art, so it keeps its own front name
                .addTextureMC(modRes("block/oak_cabinet_front"), maskRes("block/ldv/masks/oak_cabinet_front_m"),
                        PaletteStrategies.MAIN_CHILD, "block/oak_wine_rack_front")
                .addTextureC(modRes("block/oak_cabinet_side"), "block/oak_wine_rack_side")
                .addTexture(modRes("block/oak_cabinet_top"))
                .addModelTransform(t -> t.replaceString("cabinet_side", "wine_rack_side").replaceString("cabinet_front", "wine_rack_front"))
                .setRenderType(RenderLayer.CUTOUT)
                .addTile(EntityTypeRegistry.STORAGE_ENTITY)
                .addTag(BlockTags.MINEABLE_WITH_AXE, Registries.BLOCK)
                .setTab(tab)
                .defaultRecipe()
                .build();
        this.addEntry(wineRackMid);

        // lattice is RenderShape.INVISIBLE - the be renderer draws it from a per-wood texture.
        // a client mixin (LatticeRendererMixin) points our blocks at the generated textures.
        lattices = SimpleEntrySet.builder(WoodType.class, "lattice",
                        getModBlock("oak_lattice", LatticeBlock.class), () -> VanillaWoodTypes.OAK,
                        w -> new LatticeBlock(BlockBehaviour.Properties.of().strength(2.0F, 3.0F)
                                .sound(SoundType.WOOD).noOcclusion()))
                .addTextureM(modRes("block/lattice/oak_lattice"), maskRes("block/ldv/masks/oak_lattice_m"))
                .setRenderType(RenderLayer.CUTOUT)
                .addTile(EntityTypeRegistry.LATTICE)
                .addTag(BlockTags.MINEABLE_WITH_AXE, Registries.BLOCK)
                .setTab(tab)
                .defaultRecipe()
                .build();
        this.addEntry(lattices);

        // chairs + tables are model rendered with transparent gaps, so cutout like vinery registers them
        chairs = SimpleEntrySet.builder(WoodType.class, "chair",
                        getModBlock("dark_cherry_chair", ChairBlock.class), darkCherry,
                        w -> new ChairBlock(BlockBehaviour.Properties.of().strength(2.0F, 3.0F).sound(SoundType.WOOD)))
                .addTexture(modRes("block/dark_cherry_chair_base"))
                .addTexture(modRes("block/dark_cherry_chair_top"))
                .setRenderType(RenderLayer.CUTOUT)
                .addTag(BlockTags.MINEABLE_WITH_AXE, Registries.BLOCK)
                .setTab(tab)
                .defaultRecipe()
                .build();
        this.addEntry(chairs);

        // vinery copies oak planks for its table, so take the same properties from the target wood's planks
        tables = SimpleEntrySet.builder(WoodType.class, "table",
                        getModBlock("dark_cherry_table", TableBlock.class), darkCherry,
                        w -> new TableBlock(Utils.copyPropertySafe(w.planks)))
                .addTextureM(modRes("block/dark_cherry_table"), maskRes("block/ldv/masks/dark_cherry_table_m"))
                .addTexture(modRes("block/dark_cherry_table_top"))
                .addTexture(modRes("block/dark_cherry_table_middle"))
                .addTexture(modRes("block/dark_cherry_table_connected"))
                .setRenderType(RenderLayer.CUTOUT)
                .addTag(BlockTags.MINEABLE_WITH_AXE, Registries.BLOCK)
                .setTab(tab)
                .defaultRecipe()
                .build();
        this.addEntry(tables);

        // two blocks long and not cutout. vinery only drops it from part=head, so the loot has to be copied
        bigTables = SimpleEntrySet.builder(WoodType.class, "big_table",
                        getModBlock("dark_cherry_big_table", BigTableBlock.class), darkCherry,
                        w -> new BigTableBlock(BlockBehaviour.Properties.of().strength(2.0F, 2.0F)
                                .pushReaction(PushReaction.IGNORE)))
                .addTextureM(modRes("block/dark_cherry_big_table"), maskRes("block/ldv/masks/dark_cherry_big_table_m"))
                .addTexture(modRes("block/dark_cherry_big_table_side"))
                .addTexture(modRes("block/stripped_dark_cherry_log_side"))
                .addTextureM(modRes("item/dark_cherry_big_table"), maskRes("item/ldv/masks/dark_cherry_big_table_m"))
                .copyParentDrop()
                .addTag(BlockTags.MINEABLE_WITH_AXE, Registries.BLOCK)
                .setTab(tab)
                .defaultRecipe()
                .build();
        this.addEntry(bigTables);

        // storage blocks with a shared BE. handles/glass are not wood, masked to keep their base colour
        cabinets = SimpleEntrySet.builder(WoodType.class, "cabinet",
                        getModBlock("dark_cherry_cabinet", CabinetBlock.class), darkCherry,
                        w -> new CabinetBlock(BlockBehaviour.Properties.of().strength(2.0F, 3.0F).sound(SoundType.WOOD),
                                SoundEventRegistry.CABINET_OPEN.get(), SoundEventRegistry.CABINET_CLOSE.get()))
                .addTextureM(modRes("block/dark_cherry_cabinet_front"), maskRes("block/ldv/masks/dark_cherry_cabinet_front_m"))
                .addTextureM(modRes("block/dark_cherry_cabinet_front_open"), maskRes("block/ldv/masks/dark_cherry_cabinet_front_open_m"))
                .addTexture(modRes("block/dark_cherry_cabinet_side"))
                .addTexture(modRes("block/dark_cherry_drawer_top"))
                .addTile(EntityTypeRegistry.CABINET_BLOCK_ENTITY)
                .addTag(BlockTags.MINEABLE_WITH_AXE, Registries.BLOCK)
                .setTab(tab)
                .defaultRecipe()
                .build();
        this.addEntry(cabinets);

        // the drawer is the same cabinet block only with different open/close sounds
        drawers = SimpleEntrySet.builder(WoodType.class, "drawer",
                        getModBlock("dark_cherry_drawer", CabinetBlock.class), darkCherry,
                        w -> new CabinetBlock(BlockBehaviour.Properties.of().strength(2.0F, 3.0F).sound(SoundType.WOOD),
                                SoundEventRegistry.DRAWER_OPEN.get(), SoundEventRegistry.DRAWER_CLOSE.get()))
                .addTextureM(modRes("block/dark_cherry_drawer_front"), maskRes("block/ldv/masks/dark_cherry_drawer_front_m"))
                .addTextureM(modRes("block/dark_cherry_cabinet_front_open"), maskRes("block/ldv/masks/dark_cherry_cabinet_front_open_m"))
                .addTexture(modRes("block/dark_cherry_drawer_side"))
                .addTexture(modRes("block/dark_cherry_drawer_top"))
                .addTile(EntityTypeRegistry.CABINET_BLOCK_ENTITY)
                .addTag(BlockTags.MINEABLE_WITH_AXE, Registries.BLOCK)
                .setTab(tab)
                .defaultRecipe()
                .build();
        this.addEntry(drawers);

        // shelf shares the racks' storage BE; its item sprite is item/generated so it gets its own texture
        shelves = SimpleEntrySet.builder(WoodType.class, "shelf",
                        getModBlock("dark_cherry_shelf", ShelfBlock.class), darkCherry,
                        w -> new ShelfBlock(BlockBehaviour.Properties.of().strength(2.0F, 3.0F).sound(SoundType.WOOD).noOcclusion()))
                .addTexture(modRes("block/dark_cherry_drawer_top"))
                .addTextureM(modRes("item/dark_cherry_shelf"), maskRes("item/ldv/masks/dark_cherry_shelf_m"))
                .addTile(EntityTypeRegistry.STORAGE_ENTITY)
                .addTag(BlockTags.MINEABLE_WITH_AXE, Registries.BLOCK)
                .setTab(tab)
                .defaultRecipe()
                .build();
        this.addEntry(shelves);

        // barrel copies vanilla barrel block properties; the iron bands are masked
        barrels = SimpleEntrySet.builder(WoodType.class, "barrel",
                        getModBlock("dark_cherry_barrel", DarkCherryBarrelBlock.class), darkCherry,
                        w -> new DarkCherryBarrelBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BARREL)))
                .addTextureM(modRes("block/dark_cherry_barrel_top"), maskRes("block/ldv/masks/dark_cherry_barrel_top_m"))
                .addTexture(modRes("block/dark_cherry_barrel_top_open"))
                .addTexture(modRes("block/dark_cherry_cabinet_side"))
                .addTexture(modRes("block/dark_cherry_drawer_top"))
                .addTile(EntityTypeRegistry.DARK_CHERRY_BARREL_ENTITY)
                .addTag(BlockTags.MINEABLE_WITH_AXE, Registries.BLOCK)
                .setTab(tab)
                .defaultRecipe()
                .build();
        this.addEntry(barrels);
    }

    // ec skips vanilla woods assuming the mod ships them itself. vinery only has dark_cherry furniture
    // and misses crimson/warped racks, so those are the ones cloned here
    @Override
    public boolean isEntryAlreadyRegistered(String entrySetId, ResourceLocation blockId, BlockType blockType, Registry<?> registry) {
        if (blockType.isVanilla() && !registry.containsKey(blockId) && !vineryHasBlock(blockId)) {
            return false;
        }
        return super.isEntryAlreadyRegistered(entrySetId, blockId, blockType, registry);
    }

    // entry names are <wood>_<piece>, same as vinery's own oak_/dark_cherry_ blocks
    private static boolean vineryHasBlock(ResourceLocation blockId) {
        String path = blockId.getPath();
        String blockName = path.substring(path.lastIndexOf('/') + 1);
        return BuiltInRegistries.BLOCK.containsKey(ResourceLocation.fromNamespaceAndPath("vinery", blockName));
    }
}