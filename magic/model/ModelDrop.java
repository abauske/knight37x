package knight37x.magic.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class ModelDrop extends ModelBase {
	// fields
	ModelRenderer drop;

	public ModelDrop() {
		textureWidth = 32;
		textureHeight = 32;

		drop = new ModelRenderer(this, 0, 0);
		drop.addBox(0F, 0F, 0F, 4, 7, 0);
		drop.setRotationPoint(0F, 0F, 0F);
		drop.setTextureSize(64, 32);
		drop.mirror = true;
		setRotation(drop, 0F, 0F, 0F);
	}

	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		drop.render(f5);
	}

	private void setRotation(ModelRenderer model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}
}
