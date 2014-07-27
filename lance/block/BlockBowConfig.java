package knight37x.lance.block;

import knight37x.lance.Lance;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockBowConfig extends Block {

	@SideOnly(Side.CLIENT)
    private IIcon top;
	@SideOnly(Side.CLIENT)
    private IIcon bottom;
	
	public BlockBowConfig(Material m) {
		super(m);
	}

    /**
     * Gets the block's texture. Args: side, meta
     */
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int i, int j)
    {
        return i == 1 ? this.top : (i == 0 ? this.bottom : this.blockIcon);
    }

    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister reg)
    {
        this.blockIcon = reg.registerIcon("lance:bowConfig_side");
        this.top = reg.registerIcon("lance:bowConfig_top");
        this.bottom = reg.registerIcon("lance:bowConfig_bottom");
    }

    /**
     * Called upon block activation (right click on the block.)
     */
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int l, float m, float n, float o) {
    	if(player.isSneaking()) {
    		return false;
    	}
        if (world.isRemote) {
            return true;
        } else {
            player.openGui(Lance.instance, 0, world, x, y, z);
            return true;
        }
    }
}
