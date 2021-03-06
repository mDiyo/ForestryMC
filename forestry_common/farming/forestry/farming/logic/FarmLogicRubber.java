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
package forestry.farming.logic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Stack;

import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraftforge.common.util.ForgeDirection;

import forestry.api.farming.ICrop;
import forestry.api.farming.IFarmHousing;
import forestry.core.proxy.Proxies;
import forestry.core.utils.StackUtils;
import forestry.core.utils.Vect;
import forestry.plugins.PluginIC2;

public class FarmLogicRubber extends FarmLogic {

	private boolean inActive;

	public FarmLogicRubber(IFarmHousing housing) {
		super(housing);
		if (PluginIC2.rubberwood == null || PluginIC2.resin == null) {
			Proxies.log.warning("Failed to init a farm logic %s since IC2 was not found", getClass().getName());
			inActive = true;
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon() {
		if (!inActive)
			return PluginIC2.resin.getIconIndex();
		else
			return Items.gunpowder.getIconFromDamage(0);
	}

	@Override
	public String getName() {
		return "Rubber Plantation";
	}

	@Override
	public int getFertilizerConsumption() {
		return 40;
	}

	@Override
	public int getWaterConsumption(float hydrationModifier) {
		return (int) (5 * hydrationModifier);
	}

	@Override
	public boolean isAcceptedResource(ItemStack itemstack) {
		return false;
	}

	@Override
	public boolean isAcceptedGermling(ItemStack itemstack) {
		return false;
	}

	@Override
	public Collection<ItemStack> collect() {
		return null;
	}

	@Override
	public boolean cultivate(int x, int y, int z, ForgeDirection direction, int extent) {
		return false;
	}

	private final HashMap<Vect, Integer> lastExtents = new HashMap<Vect, Integer>();

	@Override
	public Collection<ICrop> harvest(int x, int y, int z, ForgeDirection direction, int extent) {
		if (inActive)
			return null;

		world = housing.getWorld();

		Collection<ICrop> crops = null;
		Vect start = new Vect(x, y, z);
		if (!lastExtents.containsKey(start))
			lastExtents.put(start, 0);

		int lastExtent = lastExtents.get(start);
		if (lastExtent > extent)
			lastExtent = 0;

		Vect position = translateWithOffset(x, y + 1, z, direction, lastExtent);
		crops = getHarvestBlocks(position);
		lastExtent++;
		lastExtents.put(start, lastExtent);

		return crops;
	}

	private Collection<ICrop> getHarvestBlocks(Vect position) {

		ArrayList<Vect> seen = new ArrayList<Vect>();
		Stack<ICrop> crops = new Stack<ICrop>();

		// Determine what type we want to harvest.
		Block block = getBlock(position);
		if (!StackUtils.equals(block, PluginIC2.rubberwood))
			return crops;

		int meta = this.getBlockMeta(position);
		if (meta >= 2 && meta <= 5)
			crops.push(new CropRubber(world, block, meta, position));

		ArrayList<Vect> candidates = processHarvestBlock(crops, seen, position);
		ArrayList<Vect> temp = new ArrayList<Vect>();
		while (!candidates.isEmpty() && crops.size() < 100) {
			for (Vect candidate : candidates)
				temp.addAll(processHarvestBlock(crops, seen, candidate));
			candidates.clear();
			candidates.addAll(temp);
			temp.clear();
		}

		return crops;
	}

	private ArrayList<Vect> processHarvestBlock(Stack<ICrop> crops, Collection<Vect> seen, Vect position) {

		ArrayList<Vect> candidates = new ArrayList<Vect>();

		// Get additional candidates to return
		for (int j = 0; j < 2; j++) {
			Vect candidate = new Vect(position.x, position.y + j, position.z);
			if (candidate.equals(position))
				continue;

			// See whether the given position has already been processed
			boolean skip = false;
			for (Vect prcs : seen)
				if (candidate.equals(prcs)) {
					skip = true;
					break;
				}

			if (skip)
				continue;

			Block block = getBlock(candidate);
			if (StackUtils.equals(block, PluginIC2.rubberwood)) {
				int meta = this.getBlockMeta(candidate);
				if (meta >= 2 && meta <= 5)
					crops.push(new CropRubber(world, block, meta, candidate));
				candidates.add(candidate);
				seen.add(candidate);
			}
		}

		return candidates;
	}

}
