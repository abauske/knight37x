package knight37x.lance.render;

import knight37x.lance.item.ItemSpear;
import knight37x.lance.model.ModelSpear;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainerCreative;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.model.AdvancedModelLoader;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class RenderSpear implements IItemRenderer {
	
	protected ModelSpear model = new ModelSpear();
	private ResourceLocation texture;
	private final AdvancedModelLoader modelLoader = new AdvancedModelLoader();
	private float thrust = 0.0F;

	public RenderSpear() {
		this.texture = new ResourceLocation("lance:textures/models/modelSpear.png");
	}
	
	public RenderSpear(String location) {
		this.texture = new ResourceLocation(location);
	}
	
	public boolean handleRenderType(ItemStack var1, ItemRenderType type)
    {
            switch (type)
            {
                    case INVENTORY:
                    	return false;
                    case ENTITY:
                    	return false;

                    default:
                    	return true;
            }
    }

    public boolean shouldUseRenderHelper(ItemRenderType var1, ItemStack var2, ItemRendererHelper var3)
    {
            return false;
    }

	public void renderItem(ItemRenderType type, ItemStack itemstack, Object... var3) {
		switch (1) {
		case 1:
			if(itemstack.getItem() instanceof ItemSpear) {
				ItemSpear item = (ItemSpear) itemstack.getItem();
				
				GL11.glPushMatrix();
				Minecraft.getMinecraft().renderEngine.bindTexture(texture);
				
				this.thrust = item.thrustValue();
				
				if (var3.length >= 2 && var3[1] != null && var3[1] instanceof EntityPlayer) {

					if(this.thrust != 0) {
						if ((EntityPlayer) var3[1] == Minecraft.getMinecraft().renderViewEntity && Minecraft.getMinecraft().gameSettings.thirdPersonView == 0 && (!(Minecraft.getMinecraft().currentScreen instanceof GuiInventory) && !(Minecraft.getMinecraft().currentScreen instanceof GuiContainerCreative) || RenderManager.instance.playerViewY != 180.0F)) {
							GL11.glScalef(0.4F, 0.5F, 0.5F);
							GL11.glRotatef( 0.0F + 110, 0.0F + 0F, 0.0F + 0F, 0.0F + 1F);
							GL11.glRotatef( 0.0F + 10F, 0.0F, 0.0F + 1F, 0.0F);
							GL11.glRotatef( 0.0F + 350, 0.0F + 1F, 0.0F + 0, 0.0F + 0F);
							GL11.glTranslatef(1.7F, -1F + this.thrust, 0F);
						} else {
							GL11.glScalef(0.4F, 0.5F, 0.5F);
							GL11.glRotatef(0.0F + 180, 0.0F + 180F, 0.0F + 120F, 0.0F + 0F);
							GL11.glRotatef(0.0F + 0, 0.0F, 0.0F + 0F, 0.0F);
							GL11.glRotatef(0.0F + 0, 0.0F + 0, 0.0F + 0, 0.0F + 0F);
							GL11.glTranslatef(1.3F, 0.8F + this.thrust, 0F);
						}
					} else if ((EntityPlayer) var3[1] == Minecraft.getMinecraft().renderViewEntity && Minecraft.getMinecraft().gameSettings.thirdPersonView == 0 && (!(Minecraft.getMinecraft().currentScreen instanceof GuiInventory) && !(Minecraft.getMinecraft().currentScreen instanceof GuiContainerCreative) || RenderManager.instance.playerViewY != 180.0F)) {
						GL11.glTranslatef(1.5F, 0.0F, 1.0F);
						GL11.glScalef(0.4F, 0.5F, 0.5F);
						GL11.glRotatef(0.0F + 180, 0.0F + 0F, 0.0F + 0F, 0.0F + 1F);
						GL11.glRotatef(0.0F + 100F, 0.0F, 0.0F + 1F, 0.0F);
						GL11.glRotatef(0.0F + 0, 0.0F + 1F, 0.0F + 0, 0.0F + 0F);
					} else {
						GL11.glScalef(0.4F, 0.5F, 0.5F);
						GL11.glTranslatef(1.3F, 0.8F, 0F);
						GL11.glRotatef(0.0F + 180, 0.0F + 180F, 0.0F + 120F, 0.0F + 0F);
						GL11.glRotatef(0.0F + 0, 0.0F, 0.0F + 0F, 0.0F);
						GL11.glRotatef(0.0F + 0, 0.0F + 0, 0.0F + 0, 0.0F + 0F);
					}
				}
				
				if (var3.length > 1) {
					this.model.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
				}
				GL11.glPopMatrix();
				
			}
		default:
		}
	}
}
