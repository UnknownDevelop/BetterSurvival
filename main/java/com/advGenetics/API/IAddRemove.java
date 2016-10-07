package com.advGenetics.API;

import net.minecraft.entity.Entity;

public interface IAddRemove {
	
	void addToDNA(Entity entity);
	void removeFromDNA(Entity entity);
	
}
