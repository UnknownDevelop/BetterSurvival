package com.bettersurvival;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.world.World;

import com.bettersurvival.gui.GuiAdvancedLargeCraftingTable;
import com.bettersurvival.gui.GuiAdvancedQuartzFurnace;
import com.bettersurvival.gui.GuiAlloyPress;
import com.bettersurvival.gui.GuiAlloyToolbench;
import com.bettersurvival.gui.GuiBatteryBox;
import com.bettersurvival.gui.GuiBlueprintDrawer;
import com.bettersurvival.gui.GuiBlueprintTable;
import com.bettersurvival.gui.GuiBlueprintTableChest;
import com.bettersurvival.gui.GuiCableRollingMachine;
import com.bettersurvival.gui.GuiChargepad;
import com.bettersurvival.gui.GuiCoalGenerator;
import com.bettersurvival.gui.GuiCrusher;
import com.bettersurvival.gui.GuiDiamondFurnace;
import com.bettersurvival.gui.GuiEnergyTerminal;
import com.bettersurvival.gui.GuiEnergyTransmitter;
import com.bettersurvival.gui.GuiExtractor;
import com.bettersurvival.gui.GuiFiberizer;
import com.bettersurvival.gui.GuiFusionEnergyCellGenerator;
import com.bettersurvival.gui.GuiFusionReactor;
import com.bettersurvival.gui.GuiGeothermalGenerator;
import com.bettersurvival.gui.GuiGoldFurnace;
import com.bettersurvival.gui.GuiHTFurnace;
import com.bettersurvival.gui.GuiHeatGenerator;
import com.bettersurvival.gui.GuiHeliumExtractor;
import com.bettersurvival.gui.GuiIndustrialFurnace;
import com.bettersurvival.gui.GuiInterdimensionalEnergyTransmitter;
import com.bettersurvival.gui.GuiLargeCraftingTable;
import com.bettersurvival.gui.GuiOilRefinery;
import com.bettersurvival.gui.GuiPlasticFurnace;
import com.bettersurvival.gui.GuiQuartzFurnace;
import com.bettersurvival.gui.GuiRedstoneFurnace;
import com.bettersurvival.gui.GuiReinforcedAnvil;
import com.bettersurvival.gui.GuiWindmill;
import com.bettersurvival.gui.container.ContainerAdvancedLargeCraftingTable;
import com.bettersurvival.gui.container.ContainerAdvancedQuartzFurnace;
import com.bettersurvival.gui.container.ContainerAlloyPress;
import com.bettersurvival.gui.container.ContainerAlloyToolbench;
import com.bettersurvival.gui.container.ContainerBatteryBox;
import com.bettersurvival.gui.container.ContainerBlueprintDrawer;
import com.bettersurvival.gui.container.ContainerBlueprintTable;
import com.bettersurvival.gui.container.ContainerBlueprintTableChest;
import com.bettersurvival.gui.container.ContainerCableRollingMachine;
import com.bettersurvival.gui.container.ContainerChargepad;
import com.bettersurvival.gui.container.ContainerCoalGenerator;
import com.bettersurvival.gui.container.ContainerCrusher;
import com.bettersurvival.gui.container.ContainerDiamondFurnace;
import com.bettersurvival.gui.container.ContainerEnergyTerminal;
import com.bettersurvival.gui.container.ContainerEnergyTransmitter;
import com.bettersurvival.gui.container.ContainerExtractor;
import com.bettersurvival.gui.container.ContainerFiberizer;
import com.bettersurvival.gui.container.ContainerFusionEnergyCellGenerator;
import com.bettersurvival.gui.container.ContainerFusionReactor;
import com.bettersurvival.gui.container.ContainerGeothermalGenerator;
import com.bettersurvival.gui.container.ContainerGoldFurnace;
import com.bettersurvival.gui.container.ContainerHTFurnace;
import com.bettersurvival.gui.container.ContainerHeatGenerator;
import com.bettersurvival.gui.container.ContainerHeliumExtractor;
import com.bettersurvival.gui.container.ContainerIndustrialFurnace;
import com.bettersurvival.gui.container.ContainerInterdimensionalEnergyTransmitter;
import com.bettersurvival.gui.container.ContainerLargeCraftingTable;
import com.bettersurvival.gui.container.ContainerOilRefinery;
import com.bettersurvival.gui.container.ContainerPlasticFurnace;
import com.bettersurvival.gui.container.ContainerQuartzFurnace;
import com.bettersurvival.gui.container.ContainerRedstoneFurnace;
import com.bettersurvival.gui.container.ContainerReinforcedAnvil;
import com.bettersurvival.gui.container.ContainerWindmill;
import com.bettersurvival.tileentity.TileEntityAdvancedLargeCraftingTable;
import com.bettersurvival.tileentity.TileEntityAdvancedQuartzFurnace;
import com.bettersurvival.tileentity.TileEntityAlloyPress;
import com.bettersurvival.tileentity.TileEntityAlloyToolbench;
import com.bettersurvival.tileentity.TileEntityBatteryBox;
import com.bettersurvival.tileentity.TileEntityBlueprintDrawer;
import com.bettersurvival.tileentity.TileEntityBlueprintTable;
import com.bettersurvival.tileentity.TileEntityCableRollingMachine;
import com.bettersurvival.tileentity.TileEntityChargepad;
import com.bettersurvival.tileentity.TileEntityCoalGenerator;
import com.bettersurvival.tileentity.TileEntityCrusher;
import com.bettersurvival.tileentity.TileEntityDiamondFurnace;
import com.bettersurvival.tileentity.TileEntityEnergyTerminal;
import com.bettersurvival.tileentity.TileEntityEnergyTransmitter;
import com.bettersurvival.tileentity.TileEntityExtractor;
import com.bettersurvival.tileentity.TileEntityFiberizer;
import com.bettersurvival.tileentity.TileEntityFusionEnergyCellGenerator;
import com.bettersurvival.tileentity.TileEntityFusionReactor;
import com.bettersurvival.tileentity.TileEntityGeothermalGenerator;
import com.bettersurvival.tileentity.TileEntityGoldFurnace;
import com.bettersurvival.tileentity.TileEntityHTFurnace;
import com.bettersurvival.tileentity.TileEntityHeatGenerator;
import com.bettersurvival.tileentity.TileEntityHeliumExtractor;
import com.bettersurvival.tileentity.TileEntityIndustrialFurnace;
import com.bettersurvival.tileentity.TileEntityOilRefinery;
import com.bettersurvival.tileentity.TileEntityPlasticFurnace;
import com.bettersurvival.tileentity.TileEntityQuartzFurnace;
import com.bettersurvival.tileentity.TileEntityRedstoneFurnace;
import com.bettersurvival.tileentity.TileEntityWindmill;
import com.bettersurvival.tribe.client.TribeClientHandler;
import com.bettersurvival.tribe.data.ExtendedTribeProperties;
import com.bettersurvival.tribe.entity.EntityAutoTurret;
import com.bettersurvival.tribe.gui.GuiAutoTurret;
import com.bettersurvival.tribe.gui.GuiTribeInventory;
import com.bettersurvival.tribe.gui.container.ContainerAutoTurret;
import com.bettersurvival.tribe.gui.container.ContainerTribeInventory;

