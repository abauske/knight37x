package knight37x.lance.block;

import static io.netty.buffer.Unpooled.buffer;
import io.netty.buffer.ByteBuf;
import knight37x.lance.Lance;
import knight37x.lance.StaticMethods;
import knight37x.lance.item.ItemMayorBow;
import net.minecraft.block.Block;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ContainerWorkbench;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.network.internal.FMLProxyPacket;

public class GuiBowConfig extends GuiContainer
{
    private static final ResourceLocation texture = new ResourceLocation("lance:textures/gui/guiBowConfig.png");
    private World world;
    private int x;
    private int y;
    private int z;
    private ContainerBowConfig container;
    private EntityPlayer player;
    
    public GuiBowConfig(InventoryPlayer inventoryPlayer, World world, int x, int y, int z, ContainerBowConfig container)
    {
        super(container);
        this.container = container;
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
        this.player = inventoryPlayer.player;
        this.ySize += 36;
    }
    
    /**
     * Draw the foreground layer for the GuiContainer (everything in front of the items)
     */
    protected void drawGuiContainerForegroundLayer(int p_146979_1_, int p_146979_2_)
    {
        this.fontRendererObj.drawString("Bow Configuration Table", 28, 6, 4210752);
    }

    protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(this.texture);
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
        
        this.drawTexturedModalRect(k + 7, l + 63, this.xSize + 36, 20, 5, 5);
        this.drawTexturedModalRect(k + 7, l + 68, this.xSize + 36, 20, 5, 5);
        this.drawTexturedModalRect(k + 7, l + 73, this.xSize + 36, 20, 5, 5);
        this.drawTexturedModalRect(k + 7, l + 78, this.xSize + 36, 20, 5, 5);
        this.drawTexturedModalRect(k + 7, l + 83, this.xSize + 36, 20, 5, 5);
        this.drawTexturedModalRect(k + 7, l + 88, this.xSize + 36, 20, 5, 5);
        this.drawTexturedModalRect(k + 7, l + 93, this.xSize + 36, 20, 5, 5);
        this.drawTexturedModalRect(k + 7, l + 98, this.xSize + 36, 20, 5, 5);
        this.drawTexturedModalRect(k + 7, l + 103, this.xSize + 36, 20, 5, 5);
        
