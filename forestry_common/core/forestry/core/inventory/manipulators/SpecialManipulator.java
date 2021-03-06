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
package forestry.core.inventory.manipulators;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

import net.minecraftforge.common.util.ForgeDirection;

import buildcraft.api.inventory.ISpecialInventory;

import forestry.core.inventory.filters.IStackFilter;

/**
 *
 * @author CovertJaguar <http://www.railcraft.info>
 */
public class SpecialManipulator extends InventoryManipulator {

	private final ISpecialInventory inv;
	protected ForgeDirection side = ForgeDirection.UNKNOWN;

	protected SpecialManipulator(ISpecialInventory inv) {
		super(inv);
		this.inv = inv;
	}

	public void setSide(ForgeDirection side) {
		this.side = side;
	}

	@Override
	public ItemStack tryAddStack(ItemStack stack) {
		return addStackInternal(stack, false);
	}

	@Override
	public ItemStack addStack(ItemStack stack) {
		return addStackInternal(stack, true);
	}

	private ItemStack addStackInternal(ItemStack stack, boolean doAdd) {
		if (stack == null) {
			return null;
		}
		stack = stack.copy();
		int used = inv.addItem(stack.copy(), doAdd, side);
		if (used >= stack.stackSize) {
			return null;
		}
		stack.stackSize -= used;
		return stack;
	}

	@Override
	public boolean canRemoveItem(IStackFilter filter) {
		return tryRemoveItem(filter) == null;
	}

	@Override
	public ItemStack tryRemoveItem(IStackFilter filter) {
		return removeStackInternal(filter, false);
	}

	@Override
	public ItemStack removeItem(IStackFilter filter) {
		return removeStackInternal(filter, true);
	}

	protected ItemStack removeStackInternal(IStackFilter filter, boolean doRemove) {
		ItemStack[] extracted = inv.extractItem(false, side, 1);
		if (extracted != null && extracted.length > 0 && filter.matches(extracted[0])) {
			if (doRemove) {
				inv.extractItem(true, side, 1);
			}
			return extracted[0];
		}
		return null;
	}

	@Override
	public ItemStack moveItem(IInventory dest, IStackFilter filter) {
		InventoryManipulator imDest = InventoryManipulator.get(dest);
		ItemStack stack = tryRemoveItem(filter);
		if (stack != null) {
			ItemStack result = imDest.addStack(stack);
			if (result == null) {
				return removeItem(filter);
			}
		}
		return null;
	}

}
