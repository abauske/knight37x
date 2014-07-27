package knight37x.magic;

import knight37x.magic.items.ItemWand;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class LoadingRecipe implements IRecipe {

	@Override
	public boolean matches(InventoryCrafting inv, World world) {
		int wandCount = 0;
		int manaCount = 0;
		for(int i = 0; i < inv.getSizeInventory(); i++) {
			ItemStack stack = inv.getStackInSlot(i);
			if(stack != null) {
				if(stack.getItem() == Base.wand) {
					wandCount++;
				} else if(stack.getItem() == Base.mana) {
					manaCount += stack.stackSize;
				} else {
					return false;
				}
			}
		}
		return wandCount == 1 && manaCount > 0;
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting inv) {
		int wandPos = 0;
		int manaCount = 0;
		for(int i = 0; i < inv.getSizeInventory(); i++) {
			ItemStack stack = inv.getStackInSlot(i);
			if(stack != null) {
				if(stack.getItem() == Base.wand) {
					wandPos = i;
				} else if(stack.getItem() == Base.mana) {
					manaCount += stack.stackSize;
				}
			}
		}
		ItemStack stack = new ItemStack(Base.wand, 1, inv.getStackInSlot(wandPos).getItemDamage());
		((ItemWand) stack.getItem()).setNewNBT(stack);
		NBTTagCompound tag = stack.stackTagCompound;
		tag.setInteger("storage", inv.getStackInSlot(wandPos).stackTagCompound.getInteger("storage") + manaCount);
		return stack;
	}

	@Override
	public int getRecipeSize() {
		return 2;
	}

	@Override
	public ItemStack getRecipeOutput() {
		ItemStack stack = new ItemStack(Base.wand, 1);
		return null;
	}

}
