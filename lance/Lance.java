package knight37x.lance;

import java.util.Locale.Category;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderArrow;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.client.resources.FolderResourcePack;
import net.minecraft.command.CommandHandler;
import net.minecraft.command.ICommandManager;
import net.minecraft.command.ServerCommandManager;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import knight37x.lance.proxies.LanceClientProxy;
import knight37x.lance.proxies.LanceCommonProxy;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameData;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@Mod(modid = "lance", name = "Lance Mod", version = "2.5.1.172")
public class Lance {
	
	@Instance("lance")
	public static Lance instance = new Lance();
	
	@SidedProxy(clientSide="knight37x.lance.proxies.LanceClientProxy", serverSide="knight37x.lance.proxies.LanceCommonProxy")
	public static LanceCommonProxy proxy;
	
	public static final PacketHandlerLance packetHandlerLance = new PacketHandlerLance();
	public static final PacketHandlerSpear packetHandlerSpear = new PacketHandlerSpear();
	
	//-----------------------------------------------------------
	// All Variables:
	// Lances:
	public static Item lanceOnIron;
	public static Item lanceUpIron;
	public static Item lanceOnDia;
	public static Item lanceUpDia;
	public static Item lanceOnCopper;
	public static Item lanceUpCopper;
	public static Item lanceOnSteel;
	public static Item lanceUpSteel;
	
	// Other:
	public static Item shaft;
	
	public static Item spear;
	
	//Ripper:
	public static Item diamondRipper;
	public static Item ironRipper;
	public static Item copperRipper;
	public static Item steelRipper;
	
	//Other Configurations:
	public static boolean craftableSaddle = true;
	public static boolean shouldLanceBreak = true;
	private int numberOfHits = 500;
	public static boolean shouldTakeDamageFromArmour = true;
	public static int armorBehaviour = 5;
	
	//-----------------------------------------------------------
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		Configuration config = new Configuration(event.getSuggestedConfigurationFile());
		config.load();
		craftableSaddle = config.get(Configuration.CATEGORY_GENERAL, "Saddle craftable", true).getBoolean(true);
		
		shouldLanceBreak = config.get(Configuration.CATEGORY_GENERAL, "Should the lance take damage?", true).getBoolean(true);
		shouldTakeDamageFromArmour = config.get(Configuration.CATEGORY_GENERAL, "Should the lance take more damage when hitting an armoured mob?", true).getBoolean(true);
		
		numberOfHits = config.get(Configuration.CATEGORY_GENERAL, "How often you can hit a mob until the lance will break (Iron Lance)", 1500).getInt();
		armorBehaviour = config.get(Configuration.CATEGORY_GENERAL, "Armour rating to instantly break a lance <settable to any value between 0 and 20 with increments of 0.5>", 5).getInt() * 2;
		
		if(armorBehaviour > 20) {
			armorBehaviour = 20;
		} else if(armorBehaviour < 0) {
			armorBehaviour = 0;
		}
		
		config.save();
		
		// ---------------------------------------------------------------------------------------------------------------------------------
		
		lanceOnIron = new ItemLanceIron().setUnlocalizedName("lanceI").setMaxStackSize(1).setMaxDamage(numberOfHits).setTextureName("lance:lanceIron");
		lanceUpIron = new ItemLanceUp(Lance.lanceOnIron, "Iron").setUnlocalizedName("lanceUpI").setMaxStackSize(1).setMaxDamage(numberOfHits).setCreativeTab(CreativeTabs.tabCombat).setTextureName("lance:lanceIron");
		lanceOnDia = new ItemLanceDiamond().setUnlocalizedName("lanceD").setMaxStackSize(1).setMaxDamage(numberOfHits * 6).setTextureName("lance:lanceDiamond");
		lanceUpDia = new ItemLanceUp(Lance.lanceOnDia, "Diamond").setUnlocalizedName("lanceUpD").setMaxStackSize(1).setMaxDamage(numberOfHits * 6).setCreativeTab(CreativeTabs.tabCombat).setTextureName("lance:lanceDiamond");
		
		shaft = new ItemShaft().setCreativeTab(CreativeTabs.tabMaterials).setUnlocalizedName("shaft");
		
		//Copper
		lanceOnCopper = new ItemLanceCopper().setUnlocalizedName("lanceC").setMaxStackSize(1).setMaxDamage((numberOfHits / 5) * 4).setTextureName("lance:lanceCopper");
		lanceUpCopper = new ItemLanceUp(Lance.lanceOnCopper, "Copper").setUnlocalizedName("lanceUpC").setMaxStackSize(1).setMaxDamage((numberOfHits / 5) * 4).setTextureName("lance:lanceCopper");
		
