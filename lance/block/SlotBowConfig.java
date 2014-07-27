package knight37x.lance.block;

import knight37x.lance.item.ItemMayorBow;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotBowConfig extends Slot {

	public SlotBowConfig(IInventory par1iInventory, int par2, int par3, int par4) {
		super(par1iInventory, par2, par3, par4);
	}
	
	@Override
	public boolean isItemValid(ItemStack itemstack)
    {
		if(itemstack == null || itemstack.getItem() == null) {
			return false;
		}
        return itemstack.getItem() instanceof ItemMayorBow ? true : false;
    }

}
