package knight37x.magic;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import knight37x.magic.entity.EntityLightning;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLServerStartedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.Side;

public class EventHookContainerMagic {
	
	@SubscribeEvent
	public void itemCrafted(ItemCraftedEvent event) {
		if(event.crafting.getItem() == Base.wand) {
			boolean flag = true;
			for(int i = 0; i < event.craftMatrix.getSizeInventory(); i++) {
				if(event.craftMatrix.getStackInSlot(i) != null) {
					Item current = event.craftMatrix.getStackInSlot(i).getItem();
					if(current != Base.mana && current != Base.wand) {
						flag = false;
					}
				}
			}
			if(flag) {
				for(int i = 0; i < event.craftMatrix.getSizeInventory(); i++) {
					event.craftMatrix.setInventorySlotContents(i, null);
				}
			}
		}
	}
}
