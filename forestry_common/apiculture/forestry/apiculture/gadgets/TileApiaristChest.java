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
package forestry.apiculture.gadgets;

import forestry.api.genetics.AlleleManager;
import forestry.apiculture.genetics.BeeHelper;
import forestry.core.gadgets.TileNaturalistChest;
import forestry.core.network.GuiId;

public class TileApiaristChest extends TileNaturalistChest {

	public TileApiaristChest() {
		super(AlleleManager.alleleRegistry.getSpeciesRoot(BeeHelper.UID), GuiId.ApiaristChestGUI.ordinal());
	}

}
