package knight37x.magic.items;

import java.util.HashMap;
import java.util.List;

import knight37x.magic.Base;
import knight37x.magic.render.ModelArmor;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockDispenser;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.command.IEntitySelector;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IBehaviorDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.util.EnumHelper;

public class ItemTrainingArmor extends ItemArmor {
    
    /** The EnumArmorMaterial used for this ItemArmor */
    private final ItemArmor.ArmorMaterial material;
    @SideOnly(Side.CLIENT)
    private IIcon overlayIcon;
    @SideOnly(Side.CLIENT)
    private IIcon emptySlotIcon;
    
    public final HashMap<Integer, Integer> colors = new HashMap();
    
    public ItemTrainingArmor(ItemArmor.ArmorMaterial material, int renderIndex, int armorType) {
    	super(material, renderIndex, armorType);
        this.material = material;
        this.setMaxDamage(material.getDurability(armorType));
        this.maxStackSize = 1;
        ItemArmor.ArmorMaterial.CLOTH.customCraftingMaterial = Item.getItemFromBlock(Blocks.wool);
        
        colors.put(0, 0xFFFFFF);
		colors.put(1, 0xFFAA00);
		colors.put(2, 0xFF55FF);
		colors.put(3, 0x5495C3);
		colors.put(4, 0xFFFF55);
		colors.put(5, 0x55FF55);
		colors.put(6, 0xFF86CF);
		colors.put(7, 0x555555);
		colors.put(8, 0xAAAAAA);
		colors.put(9, 0x00AAAA);
		colors.put(10, 0x7500B0);
		colors.put(11, 0x0000AA);
		colors.put(12, 0x4B1C08);
		colors.put(13, 0x085112);
		colors.put(14, 0xAA0000);
		colors.put(15, 0x000000);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack stack, int par2) {
    	if (par2 > 0)
        {
            return this.colors.get(0);
        }
        else
        {
            int j = this.getColor(stack);

            if (j < 0)
            {
                j = this.colors.get(0);
            }

            return j;
        }
//        NBTTagCompound tag = stack.stackTagCompound;
//        if(tag == null) {
//        	tag = this.setNewNBT(stack);
//        } else if(!tag.hasKey("color")) {
//        	tag.setInteger("color", 0);
//        }
//        
//        return this.colors.getOrDefault(tag.getInteger("color"), 0xFFFFFF);
    }

    public NBTTagCompound setNewNBT(ItemStack stack) {
		if(stack.stackTagCompound == null) {
			stack.stackTagCompound = new NBTTagCompound();
			NBTTagCompound tag = stack.stackTagCompound;
			tag.setInteger("color", 0);
		}
		return stack.stackTagCompound;
	}
    
    @Override
    @SideOnly(Side.CLIENT)
    public boolean requiresMultipleRenderPasses()
    {
        return this.material == Base.trainingsMaterial;
    }

    @Override
    /**
     * Return the enchantability factor of the item, most of the time is based on material.
     */
    public int getItemEnchantability()
    {
        return this.material.getEnchantability();
    }

    @Override
    /**
     * Return the armor material for this armor item.
     */
    public ItemArmor.ArmorMaterial getArmorMaterial()
    {
        return this.material;
    }

    @Override
    /**
     * Return whether the specified armor ItemStack has a color.
     */
    public boolean hasColor(ItemStack par1ItemStack)
    {
        return this.material != Base.trainingsMaterial ? false : (!par1ItemStack.hasTagCompound() ? false : (!par1ItemStack.getTagCompound().hasKey("display", 10) ? false : par1ItemStack.getTagCompound().getCompoundTag("display").hasKey("color", 3)));
    }

