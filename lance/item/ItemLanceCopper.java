package knight37x.lance.item;

import knight37x.lance.Lance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemLanceCopper extends ItemLance {
	
	private final String material = "Copper";
	private final int strengh = 4;
	
	@Override
	public String getMaterialString() {
		return this.material;
	}
	
	@Override
	public int getStrengh() {
		return this.strengh;
	}

	@Override
	public Item getSwitch() {
		return Lance.lanceUpCopper;
	}
	
}
