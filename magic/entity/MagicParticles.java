package knight37x.magic.entity;

import knight37x.lance.StaticMethods;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.world.World;

public class MagicParticles {

	private static Minecraft mc = Minecraft.getMinecraft();
	private static World world = mc.theWorld;

	public static EntityFX spawnParticle(String particleName, double par2, double par4, double par6, double par8, double par10, double par12) {
		if (mc != null && mc.renderViewEntity != null && mc.effectRenderer != null) {
			int var14 = mc.gameSettings.particleSetting;

			if (var14 == 1 && world.rand.nextInt(3) == 0) {
				var14 = 2;
			}

			double var15 = mc.renderViewEntity.posX - par2;
			double var17 = mc.renderViewEntity.posY - par4;
			double var19 = mc.renderViewEntity.posZ - par6;
			EntityFX entity = null;
			double var22 = 200.0D;//16.0D;

			if (var15 * var15 + var17 * var17 + var19 * var19 > var22 * var22) {
				return null;
			} else if (var14 > 1) {
				return null;
			} else {
				if (particleName.equals("Mana")) {
					entity = new EntityManaParticle(world, par2, par4, par6, (float) par8, (float) par10, (float) par12);
					entity.setVelocity(0, 0, 0);
				} else if (particleName.equals("Drop")) {
					entity = new EntityDropParticle(world, par2, par4, par6, Material.water);
					entity.setVelocity(0, 0, 0);
				}

				mc.effectRenderer.addEffect((EntityFX) entity);
				return (EntityFX) entity;
			}
		}
		return null;
	}
}