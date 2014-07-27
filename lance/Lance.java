package knight37x.lance;

import java.util.EnumMap;
import java.util.Locale.Category;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDispenser;
<<<<<<< HEAD
import net.minecraft.block.material.Material;
=======
>>>>>>> origin/master
import net.minecraft.command.CommandHandler;
import net.minecraft.command.ICommandManager;
import net.minecraft.command.ServerCommandManager;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.dispenser.BehaviorProjectileDispense;
import net.minecraft.dispenser.IPosition;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityEgg;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import knight37x.lance.block.BlockBowConfig;
import knight37x.lance.block.GuiHandler;
import knight37x.lance.entity.EntitySpear;
import knight37x.lance.item.ItemEnderCannon;
import knight37x.lance.item.ItemLanceCopper;
import knight37x.lance.item.ItemLanceDiamond;
import knight37x.lance.item.ItemLanceIron;
import knight37x.lance.item.ItemLanceSteel;
import knight37x.lance.item.ItemLanceUp;
import knight37x.lance.item.ItemShaft;
import knight37x.lance.item.ItemSks;
import knight37x.lance.item.ItemSpear;
import knight37x.lance.item.ItemSpearFire;
import knight37x.lance.item.ItemSpearPoison;
import knight37x.lance.item.ItemSpearTNT;
import knight37x.lance.item.ItemMayorBow;
<<<<<<< HEAD
=======
import knight37x.lance.network.PacketHandlerLance;
import knight37x.lance.network.PacketHandlerSpear;
>>>>>>> origin/master
import knight37x.lance.network.PacketHandler;
import knight37x.lance.proxies.LanceClientProxy;
import knight37x.lance.proxies.LanceCommonProxy;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.FMLEmbeddedChannel;
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
	
	public static final PacketHandler packetHandler = new PacketHandler();
	
	public static final EventHookContainer eventHandler = new EventHookContainer();
	
	public static CreativeTabs tabLance = new CreativeTabs("tabLance") {
		
		@Override
		public Item getTabIconItem() {
			return Lance.lanceUpIron;
		}
	};
	
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
	public static Item mayorBow;
<<<<<<< HEAD
	public static Block bowConfig;
=======
//	public static Item enderCannon;
>>>>>>> origin/master
	
	//Self-Knockback_Swords
	public static Item wood_sks;
	public static Item stone_sks;
	public static Item iron_sks;
	public static Item gold_sks;
	public static Item diamond_sks;
	
	//Spears:
	public static Item spear;
	public static Item spearTNT;
	public static Item spearPoison;
	public static Item spearFire;
	
	//Ripper:
	public static Item diamondRipper;
	public static Item ironRipper;
	public static Item copperRipper;
	public static Item steelRipper;
	
	//Other Configurations:
	public static boolean sksOnBlocks = false;
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
		
		sksOnBlocks = config.get(Configuration.CATEGORY_GENERAL, "Self-Knockback-Sword usable on Blocks (Fast travelling)", false).getBoolean(false);
		
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
		lanceUpIron = new ItemLanceUp(Lance.lanceOnIron, "Iron").setUnlocalizedName("lanceUpI").setMaxStackSize(1).setMaxDamage(numberOfHits).setTextureName("lance:lanceIron");
		lanceOnDia = new ItemLanceDiamond().setUnlocalizedName("lanceD").setMaxStackSize(1).setMaxDamage(numberOfHits * 6).setTextureName("lance:lanceDiamond");
		lanceUpDia = new ItemLanceUp(Lance.lanceOnDia, "Diamond").setUnlocalizedName("lanceUpD").setMaxStackSize(1).setMaxDamage(numberOfHits * 6).setTextureName("lance:lanceDiamond");
		
		shaft = new ItemShaft().setCreativeTab(this.tabLance).setUnlocalizedName("shaft").setTextureName("lance:lanceShaft");
		mayorBow = new ItemMayorBow().setCreativeTab(this.tabLance).setUnlocalizedName("mayorBow").setTextureName("lance:mayorBow_standby").setMaxStackSize(1);
<<<<<<< HEAD
		bowConfig = new BlockBowConfig(Material.wood).setBlockName("bowConfig").setBlockTextureName("lance:bowConfig").setCreativeTab(this.tabLance).setStepSound(Blocks.crafting_table.stepSound);
		
		wood_sks = new ItemSks(ToolMaterial.WOOD, "lance:wood_sks").setTextureName("wood_sword").setUnlocalizedName("wood_sks").setCreativeTab(null);
		stone_sks = new ItemSks(ToolMaterial.STONE, "lance:stone_sks").setTextureName("stone_sword").setUnlocalizedName("stone_sks").setCreativeTab(null);
		iron_sks = new ItemSks(ToolMaterial.IRON, "lance:iron_sks").setTextureName("iron_sword").setUnlocalizedName("iron_sks").setCreativeTab(null);
		gold_sks = new ItemSks(ToolMaterial.GOLD, "lance:gold_sks").setTextureName("gold_sword").setUnlocalizedName("gold_sks").setCreativeTab(null);
		diamond_sks = new ItemSks(ToolMaterial.EMERALD, "lance:diamond_sks").setTextureName("diamond_sword").setUnlocalizedName("diamond_sks").setCreativeTab(null);
