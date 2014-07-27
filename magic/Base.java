package knight37x.magic;

import java.util.List;
import java.util.ArrayList;

import knight37x.lance.Lance;
import knight37x.lance.network.NetworkBase;
import knight37x.lance.network.PacketHandler;
import knight37x.magic.blocks.BlockMana;
import knight37x.magic.entity.EntityTroll;
import knight37x.magic.items.ItemEnderCannon;
import knight37x.magic.items.ItemMana;
import knight37x.magic.items.ItemMirror;
import knight37x.magic.items.ItemWand;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.biome.BiomeCache;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.terraingen.BiomeEvent;
import net.minecraftforge.event.terraingen.WorldTypeEvent.BiomeSize;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

@Mod(modid = "lance_magic", name = "Magic Plugin", version = "1.0.0.172")
public class Base {
	
	@Instance("base")
	public static Base instance = new Base();
	
	@SidedProxy(clientSide="knight37x.magic.MagicClientProxy", serverSide="knight37x.magic.MagicCommonProxy")
	public static MagicCommonProxy proxy;

	public static final EventHookContainerMagic eventHandler = new EventHookContainerMagic();
	
	//-----------------------------------------------------------
	public static int lightningPacketID = 4;
	public static int magicPacketID = 5;
	public static int magicSuccedPacketID = 6;
	
	// All Variables:
	public static Item mana;
	public static Item wand;
	public static Item mirror;
	
	public static Block mana_block;
	
	
	//-----------------------------------------------------------
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
//		Configuration config = new Configuration(event.getSuggestedConfigurationFile());
//		config.load();
//		config.save();
		
		// ---------------------------------------------------------------------------------------------------------------------------------
		
		mana = new ItemMana().setUnlocalizedName("mana").setCreativeTab(Lance.tabLance).setTextureName("magic:mana").setMaxStackSize(16);
		wand = new ItemWand().setUnlocalizedName("wand").setCreativeTab(Lance.tabLance).setTextureName("magic:wand").setMaxStackSize(1);
		mirror = new ItemMirror().setUnlocalizedName("mirror").setCreativeTab(Lance.tabLance).setTextureName("magic:mirror").setMaxStackSize(1).setMaxDamage(5);
		
		mana_block = new BlockMana(Material.glass).setBlockName("mana_block").setBlockTextureName("magic:mana_block").setCreativeTab(Lance.tabLance).setHardness(1.0F).setLightLevel(100).setLightOpacity(100).setResistance(1.0F);
		
		registerItems();
	}

	@EventHandler
	public void load(FMLInitializationEvent event) {
		NetworkMsg packetHandler = new NetworkMsg();
		if(!PacketHandler.registerHandler(lightningPacketID, packetHandler)) {
			lightningPacketID = PacketHandler.getFreePacketID();
			PacketHandler.registerHandler(lightningPacketID, packetHandler);
		}
		if(!PacketHandler.registerHandler(magicPacketID, packetHandler)) {
			magicPacketID = PacketHandler.getFreePacketID();
			PacketHandler.registerHandler(magicPacketID, packetHandler);
		}
		if(!PacketHandler.registerHandler(magicSuccedPacketID, packetHandler)) {
			magicSuccedPacketID = PacketHandler.getFreePacketID();
			PacketHandler.registerHandler(magicSuccedPacketID, packetHandler);
		}
		
		FMLCommonHandler.instance().bus().register(this.eventHandler);
		
		EntityRegistry.registerModEntity(EntityTroll.class, "EntityTroll", 0, this, 80, 1, true);
		EntityList.addMapping(EntityTroll.class, "EntityTroll", 0, 0x4B6E3F, 0xE3DAC5);
		EntityRegistry.addSpawn(EntityTroll.class, 30, 1, 2, EnumCreatureType.monster, BiomeGenBase.plains, BiomeGenBase.desert);

//		EntityRegistry.registerModEntity(EntityMagic.class, "EntityMagic", 1, this, 80, 1, true);
		
		registerRecipes();
		proxy.registerRenderers();
		proxy.registerSound();
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		
	}
	
	private void registerRecipes() {
		GameRegistry.addRecipe(new LoadingRecipe());
		GameRegistry.addShapedRecipe(new ItemStack(wand, 1), "M", "D", "S", 'M', mana_block, 'D', Items.diamond, 'S', Items.stick);
		GameRegistry.addRecipe(new ItemStack(mana_block), "###", "###", "###", '#', mana_block);
		
	}
	
	private void registerItems() {
		GameRegistry.registerItem(mana, "Mana");
		GameRegistry.registerItem(wand, "Wand");
		GameRegistry.registerItem(mirror, "Mirror");
		
		GameRegistry.registerBlock(mana_block, "mana_block");
	}
}