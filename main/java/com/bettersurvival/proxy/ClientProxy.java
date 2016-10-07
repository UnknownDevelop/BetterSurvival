package com.bettersurvival.proxy;

import java.lang.reflect.InvocationTargetException;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;

import com.bettersurvival.BetterSurvival;
import com.bettersurvival.event.client.BlockHighlightEvent;
import com.bettersurvival.event.client.ItemTooltipEvent;
import com.bettersurvival.event.client.KeyInputHandler;
import com.bettersurvival.gui.overlay.GuiDebug;
import com.bettersurvival.gui.overlay.GuiRadioactivity;
import com.bettersurvival.item.ItemFacade.FacadeType;
import com.bettersurvival.item.renderer.ItemRendererCableColored;
import com.bettersurvival.item.renderer.ItemRendererCopperCable;
import com.bettersurvival.item.renderer.ItemRendererCrusher;
import com.bettersurvival.item.renderer.ItemRendererFacade;
import com.bettersurvival.item.renderer.ItemRendererOilRefinery;
import com.bettersurvival.item.renderer.ItemRendererTransparentCable;
import com.bettersurvival.item.renderer.ItemRendererUninsulatedCopperCable;
import com.bettersurvival.tileentity.TileEntityAlloyToolbench;
import com.bettersurvival.tileentity.TileEntityCableColored;
import com.bettersurvival.tileentity.TileEntityCopperCable;
import com.bettersurvival.tileentity.TileEntityCrusher;
import com.bettersurvival.tileentity.TileEntityFusionReactor;
import com.bettersurvival.tileentity.TileEntityOilRefinery;
import com.bettersurvival.tileentity.TileEntityPowerSwitch;
import com.bettersurvival.tileentity.TileEntityTransparentCable;
import com.bettersurvival.tileentity.TileEntityUninsulatedCopperCable;
import com.bettersurvival.tileentity.TileEntityWindmill;
import com.bettersurvival.tileentity.TileEntityWindmillFoundation;
import com.bettersurvival.tileentity.renderer.TileEntityRendererAlloyToolbench;
import com.bettersurvival.tileentity.renderer.TileEntityRendererCableColored;
import com.bettersurvival.tileentity.renderer.TileEntityRendererCopperCable;
import com.bettersurvival.tileentity.renderer.TileEntityRendererCrusher;
import com.bettersurvival.tileentity.renderer.TileEntityRendererFusionReactor;
import com.bettersurvival.tileentity.renderer.TileEntityRendererOilRefinery;
import com.bettersurvival.tileentity.renderer.TileEntityRendererPowerSwitch;
import com.bettersurvival.tileentity.renderer.TileEntityRendererTransparentCable;
import com.bettersurvival.tileentity.renderer.TileEntityRendererUninsulatedCopperCable;
import com.bettersurvival.tileentity.renderer.TileEntityRendererWindmill;
import com.bettersurvival.tileentity.renderer.TileEntityRendererWindmillFoundation;
import com.bettersurvival.tribe.entity.EntityAutoTurret;
import com.bettersurvival.tribe.entity.EntityColoredLightningBolt;
import com.bettersurvival.tribe.entity.renderer.AutoTurretRenderer;
import com.bettersurvival.tribe.entity.renderer.ColoredLightningBoltRenderer;
import com.bettersurvival.tribe.gui.GuiTribeCreated;
import com.bettersurvival.tribe.item.renderer.ItemRendererAutoTurret;
import com.bettersurvival.util.ColorList;
import com.bettersurvival.util.KeyBindings;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;

public class ClientProxy extends ServerProxy
{
	public static ClientProxy INSTANCE;
	
	public ClientProxy()
	{
		INSTANCE = this;
	}
	
