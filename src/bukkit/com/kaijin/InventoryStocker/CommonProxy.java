package com.kaijin.InventoryStocker;

import forge.Configuration;
import java.io.File;
import net.minecraft.server.ModLoader;
import net.minecraft.server.NetServerHandler;
import net.minecraft.server.NetworkManager;
import net.minecraft.server.Packet250CustomPayload;
import net.minecraft.server.World;
import net.minecraft.server.mod_InventoryStocker;

public class CommonProxy
{
    public static void load()
    {
        ModLoader.getLogger().info("InventoryStocker v" + mod_InventoryStocker.instance.getVersion() + " loaded.");
    }

    public static Configuration getConfiguration()
    {
        return new Configuration(new File("config/InventoryStocker.cfg"));
    }

    public static World PacketHandlerGetWorld(NetworkManager var0)
    {
        return ((NetServerHandler)var0.getNetHandler()).getPlayerEntity().world;
    }

    public static boolean isClient(World var0)
    {
        return false;
    }

    public static boolean isServer()
    {
        return true;
    }

    public static void sendPacketToPlayer(String var0, Packet250CustomPayload var1)
    {
        ModLoader.getMinecraftServerInstance().serverConfigurationManager.a(var0, var1);
    }

    public static void sendPacketToServer(Packet250CustomPayload var0) {}
}
