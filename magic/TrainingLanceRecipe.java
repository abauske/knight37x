package knight37x.magic;

import knight37x.lance.Lance;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class TrainingLanceRecipe implements IRecipe {

	@Override
	public boolean matches(InventoryCrafting craft, World var2) {
		if(craft != null) {
			ItemStack wool = craft.getStackInSlot(2);
			ItemStack shaft1 = craft.getStackInSlot(4);
			ItemStack shaft2 = craft.getStackInSlot(6);
			if(wool != null && shaft1 != null && shaft2 != null) {
				if(wool.getItem() == Item.getItemFromBlock(Blocks.wool) && shaft1.getItem() == Lance.shaft && shaft2.getItem() == Lance.shaft) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting craft) {
		if(craft != null) {
			ItemStack wool = craft.getStackInSlot(2);
			ItemStack shaft1 = craft.getStackInSlot(4);
			ItemStack shaft2 = craft.getStackInSlot(6);
			if(wool != null && shaft1 != null && shaft2 != null) {
				if(wool.getItem() == Item.getItemFromBlock(Blocks.wool) && shaft1.getItem() == Lance.shaft && shaft2.getItem() == Lance.shaft) {
					ItemStack result = new ItemStack(Base.training_lance_up, 1);
					NBTTagCompound tag = result.stackTagCompound = new NBTTagCompound();
					tag.setInteger("colorDamage", wool.getItemDamage());
					return result;
				}
			}
		}
		return null;
	}

	@Override
	public int getRecipeSize() {
		return 10;
	}

	@Override
	public ItemStack getRecipeOutput() {
		return null;
	}

}
