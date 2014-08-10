package knight37x.magic.render;

import knight37x.lance.StaticMethods;
import knight37x.lance.item.ItemLance;
import knight37x.lance.render.RenderLance;
import knight37x.magic.model.ModelHammer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainerCreative;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderCow;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.common.MinecraftForge;

public class RenderTroll extends RenderCow {
	
    private static final Logger logger = LogManager.getLogger();
    private static final ResourceLocation RES_ITEM_GLINT = new ResourceLocation("textures/misc/enchanted_item_glint.png");
    private static final ResourceLocation TROLL_TEXTURE = new ResourceLocation("magic:textures/entity/ModelTroll.png");
    
    public static final ModelBase HAMMER_MODEL = new ModelHammer();
    public static final ResourceLocation HAMMER_TEXTURE = new ResourceLocation("magic:textures/models/hammer.png");
	
	public RenderTroll(ModelBase par1ModelBase, float par2) {
		super(par1ModelBase, par2);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity var1) {
		return TROLL_TEXTURE;
	}

	@Override
	protected void renderEquippedItems(EntityLivingBase entity, float par2) {
		GL11.glPushMatrix();
		
		Minecraft.getMinecraft().renderEngine.bindTexture(HAMMER_TEXTURE);
		
//		StaticMethods.out(par2);
		
		GL11.glScalef(1.0F, 1.0F, 1.0F);
		GL11.glRotatef(90.0F, 	0.0F, 	0.0F, 	1F);
		GL11.glRotatef(0.0F, 	0.0F, 	1F, 	0.0F);
		GL11.glRotatef(90.0F, 	1F, 	0.0F, 	0.0F);
		GL11.glTranslatef(0.4F, -0.6F, -0.53F);
		
		HAMMER_MODEL.render(entity, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
		GL11.glPopMatrix();
	}

}
