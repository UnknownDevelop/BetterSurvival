package com.advGenetics.API.Power;

public interface IPowerReceiver extends IPower {

	int getStoredEnergy();
	int getMaxStoredEnergy();
	void setStoredEnergy(double par1);
	
}
