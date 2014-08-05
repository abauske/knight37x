package knight37x.magic;

import java.util.List;

import net.minecraft.client.particle.EntityFX;
import net.minecraft.entity.EntityLivingBase;

public class VictimWithDrops {

	private EntityLivingBase victim;
	private List<EntityFX> drops;
	
	public VictimWithDrops(EntityLivingBase victim, List<EntityFX> drops) {
		this.victim = victim;
		this.drops = drops;
	}

	public EntityLivingBase getVictim() {
		return victim;
	}

	public List<EntityFX> getDrops() {
		return drops;
	}
	
}
