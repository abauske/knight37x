package knight37x.magic.render;

import java.util.HashMap;

import knight37x.lance.StaticMethods;
import knight37x.lance.item.ItemLance;
import knight37x.lance.model.ModelLanceUp;
import knight37x.lance.render.RenderLance;
import knight37x.magic.model.ModelTrainingLance;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainerCreative;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;
import net.minecraftforge.client.IItemRenderer.ItemRendererHelper;
import net.minecraftforge.client.model.AdvancedModelLoader;

public class RenderTrainingLance implements IItemRenderer {

	protected ModelTrainingLance model = new ModelTrainingLance();
	
	public ResourceLocation[] textures = new ResourceLocation[16];
	public final String[] colorNames = new String[] {"white", "orange", "magenta", "lightBlue", "yellow", "lime", "pink", "gray", "lightGray", "cyan", "purple", "blue", "brown", "green", "red", "black"};
	
	private final AdvancedModelLoader modelLoader = new AdvancedModelLoader();
	
	public RenderTrainingLance() {
		for(int i = 0; i < colorNames.length; i++) {
			this.textures[i] = new ResourceLocation("magic:textures/models/trainingLance_" + this.colorNames[i] + ".png");
		}
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
				
				Minecraft.getMinecraft().renderEngine.bindTexture(this.getTexture(itemstack));
				boolean var4 = false;
				
				if (player.length >= 2 && player[1] != null && player[1] instanceof EntityPlayer) {
					Entity e = ((Entity) player[1]);
					NBTTagCompound tag = e.getEntityData();
					if(!tag.hasKey("knockTime")) {
						tag.setFloat("knockTime", 0.0F);
					}
					if(RenderLance.data.getOrDefault(e.getEntityId(), false)) {
						if(tag.getFloat("knockTime") < 1.0F) {
							tag.setFloat("knockTime", tag.getFloat("knockTime") + 0.03F);
						}
						if(tag.getFloat("knockTime") > 1.0F) {
							tag.setFloat("knockTime", 1.0F);
						}
					} else {
						tag.setFloat("knockTime", 0.0F);
					}
					float var5;

					if ((EntityPlayer) e == Minecraft.getMinecraft().renderViewEntity && Minecraft.getMinecraft().gameSettings.thirdPersonView == 0 && (!(Minecraft.getMinecraft().currentScreen instanceof GuiInventory) && !(Minecraft.getMinecraft().currentScreen instanceof GuiContainerCreative) || RenderManager.instance.playerViewY != 180.0F)) {
						var4 = true;
							GL11.glScalef(1.1F, 1.0F, 1.4F);
							GL11.glRotatef( 0.0F + 110, 0.0F + 0F, 0.0F + 0F, 0.0F + 1F);
							GL11.glRotatef( 0.0F + 10F, 0.0F, 0.0F + 1F, 0.0F);
							GL11.glRotatef( 0.0F + 350, 0.0F + 1F, 0.0F + 0, 0.0F + 0F);
							GL11.glTranslatef(0F, -1F + tag.getFloat("knockTime"), 0F);
					} else {
							GL11.glScalef(1.0F, 1.0F, 1.0F);
							GL11.glRotatef( 0.0F + 185, 0.0F + 0F, 0.0F + 0F, 0.0F + 1F);
							GL11.glRotatef( 0.0F + 0F, 0.0F, 0.0F + 1F, 0.0F);
							GL11.glRotatef( 0.0F + 330, 0.0F + 1F, 0.0F + 0, 0.0F + 0F);
							GL11.glTranslatef(-0.5F, 0.0F + tag.getFloat("knockTime"), 0.0F);
					}
				}
				
				if (player.length > 1) {
					this.model.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
				}
				GL11.glPopMatrix();
			}
		default:
		}
	}
	
	public ResourceLocation getTexture(ItemStack stack) {
		NBTTagCompound tag = stack.stackTagCompound;
		if(tag != null && tag.hasKey("colorDamage")) {
			return this.textures[tag.getInteger("colorDamage")];
		}
		return this.textures[0];
	}
}
