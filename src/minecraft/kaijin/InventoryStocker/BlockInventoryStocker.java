package kaijin.InventoryStocker;

import net.minecraft.src.forge.*;
import java.util.*;
import net.minecraft.src.*;
import net.minecraft.src.mod_InventoryStocker.*;
import kaijin.InventoryStocker.*;

public class BlockInventoryStocker extends BlockContainer implements ITextureProvider
{
    public BlockInventoryStocker(int i, int j)
    {
        super(i, j, Material.iron);
    }

    public void addCreativeItems(ArrayList itemList)
    {
        itemList.add(new ItemStack(this));
    }

    public String getTextureFile()
    {
        return "/kaijin/InventoryStocker/terrain.png";
    }

	public int getBlockTextureFromSide(int i)
	{
		// Needs to call tile entity to determine correct facing and animation state?
		switch (i)
		{
		case 0: // Bottom
			return 16;
		case 1: // Top
			return 16;
		case 2: // North/South
			return 1;
		case 3: // North/South
			return 0;
		default: // 4-5 East/West
			return 19;
		}
	}

    @Override
    public boolean blockActivated(World world, int x, int y, int z, EntityPlayer entityplayer)
    {
        if (!Utils.isClient(world))
        {
            entityplayer.openGui(mod_InventoryStocker.instance, 1, world, x, y, z);
        }
        return true;
    }

    @Override
    public TileEntity getBlockEntity()
    {
        return new TileEntityInventoryStocker();
    }
    
    public void onBlockPlaced(World world, int x, int y, int z, int facing)
    {
    	TileEntity tile = world.getBlockTileEntity(x, y, z);
    };
    
	public void onBlockRemoval(World world, int x, int y, int z)
	{
		Utils.preDestroyBlock(world, x, y, z);
		super.onBlockRemoval(world, x, y, z);
	}
}
