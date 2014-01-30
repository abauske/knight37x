package knight37x.lance;

import java.util.Locale.Category;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.FolderResourcePack;
import net.minecraft.command.CommandHandler;
import net.minecraft.command.ICommandManager;
import net.minecraft.command.ServerCommandManager;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
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

@Mod(modid = "lance", name = "Lances Mod", version = "2.4.0.172")
public class Lance {
	
	@Instance("lance")
	public static Lance instance = new Lance();
	
	@SidedProxy(clientSide="knight37x.lance.proxies.LanceClientProxy", serverSide="knight37x.lance.proxies.LanceCommonProxy")
	public static LanceCommonProxy proxy;
	
	public static final PacketHandler packetHandler = new PacketHandler();
	
	//-----------------------------------------------------------
	// All Variables:
	// Lances:

	public static Item lanceOnIron;
	private int lanceOnIronID = 450;
	
	public static Item lanceUpIron;
	private int lanceUpIronID = 451;
	
	public static Item lanceOnDia;
	private int lanceOnDiaID = 452;
	
	public static Item lanceUpDia;
	private int lanceUpDiaID = 453;
	
	public static Item lanceOnCopper;
	private int lanceOnCopperID = 454;
	
	public static Item lanceUpCopper;
	private int lanceUpCopperID = 455;
	
	public static Item lanceOnSteel;
	private int lanceOnSteelID = 456;
	
	public static Item lanceUpSteel;
	private int lanceUpSteelID = 457;
	
	public static Item shaft;
	private int shaftID = 460;
	
	//Other Configurations:
	public static boolean shouldLanceBreak = true;
	private int numberOfHits = 500;
	public static boolean shouldTakeDamageFromArmour = true;
	public static int armorBehaviour = 5;
	
	//-----------------------------------------------------------
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		Configuration config = new Configuration(event.getSuggestedConfigurationFile());
		config.load();
		
		lanceOnIronID = config.get("ItemIDs", "Iron Lance ID", 450).getInt();
		lanceUpIronID = config.get("ItemIDs", "Iron Lance Up ID", 451).getInt();
		lanceOnDiaID = config.get("ItemIDs", "Diamond Lance ID", 452).getInt();
		lanceUpDiaID = config.get("ItemIDs", "Diamond Lance Up ID", 453).getInt();
		lanceOnCopperID = config.get("ItemIDs", "Copper Lance ID", 454).getInt();
		lanceUpCopperID = config.get("ItemIDs", "Copper Lance Up ID", 455).getInt();
		lanceOnSteelID = config.get("ItemIDs", "Steel Lance ID", 456).getInt();
		lanceUpSteelID = config.get("ItemIDs", "Steel Lance Up ID", 457).getInt();
		
		shaftID = config.get("ItemIDs", "Shaft ID", 460).getInt();
		
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
		
		lanceOnIron = new ItemLanceIron().setUnlocalizedName("lanceI").setMaxStackSize(1).setMaxDamage(numberOfHits).setTextureName("lance:lanceiron").setTextureName("lance:lanceIron");
		lanceUpIron = new ItemLanceUp(Lance.lanceOnIron, "Iron").setUnlocalizedName("lanceUpI").setMaxStackSize(1).setMaxDamage(numberOfHits).setCreativeTab(CreativeTabs.tabCombat).setTextureName("lance:lanceIron");
		lanceOnDia = new ItemLanceDiamond().setUnlocalizedName("lanceD").setMaxStackSize(1).setMaxDamage(numberOfHits * 6).setTextureName("lance:lanceDiamond");
		lanceUpDia = new ItemLanceUp(Lance.lanceOnDia, "Diamond").setUnlocalizedName("lanceUpD").setMaxStackSize(1).setMaxDamage(numberOfHits * 6).setCreativeTab(CreativeTabs.tabCombat).setTextureName("lance:lanceDiamond");
		
		shaft = new ItemShaft().setCreativeTab(CreativeTabs.tabMaterials).setUnlocalizedName("shaft");
		
		registerItems();
		registerRecipes();
	}

	@EventHandler
	public void load(FMLInitializationEvent event) {
		NetworkRegistry.INSTANCE.newChannel("lance", packetHandler);
		
		proxy.registerRenderers();
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		
		if(this.isAvailable("ingotCopper")) {
			lanceOnCopper = new ItemLanceCopper().setUnlocalizedName("lanceC").setMaxStackSize(1).setMaxDamage((numberOfHits / 5) * 4).setTextureName("lance:lanceCopper");
			lanceUpCopper = new ItemLanceUp(Lance.lanceOnCopper, "Copper").setUnlocalizedName("lanceUpC").setMaxStackSize(1).setMaxDamage((numberOfHits / 5) * 4).setTextureName("lance:lanceCopper");
			
			GameRegistry.addRecipe(new ShapedOreRecipe(lanceUpCopper, "  X", " # ", "#  ", '#', shaft, 'X', "ingotCopper"));
			
			GameRegistry.registerItem(lanceOnCopper, "lanceC");
			GameRegistry.registerItem(lanceUpCopper, "lanceUpC");

			proxy.registerCopper();
		}
		
		
		
		if(this.isAvailable("ingotSteel")) {
			lanceOnSteel = new ItemLanceSteel().setUnlocalizedName("lanceS").setMaxStackSize(1).setMaxDamage(numberOfHits * 2).setTextureName("lance:lanceSteel");
			lanceUpSteel = new ItemLanceUp(Lance.lanceOnSteel, "Steel").setUnlocalizedName("lanceUpS").setMaxStackSize(1).setMaxDamage(numberOfHits * 2).setTextureName("lance:lanceSteel");
			
			GameRegistry.addRecipe(new ShapedOreRecipe(lanceUpSteel, "  X", " # ", "#  ", '#', shaft, 'X', "ingotSteel"));
			
			GameRegistry.registerItem(lanceOnSteel, "lanceS");
			GameRegistry.registerItem(lanceUpSteel, "lanceUpS");

			proxy.registerSteel();
		}
	}
	
	private void registerRecipes()
	{
		GameRegistry.addRecipe(new ShapedOreRecipe(shaft, "#  ", " # ", "  #", '#', Items.stick));
		GameRegistry.addRecipe(new ShapedOreRecipe(lanceUpIron, "  X", " # ", "#  ", '#', shaft, 'X', Items.iron_ingot));
		GameRegistry.addRecipe(new ShapedOreRecipe(lanceUpDia, "  X", " # ", "#  ", '#', shaft, 'X', Items.diamond));
	}
	
	private void registerItems()
	{
		GameRegistry.registerItem(lanceOnIron, "iron_lance_on");
		GameRegistry.registerItem(lanceUpIron, "iron_lance_up");
		GameRegistry.registerItem(lanceOnDia, "diamond_lance_on");
		GameRegistry.registerItem(lanceUpDia, "diamon_lance_up");
		
		GameRegistry.registerItem(shaft, "shaft");
	}
	
	private boolean isAvailable(String item) {
		String[] names = OreDictionary.getOreNames();
		for(int i = 0; i < names.length; i++) {
			if(names[i].equals(item)) {
				return true;
			}
		}
		return false;
	}
}