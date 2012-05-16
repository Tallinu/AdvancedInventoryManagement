package net.minecraft.server;

import forge.Configuration;
import forge.MinecraftForge;
import forge.NetworkMod;
import java.io.File;
import kaijin.InventoryStocker.BlockInventoryStocker;
import kaijin.InventoryStocker.ConnectionHandler;
import kaijin.InventoryStocker.GuiHandlerInventoryStocker;
import kaijin.InventoryStocker.TileEntityInventoryStocker;

public class mod_InventoryStocker extends NetworkMod
{
    static Configuration configuration = new Configuration(new File("config/InventoryStocker.cfg"));
    static int InventoryStockerBlockID = configurationProperties();
    public static final Block InventoryStocker = (new BlockInventoryStocker(InventoryStockerBlockID, 0)).c(0.75F).b(5.0F).a(Block.h).a("inventoryStocker");
    public static mod_InventoryStocker instance;

    public mod_InventoryStocker()
    {
        instance = this;
    }

    public void load()
    {
        MinecraftForge.versionDetect("Inventory Stocker", 3, 1, 2);
        MinecraftForge.registerConnectionHandler(new ConnectionHandler());
        ModLoader.registerBlock(InventoryStocker);
        ModLoader.registerTileEntity(TileEntityInventoryStocker.class, "InventoryStocker");
        ModLoader.addRecipe(new ItemStack(InventoryStocker, 16), new Object[] {"XX", "XX", 'X', Block.DIRT});
        ModLoader.addRecipe(new ItemStack(InventoryStocker, 1), new Object[] {"IWI", "PRP", "IWI", 'I', Item.IRON_INGOT, 'W', Block.WOOD, 'P', Block.PISTON, 'R', Item.REDSTONE});
        MinecraftForge.setGuiHandler(instance, new GuiHandlerInventoryStocker());
    }

    public static int configurationProperties()
    {
        configuration.load();
        InventoryStockerBlockID = Integer.parseInt(configuration.getOrCreateBlockIdProperty("InventoryStocker", 249).value);
        configuration.save();
        return InventoryStockerBlockID;
    }

    public String getVersion()
    {
        return "0.2.5";
    }

    public boolean clientSideRequired()
    {
        return true;
    }

    public boolean serverSideRequired()
    {
        return false;
    }
}