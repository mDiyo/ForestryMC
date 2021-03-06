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
package forestry.core.genetics;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import forestry.api.genetics.IAlleleSpecies;
import forestry.api.genetics.IIndividual;
import forestry.core.proxy.Proxies;
import forestry.core.utils.StringUtil;

public abstract class ItemGE extends Item {

	protected ItemGE() {
		super();
		// maxStackSize = 1;
		hasSubtypes = true;
	}

	protected abstract int getDefaultPrimaryColour();

	protected abstract int getDefaultSecondaryColour();

	protected abstract IIndividual getIndividual(ItemStack itemstack);

	@Override
	public boolean isDamageable() {
		return false;
	}

	@Override
	public boolean isRepairable() {
		return false;
	}

	@Override
	public boolean getShareTag() {
		return true;
	}

	@Override
	public boolean hasEffect(ItemStack itemstack) {
		if (!itemstack.hasTagCompound())
			return false;

		IIndividual individual = getIndividual(itemstack);
		return individual.hasEffect();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean flag) {
		if(!itemstack.hasTagCompound())
			return;

		IIndividual individual = getIndividual(itemstack);

		if(individual.isAnalyzed()) {
			if(Proxies.common.isShiftDown())
				individual.addTooltip(list);
			else
				list.add("\u00A7o<" + StringUtil.localize("gui.tooltip.tmi") + ">");
		} else
			list.add("<" + StringUtil.localize("gui.unknown") + ">");
	}

	/* ICONS */
	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IIconRegister par1IconRegister) {
		// Need to disable normal registration.
	}

	@Override
	public int getColorFromItemStack(ItemStack itemstack, int renderPass) {

		if (renderPass == 0)
			return getDefaultPrimaryColour();
		else if (renderPass == 1)
			return getDefaultSecondaryColour();
		else
			return 0xffffff;

	}

	public int getColourFromSpecies(IAlleleSpecies species, int renderPass) {
		return 0xffffff;
	}

}