=======
//		enderCannon = new ItemEnderCannon().setCreativeTab(this.tabLance).setUnlocalizedName("enderCannon").setTextureName("lance:enderCannon");
		
		wood_sks = new ItemSks(ToolMaterial.WOOD, "lance:wood_sks").setTextureName("wood_sword").setUnlocalizedName("wood_sks");
		stone_sks = new ItemSks(ToolMaterial.STONE, "lance:stone_sks").setTextureName("stone_sword").setUnlocalizedName("stone_sks");
		iron_sks = new ItemSks(ToolMaterial.IRON, "lance:iron_sks").setTextureName("iron_sword").setUnlocalizedName("iron_sks");
		gold_sks = new ItemSks(ToolMaterial.GOLD, "lance:gold_sks").setTextureName("gold_sword").setUnlocalizedName("gold_sks");
		diamond_sks = new ItemSks(ToolMaterial.EMERALD, "lance:diamond_sks").setTextureName("diamond_sword").setUnlocalizedName("diamond_sks");
>>>>>>> origin/master
		
		//Copper
		lanceOnCopper = new ItemLanceCopper().setUnlocalizedName("lanceC").setMaxStackSize(1).setMaxDamage((numberOfHits / 5) * 4).setTextureName("lance:lanceCopper");
		lanceUpCopper = new ItemLanceUp(Lance.lanceOnCopper, "Copper").setUnlocalizedName("lanceUpC").setMaxStackSize(1).setMaxDamage((numberOfHits / 5) * 4).setTextureName("lance:lanceCopper");
		
		//Steel
		lanceOnSteel = new ItemLanceSteel().setUnlocalizedName("lanceS").setMaxStackSize(1).setMaxDamage(numberOfHits * 2).setTextureName("lance:lanceSteel");
		lanceUpSteel = new ItemLanceUp(Lance.lanceOnSteel, "Steel").setUnlocalizedName("lanceUpS").setMaxStackSize(1).setMaxDamage(numberOfHits * 2).setTextureName("lance:lanceSteel");
		
		//Spear
		spear = new ItemSpear().setUnlocalizedName("spear").setCreativeTab(this.tabLance).setTextureName("lance:spearIron").setMaxStackSize(16);
		spearTNT = new ItemSpearTNT().setUnlocalizedName("spearTNT").setCreativeTab(this.tabLance).setTextureName("lance:spearTNT").setMaxStackSize(16);
		spearPoison = new ItemSpearPoison().setUnlocalizedName("spearPoison").setCreativeTab(this.tabLance).setTextureName("lance:spearPoison").setMaxStackSize(16);
		spearFire = new ItemSpearFire().setUnlocalizedName("spearFire").setCreativeTab(this.tabLance).setTextureName("lance:spearFire").setMaxStackSize(16);

		//Ripper:
		diamondRipper = new Item().setTextureName("lance:diamond_ripper").setUnlocalizedName("diamond_ripper").setCreativeTab(this.tabLance);
		ironRipper = new Item().setTextureName("lance:iron_ripper").setUnlocalizedName("iron_ripper").setCreativeTab(this.tabLance);
		copperRipper = new Item().setTextureName("lance:copper_ripper").setUnlocalizedName("copper_ripper").setCreativeTab(this.tabLance);
		steelRipper = new Item().setTextureName("lance:steel_ripper").setUnlocalizedName("steel_ripper").setCreativeTab(this.tabLance);
		
		registerItems();
	}

	@EventHandler
	public void load(FMLInitializationEvent event) {
		NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());
		packetHandler.initialise();
		EntityRegistry.registerGlobalEntityID(EntitySpear.class, "Spear", EntityRegistry.findGlobalUniqueEntityId());
		
		FMLCommonHandler.instance().bus().register(this.eventHandler);
		MinecraftForge.EVENT_BUS.register(this.eventHandler);

		registerRecipes();
		proxy.registerRenderers();
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		
	}
	
	private void registerRecipes()
	{
		//Others:
		GameRegistry.addRecipe(new ShapedOreRecipe(shaft, "#  ", " # ", "  #", '#', Items.stick));
		GameRegistry.addRecipe(new ShapedOreRecipe(mayorBow, " #S", "# S", " #S", '#', Items.bow, 'S', Items.string));
		GameRegistry.addRecipe(new ShapedOreRecipe(bowConfig, "SCS", "STS", "SBS", 'S', Items.stick, 'C', Blocks.crafting_table, 'T', Items.string, 'B', mayorBow));
		
		//Lances:
		GameRegistry.addRecipe(new ShapedOreRecipe(lanceUpDia, "  X", " # ", "#  ", '#', shaft, 'X', diamondRipper));		//Diamond
		GameRegistry.addRecipe(new ShapedOreRecipe(lanceUpIron, "  X", " # ", "#  ", '#', shaft, 'X', ironRipper));			//Iron
		GameRegistry.addRecipe(new ShapedOreRecipe(lanceUpCopper, "  X", " # ", "#  ", '#', shaft, 'X', copperRipper));		//Copper
		GameRegistry.addRecipe(new ShapedOreRecipe(lanceUpSteel, "  X", " # ", "#  ", '#', shaft, 'X', steelRipper));		//Steel
		
		//Spear:
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(spear, 4), "  X", " # ", "#  ", '#', Items.stick, 'X', Items.iron_ingot));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(spearTNT, 1), "X", "#", 'X', Blocks.tnt, '#', spear));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(spearPoison, 1), "X", "#", 'X', new ItemStack(Items.potionitem, 1, 8196), '#', spear));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(spearFire, 1), "X", "#", 'X', new ItemStack(Items.flint_and_steel, 1, OreDictionary.WILDCARD_VALUE), '#', spear));
		
		//Saddle:
		GameRegistry.addRecipe(new ShapedOreRecipe(Items.saddle, "# #", "###", '#', Items.leather));
		
		//Self-Knockback_Swords:
