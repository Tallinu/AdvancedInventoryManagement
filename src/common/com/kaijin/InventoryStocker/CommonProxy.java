/* Inventory Stocker
 *  Copyright (c) 2012 Yancarlo Ramsey and CJ Bowman
 *  Licensed as open source with restrictions. Please see attached LICENSE.txt.
 */

package com.kaijin.InventoryStocker;

import java.io.File;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.server.FMLServerHandler;
import net.minecraft.src.*;
import net.minecraftforge.common.Configuration;
import com.kaijin.InventoryStocker.*;

public class CommonProxy implements IGuiHandler
{
	public static String BLOCK_PNG = "/com/kaijin/InventoryStocker/textures/terrain.png";

	public void load()
	{

	}

	public boolean isClient(World world)
	{
		return world.isRemote;
	}

	public boolean isServer()
	{
		return true;
	}

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) 
	{
		if (!world.blockExists(x, y, z))
		{
			return null;
		}

		TileEntity tile = world.getBlockTileEntity(x, y, z);

		if (!(tile instanceof TileEntityInventoryStocker))
		{
			return null;
		}

		((TileEntityInventoryStocker)tile).sendSnapshotStateClient((EntityPlayerMP)player);

		return new ContainerInventoryStocker(player.inventory, (TileEntityInventoryStocker)tile);
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) 
	{
		if (!world.blockExists(x, y, z))
		{
			return null;
		}

		TileEntity tile = world.getBlockTileEntity(x, y, z);

		if (!(tile instanceof TileEntityInventoryStocker))
		{
			return null;
		}

		return new GuiInventoryStocker(player.inventory, (TileEntityInventoryStocker)tile);
	}
}