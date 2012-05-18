package com.kaijin.InventoryStocker;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.kaijin.InventoryStocker.CommonProxy;

import net.minecraft.src.*;

public class Utils
{
    /*
     * Set the mod version here and it'll update in both client and server mod_ files
     */

    public static String getVersion()
    {
        return "0.3.0";
    }

    public static void init()
    {
        if (CommonProxy.isServer())
        {
            ModLoader.getLogger().info ("InventoryStocker v" + getVersion()+ " loaded.");
        }
    }
    
    //JUST in case we need it
    public String hashSHA1(String tilename)
    {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
        }
        catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        md.update(tilename.getBytes());
 
        byte byteData[] = md.digest();
        
        //convert the byte to hex format method 1
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < byteData.length; i++) {
         sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        }
 
        //System.out.println("Hex format : " + sb.toString());
 
        //convert the byte to hex format method 2
        StringBuffer hexString = new StringBuffer();
        for (int i=0;i<byteData.length;i++) {
            String hex=Integer.toHexString(0xff & byteData[i]);
            if(hex.length()==1) hexString.append('0');
            hexString.append(hex);
        }
        //System.out.println("Hex format : " + hexString.toString());
        return hexString.toString();
    }
    
    public static void dropItems(World world, ItemStack stack, int i, int j, int k)
    {
        float f1 = 0.7F;
        double d = (double)(world.rand.nextFloat() * f1) + (double)(1.0F - f1) * 0.5D;
        double d1 = (double)(world.rand.nextFloat() * f1) + (double)(1.0F - f1) * 0.5D;
        double d2 = (double)(world.rand.nextFloat() * f1) + (double)(1.0F - f1) * 0.5D;
        EntityItem entityitem = new EntityItem(world, (double) i + d,
                (double) j + d1, (double) k + d2, stack);
        entityitem.delayBeforeCanPickup = 10;
        world.spawnEntityInWorld(entityitem);
    }

    public static void dropItems(World world, IInventory inventory, int i, int j, int k)
    {
        for (int l = 0; l < inventory.getSizeInventory(); ++l)
        {
            ItemStack items = inventory.getStackInSlot(l);

            if (items != null && items.stackSize > 0)
            {
                dropItems(world, inventory.getStackInSlot(l).copy(), i, j, k);
            }
        }
    }

    public static void preDestroyBlock(World world, int i, int j, int k)
    {
        TileEntity tile = world.getBlockTileEntity(i, j, k);

        if (tile instanceof IInventory && !CommonProxy.isClient(world))
        {
            dropItems(world, (IInventory) tile, i, j, k);
        }
    }

    /*
     * Convert desired side to actual side based on orientation of block
     * I  Meta
     *    D U N S W E     0 1 2 3 4 5
     * 0  F K T T T T   0 0 1 2 2 2 2
     * 1  K F B B B B   1 1 0 3 3 3 3
     * 2  T B F K L R   2 2 3 0 1 5 4
     * 3  B T K F R L   3 3 2 1 0 4 5
     * 4  L L L R F K   4 5 5 5 4 0 1
     * 5  R R R L K F   5 4 4 4 5 1 0
     *
     */
    public static int lookupRotatedSide(int side, int orientation)
    {
        final int table[][] =
        {
            {0, 1, 2, 2, 2, 2},
            {1, 0, 3, 3, 3, 3},
            {2, 3, 0, 1, 5, 4},
            {3, 2, 1, 0, 4, 5},
            {5, 5, 5, 4, 0, 1},
            {4, 4, 4, 5, 1, 0}
        };
        return table[side][orientation];
    }
}