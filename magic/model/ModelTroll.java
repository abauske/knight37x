package knight37x.magic.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class ModelTroll extends ModelBase {
	// fields
	ModelRenderer RightFoot;
	ModelRenderer LeftFoot;
	ModelRenderer RightLeg;
	ModelRenderer LeftLeg;
	ModelRenderer Body;
	ModelRenderer LeftArm;
	ModelRenderer RightArm;
	ModelRenderer Fat;
	ModelRenderer Head;
	ModelRenderer RightEar;
	ModelRenderer LeftEar;
	ModelRenderer Nose;

	public ModelTroll() {
		textureWidth = 64;
		textureHeight = 64;

		RightFoot = new ModelRenderer(this, 40, 0);
		RightFoot.addBox(-1.5F, 5F, -5F, 3, 1, 6);
		RightFoot.setRotationPoint(3F, 18F, 1F);
		RightFoot.setTextureSize(64, 32);
		RightFoot.mirror = true;
		setRotation(RightFoot, 0F, 0F, 0F);
		LeftFoot = new ModelRenderer(this, 40, 0);
		LeftFoot.addBox(-1.5F, 0F, -5F, 3, 1, 6);
		LeftFoot.setRotationPoint(-3F, 23F, 1F);
		LeftFoot.setTextureSize(64, 32);
		LeftFoot.mirror = true;
		setRotation(LeftFoot, 0F, 0F, 0F);
		RightLeg = new ModelRenderer(this, 12, 34);
		RightLeg.addBox(-1.5F, 0F, -1.5F, 3, 5, 3);
		RightLeg.setRotationPoint(3F, 18F, 0.5F);
		RightLeg.setTextureSize(64, 32);
		RightLeg.mirror = true;
		setRotation(RightLeg, 0F, 0F, 0F);
		LeftLeg = new ModelRenderer(this, 12, 34);
		LeftLeg.addBox(-1.5F, 0F, -1.5F, 3, 5, 3);
		LeftLeg.setRotationPoint(-3F, 18F, 0.5F);
		LeftLeg.setTextureSize(64, 32);
		LeftLeg.mirror = true;
		setRotation(LeftLeg, 0F, 0F, 0F);
		Body = new ModelRenderer(this, 0, 0);
		Body.addBox(-4F, -4.5F, -2F, 14, 14, 6);
		Body.setRotationPoint(-3F, 8.5F, -0.5F);
		Body.setTextureSize(64, 32);
		Body.mirror = true;
		setRotation(Body, 0F, 0F, 0F);
		LeftArm = new ModelRenderer(this, 0, 34);
		LeftArm.addBox(-3F, -1F, -1F, 3, 11, 3);
		LeftArm.setRotationPoint(-7F, 5F, 0F);
		LeftArm.setTextureSize(64, 32);
		LeftArm.mirror = true;
		setRotation(LeftArm, 0F, 0F, 0F);
		RightArm = new ModelRenderer(this, 0, 34);
		RightArm.addBox(0F, -1F, -1F, 3, 11, 3);
		RightArm.setRotationPoint(7F, 5F, 0F);
		RightArm.setTextureSize(64, 32);
		RightArm.mirror = true;
		setRotation(RightArm, 0F, 0F, 0F);
		Fat = new ModelRenderer(this, 40, 7);
		Fat.addBox(-3F, -2F, -1F, 9, 6, 1);
		Fat.setRotationPoint(-1.5F, 11F, -2.5F);
		Fat.setTextureSize(64, 32);
		Fat.mirror = true;
		setRotation(Fat, 0F, 0F, 0F);
		Head = new ModelRenderer(this, 0, 20);
		Head.addBox(-3.5F, -7F, -3.5F, 7, 7, 7);
		Head.setRotationPoint(0F, 4F, 0.5F);
		Head.setTextureSize(64, 32);
		Head.mirror = true;
		setRotation(Head, 0F, 0F, 0F);
		RightEar = new ModelRenderer(this, 0, 48);
		RightEar.addBox(0.5F, -6F, 0.5F, 2, 3, 0);
		RightEar.setRotationPoint(0F, 4F, 0.5F);
		RightEar.setTextureSize(64, 32);
		RightEar.mirror = true;
		setRotation(RightEar, 0F, 0F, 0.5410521F);
		LeftEar = new ModelRenderer(this, 0, 48);
		LeftEar.addBox(-2.5F, -6F, 0.5F, 2, 3, 0);
		LeftEar.setRotationPoint(0F, 4F, 0.5F);
		LeftEar.setTextureSize(64, 32);
		LeftEar.mirror = true;
		setRotation(LeftEar, 0F, 0F, -0.5410521F);
		Nose = new ModelRenderer(this, 12, 42);
		Nose.addBox(-0.5F, -4F, -4F, 1, 3, 1);
		Nose.setRotationPoint(0F, 4F, 0.5F);
		Nose.setTextureSize(64, 32);
		Nose.mirror = true;
		setRotation(Nose, 0F, 0F, 0F);
	}

	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		RightFoot.render(f5);
		LeftFoot.render(f5);
		RightLeg.render(f5);
		LeftLeg.render(f5);
		Body.render(f5);
		LeftArm.render(f5);
		RightArm.render(f5);
		Fat.render(f5);
		Head.render(f5);
		RightEar.render(f5);
		LeftEar.render(f5);
		Nose.render(f5);
	}

	private void setRotation(ModelRenderer model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

	public void setRotationAngles(float par1, float par2, float f2, float par4, float par5, float f5, Entity entity) {
		this.Head.rotateAngleY = par4 / (180F / (float)Math.PI);
		this.Head.rotateAngleX = par5 / (180F / (float)Math.PI);
		this.RightEar.rotateAngleY = par4 / (180F / (float)Math.PI);
		this.RightEar.rotateAngleX = par5 / (180F / (float)Math.PI);
		this.LeftEar.rotateAngleY = par4 / (180F / (float)Math.PI);
		this.LeftEar.rotateAngleX = par5 / (180F / (float)Math.PI);
		this.Nose.rotateAngleY = par4 / (180F / (float)Math.PI);
		this.Nose.rotateAngleX = par5 / (180F / (float)Math.PI);
		this.RightArm.rotateAngleX = MathHelper.cos(par1 * 0.6662F + (float)Math.PI) * 2.0F * par2 * 0.5F;
		this.LeftArm.rotateAngleX = MathHelper.cos(par1 * 0.6662F) * 2.0F * par2 * 0.5F;
		this.RightArm.rotateAngleZ = 0.0F;
		this.LeftArm.rotateAngleZ = 0.0F;
		this.RightLeg.rotateAngleX = MathHelper.cos(par1 * 0.6662F) * 1.4F * par2;
		this.LeftLeg.rotateAngleX = MathHelper.cos(par1 * 0.6662F + (float)Math.PI) * 1.4F * par2;
		this.RightFoot.rotateAngleX = MathHelper.cos(par1 * 0.6662F) * 1.4F * par2;
		this.LeftFoot.rotateAngleX = MathHelper.cos(par1 * 0.6662F + (float)Math.PI) * 1.4F * par2;
		this.RightLeg.rotateAngleY = 0.0F;
		this.LeftLeg.rotateAngleY = 0.0F;
		this.RightFoot.rotateAngleY = 0.0F;
		this.LeftFoot.rotateAngleY = 0.0F;
	}

}