		//Steel
		lanceOnSteel = new ItemLanceSteel().setUnlocalizedName("lanceS").setMaxStackSize(1).setMaxDamage(numberOfHits * 2).setTextureName("lance:lanceSteel");
		lanceUpSteel = new ItemLanceUp(Lance.lanceOnSteel, "Steel").setUnlocalizedName("lanceUpS").setMaxStackSize(1).setMaxDamage(numberOfHits * 2).setTextureName("lance:lanceSteel");
		
		//Spear
		spear = new ItemSpear().setUnlocalizedName("spear").setCreativeTab(CreativeTabs.tabCombat).setTextureName("lance:spearIron").setMaxStackSize(16);

		//Ripper:
		diamondRipper = new Item().setTextureName("lance:diamond_ripper").setUnlocalizedName("diamond_ripper").setCreativeTab(CreativeTabs.tabMaterials);
		ironRipper = new Item().setTextureName("lance:iron_ripper").setUnlocalizedName("iron_ripper").setCreativeTab(CreativeTabs.tabMaterials);
		copperRipper = new Item().setTextureName("lance:copper_ripper").setUnlocalizedName("copper_ripper").setCreativeTab(CreativeTabs.tabMaterials);
		steelRipper = new Item().setTextureName("lance:steel_ripper").setUnlocalizedName("steel_ripper").setCreativeTab(CreativeTabs.tabMaterials);
		
		registerItems();
	}

	@EventHandler
	public void load(FMLInitializationEvent event) {
		NetworkRegistry.INSTANCE.newChannel("lance", packetHandlerLance);
		NetworkRegistry.INSTANCE.newChannel("spear", packetHandlerSpear);
		EntityRegistry.registerGlobalEntityID(EntitySpear.class, "Spear", EntityRegistry.findGlobalUniqueEntityId());

		registerRecipes();
		proxy.registerRenderers();
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		
	}
	
	private void registerRecipes()
	{
		//Lance Shaft:
		GameRegistry.addRecipe(new ShapedOreRecipe(shaft, "#  ", " # ", "  #", '#', Items.stick));
		
		//Lances:
		GameRegistry.addRecipe(new ShapedOreRecipe(lanceUpDia, "  X", " # ", "#  ", '#', shaft, 'X', diamondRipper));		//Diamond
		GameRegistry.addRecipe(new ShapedOreRecipe(lanceUpIron, "  X", " # ", "#  ", '#', shaft, 'X', ironRipper));			//Iron
		GameRegistry.addRecipe(new ShapedOreRecipe(lanceUpCopper, "  X", " # ", "#  ", '#', shaft, 'X', copperRipper));		//Copper
		GameRegistry.addRecipe(new ShapedOreRecipe(lanceUpSteel, "  X", " # ", "#  ", '#', shaft, 'X', steelRipper));		//Steel
		
		//Spear:
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(spear, 4), "  X", " # ", "#  ", '#', Items.stick, 'X', Items.iron_ingot));
		
		//Saddle:
		GameRegistry.addRecipe(new ShapedOreRecipe(Items.saddle, "# #", "###", '#', Items.leather));
		
		//Rippers:
		GameRegistry.addRecipe(new ShapedOreRecipe(diamondRipper, " # ", "# #", '#', Items.diamond));
		GameRegistry.addRecipe(new ShapedOreRecipe(ironRipper, " # ", "# #", '#', Items.iron_ingot));
		GameRegistry.addRecipe(new ShapedOreRecipe(copperRipper, " # ", "# #", '#', "ingot_Copper"));
		GameRegistry.addRecipe(new ShapedOreRecipe(copperRipper, " # ", "# #", '#', "copper_ingot"));
		GameRegistry.addRecipe(new ShapedOreRecipe(steelRipper, " # ", "# #", '#', "ingot_Steel"));
		GameRegistry.addRecipe(new ShapedOreRecipe(steelRipper, " # ", "# #", '#', "steel_ingot"));
	}
	
	private void registerItems()
	{
		GameRegistry.registerItem(lanceOnIron, "iron_lance_on");
		GameRegistry.registerItem(lanceUpIron, "iron_lance");
		GameRegistry.registerItem(lanceOnDia, "diamond_lance_on");
		GameRegistry.registerItem(lanceUpDia, "diamon_lance");
		GameRegistry.registerItem(lanceOnSteel, "steel_lance_on");
		GameRegistry.registerItem(lanceUpSteel, "steel_lance");
		GameRegistry.registerItem(lanceOnCopper, "copper_lance_on");
		GameRegistry.registerItem(lanceUpCopper, "copper_lance");
		
		GameRegistry.registerItem(shaft, "shaft");
		
		GameRegistry.registerItem(spear, "spear");
		
		GameRegistry.registerItem(diamondRipper, "diamond_ripper");
		GameRegistry.registerItem(ironRipper, "iron_ripper");
		GameRegistry.registerItem(copperRipper, "copper_ripper");
		GameRegistry.registerItem(steelRipper, "steel_ripper");
	}
}