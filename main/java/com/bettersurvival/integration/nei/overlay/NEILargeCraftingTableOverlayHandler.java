package com.bettersurvival.integration.nei.overlay;

import java.util.LinkedList;
import java.util.List;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Slot;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.DefaultOverlayHandler;
import cpw.mods.fml.common.Optional;

@Optional.Interface(iface = "codechicken.nei.api.API", modid = "NotEnoughItems")
public class NEILargeCraftingTableOverlayHandler extends DefaultOverlayHandler
{
    @SuppressWarnings("unchecked")
	@Override
    public Slot[][] mapIngredSlots(GuiContainer gui, List<PositionedStack> ingredients)
    {
    	Slot[][] recipeSlotList = new Slot[ingredients.size()][];
        for(int i = 0; i < ingredients.size(); i++)//identify slots
        {
            LinkedList<Slot> recipeSlots = new LinkedList<Slot>();
            PositionedStack pstack = ingredients.get(i);
            for(Slot slot : (List<Slot>)gui.inventorySlots.inventorySlots)
            {
                if(slot.xDisplayPosition == pstack.relx+offsetx && slot.yDisplayPosition == pstack.rely+offsety)
                {
                    recipeSlots.add(slot);
                    break;
                }
            }
            recipeSlotList[i] = recipeSlots.toArray(new Slot[0]);
        }
        return recipeSlotList;
    }
}
