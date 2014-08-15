package knight37x.lance;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import knight37x.lance.item.ItemSks;
import knight37x.lance.item.ItemSpearFire;

import com.google.common.eventbus.Subscribe;

import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLServerStartedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.ItemPickupEvent;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayer.EnumStatus;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemFlintAndSteel;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;

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
	
	@SubscribeEvent
	public void playerJoinWorld(PlayerEvent.PlayerLoggedInEvent event) {
		if(Lance.newestVersion != null && !Lance.newestVersion.isInstalledVersion()) {
			event.player.addChatMessage(new ChatComponentText("Version " + Lance.newestVersion.getMCVersion() + " of Lance Mod available!"));
			String[] version = Lance.version.split("\\.");
//			System.out.println(version[3] + Lance.newestVersion.versionNumber[3]);
			if(version.length == 4 && version[3].equals(Lance.newestVersion.versionNumber[3] + "")) {
				String s = "{ text:\"\", extra:[{ text:\"Auto install\", color:blue, bold:false, italic:true, underlined:true, clickEvent:{ action:run_command, value:\"/lanceAutoUpdate\" }, hoverEvent:{ action:show_text, value:\"Download and install automatically\" }},{ text:\", \"},{ text:\"Download\", color:blue, bold:false, italic:true, underlined:true, clickEvent:{ action:open_url, value:\"" + Lance.newestVersion.getDownloadURL() + "\" }, hoverEvent:{ action:show_text, value:\"Click to download\" }},{text:\" or \"}, {text:\"open website\", color:blue, bold:false, italic:true, underlined:true, clickEvent:{ action:open_url, value:\"http://lance-mod.selfhost.eu/Lance%20Mod.html\" }, hoverEvent:{ action:show_text, value:\"Click to open the website\" }}]}";
				event.player.addChatComponentMessage(IChatComponent.Serializer.func_150699_a(s));
			} else {
				String s = "{ text:\"\", extra:[{ text:\"Download\", color:blue, bold:false, italic:true, underlined:true, clickEvent:{ action:open_url, value:\"" + Lance.newestVersion.getDownloadURL() + "\" }, hoverEvent:{ action:show_text, value:\"Click to download\" }},{text:\" or \"}, {text:\"open website\", color:blue, bold:false, italic:true, underlined:true, clickEvent:{ action:open_url, value:\"http://lance-mod.selfhost.eu/Lance%20Mod.html\" }, hoverEvent:{ action:show_text, value:\"Click to open the website\" }}]}";
				event.player.addChatComponentMessage(IChatComponent.Serializer.func_150699_a(s));
			}
		}
	}
	
}