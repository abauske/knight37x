package knight37x.magic.render;

import knight37x.magic.entity.EntityDrop;
import knight37x.magic.model.ModelDrop;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class RenderDrop extends Render {

	protected ModelBase model;
	private ResourceLocation textures;
	
	public RenderDrop() {
		this.textures = new ResourceLocation("magic:textures/entity/drop.png");
		this.model = new ModelDrop();
	}
	
	@Override
	public void doRender(Entity entity, double par2, double par4, double par6, float par8, float par9)
    {
		this.bindEntityTexture(entity);
		float height = 1.7F;
		if(entity != null) {
			height = entity.height + 0.3F;
		}
		if(entity instanceof EntityDrop && ((EntityDrop) entity).victim == Minecraft.getMinecraft().thePlayer) {
			height = 2.2F;
			for (float i = 0.0F; i <= 90; i += 0.75F) {
				for (float j = 0.0F; j < height; j += 0.15F) {
					GL11.glPushMatrix();
					GL11.glTranslatef((float) (par2 + Math.cos(i)), (float) par4 + 0.8F - j, (float) (par6 + Math.sin(i)));

					GL11.glRotatef(0.0F, 1.0F, 0.0F, 0.0F);
					GL11.glRotatef(0.0F, 0.0F, 1.0F, 0.0F);
					GL11.glRotatef(0.0F, 0.0F, 0.0F, 1.0F);
					float f10 = 0.3F;
					GL11.glScalef(f10, f10, f10);

					this.model.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);

					GL11.glDisable(GL12.GL_RESCALE_NORMAL);
					GL11.glPopMatrix();
				}
			}
		} else {
			for (float i = 0.0F; i <= 90; i += 0.75F) {
				for (float j = 0.0F; j < height; j += 0.15F) {
					GL11.glPushMatrix();
					GL11.glTranslatef((float) (par2 + Math.cos(i)), (float) par4 + height - j, (float) (par6 + Math.sin(i)));

					GL11.glRotatef(0.0F, 1.0F, 0.0F, 0.0F);
					GL11.glRotatef(0.0F, 0.0F, 1.0F, 0.0F);
					GL11.glRotatef(0.0F, 0.0F, 0.0F, 1.0F);
					float f10 = 0.3F;
					GL11.glScalef(f10, f10, f10);

					this.model.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);

					GL11.glDisable(GL12.GL_RESCALE_NORMAL);
					GL11.glPopMatrix();
				}
			}
		}
    }
	
	@Override
	protected ResourceLocation getEntityTexture(Entity par1Entity) {
		return this.textures;
    }
}