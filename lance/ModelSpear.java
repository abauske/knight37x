package knight37x.lance;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelSpear extends ModelBase {
	// fields
	ModelRenderer Hinterteil;
	ModelRenderer Handteil;
	ModelRenderer Mittelteil1;
	ModelRenderer Mittelteil2;
	ModelRenderer Spitze11;
	ModelRenderer Spitze12;
	ModelRenderer Spitze21;
	ModelRenderer Spitze22;
	ModelRenderer Spitze32;
	ModelRenderer Spitze31;
	ModelRenderer SpitzeMittelteil;

	public ModelSpear() {
		textureWidth = 512;
		textureHeight = 512;

		Hinterteil = new ModelRenderer(this, 0, 100);
		Hinterteil.addBox(-2F, 4F, -2F, 3, 15, 3);
		Hinterteil.setRotationPoint(0.5F, 5F, 0.5F);
		Hinterteil.setTextureSize(64, 32);
		Hinterteil.mirror = true;
		setRotation(Hinterteil, 0F, 0F, 0F);
		Handteil = new ModelRenderer(this, 0, 0);
		Handteil.addBox(-1F, -4F, -1F, 2, 8, 2);
		Handteil.setRotationPoint(0F, 5F, 0F);
		Handteil.setTextureSize(64, 32);
		Handteil.mirror = true;
		setRotation(Handteil, 0F, 0F, 0F);
		Mittelteil1 = new ModelRenderer(this, 0, 100);
		Mittelteil1.addBox(-2.5F, -72F, -1.5F, 3, 34, 3);
		Mittelteil1.setRotationPoint(1F, 39F, 0F);
		Mittelteil1.setTextureSize(64, 32);
		Mittelteil1.mirror = true;
		setRotation(Mittelteil1, 0F, 0F, 0F);
		Mittelteil2 = new ModelRenderer(this, 0, 100);
		Mittelteil2.addBox(-1F, -106F, -1F, 2, 34, 2);
		Mittelteil2.setRotationPoint(0F, 39F, 0F);
		Mittelteil2.setTextureSize(64, 32);
		Mittelteil2.mirror = true;
		setRotation(Mittelteil2, 0F, 0F, 0F);
		Spitze11 = new ModelRenderer(this, 0, 0);
		Spitze11.addBox(-2F, -110F, 0F, 4, 4, 0);
		Spitze11.setRotationPoint(0F, 39F, 0F);
		Spitze11.setTextureSize(64, 32);
		Spitze11.mirror = true;
		setRotation(Spitze11, 0F, 0F, 0F);
		Spitze12 = new ModelRenderer(this, 0, 0);
		Spitze12.addBox(0F, -110F, -2F, 0, 4, 4);
		Spitze12.setRotationPoint(0F, 39F, 0F);
		Spitze12.setTextureSize(64, 32);
		Spitze12.mirror = true;
		setRotation(Spitze12, 0F, 0F, 0F);
		Spitze21 = new ModelRenderer(this, 0, 0);
		Spitze21.addBox(-1F, -114F, 0F, 2, 4, 0);
		Spitze21.setRotationPoint(0F, 39F, 0F);
		Spitze21.setTextureSize(64, 32);
		Spitze21.mirror = true;
		setRotation(Spitze21, 0F, 0F, 0F);
		Spitze22 = new ModelRenderer(this, 0, 0);
		Spitze22.addBox(0F, -114F, -1F, 0, 4, 2);
		Spitze22.setRotationPoint(0F, 39F, 0F);
		Spitze22.setTextureSize(64, 32);
		Spitze22.mirror = true;
		setRotation(Spitze22, 0F, 0F, 0F);
		Spitze32 = new ModelRenderer(this, 0, 0);
		Spitze32.addBox(0F, -118F, -0.5F, 0, 4, 1);
		Spitze32.setRotationPoint(0F, 39F, 0F);
		Spitze32.setTextureSize(64, 32);
		Spitze32.mirror = true;
		setRotation(Spitze32, 0F, 0F, 0F);
		Spitze31 = new ModelRenderer(this, 0, 0);
		Spitze31.addBox(-0.5F, -118F, 0F, 1, 4, 0);
		Spitze31.setRotationPoint(0F, 39F, 0F);
		Spitze31.setTextureSize(64, 32);
		Spitze31.mirror = true;
		setRotation(Spitze31, 0F, 0F, 0F);
		SpitzeMittelteil = new ModelRenderer(this, 0, 0);
		SpitzeMittelteil.addBox(-0.5F, -112F, -0.5F, 1, 6, 1);
		SpitzeMittelteil.setRotationPoint(0F, 39F, 0F);
		SpitzeMittelteil.setTextureSize(64, 32);
		SpitzeMittelteil.mirror = true;
		setRotation(SpitzeMittelteil, 0F, 0F, 0F);
	}

	public void render(float f, float f1, float f2, float f3, float f4, float f5) {
		Hinterteil.render(f5);
		Handteil.render(f5);
		Mittelteil1.render(f5);
		Mittelteil2.render(f5);
		Spitze11.render(f5);
		Spitze12.render(f5);
		Spitze21.render(f5);
		Spitze22.render(f5);
		Spitze32.render(f5);
		Spitze31.render(f5);
		SpitzeMittelteil.render(f5);
	}

	private void setRotation(ModelRenderer model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

}
