package knight37x.lance.render;

import knight37x.lance.model.ModelLanceUp;
import knight37x.lance.model.ModelSpear;

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
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

public class RenderSpearTNTEntity extends RenderSpearEntity {

	private static final ResourceLocation arrowTextures = new ResourceLocation("lance:textures/models/modelSpearTNT.png");
	private static final ResourceLocation arrowTextures2 = new ResourceLocation("lance:textures/models/modelSpearTNT2.png");
	
	private int counter = 0;
	
	public RenderSpearTNTEntity(ModelBase model) {
		this.model = model;
	}
	
	@Override
	protected ResourceLocation getEntityTexture(Entity par1Entity)
    {
		this.counter++;
		if(this.counter > 40) {
			this.counter = 0;
		}
		if(this.counter > 20) {
	        return this.arrowTextures;
		} else {
	        return this.arrowTextures2;
		}
    }
}