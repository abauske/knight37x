package knight37x.magic;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;

import knight37x.lance.StaticMethods;
import knight37x.magic.items.ItemTrainingArmor;
import net.minecraft.block.BlockColored;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

public class RecipeArmor implements IRecipe {

	private Integer[][][] recipes = new Integer[][][] {
			{	{0, 1, 2, 3, 5}, {3, 4, 5, 6, 8}	},
			{	{0, 2, 3, 4, 5, 6, 7, 8}			},
			{	{0, 1, 2, 3, 5, 6, 8}				},
			{	{0, 2, 3, 5}, {3, 5, 6, 8}			}
	};
	
	/*
	 * Color damage value to hex value mapping
	 */
	public final HashMap<Integer, Integer> colors = new HashMap();
	
	public RecipeArmor() {
		colors.put(0, 0xFFFFFF);
		colors.put(1, 0xFFAA00);
		colors.put(2, 0xFF55FF);
		colors.put(3, 0x5495C3);
		colors.put(4, 0xFFFF55);
		colors.put(5, 0x55FF55);
		colors.put(6, 0xFF86CF);
		colors.put(7, 0x555555);
		colors.put(8, 0xAAAAAA);
		colors.put(9, 0x00AAAA);
		colors.put(10, 0x7500B0);
		colors.put(11, 0x0000AA);
		colors.put(12, 0x4B1C08);
		colors.put(13, 0x085112);
		colors.put(14, 0xAA0000);
		colors.put(15, 0x000000);
	}
	
	@Override
	public boolean matches(InventoryCrafting matrix, World world) {
		if(matrix != null) {
			ArrayList<Integer> wool = new ArrayList();
			for(int i = 0; i < matrix.getSizeInventory(); i++) {
				ItemStack stack = matrix.getStackInSlot(i);
				if(stack != null) {
					if(stack.getItem() == Item.getItemFromBlock(Blocks.wool)) {
						wool.add(i);
					} else {
						return false;
					}
				}
			}
			
			wool.trimToSize();
			if(wool.size() <= 0) return false;
			
//			boolean helmet = true;
//			for(int i = 0; i < wool.size(); i++) {
//				if(wool.size() != this.rec[0][0].length || wool.get(i) != this.rec[0][0][i]) {
//					helmet = false;
//					break;
//				}
//			}
//			if(helmet) return helmet;
			
			boolean[][] matches = new boolean[][] {{true, true}, {true}, {true}, {true, true}};
			for (int i = 0; i < this.recipes.length; i++) {
				for (int j = 0; j < this.recipes[i].length; j++) {
					for(int k = 0; k < wool.size(); k++) {
						if(wool.size() != this.recipes[i][j].length || wool.get(k) != this.recipes[i][j][k]) {
							matches[i][j] = false;
							break;
						}
					}
				}
			}
			for(boolean[] bArray : matches) {
				for(boolean b : bArray) {
					if(b) {
						return true;
					}
				}
			}
		}
		return false;
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting matrix) {
		if(matrix != null) {
			ArrayList<Integer> wool = new ArrayList();
			// color -1 when different
			int color = -10;
			for(int i = 0; i < matrix.getSizeInventory(); i++) {
				ItemStack stack = matrix.getStackInSlot(i);
				if(stack != null) {
					if(stack.getItem() == Item.getItemFromBlock(Blocks.wool)) {
						wool.add(i);
						if(color == -10) {
							color = stack.getItemDamage();
						} else if(color != stack.getItemDamage()) {
							color = -1;
						}
					} else {
						return null;
					}
				}
			}
			
			wool.trimToSize();
			if(wool.size() <= 0) return null;
			
//			boolean helmet = true;
//			for(int i = 0; i < wool.size(); i++) {
//				if(wool.size() != this.rec[0][0].length || wool.get(i) != this.rec[0][0][i]) {
//					helmet = false;
//					break;
//				}
//			}
//			if(helmet) return helmet;
			
			if(color < 0 || color > 15) {
				color = 7;
			}
			
			boolean[][] matches = new boolean[][] {{true, true}, {true}, {true}, {true, true}};
			for (int i = 0; i < this.recipes.length; i++) {
				for (int j = 0; j < this.recipes[i].length; j++) {
					for(int k = 0; k < wool.size(); k++) {
						if(wool.size() != this.recipes[i][j].length || wool.get(k) != this.recipes[i][j][k]) {
							matches[i][j] = false;
							break;
						}
					}
				}
			}
			for(int i = 0; i < matches.length; i++) {
				boolean[] bArray = matches[i];
				for(boolean b : bArray) {
					if(b) {
						ItemTrainingArmor armor;
						switch(i) {
						case 0: armor = Base.training_helmet;
								break;
						case 1: armor = Base.training_chestplate;
								break;
						case 2: armor = Base.training_leggings;
								break;
						case 3: armor = Base.training_boots;
								break;
						default: return null;
						}
						ItemStack stack = new ItemStack(armor);
//						armor.setColor(stack, 16252);
						armor.setColor(stack, this.colors.get(color));
						return stack;
//						return this.handleColor(stack, color);
					}
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
