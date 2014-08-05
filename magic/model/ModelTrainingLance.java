package knight37x.magic.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelTrainingLance extends ModelBase {
	// fields
	ModelRenderer Hinterteil;
	ModelRenderer Handteil;
	ModelRenderer Mittelteil1;
	ModelRenderer Mittelteil2;
	ModelRenderer Mittelteil3;
	ModelRenderer SpitzeMittelteil;

	public ModelTrainingLance() {
		textureWidth = 64;
		textureHeight = 64;

		Hinterteil = new ModelRenderer(this, 0, 0);
		Hinterteil.addBox(-2F, 4F, -2F, 4, 15, 4);
		Hinterteil.setRotationPoint(0F, 5F, 0F);
		Hinterteil.setTextureSize(64, 32);
		Hinterteil.mirror = true;
		setRotation(Hinterteil, 0F, 0F, 0F);
		Handteil = new ModelRenderer(this, 17, 0);
		Handteil.addBox(-1F, -4F, -1F, 2, 8, 2);
		Handteil.setRotationPoint(0F, 5F, 0F);
		Handteil.setTextureSize(64, 32);
		Handteil.mirror = true;
		setRotation(Handteil, 0F, 0F, 0F);
		Mittelteil1 = new ModelRenderer(this, 0, 20);
		Mittelteil1.addBox(-2F, -38F, -2F, 4, 34, 4);
		Mittelteil1.setRotationPoint(0F, 5F, 0F);
		Mittelteil1.setTextureSize(64, 32);
		Mittelteil1.mirror = true;
		setRotation(Mittelteil1, 0F, 0F, 0F);
		Mittelteil2 = new ModelRenderer(this, 17, 11);
		Mittelteil2.addBox(-1.5F, -72F, -1.5F, 3, 34, 3);
		Mittelteil2.setRotationPoint(0F, 5F, 0F);
		Mittelteil2.setTextureSize(64, 32);
		Mittelteil2.mirror = true;
		setRotation(Mittelteil2, 0F, 0F, 0F);
		Mittelteil3 = new ModelRenderer(this, 30, 0);
		Mittelteil3.addBox(-1F, -106F, -1F, 2, 34, 2);
		Mittelteil3.setRotationPoint(0F, 5F, 0F);
		Mittelteil3.setTextureSize(64, 32);
		Mittelteil3.mirror = true;
		setRotation(Mittelteil3, 0F, 0F, 0F);
		SpitzeMittelteil = new ModelRenderer(this, 17, 49);
		SpitzeMittelteil.addBox(-2F, -112F, -2F, 4, 6, 4);
		SpitzeMittelteil.setRotationPoint(0F, 7F, 0F);
		SpitzeMittelteil.setTextureSize(64, 32);
		SpitzeMittelteil.mirror = true;
		setRotation(SpitzeMittelteil, 0F, 0F, 0F);
	}

	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		Hinterteil.render(f5);
		Handteil.render(f5);
		Mittelteil1.render(f5);
		Mittelteil2.render(f5);
		Mittelteil3.render(f5);
		SpitzeMittelteil.render(f5);
	}

	private void setRotation(ModelRenderer model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

}
