package knight37x.magic.items;

import static io.netty.buffer.Unpooled.buffer;

import java.util.List;

import org.lwjgl.input.Mouse;

import io.netty.buffer.ByteBuf;
import knight37x.lance.Lance;
import knight37x.lance.StaticMethods;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.network.internal.FMLProxyPacket;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderPearl;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

public class ItemEnderCannon extends Item {
	
	@Override
	public void onCreated(ItemStack itemstack, World world, EntityPlayer player) {
		super.onCreated(itemstack, world, player);
		this.setNewNBT(itemstack);
	}
	
	public void setNewNBT(ItemStack stack) {
		if(stack.stackTagCompound == null) {
			stack.stackTagCompound = new NBTTagCompound();
			NBTTagCompound tag = stack.stackTagCompound;
			tag.setInteger("storage", 0);
		}
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer player) {
		boolean flag = true;
		if (!player.capabilities.isCreativeMode) {
			flag = player.inventory.consumeInventoryItem(Items.ender_pearl);
		}
		if(flag) {
			world.playSoundAtEntity(player, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
			
			itemstack.damageItem(1, player);
			if (!world.isRemote) {
				world.spawnEntityInWorld(new EntityEnderPearl(world, player));
			}
		}
		return itemstack;
	}
	
	@Override
	public Item getContainerItem() {
		return super.getContainerItem();
	}

	@Override
	public ItemStack getContainerItem(ItemStack stack) {
		NBTTagCompound tag = stack.stackTagCompound;
		if(tag == null) {
			this.setNewNBT(stack);
			tag = stack.stackTagCompound;
		}
		tag.setInteger("storage", 100);
		return super.getContainerItem(stack);
	}

	@Override
	public void onUpdate(ItemStack itemstack, World world, Entity entity, int par4, boolean par5) {
		super.onUpdate(itemstack, world, entity, par4, par5);
		
		
		NBTTagCompound tag = itemstack.stackTagCompound;
		if(tag == null) {
			tag = itemstack.stackTagCompound = new NBTTagCompound();
			tag.setInteger("storage", 0);
		}
	}

	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean par4) {
		if (itemstack.stackTagCompound != null) {
			list.add("Stored Ender Pearls: ");
			list.add(EnumChatFormatting.GREEN.toString() + itemstack.stackTagCompound.getInteger("storage"));
		}
	}
}
