package knight37x.magic;

import java.util.ArrayList;

import knight37x.magic.items.ItemTrainingArmor;
import net.minecraft.block.BlockColored;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class RecipeArmorDyes implements IRecipe {
    /**
     * Used to check if a recipe matches current crafting inventory
     */
    public boolean matches(InventoryCrafting par1InventoryCrafting, World par2World)
    {
        ItemStack itemstack = null;
        ArrayList arraylist = new ArrayList();

        for (int i = 0; i < par1InventoryCrafting.getSizeInventory(); ++i)
        {
            ItemStack itemstack1 = par1InventoryCrafting.getStackInSlot(i);

            if (itemstack1 != null)
            {
                if (itemstack1.getItem() instanceof ItemArmor)
                {
                    ItemArmor itemarmor = (ItemArmor)itemstack1.getItem();

                    if (itemarmor.getArmorMaterial() != Base.trainingsMaterial || itemstack != null)
                    {
                        return false;
                    }

                    itemstack = itemstack1;
                }
                else
                {
                    if (itemstack1.getItem() != Items.dye)
                    {
                        return false;
                    }

                    arraylist.add(itemstack1);
                }
            }
        }

        return itemstack != null && !arraylist.isEmpty();
    }

    /**
     * Returns an Item that is the result of this recipe
     */
    public ItemStack getCraftingResult(InventoryCrafting craft) {
        ItemStack armor = null;
        ItemStack dye = null;
        for(int i = 0; i < craft.getSizeInventory(); i++) {
        	ItemStack stack = craft.getStackInSlot(i);
        	if(stack != null) {
        		if(stack.getItem() instanceof ItemArmor) {
        			armor = stack.copy();
        		} else if(stack.getItem() instanceof ItemDye) {
        			dye = stack.copy();
        		}
        		if(armor != null && dye != null) {
        			break;
        		}
        	}
        }
        if(armor != null && dye != null) {
        	int color = ((ItemTrainingArmor) armor.getItem()).colors.containsKey(15 - dye.getItemDamage()) ? ((ItemTrainingArmor) armor.getItem()).colors.get(15 - dye.getItemDamage()) : 0x555555;
        	((ItemTrainingArmor) armor.getItem()).setColor(armor, color);
        	return armor;
		} else {
			return null;
		}
    }

    /**
     * Returns the size of the recipe area
     */
    public int getRecipeSize()
    {
        return 10;
    }

    public ItemStack getRecipeOutput()
    {
        return null;
    }
}