    @Override
    /**
     * Return the color for the specified armor ItemStack.
     */
    public int getColor(ItemStack stack) {
//      NBTTagCompound tag = stack.stackTagCompound;
//      if(tag == null) {
//      	tag = this.setNewNBT(stack);
//      } else if(!tag.hasKey("color")) {
//      	tag.setInteger("color", 0);
//      }
////      return 10511680;
//      return this.colors.getOrDefault(Integer.getInteger(tag.getInteger("color") + ""), 0xFFFFFF);
        if(this.material != Base.trainingsMaterial) {
            return -1;
        }
        else
        {
            NBTTagCompound nbttagcompound = stack.getTagCompound();

            if (nbttagcompound == null)
            {
                return this.colors.get(0);
            }
            else
            {
                NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag("display");
                return nbttagcompound1 == null ? this.colors.get(0) : (nbttagcompound1.hasKey("color", 3) ? nbttagcompound1.getInteger("color") : this.colors.get(0));
            }
        }
    }

    @Override
    /**
     * Gets an icon index based on an item's damage value and the given render pass
     */
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamageForRenderPass(int par1, int par2)
    {
        return par2 == 1 ? this.overlayIcon : super.getIconFromDamageForRenderPass(par1, par2);
    }

    @Override
    /**
     * Remove the color from the specified armor ItemStack.
     */
    public void removeColor(ItemStack par1ItemStack)
    {
        if (this.material == Base.trainingsMaterial)
        {
            NBTTagCompound nbttagcompound = par1ItemStack.getTagCompound();

            if (nbttagcompound != null)
            {
                NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag("display");

                if (nbttagcompound1.hasKey("color"))
                {
                    nbttagcompound1.removeTag("color");
                }
            }
        }
    }

    public void setColor(ItemStack stack, int color) {
    	this.func_82813_b(stack, color);
//    	NBTTagCompound tag = stack.stackTagCompound;
//    	if(tag == null) {
//    		tag = this.setNewNBT(stack);
//    	}
//    	tag.setInteger("color", color);
    }
    
    @Override
    public void func_82813_b(ItemStack par1ItemStack, int par2)
    {
        if (this.material != Base.trainingsMaterial)
        {
            throw new UnsupportedOperationException("Can\'t dye non-leather!");
        }
        else
        {
            NBTTagCompound nbttagcompound = par1ItemStack.getTagCompound();

            if (nbttagcompound == null)
            {
                nbttagcompound = new NBTTagCompound();
                par1ItemStack.setTagCompound(nbttagcompound);
            }

            NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag("display");

            if (!nbttagcompound.hasKey("display", 10))
            {
                nbttagcompound.setTag("display", nbttagcompound1);
            }

            nbttagcompound1.setInteger("color", par2);
        }
    }

    @Override
    /**
     * Return whether this item is repairable in an anvil.
     */
    public boolean getIsRepairable(ItemStack par1ItemStack, ItemStack par2ItemStack)
    {
        return this.material.func_151685_b() == par2ItemStack.getItem() ? true : super.getIsRepairable(par1ItemStack, par2ItemStack);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister par1IconRegister)
    {
        super.registerIcons(par1IconRegister);

        if (this.material == Base.trainingsMaterial)
        {
            this.overlayIcon = par1IconRegister.registerIcon("magic:training_overlay");
        }

        this.emptySlotIcon = par1IconRegister.registerIcon(EMPTY_SLOT_NAMES[this.armorType]);
    }

    @Override
    /**
     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
     */
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
        int i = EntityLiving.getArmorPosition(par1ItemStack) - 1;
        ItemStack itemstack1 = par3EntityPlayer.getCurrentArmor(i);

        if (itemstack1 == null)
        {
            par3EntityPlayer.setCurrentItemOrArmor(i + 1, par1ItemStack.copy());  //Forge: Vanilla bug fix associated with fixed setCurrentItemOrArmor indexs for players.
            par1ItemStack.stackSize = 0;
        }

        return par1ItemStack;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, int armorSlot) {
    	return null;
    }
    
    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {
    	if(type != null && type.equals("overlay")) {
            return "magic:textures/items/training_overlay.png";
    	}
    	if(slot == 2) {
            return "textures/models/armor/leather_layer_2.png";
    	}
        return "textures/models/armor/leather_layer_1.png";
    }
}