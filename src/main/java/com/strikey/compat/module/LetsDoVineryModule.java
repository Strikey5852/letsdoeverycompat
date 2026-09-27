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
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.satisfy.vinery.core.block.BigBottleStorageBlock;
import net.satisfy.vinery.core.block.DarkCherryBarrelBlock;
import net.satisfy.vinery.core.block.FourBottleStorageBlock;
import net.satisfy.vinery.core.block.LatticeBlock;
import net.satisfy.vinery.core.block.NineBottleStorageBlock;
import net.satisfy.vinery.core.registry.EntityTypeRegistry;

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
    public final SimpleEntrySet<WoodType, DarkCherryBarrelBlock> barrels;

    public LetsDoVineryModule(String modId) {
        super(modId, "ldv", LetsDoEveryCompat.MODID);
        Supplier<CreativeModeTab> tab = getTab(modRes("vinery"));
        // the racks/lattice are oak based, only the barrel is dark_cherry
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

        // the mid rack is cutout in vinery; its handle is hand tinted per wood
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

    // ec skips vanilla woods assuming the mod ships them itself. vinery only has oak/dark_cherry racks,
    // so the other vanilla woods are cloned here
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