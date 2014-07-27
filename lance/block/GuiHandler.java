package knight37x.lance.block;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		if(world.getBlock(x, y, z) instanceof BlockBowConfig) {
			return new ContainerBowConfig(player.inventory, world, x, y, z);
		}
		return null;
	}

	// returns an instance of the Gui you made earlier
	@Override
	public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		if (world.getBlock(x, y, z) instanceof BlockBowConfig) {
			return new GuiBowConfig(player.inventory, world, x, y, z, new ContainerBowConfig(player.inventory, world, x, y, z));
		}
		return null;
	}
}