        ItemStack stack = container.getCurrentStack();
        if(stack != null) {
        	NBTTagCompound tag = stack.stackTagCompound;
            if(tag.getBoolean("horizontal")) {
        		this.drawTexturedModalRect(k + 127, l + 44, this.xSize + 16, 0, 29, 20);
            } else {
        		this.drawTexturedModalRect(k + 99, l + 71, this.xSize + 16, 20, 20, 29);
            }
            
            switch(tag.getInteger("spread")) {
            case 0:
                this.drawTexturedModalRect(k + 9, l + 85, this.xSize + 41, 22, 1, 1);
            	break;
            case 1:
                this.drawTexturedModalRect(k + 9, l + 80, this.xSize + 41, 22, 1, 1);
                this.drawTexturedModalRect(k + 9, l + 90, this.xSize + 41, 22, 1, 1);
        		this.drawTexturedModalRect(k + 16, l + 79, this.xSize, 23, 16, 4);
        		this.drawTexturedModalRect(k + 16, l + 88, this.xSize, 27, 16, 4);
            	break;
            case 2:
                this.drawTexturedModalRect(k + 9, l + 75, this.xSize + 41, 22, 1, 1);
                this.drawTexturedModalRect(k + 9, l + 95, this.xSize + 41, 22, 1, 1);
        		this.drawTexturedModalRect(k + 16, l + 75, this.xSize, 16, 16, 7);
        		this.drawTexturedModalRect(k + 16, l + 89, this.xSize, 31, 16, 7);
            	break;
            case 3:
                this.drawTexturedModalRect(k + 9, l + 70, this.xSize + 41, 22, 1, 1);
                this.drawTexturedModalRect(k + 9, l + 100, this.xSize + 41, 22, 1, 1);
        		this.drawTexturedModalRect(k + 16, l + 71, this.xSize, 10, 16, 6);
        		this.drawTexturedModalRect(k + 16, l + 94, this.xSize, 38, 16, 6);
            	break;
            case 4:
                this.drawTexturedModalRect(k + 9, l + 65, this.xSize + 41, 22, 1, 1);
                this.drawTexturedModalRect(k + 9, l + 105, this.xSize + 41, 22, 1, 1);
        		this.drawTexturedModalRect(k + 16, l + 65, this.xSize, 0, 16, 10);
        		this.drawTexturedModalRect(k + 16, l + 96, this.xSize, 44, 16, 10);
            	break;
        	default:
        		tag.setInteger("spread", 0);
        		this.sendSpread(player, 0);
            	break;
            }
        }
    }
    
    @Override
	protected void mouseClicked(int n, int m, int o) {
		super.mouseClicked(n, m, o);
		n -= this.guiLeft;
		m -= this.guiTop;
		if(this.container.getCurrentBow() != null) {
        	NBTTagCompound tag = this.container.getCurrentStack().stackTagCompound;
			if((n > 22 && m > 65 && n < 32 && m < 71) || (n > 27 && m > 65 && n < 32 && m < 75) || (n > 22 && m > 100 && n < 32 && m < 106) || (n > 27 && m > 96 && n < 32 && m < 106) || (n > 7 && m > 63 && n < 12 && m < 68) || (n > 7 && m > 103 && n < 12 && m < 108)) {
				tag.setInteger("spread", 4);
				this.sendSpread(player, 4);
			} else if((n > 19 && m > 71 && n < 31 && m < 75) || (n > 25 && m > 74 && n < 31 && m < 77) || (n > 19 && m > 96 && n < 26 && m < 100) || (n > 26 && m > 94 && n < 31 && m < 96) || (n > 7 && m > 68 && n < 12 && m < 73) || (n > 7 && m > 98 && n < 12 && m < 103)) {
				tag.setInteger("spread", 3);
				this.sendSpread(player, 3);
			} else if((n > 17 && m > 75 && n < 31 && m < 79) || (n > 25 && m > 77 && n < 31 && m < 81) || (n > 17 && m > 92 && n < 25 && m < 96) || (n > 24 && m > 90 && n < 31 && m < 94) || (n > 7 && m > 73 && n < 12 && m < 78) || (n > 7 && m > 93 && n < 12 && m < 98)) {
				tag.setInteger("spread", 2);
				this.sendSpread(player, 2);
			} else if((n > 16 && m > 79 && n < 31 && m < 83) || (n > 16 && m > 87 && n < 31 && m < 90) || (n > 16 && m > 87 && n < 27 && m < 92) || (n > 7 && m > 78 && n < 12 && m < 83) || (n > 7 && m > 88 && n < 12 && m < 93)) {
				tag.setInteger("spread", 1);
				this.sendSpread(player, 1);
			} else if((n > 15 && m > 83 && n < 31 && m < 88) || (n > 7 && m > 83 && n < 12 && m < 88)) {
				tag.setInteger("spread", 0);
				this.sendSpread(player, 0);
			}
			
			if(n > 99 && m > 71 && n < 119 && m < 100) {
				tag.setBoolean("horizontal", false);
				this.sendDirection(player, false);
			} else if(n > 127 && m > 44 && n < 156 && m < 64) {
				tag.setBoolean("horizontal", true);
				this.sendDirection(player, true);
			}
		}
	}
    
	@SubscribeEvent(priority = EventPriority.NORMAL)
    private void sendSpread(EntityPlayer player, int spread) {
		ByteBuf data = buffer(4);
		data.writeInt(3);
		data.writeInt(0);
		data.writeInt(player.getEntityId());
		data.writeInt(spread);
		FMLProxyPacket packet = new FMLProxyPacket(data, "lance");
		Lance.packetHandler.sendToServer(packet);
	}
    

    @SubscribeEvent(priority = EventPriority.NORMAL)
    private void sendDirection(EntityPlayer player, boolean horizontal) {
		ByteBuf data = buffer(4);
		data.writeInt(3);
		data.writeInt(1);
		data.writeInt(player.getEntityId());
		data.writeBoolean(horizontal);
		FMLProxyPacket packet = new FMLProxyPacket(data, "lance");
		Lance.packetHandler.sendToServer(packet);
	}
}