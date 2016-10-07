package com.bettersurvival.energy;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class UpgradeUtility 
{
	public static MachineInformation getModifiedMachineInformation(MachineInformation defaultInfo, ItemStack[] upgradeSlots)
	{
		MachineInformation newInfo = defaultInfo.copy();
		
		for(int i = 0; i < upgradeSlots.length; i++)
		{
			if(upgradeSlots[i] != null)
			{
				Item item = upgradeSlots[i].getItem();
				
				if(item instanceof IUpgrade)
				{
					newInfo.addSpeed(((IUpgrade)item).getModifiedSpeed()*upgradeSlots[i].stackSize);
					newInfo.addItemEfficiency(((IUpgrade)item).getModifiedItemEfficiency()*upgradeSlots[i].stackSize);
					newInfo.addEfficiency(((IUpgrade)item).getModifiedEfficiency()*upgradeSlots[i].stackSize);
				}
			}
		}
		
		if(newInfo.efficiency() < 0.1f)
		{
			newInfo.addEfficiency(0.1f-newInfo.efficiency());
		}
		
		return newInfo;
	}
}
