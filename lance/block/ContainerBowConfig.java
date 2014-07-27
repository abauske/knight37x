package knight37x.lance.block;

import knight37x.lance.Lance;
import knight37x.lance.item.ItemMayorBow;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;

public class ContainerBowConfig extends Container
{
    private World worldObj;
    private int posX;
    private int posY;
    private int posZ;
    
    private ItemStack currentBow = null;

    public ContainerBowConfig(InventoryPlayer inventoryPlayer, World world, int x, int y, int z)
    {
        this.worldObj = world;
        this.posX = x;
        this.posY = y;
        this.posZ = z;
        this.addSlotToContainer(new SlotBowConfig(new InventoryCrafting(this, 1, 1), 0, 80, 21));
        
        int l;
        int i1;

        for (l = 0; l < 3; ++l)
        {
            for (i1 = 0; i1 < 9; ++i1)
            {
                this.addSlotToContainer(new Slot(inventoryPlayer, i1 + l * 9 + 9, 8 + i1 * 18, 121 + l * 18));
            }
        }

        for (l = 0; l < 9; ++l)
        {
            this.addSlotToContainer(new Slot(inventoryPlayer, l, 8 + l * 18, 179));
        }
    }
    
    @Override
    /**
     * Called when the container is closed.
     */
    public void onContainerClosed(EntityPlayer par1EntityPlayer)
    {
        super.onContainerClosed(par1EntityPlayer);

        if (!this.worldObj.isRemote) {
        	ItemStack itemstack = (ItemStack) this.inventoryItemStacks.get(0);

            if (itemstack != null)
            {
                par1EntityPlayer.dropPlayerItemWithRandomChoice(itemstack, false);
            }
        }
    }

    @Override
	public ItemStack slotClick(int par1, int par2, int par3, EntityPlayer par4EntityPlayer) {
    	
		return super.slotClick(par1, par2, par3, par4EntityPlayer);
	}

	public boolean canInteractWith(EntityPlayer par1EntityPlayer)
    {
        return this.worldObj.getBlock(this.posX, this.posY, this.posZ) != Lance.bowConfig ? false : par1EntityPlayer.getDistanceSq((double)this.posX + 0.5D, (double)this.posY + 0.5D, (double)this.posZ + 0.5D) <= 64.0D;
    }

    /**
     * Called when a player shift-clicks on a slot. You must override this or you will crash when someone does that.
     */
	public ItemStack transferStackInSlot(EntityPlayer player, int slot) {
		ItemStack stack = null;
		Slot slotObject = (Slot) inventorySlots.get(slot);

		// null checks and checks if the item can be stacked (maxStackSize > 1)
		if (slotObject != null) {
			ItemStack stackInSlot = slotObject.getStack();
			if(stackInSlot == null) {
				return null;
			}
			stack = stackInSlot.copy();

			// merges the item into player inventory since its in the tileEntity
			if (slot == 0) {
				if (!this.mergeItemStack(stackInSlot, 0, 37, true)) {
					return null;
				}
			} else if(stackInSlot != null && stackInSlot.getItem() instanceof ItemMayorBow && !this.getSlot(0).getHasStack()) {
				this.putStackInSlot(0, stackInSlot);
				this.putStackInSlot(slot, null);
			}

			if (stackInSlot.stackSize == 0) {
				slotObject.putStack(null);
			} else {
				slotObject.onSlotChanged();
			}

			if (stackInSlot.stackSize == stack.stackSize) {
				return null;
			}
			slotObject.onPickupFromSlot(player, stackInSlot);
		}
		return stack;
	}
    
    @Override
    public void onCraftMatrixChanged(IInventory par1IInventory)
    {
    	this.currentBow = null;
		ItemStack itemstack = par1IInventory.getStackInSlot(0);
		int i;

		if (itemstack != null && itemstack.getItem() instanceof ItemMayorBow) {
			this.currentBow = itemstack;

			this.detectAndSendChanges();
		}
        
    }
    
    public ItemMayorBow getCurrentBow() {
    	return this.currentBow == null ? null : (ItemMayorBow) this.currentBow.getItem();
    }
    
    public ItemStack getCurrentStack() {
    	return this.currentBow;
    }
}
