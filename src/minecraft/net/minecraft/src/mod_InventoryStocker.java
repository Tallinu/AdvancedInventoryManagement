package net.minecraft.src;

import net.minecraft.src.forge.*;
import java.io.File;
import java.util.*;
import net.minecraft.client.Minecraft;
import kaijin.InventoryStocker.*;

public class mod_InventoryStocker extends NetworkMod {
	static Configuration configuration = new Configuration(new File("config/InventoryStocker.cfg"));
	static int InventoryStockerBlockID = configurationProperties();
    public static final Block InventoryStocker = new BlockInventoryStocker(InventoryStockerBlockID, 0).setHardness(0.2F).setResistance(5F).setStepSound(Block.soundStoneFootstep).setBlockName("oreTitanium");
    
    public static mod_InventoryStocker instance;
    
    public mod_InventoryStocker() {
		instance = this;
    }

    public void load() {
    	MinecraftForgeClient.preloadTexture("/Kaijin/StockerBlock/terrain.png");
        ModLoader.registerBlock(InventoryStocker);
        ModLoader.addName(InventoryStocker, "Inventory Stocker");
        ModLoader.addRecipe(new ItemStack(InventoryStocker, 16), new Object[] {"XX", "XX", Character.valueOf('X'), Block.dirt});
        // needs more GUI stuff added
//		MinecraftForge.setGuiHandler(null, null);
    }

    public static int configurationProperties() {
    	configuration.load();
    	InventoryStockerBlockID = Integer.parseInt(configuration.getOrCreateBlockIdProperty("Inventory Stocker", 250).value);
    	configuration.save();
    	return InventoryStockerBlockID;
    }
    
    @Override
    public String getVersion() {
        return "1.0.0";
    }
	@Override public boolean clientSideRequired() { return true; }
	@Override public boolean serverSideRequired() { return false; }
}
