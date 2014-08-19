package knight37x.magic;

import java.util.List;
import java.util.ArrayList;

import knight37x.lance.Lance;
import knight37x.lance.item.ItemLanceUp;
import knight37x.lance.network.NetworkBase;
import knight37x.lance.network.PacketHandler;
import knight37x.magic.blocks.BlockBarrier;
import knight37x.magic.blocks.BlockMana;
import knight37x.magic.entity.EntityTroll;
import knight37x.magic.items.ItemEnderCannon;
import knight37x.magic.items.ItemMana;
import knight37x.magic.items.ItemMirror;
import knight37x.magic.items.ItemTrainingArmor;
import knight37x.magic.items.ItemTrainingLance;
import knight37x.magic.items.ItemTrainingLanceUp;
import knight37x.magic.items.ItemWand;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.world.biome.BiomeCache;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.EnumHelper;
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

@Mod(modid = Base.modid, name = Base.name, version = Base.version)
public class Base {

	public static final String modid = "lance_magic",
			name = "Magic Plugin",
			version = "1.0.0.1710";
	
	@Instance("base")
	public static Base instance = new Base();
	
	@SidedProxy(clientSide="knight37x.magic.MagicClientProxy", serverSide="knight37x.magic.MagicCommonProxy")
	public static MagicCommonProxy proxy;

	public static final EventHookContainerMagic eventHandler = new EventHookContainerMagic();
	
	public final static ArmorMaterial trainingsMaterial =  EnumHelper.addArmorMaterial("WOOL", 30, new int[]{1, 1, 1, 1}, 0);
	
	//-----------------------------------------------------------
	public static int spawnLightningPacketID = 4;
	public static int spawnDropsPacketID = 5;
	public static int mirrorUsePacketID = 6;
	public static int trainingLancePacketID = 7;
	public static int trollArmStatePacketID = 8;
	
	// All Variables:
	public static Item mana;
	public static Item wand;
	public static Item mirror;
	
	public static Item training_lance;
	public static Item training_lance_up;
	
	public static ItemTrainingArmor training_helmet;
	public static ItemTrainingArmor training_chestplate;
	public static ItemTrainingArmor training_leggings;
	public static ItemTrainingArmor training_boots;
	
	public static Block mana_block;
	public static Block barrier;
	
	
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
		
		training_lance = new ItemTrainingLance().setUnlocalizedName("training_lance").setMaxDamage(1000);
		training_lance_up = new ItemTrainingLanceUp(training_lance, "Wool").setUnlocalizedName("training_lance_up").setMaxDamage(1000);
		
		training_helmet = (ItemTrainingArmor) new ItemTrainingArmor(trainingsMaterial, 0, 0).setUnlocalizedName("training_helmet").setTextureName("magic:training_helmet").setCreativeTab(Lance.tabLance);
		training_chestplate = (ItemTrainingArmor) new ItemTrainingArmor(trainingsMaterial, 0, 1).setUnlocalizedName("training_chestplate").setTextureName("magic:training_chestplate").setCreativeTab(Lance.tabLance);
		training_leggings = (ItemTrainingArmor) new ItemTrainingArmor(trainingsMaterial, 0, 2).setUnlocalizedName("training_leggins").setTextureName("magic:training_leggings").setCreativeTab(Lance.tabLance);
		training_boots = (ItemTrainingArmor) new ItemTrainingArmor(trainingsMaterial, 0, 3).setUnlocalizedName("training_boots").setTextureName("magic:training_boots").setCreativeTab(Lance.tabLance);
		
		mana_block = new BlockMana(Material.glass).setBlockName("mana_block").setBlockTextureName("magic:mana_block").setCreativeTab(Lance.tabLance).setHardness(1.0F).setLightLevel(0.9375F).setResistance(1.0F);
//		barrier = new BlockBarrier(Material.wood).setBlockName("barrier").setBlockTextureName("magic:barrier").setCreativeTab(Lance.tabLance);
		
		registerItems();
	}

	@EventHandler
	public void load(FMLInitializationEvent event) {
		NetworkMsg packetHandler = new NetworkMsg();
		if(!PacketHandler.registerHandler(spawnLightningPacketID, packetHandler)) {
			spawnLightningPacketID = PacketHandler.getFreePacketID();
			PacketHandler.registerHandler(spawnLightningPacketID, packetHandler);
		}
		if(!PacketHandler.registerHandler(spawnDropsPacketID, packetHandler)) {
			spawnDropsPacketID = PacketHandler.getFreePacketID();
			PacketHandler.registerHandler(spawnDropsPacketID, packetHandler);
		}
		if(!PacketHandler.registerHandler(mirrorUsePacketID, packetHandler)) {
			mirrorUsePacketID = PacketHandler.getFreePacketID();
			PacketHandler.registerHandler(mirrorUsePacketID, packetHandler);
		}
		if(!PacketHandler.registerHandler(trainingLancePacketID, packetHandler)) {
			trainingLancePacketID = PacketHandler.getFreePacketID();
			PacketHandler.registerHandler(trainingLancePacketID, packetHandler);
		}
		if(!PacketHandler.registerHandler(trollArmStatePacketID, packetHandler)) {
			trollArmStatePacketID = PacketHandler.getFreePacketID();
			PacketHandler.registerHandler(trollArmStatePacketID, packetHandler);
		}
		
		FMLCommonHandler.instance().bus().register(this.eventHandler);
		MinecraftForge.EVENT_BUS.register(this.eventHandler);
		
		EntityRegistry.registerGlobalEntityID(EntityTroll.class, "EntityTroll", 0, 0x4B6E3F, 0xE3DAC5);
		EntityRegistry.registerModEntity(EntityTroll.class, "EntityTroll", 0, this, 80, 1, true);
//		EntityList.addMapping(EntityTroll.class, "EntityTroll", 0, 0x4B6E3F, 0xE3DAC5);
		EntityRegistry.addSpawn(EntityTroll.class, 20, 1, 2, EnumCreatureType.monster, BiomeGenBase.plains, BiomeGenBase.desert);
		
		trainingsMaterial.customCraftingMaterial = Item.getItemFromBlock(Blocks.wool);
		
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
		GameRegistry.addRecipe(new ItemStack(mirror), "III", "I#I", " G ", 'I', Items.iron_ingot, '#', Blocks.glass_pane, 'G', Items.gold_ingot);
		GameRegistry.addRecipe(new ItemStack(mana_block), "###", "###", "###", '#', mana);
		
		GameRegistry.addRecipe(new TrainingLanceRecipe());
//		GameRegistry.addRecipe(new ItemStack(training_lance_up), "  X", " # ", "#  ", '#', Lance.shaft, 'X', Blocks.wool);
		
		GameRegistry.addRecipe(new RecipeArmorDyes());
		GameRegistry.addRecipe(new RecipeArmor());
	}
	
	private void registerItems() {
		GameRegistry.registerItem(mana, "Mana");
		GameRegistry.registerItem(wand, "Wand");
		GameRegistry.registerItem(mirror, "Mirror");

		GameRegistry.registerItem(training_lance, "training_lance");
		GameRegistry.registerItem(training_lance_up, "training_lance_up");
		
		GameRegistry.registerItem(training_boots, "training_boots");
		GameRegistry.registerItem(training_chestplate, "training_chestplate");
		GameRegistry.registerItem(training_helmet, "training_helmet");
		GameRegistry.registerItem(training_leggings, "training_leggins");
		
		GameRegistry.registerBlock(mana_block, "mana_block");
//		GameRegistry.registerBlock(barrier, "barrier");
	}
}