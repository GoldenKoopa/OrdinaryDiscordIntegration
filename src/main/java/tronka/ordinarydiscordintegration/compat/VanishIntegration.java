package tronka.ordinarydiscordintegration.compat;

import me.drex.vanish.api.VanishAPI;
import me.drex.vanish.api.VanishEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.server.network.ServerPlayerEntity;
import tronka.ordinarydiscordintegration.OrdinaryDiscordIntegration;

import java.util.UUID;

public class VanishIntegration {
    private boolean loaded;
    public VanishIntegration(OrdinaryDiscordIntegration integration) {
        if (!integration.getConfig().integrations.enableVanishIntegration) { return; }
        loaded = FabricLoader.getInstance().isModLoaded("melius-vanish");
        VanishEvents.VANISH_EVENT.register((player, isVanished) -> {
            if (isVanished) {
                integration.getChatBridge().onPlayerLeave(player);
            } else {
                integration.getChatBridge().onPlayerJoin(player);
            }
        });
    }

    public boolean isVanished(ServerPlayerEntity player) {
        return loaded && VanishAPI.isVanished(player);
    }
}
