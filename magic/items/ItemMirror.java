package knight37x.magic.items;

import java.util.Iterator;

import knight37x.lance.StaticMethods;
import knight37x.magic.Base;
import knight37x.magic.entity.EntityLightning;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class ItemMirror extends Item {

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		Iterator<EntityLivingBase> it = ((ItemWand) Base.wand).victims.iterator();
		while(it.hasNext()) {
			if(it.next() == player) {
				it.remove();
				if(StaticMethods.isRunningOnClient()) {
					((ItemWand) Base.wand).sendSpawnLightning((EntityClientPlayerMP) player, true);
					stack.damageItem(1, player);
					if(stack.getItemDamage() > stack.getMaxDamage()) {
						stack.stackSize = 0;
					}
				}
			}
		}
		return stack;
	}
	
}
