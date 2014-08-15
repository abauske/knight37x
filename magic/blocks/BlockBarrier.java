package knight37x.magic.blocks;

import knight37x.lance.StaticMethods;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockBarrier extends Block {

	public BlockBarrier(Material material) {
		super(material);
		

        float f = 0.375F;
        float f1 = f / 2.0F;
        this.setBlockBounds(-0.05F, 0.0F, 0.0F, 1.0F, 1.5F, 0.05F);
        this.setHarvestLevel("axe", 0);
	}

	@Override
	public boolean canPlaceBlockOnSide(World world, int x, int y, int z, int side) {
		return world.getBlock(x, y - 1, z).isOpaqueCube();
	}

	@Override
	public boolean canPlaceTorchOnTop(World world, int x, int y, int z) {
		return false;
	}

	@Override
	public boolean isBlockNormalCube() {
		return false;
	}

	@Override
	public boolean isNormalCube() {
		return false;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public boolean isBlockSolid(IBlockAccess p_149747_1_, int p_149747_2_, int p_149747_3_, int p_149747_4_, int p_149747_5_) {
		return false;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public int onBlockPlaced(World p_149660_1_, int p_149660_2_, int p_149660_3_, int p_149660_4_, int p_149660_5_, float p_149660_6_, float p_149660_7_, float p_149660_8_, int p_149660_9_) {
		return super.onBlockPlaced(p_149660_1_, p_149660_2_, p_149660_3_, p_149660_4_, p_149660_5_, p_149660_6_, p_149660_7_, p_149660_8_, p_149660_9_);
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack) {
		double distX = x - entity.posX;
		double distZ = z - entity.posZ;
		if(distX > distZ) {
			if(distX > 0) {
				this.setBlockBounds(0.95F, 0.0F, 0.0F, 1.0F, 1.5F, 1.0F);
			} else {
				this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.5F, 0.05F);
			}
		} else {
			if(distZ > 0) {
				this.setBlockBounds(0.0F, 0.0F, 0.95F, 1.0F, 1.5F, 1.0F);
			} else {
				this.setBlockBounds(0.0F, 0.0F, 0.0F, 0.05F, 1.5F, 1.0F);
			}
		}
	}

	@Override
	public boolean isNormalCube(IBlockAccess world, int x, int y, int z) {
		return false;
	}
}
