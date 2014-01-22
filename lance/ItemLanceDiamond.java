package knight37x.lance;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemLanceDiamond extends ItemLance {
	
	private final String material = "Diamond";
	private final int strengh = 10;
	
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
		return Lance.lanceUpDia;
	}

}
