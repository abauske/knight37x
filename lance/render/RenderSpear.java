package knight37x.lance.render;

import java.util.HashMap;

import knight37x.lance.StaticMethods;
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
	private float knockTime = 0.0F;

	public static HashMap<Integer, Boolean> data = new HashMap();

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

	public void renderItem(ItemRenderType type, ItemStack itemstack, Object... player) {
		switch (1) {
		case 1:
			if(itemstack.getItem() instanceof ItemSpear) {
				ItemSpear item = (ItemSpear) itemstack.getItem();
				
				GL11.glPushMatrix();
				Minecraft.getMinecraft().renderEngine.bindTexture(this.getTexture());
				
				if (player.length >= 2 && player[1] != null && player[1] instanceof EntityPlayer) {
					if(RenderSpear.data.getOrDefault(((Entity) player[1]).getEntityId(), false)) {
						if(this.knockTime < 2.5F) {
							this.knockTime += 0.1F;
						}
						if(this.knockTime > 2.5F) {
							this.knockTime = 2.5F;
						}
					} else {
						this.knockTime = 0.0F;
					}

					if(knockTime != 0) {
						if ((EntityPlayer) player[1] == Minecraft.getMinecraft().renderViewEntity && Minecraft.getMinecraft().gameSettings.thirdPersonView == 0 && (!(Minecraft.getMinecraft().currentScreen instanceof GuiInventory) && !(Minecraft.getMinecraft().currentScreen instanceof GuiContainerCreative) || RenderManager.instance.playerViewY != 180.0F)) {
							GL11.glScalef(0.4F, 0.5F, 0.5F);
							GL11.glRotatef( 0.0F + 110, 0.0F + 0F, 0.0F + 0F, 0.0F + 1F);
							GL11.glRotatef( 0.0F + 10F, 0.0F, 0.0F + 1F, 0.0F);
							GL11.glRotatef( 0.0F + 350, 0.0F + 1F, 0.0F + 0, 0.0F + 0F);
							GL11.glTranslatef(1.7F, -1F + knockTime, 0F);
						} else {
							GL11.glScalef(0.4F, 0.5F, 0.5F);
							GL11.glRotatef(10.0F, 1.0F, 0.0F, 0.0F);
							GL11.glRotatef(0.0F, 0.0F, 1.0F, 0.0F);
							GL11.glRotatef(200.0F, 0.0F, 0.0F, 1.0F);
							GL11.glTranslatef(-0.2F, 0.0F + knockTime, -3.5F);
						}
					} else if ((EntityPlayer) player[1] == Minecraft.getMinecraft().renderViewEntity && Minecraft.getMinecraft().gameSettings.thirdPersonView == 0 && (!(Minecraft.getMinecraft().currentScreen instanceof GuiInventory) && !(Minecraft.getMinecraft().currentScreen instanceof GuiContainerCreative) || RenderManager.instance.playerViewY != 180.0F)) {
//						StaticMethods.out(0);
						GL11.glTranslatef(1.5F, 0.0F, 1.0F);
						GL11.glScalef(0.4F, 0.5F, 0.5F);
						GL11.glRotatef(0.0F + 180, 0.0F + 0F, 0.0F + 0F, 0.0F + 1F);
						GL11.glRotatef(-70.0F, 0.0F, 0.0F + 1F, 0.0F);
						GL11.glRotatef(0.0F + 0, 0.0F + 1F, 0.0F + 0, 0.0F + 0F);
					} else {
//						StaticMethods.out(1);
						GL11.glScalef(0.4F, 0.5F, 0.5F);
						GL11.glTranslatef(1.3F, 0.8F, 0F);
						GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
						GL11.glRotatef(0.0F, 0.0F, 1.0F, 0.0F);
						GL11.glRotatef(-15.0F, 0.0F, 0.0F, 1.0F);
					}
				}
				
				if (player.length > 1) {
					this.getModel().render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
				}
				GL11.glPopMatrix();
				
			}
		default:
		}
	}
	
	public ModelSpear getModel() {
		return model;
	}
	
	public ResourceLocation getTexture() {
		return this.texture;
	}
}