import cpw.mods.fml.common.network.IGuiHandler;

public class BetterSurvivalGuiHandler implements IGuiHandler 
{
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) 
	{
		TileEntity tileentity = world.getTileEntity(x, y, z);
		
		switch(ID)
		{
			case BetterSurvival.guiIDCrusher:
				if(tileentity instanceof TileEntityCrusher)
				{
					return new ContainerCrusher(player.inventory, (TileEntityCrusher) tileentity);
				}
			case BetterSurvival.guiIDHTFurnace:
				if(tileentity instanceof TileEntityHTFurnace)
				{
					return new ContainerHTFurnace(player.inventory, (TileEntityHTFurnace) tileentity);
				}
			case BetterSurvival.guiIDWindmill:
				while(tileentity instanceof TileEntityWindmill && world.getBlockMetadata(x, y, z) < 8)
				{
					y++;
				}
				return new ContainerWindmill(player.inventory, (TileEntityWindmill) world.getTileEntity(x, y, z));
			case BetterSurvival.guiIDDestillationTower:
				if(tileentity instanceof TileEntityOilRefinery)
				{
					return new ContainerOilRefinery(player.inventory, (TileEntityOilRefinery) world.getTileEntity(x, y, z));
				}
			case BetterSurvival.guiIDGoldFurnace:
				if(tileentity instanceof TileEntityGoldFurnace)
				{
					return new ContainerGoldFurnace(player.inventory, (TileEntityGoldFurnace) world.getTileEntity(x, y, z));
				}
			case BetterSurvival.guiIDDiamondFurnace:
				if(tileentity instanceof TileEntityDiamondFurnace)
				{
					return new ContainerDiamondFurnace(player.inventory, (TileEntityDiamondFurnace) world.getTileEntity(x, y, z));
				}
			case BetterSurvival.guiIDRedstoneFurnace:
				if(tileentity instanceof TileEntityRedstoneFurnace)
				{
					return new ContainerRedstoneFurnace(player.inventory, (TileEntityRedstoneFurnace) world.getTileEntity(x, y, z));
				}
			case BetterSurvival.guiIDQuartzFurnace:
				if(tileentity instanceof TileEntityQuartzFurnace)
				{
					return new ContainerQuartzFurnace(player.inventory, (TileEntityQuartzFurnace) world.getTileEntity(x, y, z));
				}
			case BetterSurvival.guiIDPlasticFurnace:
				if(tileentity instanceof TileEntityPlasticFurnace)
				{
					return new ContainerPlasticFurnace(player.inventory, (TileEntityPlasticFurnace) world.getTileEntity(x, y, z));
				}
			case BetterSurvival.guiIDLargeCraftingTable:
				return new ContainerLargeCraftingTable(player.inventory, world, x, y, z);
			case BetterSurvival.guiIDFusionEnergyCellGenerator:
				if(tileentity instanceof TileEntityFusionEnergyCellGenerator)
				{
					return new ContainerFusionEnergyCellGenerator(player.inventory, (TileEntityFusionEnergyCellGenerator) world.getTileEntity(x, y, z));
				}
			case BetterSurvival.guiIDHeliumExtractor:
				if(tileentity instanceof TileEntityHeliumExtractor)
				{
					return new ContainerHeliumExtractor(player.inventory, (TileEntityHeliumExtractor) world.getTileEntity(x, y, z));
				}
			case BetterSurvival.guiIDCoalGenerator:
				if(tileentity instanceof TileEntityCoalGenerator)
				{
					return new ContainerCoalGenerator(player.inventory, (TileEntityCoalGenerator) world.getTileEntity(x, y, z));
				}
			case BetterSurvival.guiIDBatteryBox:
				if(tileentity instanceof TileEntityBatteryBox)
				{
					return new ContainerBatteryBox(player.inventory, (TileEntityBatteryBox) world.getTileEntity(x, y, z));
				}
			case BetterSurvival.guiIDAdvancedLargeCraftingTable:
				if(tileentity instanceof TileEntityAdvancedLargeCraftingTable)
				{
					return new ContainerAdvancedLargeCraftingTable(player.inventory, world, x, y, z, (TileEntityAdvancedLargeCraftingTable)world.getTileEntity(x, y, z));
				}
			case BetterSurvival.guiIDEnergyTransmitter:
				return new ContainerEnergyTransmitter();
			case BetterSurvival.guiIDIndustrialFurnace:
				if(tileentity instanceof TileEntityIndustrialFurnace)
				{
					return new ContainerIndustrialFurnace(player.inventory, (TileEntityIndustrialFurnace) world.getTileEntity(x, y, z));
				}
			case BetterSurvival.guiIDAdvancedQuartzFurnace:
				if(tileentity instanceof TileEntityAdvancedQuartzFurnace)
				{
					return new ContainerAdvancedQuartzFurnace(player.inventory, (TileEntityAdvancedQuartzFurnace) world.getTileEntity(x, y, z));
				}
			case BetterSurvival.guiIDInterdimensionalEnergyTransmitter:
				if(tileentity instanceof TileEntityEnergyTransmitter)
				{
					return new ContainerInterdimensionalEnergyTransmitter();
				}
			case BetterSurvival.guiIDFiberizer:
				if(tileentity instanceof TileEntityFiberizer)
				{
					int metadata = world.getBlockMetadata(x, y, z);
					
					if(metadata >= 10)
					{
						y--;
					}
					
					return new ContainerFiberizer(player.inventory, (TileEntityFiberizer) world.getTileEntity(x, y, z));
				}
			case BetterSurvival.guiIDCableRollingMachine:
				if(tileentity instanceof TileEntityCableRollingMachine)
				{
					return new ContainerCableRollingMachine(player.inventory, (TileEntityCableRollingMachine) world.getTileEntity(x, y, z));
				}
			case BetterSurvival.guiIDGeothermalGenerator:
				if(tileentity instanceof TileEntityGeothermalGenerator)
				{
					return new ContainerGeothermalGenerator(player.inventory, (TileEntityGeothermalGenerator) world.getTileEntity(x, y, z));
				}
			case BetterSurvival.guiIDExtractor:
				if(tileentity instanceof TileEntityExtractor)
				{
					return new ContainerExtractor(player.inventory, (TileEntityExtractor) world.getTileEntity(x, y, z));
				}
			case BetterSurvival.guiIDEnergyTerminal:
				if(tileentity instanceof TileEntityEnergyTerminal)
				{
					return new ContainerEnergyTerminal(player.inventory, (TileEntityEnergyTerminal) world.getTileEntity(x, y, z));
				}
			case BetterSurvival.guiIDTribeInventory:
				ExtendedTribeProperties properties = (ExtendedTribeProperties) player.getExtendedProperties("BetterSurvivalTribe");
				return new ContainerTribeInventory(player, player.inventory, BetterSurvival.tribeHandler.getTribe(properties.tribeID));
			case BetterSurvival.guiIDAutoTurret:
				Entity entity = world.getEntityByID(x);
				
				if(entity != null && entity instanceof EntityAutoTurret)
				{
					return new ContainerAutoTurret(player, player.inventory, (EntityAutoTurret)entity);
				}
				
				return null;
			case BetterSurvival.guiIDChargepad:
				if(tileentity instanceof TileEntityChargepad)
				{
					return new ContainerChargepad(player.inventory, (TileEntityChargepad) world.getTileEntity(x, y, z));
				}
			case BetterSurvival.guiIDBlueprintTable:
				if(tileentity instanceof TileEntityBlueprintTable)
				{
					return new ContainerBlueprintTable(player.inventory, (TileEntityBlueprintTable) world.getTileEntity(x, y, z));
				}
			case BetterSurvival.guiIDBlueprintTableChest:
				if(tileentity instanceof TileEntityChest)
				{
					return new ContainerBlueprintTableChest(player.inventory, (TileEntityChest) world.getTileEntity(x, y, z));
				}
			case BetterSurvival.guiIDAlloyPress:
				if(tileentity instanceof TileEntityAlloyPress)
				{
					return new ContainerAlloyPress(player.inventory, (TileEntityAlloyPress) world.getTileEntity(x, y, z));
				}
			case BetterSurvival.guiIDAlloyToolbench:
				if(tileentity instanceof TileEntityAlloyToolbench)
				{
					return new ContainerAlloyToolbench(player.inventory, (TileEntityAlloyToolbench) world.getTileEntity(x, y, z));
				}
			case BetterSurvival.guiIDReinforcedAnvil:
				return new ContainerReinforcedAnvil(player.inventory);
			case BetterSurvival.guiIDBlueprintDrawer:
				if(tileentity instanceof TileEntityBlueprintDrawer)
				{
					return new ContainerBlueprintDrawer(player.inventory, (TileEntityBlueprintDrawer) world.getTileEntity(x, y, z));
				}
			case BetterSurvival.guiIDFusionReactor:
				if(tileentity instanceof TileEntityFusionReactor)
				{
					return new ContainerFusionReactor(player.inventory, (TileEntityFusionReactor) world.getTileEntity(x, y, z));
				}
			case BetterSurvival.guiIDHeatGenerator:
				if(tileentity instanceof TileEntityHeatGenerator)
				{
					return new ContainerHeatGenerator(player.inventory, (TileEntityHeatGenerator) world.getTileEntity(x, y, z));
				}
		}
		
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) 
	{
		TileEntity tileentity = world.getTileEntity(x, y, z);
		
		switch(ID)
		{
			case BetterSurvival.guiIDCrusher:
				if(tileentity instanceof TileEntityCrusher)
				{
					return new GuiCrusher(player.inventory, (TileEntityCrusher) tileentity);
				}
			case BetterSurvival.guiIDHTFurnace:
				if(tileentity instanceof TileEntityHTFurnace)
				{
					return new GuiHTFurnace(player.inventory, (TileEntityHTFurnace) tileentity);
				}
			case BetterSurvival.guiIDWindmill:
				while(tileentity instanceof TileEntityWindmill && world.getBlockMetadata(x, y, z) < 8)
				{
					y++;
				}
				
				return new GuiWindmill(player.inventory, (TileEntityWindmill) world.getTileEntity(x, y, z));
			case BetterSurvival.guiIDDestillationTower:
				if(tileentity instanceof TileEntityOilRefinery)
				{
					return new GuiOilRefinery(player.inventory, (TileEntityOilRefinery) world.getTileEntity(x, y, z));
				}
			case BetterSurvival.guiIDGoldFurnace:
				if(tileentity instanceof TileEntityGoldFurnace)
				{
					return new GuiGoldFurnace(player.inventory, (TileEntityGoldFurnace) world.getTileEntity(x, y, z));
				}
			case BetterSurvival.guiIDDiamondFurnace:
				if(tileentity instanceof TileEntityDiamondFurnace)
				{
					return new GuiDiamondFurnace(player.inventory, (TileEntityDiamondFurnace) world.getTileEntity(x, y, z));
				}
			case BetterSurvival.guiIDRedstoneFurnace:
				if(tileentity instanceof TileEntityRedstoneFurnace)
				{
					return new GuiRedstoneFurnace(player.inventory, (TileEntityRedstoneFurnace) world.getTileEntity(x, y, z));
				}
			case BetterSurvival.guiIDQuartzFurnace:
				if(tileentity instanceof TileEntityQuartzFurnace)
				{
					return new GuiQuartzFurnace(player.inventory, (TileEntityQuartzFurnace) world.getTileEntity(x, y, z));
				}
			case BetterSurvival.guiIDPlasticFurnace:
				if(tileentity instanceof TileEntityPlasticFurnace)
				{
					return new GuiPlasticFurnace(player.inventory, (TileEntityPlasticFurnace) world.getTileEntity(x, y, z));
				}
			case BetterSurvival.guiIDLargeCraftingTable:
				return new GuiLargeCraftingTable(player.inventory, world, x, y, z);
			case BetterSurvival.guiIDFusionEnergyCellGenerator:
				if(tileentity instanceof TileEntityFusionEnergyCellGenerator)
				{
					return new GuiFusionEnergyCellGenerator(player.inventory, (TileEntityFusionEnergyCellGenerator) world.getTileEntity(x, y, z));
				}
			case BetterSurvival.guiIDHeliumExtractor:
				if(tileentity instanceof TileEntityHeliumExtractor)
				{
					return new GuiHeliumExtractor(player.inventory, (TileEntityHeliumExtractor) world.getTileEntity(x, y, z));
				}
			case BetterSurvival.guiIDCoalGenerator:
				if(tileentity instanceof TileEntityCoalGenerator)
				{
					return new GuiCoalGenerator(player.inventory, (TileEntityCoalGenerator) world.getTileEntity(x, y, z));
				}
			case BetterSurvival.guiIDBatteryBox:
				if(tileentity instanceof TileEntityBatteryBox)
				{
					return new GuiBatteryBox(player.inventory, (TileEntityBatteryBox) world.getTileEntity(x, y, z));
				}
			case BetterSurvival.guiIDAdvancedLargeCraftingTable:
				if(tileentity instanceof TileEntityAdvancedLargeCraftingTable)
				{
					return new GuiAdvancedLargeCraftingTable(player.inventory, world, x, y, z, (TileEntityAdvancedLargeCraftingTable)world.getTileEntity(x, y, z));
				}
			case BetterSurvival.guiIDEnergyTransmitter:
				if(tileentity instanceof TileEntityEnergyTransmitter)
				{
					return new GuiEnergyTransmitter((TileEntityEnergyTransmitter)world.getTileEntity(x, y, z));
				}
			case BetterSurvival.guiIDIndustrialFurnace:
				if(tileentity instanceof TileEntityIndustrialFurnace)
				{
					return new GuiIndustrialFurnace(player.inventory, (TileEntityIndustrialFurnace) world.getTileEntity(x, y, z));
				}
			case BetterSurvival.guiIDAdvancedQuartzFurnace:
				if(tileentity instanceof TileEntityAdvancedQuartzFurnace)
				{
					return new GuiAdvancedQuartzFurnace(player.inventory, (TileEntityAdvancedQuartzFurnace) world.getTileEntity(x, y, z));
				}
			case BetterSurvival.guiIDInterdimensionalEnergyTransmitter:
				if(tileentity instanceof TileEntityEnergyTransmitter)
				{
					return new GuiInterdimensionalEnergyTransmitter((TileEntityEnergyTransmitter)world.getTileEntity(x, y, z));
				}
			case BetterSurvival.guiIDFiberizer:
				if(tileentity instanceof TileEntityFiberizer)
				{
					int metadata = world.getBlockMetadata(x, y, z);
					
					if(metadata >= 10)
					{
						y--;
					}
					
					return new GuiFiberizer(player.inventory, (TileEntityFiberizer) world.getTileEntity(x, y, z));
				}
			case BetterSurvival.guiIDCableRollingMachine:
				if(tileentity instanceof TileEntityCableRollingMachine)
				{
					return new GuiCableRollingMachine(player.inventory, (TileEntityCableRollingMachine)world.getTileEntity(x, y, z));
				}
			case BetterSurvival.guiIDGeothermalGenerator:
				if(tileentity instanceof TileEntityGeothermalGenerator)
				{
					return new GuiGeothermalGenerator(player.inventory, (TileEntityGeothermalGenerator)world.getTileEntity(x, y, z));
				}
			case BetterSurvival.guiIDExtractor:
				if(tileentity instanceof TileEntityExtractor)
				{
					return new GuiExtractor(player.inventory, (TileEntityExtractor)world.getTileEntity(x, y, z));
				}
			case BetterSurvival.guiIDEnergyTerminal:
				if(tileentity instanceof TileEntityEnergyTerminal)
				{
					return new GuiEnergyTerminal(player.inventory, (TileEntityEnergyTerminal)world.getTileEntity(x, y, z));
				}
			case BetterSurvival.guiIDTribeInventory:
				ExtendedTribeProperties properties = (ExtendedTribeProperties) player.getExtendedProperties("BetterSurvivalTribe");
				
				return new GuiTribeInventory(player, player.inventory, TribeClientHandler.INSTANCE.getTribe(properties.tribeID));
			case BetterSurvival.guiIDAutoTurret:
				Entity entity = world.getEntityByID(x);
				
				if(entity != null && entity instanceof EntityAutoTurret)
				{
					return new GuiAutoTurret(player, player.inventory, (EntityAutoTurret)entity);
				}
				
				return null;
			case BetterSurvival.guiIDChargepad:
				if(tileentity instanceof TileEntityChargepad)
				{
					return new GuiChargepad(player.inventory, (TileEntityChargepad)world.getTileEntity(x, y, z));
				}
			case BetterSurvival.guiIDBlueprintTable:
				if(tileentity instanceof TileEntityBlueprintTable)
				{
					return new GuiBlueprintTable(player.inventory, (TileEntityBlueprintTable)world.getTileEntity(x, y, z));
				}
			case BetterSurvival.guiIDBlueprintTableChest:
				if(tileentity instanceof TileEntityChest)
				{
					return new GuiBlueprintTableChest(player.inventory, (TileEntityChest)world.getTileEntity(x, y, z));
				}
			case BetterSurvival.guiIDAlloyPress:
				if(tileentity instanceof TileEntityAlloyPress)
				{
					return new GuiAlloyPress(player.inventory, (TileEntityAlloyPress)world.getTileEntity(x, y, z));
				}
			case BetterSurvival.guiIDAlloyToolbench:
				if(tileentity instanceof TileEntityAlloyToolbench)
				{
					return new GuiAlloyToolbench(player.inventory, (TileEntityAlloyToolbench)world.getTileEntity(x, y, z));
				}
			case BetterSurvival.guiIDReinforcedAnvil:
				return new GuiReinforcedAnvil(player.inventory);
			case BetterSurvival.guiIDBlueprintDrawer:
				if(tileentity instanceof TileEntityBlueprintDrawer)
				{
					return new GuiBlueprintDrawer(player.inventory, (TileEntityBlueprintDrawer)world.getTileEntity(x, y, z));
				}
			case BetterSurvival.guiIDFusionReactor:
				if(tileentity instanceof TileEntityFusionReactor)
				{
					return new GuiFusionReactor(player.inventory, (TileEntityFusionReactor)world.getTileEntity(x, y, z));
				}
			case BetterSurvival.guiIDHeatGenerator:
				if(tileentity instanceof TileEntityHeatGenerator)
				{
					return new GuiHeatGenerator(player.inventory, (TileEntityHeatGenerator)world.getTileEntity(x, y, z));
				}
		}
		
		return null;
	}
}
