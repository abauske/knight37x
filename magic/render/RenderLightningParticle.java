package knight37x.magic.render;

import knight37x.lance.model.ModelLanceUp;
import knight37x.lance.model.ModelSpear;
import knight37x.magic.model.ModelLightning_old;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainerCreative;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderArrow;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.texture.SimpleTexture;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

public class RenderLightningParticle extends Render {

	protected ModelBase model;
	private ResourceLocation textures;
	
	public RenderLightningParticle() {
		this.textures = new ResourceLocation("magic:textures/entity/lightning.png");
		this.model = new ModelLightning_old();
	}
	
	@Override
	public void doRender(Entity entity, double par2, double par4, double par6, float par8, float par9)
    {
		this.bindEntityTexture(entity);
        GL11.glPushMatrix();
        GL11.glTranslatef((float)par2, (float)par4, (float)par6);
        
        GL11.glRotatef(entity.rotationYaw + 0.0F, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(0.0F, 0.0F, 0.0F, 1.0F);
        GL11.glRotatef((float) (-entity.rotationPitch * 1.0 + 180.0F), 1.0F, 0.0F, 0.0F);
        float size = 0.5F;
        int sqExist = entity.ticksExisted * entity.ticksExisted;
        GL11.glScalef((float) (size / sqExist), (float) (size / sqExist), size);

        this.model.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
        
        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        GL11.glPopMatrix();
    }
	
	@Override
	protected ResourceLocation getEntityTexture(Entity par1Entity)
    {
        return this.textures;
    }
}