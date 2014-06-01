package knight37x.lance.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.world.World;

public class ItemLanceUp extends ItemSword {

	private final Item switchTo;
	private final String material;
	
	public ItemLanceUp(Item switchTo, String material) {
		super(ToolMaterial.IRON);
		setCreativeTab(CreativeTabs.tabCombat);
		this.switchTo = switchTo;
		this.material = material;
	}

//	@Override
//	@SideOnly(Side.CLIENT)
//	public void registerIcons(IIconRegister reg) {
//		this.itemIcon = reg.registerIcon("Lance:lance" + this.material);
//	}
	
	@SideOnly(Side.CLIENT)

    /**
     * Returns True is the item is renderer in full 3D when hold.
     */
    public boolean isFull3D()
    {
        return false;
    }
	
	/**
     * returns the action that specifies what animation to play when the items is being used
     */
    public EnumAction getItemUseAction(ItemStack par1ItemStack) {
        return EnumAction.none;
    }

    @Override
	public ItemStack onItemRightClick(ItemStack itemstack, World par2World, EntityPlayer par3EntityPlayer) {
    	if(this.switchTo != null) {
			ItemStack newLance = new ItemStack(this.switchTo, 1);
			EnchantmentHelper.setEnchantments(EnchantmentHelper.getEnchantments(itemstack), newLance);
			newLance.setItemDamage(itemstack.getItemDamage());
			return newLance;
    	}
    	return itemstack;
	}

	@Override
	public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10) {
		return false;
	}

	@Override
	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
		return true;
	}

	@Override
	public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack stack) {
		return true;
	}

	@Override
	public boolean getIsRepairable(ItemStack stack1, ItemStack stack2) {
		Item item1 = stack1.getItem();
		Item item2 = stack2.getItem();
		if(item1 == null && item2 == null) {
			return false;
		}
		return (item1 instanceof ItemLance || item1 instanceof ItemLanceUp) && (item2 instanceof ItemLance || item2 instanceof ItemLanceUp);
	}
	
	@Override
	public float func_150931_i() {
        return 1F;
    }
	
}