<<<<<<< HEAD
//		GameRegistry.addRecipe(new ShapedOreRecipe(wood_sks, "#", "X", '#', Items.gunpowder, 'X', Items.wooden_sword));
//		GameRegistry.addRecipe(new ShapedOreRecipe(stone_sks, "#", "X", '#', Items.gunpowder, 'X', Items.stone_sword));
//		GameRegistry.addRecipe(new ShapedOreRecipe(iron_sks, "#", "X", '#', Items.gunpowder, 'X', Items.iron_sword));
//		GameRegistry.addRecipe(new ShapedOreRecipe(gold_sks, "#", "X", '#', Items.gunpowder, 'X', Items.golden_sword));
//		GameRegistry.addRecipe(new ShapedOreRecipe(diamond_sks, "#", "X", '#', Items.gunpowder, 'X', Items.diamond_sword));
=======
		GameRegistry.addRecipe(new ShapedOreRecipe(wood_sks, "#", "X", '#', Items.feather, 'X', Items.wooden_sword));
		GameRegistry.addRecipe(new ShapedOreRecipe(stone_sks, "#", "X", '#', Items.feather, 'X', Items.stone_sword));
		GameRegistry.addRecipe(new ShapedOreRecipe(iron_sks, "#", "X", '#', Items.feather, 'X', Items.iron_sword));
		GameRegistry.addRecipe(new ShapedOreRecipe(gold_sks, "#", "X", '#', Items.feather, 'X', Items.golden_sword));
		GameRegistry.addRecipe(new ShapedOreRecipe(diamond_sks, "#", "X", '#', Items.feather, 'X', Items.diamond_sword));
>>>>>>> origin/master
		
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
		GameRegistry.registerItem(mayorBow, "mayorBow");
<<<<<<< HEAD
		GameRegistry.registerBlock(bowConfig, "bowConfig");
=======
>>>>>>> origin/master
		
		GameRegistry.registerItem(spear, "spear");
		GameRegistry.registerItem(spearTNT, "spearTNT");
		GameRegistry.registerItem(spearPoison, "spearPoison");
		GameRegistry.registerItem(spearFire, "spearFire");
		
		GameRegistry.registerItem(wood_sks, "wood_sks");
		GameRegistry.registerItem(stone_sks, "stone_sks");
		GameRegistry.registerItem(iron_sks, "iron_sks");
		GameRegistry.registerItem(gold_sks, "gold_sks");
		GameRegistry.registerItem(diamond_sks, "diamond_sks");
		
		GameRegistry.registerItem(diamondRipper, "diamond_ripper");
		GameRegistry.registerItem(ironRipper, "iron_ripper");
		GameRegistry.registerItem(copperRipper, "copper_ripper");
		GameRegistry.registerItem(steelRipper, "steel_ripper");
	}
}