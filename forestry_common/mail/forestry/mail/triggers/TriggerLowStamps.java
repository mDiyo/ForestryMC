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
package forestry.mail.triggers;

import net.minecraft.tileentity.TileEntity;

import net.minecraftforge.common.util.ForgeDirection;

import buildcraft.api.gates.ITriggerParameter;

import forestry.core.triggers.Trigger;
import forestry.mail.gadgets.MachineTrader;

public class TriggerLowStamps extends Trigger {

	int threshold;

	public TriggerLowStamps(String tag, int threshold) {
		super(tag, "lowStamps");
		this.threshold = threshold;
	}

	@Override
	public String getDescription() {
		return super.getDescription() + " < " + threshold + "p";
	}

	@Override
	public boolean isTriggerActive(ForgeDirection direction, TileEntity tile, ITriggerParameter parameter) {

		if (!(tile instanceof MachineTrader))
			return false;

		return !((MachineTrader) tile).hasPostageMin(threshold);
	}

}