	@Override
	public void registerRenderers() 
	{
		String[] strings = StatCollector.translateToLocal("info.holdshift").split(":");
		BetterSurvival.MOREINFORMATIONFORMAT = EnumChatFormatting.GOLD + "" + strings[0] + EnumChatFormatting.ITALIC + " " + strings[1] + EnumChatFormatting.RESET + " " + EnumChatFormatting.GOLD + strings[2];
		
		MinecraftForge.EVENT_BUS.register(new BlockHighlightEvent());
		MinecraftForge.EVENT_BUS.register(new GuiTribeCreated());
		MinecraftForge.EVENT_BUS.register(new GuiRadioactivity());
		MinecraftForge.EVENT_BUS.register(new GuiDebug());
		MinecraftForge.EVENT_BUS.register(new ItemTooltipEvent());
		
		FMLCommonHandler.instance().bus().register(new KeyInputHandler());

		KeyBindings.init();
		
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCrusher.class, new TileEntityRendererCrusher());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityOilRefinery.class, new TileEntityRendererOilRefinery());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityWindmill.class, new TileEntityRendererWindmill());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityWindmillFoundation.class, new TileEntityRendererWindmillFoundation());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCableColored.class, new TileEntityRendererCableColored());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPowerSwitch.class, new TileEntityRendererPowerSwitch());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityUninsulatedCopperCable.class, new TileEntityRendererUninsulatedCopperCable());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCopperCable.class, new TileEntityRendererCopperCable());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTransparentCable.class, new TileEntityRendererTransparentCable());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityAlloyToolbench.class, new TileEntityRendererAlloyToolbench());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityFusionReactor.class, new TileEntityRendererFusionReactor());
		
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(BetterSurvival.blockCableWhite), new ItemRendererCableColored(ColorList.WHITE));
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(BetterSurvival.blockCableBlack), new ItemRendererCableColored(ColorList.BLACK));
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(BetterSurvival.blockCableRed), new ItemRendererCableColored(ColorList.RED));
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(BetterSurvival.blockCableGreen), new ItemRendererCableColored(ColorList.GREEN));
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(BetterSurvival.blockCableBrown), new ItemRendererCableColored(ColorList.BROWN));
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(BetterSurvival.blockCableBlue), new ItemRendererCableColored(ColorList.BLUE));
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(BetterSurvival.blockCablePurple), new ItemRendererCableColored(ColorList.PURPLE));
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(BetterSurvival.blockCableCyan), new ItemRendererCableColored(ColorList.CYAN));
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(BetterSurvival.blockCableLightGray), new ItemRendererCableColored(ColorList.LIGHT_GREY));
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(BetterSurvival.blockCableGray), new ItemRendererCableColored(ColorList.GREY));
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(BetterSurvival.blockCablePink), new ItemRendererCableColored(ColorList.PINK));
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(BetterSurvival.blockCableLightGreen), new ItemRendererCableColored(ColorList.LIGHT_GREEN));
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(BetterSurvival.blockCableYellow), new ItemRendererCableColored(ColorList.YELLOW));
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(BetterSurvival.blockCableLightBlue), new ItemRendererCableColored(ColorList.LIGHT_BLUE));
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(BetterSurvival.blockCableMagenta), new ItemRendererCableColored(ColorList.MAGENTA));
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(BetterSurvival.blockCableOrange), new ItemRendererCableColored(ColorList.ORANGE));
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(BetterSurvival.blockCrusher), new ItemRendererCrusher());
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(BetterSurvival.blockOilRefinery), new ItemRendererOilRefinery());
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(BetterSurvival.blockUninsulatedCopperCable), new ItemRendererUninsulatedCopperCable());
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(BetterSurvival.blockCopperCable), new ItemRendererCopperCable());
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(BetterSurvival.blockTransparentCable), new ItemRendererTransparentCable());
		MinecraftForgeClient.registerItemRenderer(BetterSurvival.itemFacadeFull, new ItemRendererFacade().setType(FacadeType.FULL));
		MinecraftForgeClient.registerItemRenderer(BetterSurvival.itemFacadeHollow, new ItemRendererFacade().setType(FacadeType.HOLLOW));
		MinecraftForgeClient.registerItemRenderer(BetterSurvival.itemAutoTurret, new ItemRendererAutoTurret());
		
		RenderingRegistry.registerEntityRenderingHandler(EntityColoredLightningBolt.class, new ColoredLightningBoltRenderer());
		RenderingRegistry.registerEntityRenderingHandler(EntityAutoTurret.class, new AutoTurretRenderer());
	}
	
	@SuppressWarnings("unchecked")
	public void spawnParticle(String particle, World world, double x, double y, double z, float motionX, float motionY, float motionZ)
	{
		try 
		{
			Class<? extends EntityFX> particleClass = (Class<? extends EntityFX>) Class.forName("com.bettersurvival.fx.Entity" + particle + "FX");
			
			EntityFX effect = particleClass.getConstructor(World.class, Double.TYPE, Double.TYPE, Double.TYPE, Float.TYPE, Float.TYPE, Float.TYPE).newInstance(world, x, y, z, motionX, motionY, motionZ);
			
			Minecraft.getMinecraft().effectRenderer.addEffect(effect);
		} 
		catch (ClassNotFoundException e) 
		{
			e.printStackTrace();
		} 
		catch (InstantiationException e) 
		{
			e.printStackTrace();
		} 
		catch (IllegalAccessException e) 
		{
			e.printStackTrace();
		} 
		catch (IllegalArgumentException e) 
		{
			e.printStackTrace();
		} 
		catch (InvocationTargetException e) 
		{
			e.printStackTrace();
		} 
		catch (NoSuchMethodException e) 
		{
			e.printStackTrace();
		} 
		catch (SecurityException e) 
		{
			e.printStackTrace();
		}
	}
	
	@Override
	public World getWorld()
	{
		return Minecraft.getMinecraft().theWorld;
	}
	
	public EntityPlayer getPlayer()
	{
		return Minecraft.getMinecraft().thePlayer;
	}
	
	public Minecraft getMinecraft()
	{
		return Minecraft.getMinecraft();
	}
}
