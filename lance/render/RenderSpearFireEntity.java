package knight37x.lance.render;

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

public class RenderSpearFireEntity extends RenderSpearEntity {

	
	private int counter = 6;
	
	public RenderSpearFireEntity(ModelBase model) {
		this.model = model;
	}
	
	@Override
	protected ResourceLocation getEntityTexture(Entity par1Entity)
    {
		this.counter++;
		if(this.counter > 59) {
			this.counter = 6;
		}
		int current = counter / 6;
		return new ResourceLocation("lance:textures/models/modelSpearFire" + current + ".png");
    }
}