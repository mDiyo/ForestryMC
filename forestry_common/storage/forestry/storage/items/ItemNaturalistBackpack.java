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
package forestry.storage.items;

import java.util.ArrayList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import forestry.api.core.ForestryAPI;
import forestry.plugins.PluginApiculture;
import forestry.plugins.PluginLepidopterology;
import forestry.storage.BackpackDefinition;

public class ItemNaturalistBackpack extends ItemBackpack {

	public static class BackpackDefinitionApiarist extends BackpackDefinition {

		public BackpackDefinitionApiarist(String name, int primaryColor) {
			super(name, primaryColor);
		}

		@Override
		public boolean isValidItem(EntityPlayer player, ItemStack stack) {
			return PluginApiculture.beeInterface.isMember(stack);
		}
	}

	public static class BackpackDefinitionLepidopterist extends BackpackDefinition {

		public BackpackDefinitionLepidopterist(String name, int primaryColor) {
			super(name, primaryColor);
		}

		@Override
		public boolean isValidItem(EntityPlayer player, ItemStack stack) {
			return PluginLepidopterology.butterflyInterface.isMember(stack);
		}
	}
	private final int guiId;

	public ItemNaturalistBackpack(int guiId, BackpackDefinition definition) {
		super(definition, 0);
		this.guiId = guiId;
	}

	@Override
	public void openGui(EntityPlayer player, ItemStack itemstack) {
		player.openGui(ForestryAPI.instance, guiId, player.worldObj, (int) player.posX, (int) player.posY, (int) player.posZ);
	}

	@Override
	public ArrayList<ItemStack> getValidItems(EntityPlayer player) {
		return null;
	}
}
