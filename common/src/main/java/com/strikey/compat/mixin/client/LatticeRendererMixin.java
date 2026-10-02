package com.strikey.compat.mixin.client;

import com.strikey.compat.LetsDoEveryCompat;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.satisfy.vinery.client.render.block.LatticeRenderer;
import net.satisfy.vinery.core.block.LatticeBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;

@Mixin(LatticeRenderer.class)
public abstract class LatticeRendererMixin {

    @Shadow
    private static Map<Block, ResourceLocation> textureMap;

    // the map is built lazily, so populate it once
    @Unique
    private static boolean ldv$done;

    @Inject(method = "getTextureMap", at = @At("TAIL"))
    private static void ldv$addGeneratedLattices(CallbackInfoReturnable<Map<Block, ResourceLocation>> cir) {
        if (ldv$done) {
            return;
        }
        ldv$done = true;
        for (Map.Entry<ResourceKey<Block>, Block> e : BuiltInRegistries.BLOCK.entrySet()) {
            Block block = e.getValue();
            if (block instanceof LatticeBlock && e.getKey().location().getNamespace().equals(LetsDoEveryCompat.MODID)) {
                // ec keeps vinery's lattice/ subfolder, so the texture is one deeper than the block id
                String path = e.getKey().location().getPath();
                int slash = path.lastIndexOf('/');
                String texPath = "textures/block/" + path.substring(0, slash + 1) + "lattice/" + path.substring(slash + 1) + ".png";
                textureMap.putIfAbsent(block, ResourceLocation.fromNamespaceAndPath(LetsDoEveryCompat.MODID, texPath));
            }
        }
    }
}