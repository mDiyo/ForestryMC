/*******************************************************************************
 * Copyright (c) 2011-2014 SirSengir.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl-3.0.txt
 * 
 * Various Contributors including, but not limited to:
 * SirSengir (original work), CovertJaguar, Player, Binnie, MysteriousAges
 ******************************************************************************/
package forestry.lepidopterology.entities;

import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.Vec3;

public abstract class AIButterflyBase extends EntityAIBase {

	protected EntityButterfly entity;

	public AIButterflyBase(EntityButterfly entity) {
		this.entity = entity;
	}

	protected ChunkCoordinates getRandomDestination() {
		if(entity.isInWater())
			return getRandomDestinationUpwards();

		Vec3 randomTarget = RandomPositionGenerator.findRandomTargetBlockAwayFrom(entity, 16, 7,
				Vec3.createVectorHelper(entity.posX, entity.posY, entity.posZ));

		if (randomTarget == null)
			return null;

		ChunkCoordinates dest = new ChunkCoordinates((int)randomTarget.xCoord, (int)randomTarget.yCoord, (int)randomTarget.zCoord);
		if(validateDestination(dest))
			return dest;
		else
			return null;
	}

	protected ChunkCoordinates getRandomDestinationUpwards() {
		ChunkCoordinates dest = new ChunkCoordinates((int)entity.posX, (int)entity.posY + entity.worldObj.rand.nextInt(10) + 2, (int)entity.posZ);
		if(validateDestination(dest))
			return dest;
		else
			return null;
	}

	protected boolean validateDestination(ChunkCoordinates dest) {
		if(!entity.worldObj.isAirBlock(dest.posX, dest.posY, dest.posZ) || dest.posY < 1)
			return false;
		if(!entity.getButterfly().isAcceptedEnvironment(entity.worldObj, dest.posX, dest.posY, dest.posZ))
			return false;

		return true;
	}


}
