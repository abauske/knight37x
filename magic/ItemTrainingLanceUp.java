package knight37x.magic;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import knight37x.lance.item.ItemLanceUp;

public class ItemTrainingLanceUp extends ItemLanceUp {
	
	public IIcon[] icons = new IIcon[16];
	public final String[] colorNames = new String[] {"white", "orange", "magenta", "lightBlue", "yellow", "lime", "pink", "gray", "lightGray", "cyan", "purple", "blue", "brown", "green", "red", "black"};

	public ItemTrainingLanceUp(Item switchTo, String material) {
		super(switchTo, material);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World par2World, EntityPlayer par3EntityPlayer) {
    	if(this.switchTo != null) {
			ItemStack newLance = new ItemStack(this.switchTo, 1);
			newLance.stackTagCompound = itemstack.stackTagCompound;
			EnchantmentHelper.setEnchantments(EnchantmentHelper.getEnchantments(itemstack), newLance);
			newLance.setItemDamage(itemstack.getItemDamage());
			return newLance;
    	}
    	return itemstack;
	}

	@Override
	public void registerIcons(IIconRegister reg) {
		this.getIcons(reg);
	}
	
	public IIcon[] getIcons(IIconRegister reg) {
		for(int i = 0; i < icons.length; i++) {
			this.icons[i] = reg.registerIcon("magic:lanceWool_" + colorNames[i]);
		}
		return this.icons;
	}
	
	@Override
	public IIcon getIconIndex(ItemStack stack) {
		NBTTagCompound tag = stack.stackTagCompound;
		if(tag != null && tag.hasKey("colorDamage")) {
			return this.icons[tag.getInteger("colorDamage")];
		}
		return this.icons[0];
	}

	@Override
	public IIcon getIcon(ItemStack stack, int renderPass, EntityPlayer player, ItemStack usingItem, int useRemaining) {
		return this.getIconIndex(stack);
	}

	@Override
	public IIcon getIcon(ItemStack stack, int pass) {
		return this.getIconIndex(stack);
	}
}
