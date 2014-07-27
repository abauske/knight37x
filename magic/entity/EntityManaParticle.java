package knight37x.magic.entity;

import java.util.Random;

import net.minecraft.client.particle.EntityFX;
import net.minecraft.world.World;

public class EntityManaParticle extends EntityFX {

	public EntityManaParticle(World par1World, double par2, double par4, double par6, double par8, double par10, double par12) {
		super(par1World, par2, par4, par6, par8, par10, par12);
		Random rand = new Random();
		int i = rand.nextInt(3);
		if(i == 0) {
			this.setRBGColorF(0.8F, 0.9F, 1F);
		} else if(i == 1) {
			this.setRBGColorF(0F, 0.3F, 1F);
		} else if(i == 2) {
			this.setRBGColorF(0F, 0.7F, 1F);
		}
	}
	
	public EntityManaParticle(World par1World, double par2, double par4, double par6) {
		super(par1World, par2, par4, par6);
	}

}
