package com.strikey.compat.fabric.client;

import net.fabricmc.api.ClientModInitializer;

public class LetsDoEveryCompatFabricClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        // the lattice mixin in common does all the client work
    }
}