package knight37x.magic.render;

import knight37x.lance.StaticMethods;
import knight37x.magic.model.ModelLightning;
import knight37x.magic.model.ModelMagic;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class RenderMagic extends Render {

	protected ModelBase model;
	private ResourceLocation textures;
	
	public RenderMagic() {
		this.textures = new ResourceLocation("magic:textures/entity/magic_on_player.png");
		this.model = new ModelMagic();
	}
	
	@Override
	public void doRender(Entity entity, double par2, double par4, double par6, float par8, float par9) {
			this.bindEntityTexture(entity);
	        GL11.glPushMatrix();
//	        StaticMethods.out(entity.height);
	        GL11.glTranslatef((float)par2, (float)par4 + 1, (float)par6);
	        
//	        GL11.glRotatef(entity.rotationYaw + 0.0F, 0.0F, 1.0F, 0.0F);
//	        GL11.glRotatef(0.0F, 0.0F, 0.0F, 1.0F);
//	        GL11.glRotatef((float) (-entity.rotationPitch * 1.0 + 180.0F), 1.0F, 0.0F, 0.0F);
	        float f10 = 0.4F;
	        GL11.glScalef(f10, f10, f10);

	        this.model.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
	        
	        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
	        GL11.glPopMatrix();
//		for(float i = entity.height + 0.2F; i > 0.2F; i -= 0.2F) {
//			this.bindEntityTexture(entity);
//	        GL11.glPushMatrix();
////	        StaticMethods.out(entity.height);
//	        GL11.glTranslatef((float)par2, (float)par4 + i, (float)par6);
//	        
////	        GL11.glRotatef(entity.rotationYaw + 0.0F, 0.0F, 1.0F, 0.0F);
////	        GL11.glRotatef(0.0F, 0.0F, 0.0F, 1.0F);
////	        GL11.glRotatef((float) (-entity.rotationPitch * 1.0 + 180.0F), 1.0F, 0.0F, 0.0F);
//	        float f10 = 0.4F;
//	        GL11.glScalef(f10, f10, f10);
//
//	        this.model.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
//	        
//	        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
//	        GL11.glPopMatrix();
//		}
    }
	
	@Override
	protected ResourceLocation getEntityTexture(Entity par1Entity) {
		return this.textures;
    }
}