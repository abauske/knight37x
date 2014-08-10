package knight37x.magic.model;

import knight37x.lance.StaticMethods;
import knight37x.magic.render.RenderTroll;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class ModelHammer extends ModelBase {
	// fields
	ModelRenderer Stick;
	ModelRenderer Front;

	public ModelHammer() {
		textureWidth = 32;
		textureHeight = 32;

		Stick = new ModelRenderer(this, 0, 8);
		Stick.addBox(7F, -12F, -1F, 2, 19, 2);
		Stick.setRotationPoint(0F, 10F, 0F);
		Stick.setTextureSize(64, 32);
		Stick.mirror = true;
		setRotation(Stick, 0F, 0F, 0F);
		Front = new ModelRenderer(this, 0, 0);
		Front.addBox(2F, -16F, -2F, 12, 4, 4);
		Front.setRotationPoint(0F, 10F, 0F);
		Front.setTextureSize(64, 32);
		Front.mirror = true;
		setRotation(Front, 0F, 0F, 0F);
	}

	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		Stick.render(f5);
		Front.render(f5);
	}

	private void setRotation(ModelRenderer model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}
	
	public void setRotationAngles(float par1, float par2, float f2, float par4, float par5, float f5, Entity entity) {
		float f = entity.getEntityData().getFloat("armState");
		if(f == 0) {
			f = -MathHelper.cos(par1 * 0.6662F + (float)Math.PI) * 2.0F * par2 * 0.5F;
		}
//		StaticMethods.out(f);
		if(f != 0) {
			this.Stick.rotateAngleZ = f;
			this.Stick.rotateAngleX = 0.0F;
			this.Front.rotateAngleZ = f;
			this.Front.rotateAngleX = 0.0F;
		}
	}
}
