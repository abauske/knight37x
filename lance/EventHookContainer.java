package knight37x.lance;

import knight37x.lance.item.ItemSks;
import knight37x.lance.item.ItemSpearFire;

import com.google.common.eventbus.Subscribe;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.ItemPickupEvent;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayer.EnumStatus;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemFlintAndSteel;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;

/**
 * Name and cast of this class are irrelevant
 */
public class EventHookContainer {
	/**
	 * The key is the @ForgeSubscribe annotation and the cast of the Event you
	 * put in as argument. The method name you pick does not matter. Method
	 * signature is public void, always.
	 */
	@SubscribeEvent
	public void itemCrafted(ItemCraftedEvent event) {
		if(event.crafting.getItem() instanceof ItemSpearFire) {
			for(int i = 0; i < event.craftMatrix.getSizeInventory(); i++) {
				ItemStack stack = event.craftMatrix.getStackInSlot(i);
				if(stack != null && stack.getItem() == Items.flint_and_steel) {
					if(stack.getMaxDamage() - stack.getItemDamage() > 0) {
						ItemStack newStack = new ItemStack(Items.flint_and_steel, 2, stack.getItemDamage() + 1);
		    			event.craftMatrix.setInventorySlotContents(i, newStack);
					}
					return ;
				}
			}
		}
	}
	
	@SubscribeEvent
	public void entityFallen(LivingFallEvent event) {
		if(event.entityLiving instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.entityLiving;
			ItemStack stack = player.getCurrentEquippedItem();
			if(stack != null && stack.getItem() instanceof ItemSks) {
				if(player.isBlocking() && ((ItemSks) stack.getItem()).isBoosted()) {
					event.distance = 1;
				}
			}
		}
	}
}