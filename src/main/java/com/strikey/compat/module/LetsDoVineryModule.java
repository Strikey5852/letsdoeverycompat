package com.strikey.compat.module;

import com.strikey.compat.LetsDoEveryCompat;
import net.mehvahdjukaar.every_compat.api.RenderLayer;
import net.mehvahdjukaar.every_compat.api.SimpleEntrySet;
import net.mehvahdjukaar.every_compat.modules.EveryCompatModule;
import net.mehvahdjukaar.moonlight.api.set.wood.VanillaWoodTypes;
import net.mehvahdjukaar.moonlight.api.set.wood.WoodType;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.satisfy.vinery.core.block.BigBottleStorageBlock;
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

    public LetsDoVineryModule(String modId) {
        super(modId, "ldv", LetsDoEveryCompat.MODID);
        Supplier<CreativeModeTab> tab = getTab(modRes("vinery"));

        // racks are model-rendered, so the shared storage BE picks the bottle renderer from type()
        wineRackSmall = SimpleEntrySet.builder(WoodType.class, "wine_rack_small",
                        getModBlock("oak_wine_rack_small", FourBottleStorageBlock.class), () -> VanillaWoodTypes.OAK,
                        w -> new FourBottleStorageBlock(BlockBehaviour.Properties.of().strength(2.0F, 3.0F)
                                .sound(SoundType.WOOD).noOcclusion()))
                .addTexture(modRes("block/oak_cabinet_side"))
                .addTexture(modRes("block/oak_cabinet_top"))
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
                .addTexture(modRes("block/oak_cabinet_side"))
                .addTexture(modRes("block/oak_cabinet_top"))
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
                .addTextureM(modRes("block/oak_cabinet_front"), maskRes("block/ldv/masks/oak_cabinet_front_m"))
                .addTexture(modRes("block/oak_cabinet_side"))
                .addTexture(modRes("block/oak_cabinet_top"))
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
    }
}