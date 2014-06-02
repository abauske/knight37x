package knight37x.lance.render;

import java.util.HashMap;

import knight37x.lance.item.ItemLance;
import knight37x.lance.model.ModelLanceUp;
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

public class RenderLance implements IItemRenderer {
	
	protected ModelLanceUp model = new ModelLanceUp();
	private static ResourceLocation texture = new ResourceLocation("textures/models/modelLanceUpIron.png");
	private final AdvancedModelLoader modelLoader = new AdvancedModelLoader();
	
	public static HashMap<Integer, Float> data = new HashMap();
	
	public RenderLance(String location) {
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
			if(itemstack.getItem() instanceof ItemLance) {
				ItemLance item = (ItemLance) itemstack.getItem();
				
				GL11.glPushMatrix();
				Minecraft.getMinecraft().renderEngine.bindTexture(texture);
				boolean var4 = false;
				
				
				if (player.length >= 2 && player[1] != null && player[1] instanceof EntityPlayer) {
					float knockTime = RenderLance.data.getOrDefault(((Entity) player[1]).getEntityId(), 0.0F);
					float var5;

					if ((EntityPlayer) player[1] == Minecraft.getMinecraft().renderViewEntity && Minecraft.getMinecraft().gameSettings.thirdPersonView == 0 && (!(Minecraft.getMinecraft().currentScreen instanceof GuiInventory) && !(Minecraft.getMinecraft().currentScreen instanceof GuiContainerCreative) || RenderManager.instance.playerViewY != 180.0F)) {
						var4 = true;
							GL11.glScalef(1.1F, 1.0F, 1.4F);
							GL11.glRotatef( 0.0F + 110, 0.0F + 0F, 0.0F + 0F, 0.0F + 1F);
							GL11.glRotatef( 0.0F + 10F, 0.0F, 0.0F + 1F, 0.0F);
							GL11.glRotatef( 0.0F + 350, 0.0F + 1F, 0.0F + 0, 0.0F + 0F);
							GL11.glTranslatef(0F, -1F + knockTime, 0F);
					} else {
							GL11.glScalef(1.0F, 1.0F, 1.0F);
							GL11.glRotatef( 0.0F + 185, 0.0F + 0F, 0.0F + 0F, 0.0F + 1F);
							GL11.glRotatef( 0.0F + 0F, 0.0F, 0.0F + 1F, 0.0F);
							GL11.glRotatef( 0.0F + 330, 0.0F + 1F, 0.0F + 0, 0.0F + 0F);
							GL11.glTranslatef(-0.5F, 0.0F + knockTime, 0.0F);
					}
				}
				
				if (player.length > 1) {
					this.model.render(player[0], 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
				}
				GL11.glPopMatrix();
				
			}
		default:
		}
	}
}
