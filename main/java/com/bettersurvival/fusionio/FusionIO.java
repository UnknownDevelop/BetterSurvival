package com.bettersurvival.fusionio;

import io.netty.buffer.ByteBuf;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.lwjgl.opengl.GL11;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.bettersurvival.BetterSurvival;
import com.bettersurvival.network.PacketSyncFusionIO;
import com.bettersurvival.network.PacketSyncFusionIOComponent;
import com.bettersurvival.proxy.ClientProxy;
import com.bettersurvival.util.MathUtility;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.relauncher.Side;

public class FusionIO
{
	private int x, y, z;
	private HashMap<FusionIOComponent, Integer> components = new HashMap<FusionIOComponent, Integer>();
	private int page;
	
	private IFusionIOInputHandler handler;
	
	public FusionIO(int x, int y, int z)
	{
		this(x, y, z, 0);
	}
	
	public FusionIO(int x, int y, int z, int page)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.page = page;
	}
	
	public void addComponent(FusionIOComponent component, int page)
	{
		components.put(component, page);
	}
	
	public void addComponent(FusionIOComponent component)
	{
		addComponent(component, -1);
	}
	
	public void setPage(int page)
	{
		this.page = page;
		sync();
	}
	
	public void render()
	{
		for(Entry<FusionIOComponent, Integer> entry : components.entrySet())
		{
			if(entry.getValue() == page || entry.getValue() == -1)
			{
				if(entry.getKey() != null && entry.getKey().active && entry.getKey().visible)
				{
					entry.getKey().render();
				}
			}
		}
	}
	
	public boolean processClick(float hitX, float hitY, float hitZ, int orientation, Side side)
	{
		float pixel = 1f/256f;
		
		float cHitX = orientation == 2 ? 1f-hitX : orientation == 3 ? hitX : orientation == 4 ? hitZ : 1f-hitZ;
		float cHitY = 1f-hitY;
		
		if(cHitX > pixel*28f && cHitX < pixel*(256f-28f))
		{
			if(cHitY > pixel*28f && cHitY < pixel*(256f-28f))
			{
				cHitX -= pixel*26f;
				cHitY -= pixel*26f;
				
				int transHitX = (int)MathUtility.lerp(0, 170f, cHitX);
				int transHitY = (int)MathUtility.lerp(0, 170f, cHitY);
				
				for(Entry<FusionIOComponent, Integer> entry : components.entrySet())
				{
					if(entry.getValue() == page || entry.getValue() == -1)
					{
						if(entry.getKey() != null)
						{
							if(entry.getKey().active)
							{
								boolean clicked = entry.getKey().mouseClicked(transHitX, transHitY, side);
								
								if(clicked && side == side.SERVER)
								{
									actionPerformed(entry.getKey().id, entry.getKey());
									sync();
								}
								
								if(clicked)
								{
									return clicked;
								}
							}
						}
						else
						{
							BetterSurvival.logger.error("FusionIO at x:" + x + " y:" + y + " z:" + z + " has a null component!");
						}
					}
				}
			}
		}
		
		return false;
	}
	
	public void actionPerformed(int id, FusionIOComponent component)
	{
		if(handler == null)
		{
			BetterSurvival.logger.warn("FusionIO at x:" + x + " y:" + y + " z:" + z + " has no handler attached.");
			return;
		}
	
		handler.actionPerformed(id, component);
	}
	
	public void sync()
	{
		BetterSurvival.network.sendToAll(new PacketSyncFusionIO(x, y, z, this));
	}
	
	public void syncComponent(int id)
	{
		syncComponent(getComponent(id));
	}
	
	public void syncComponent(FusionIOComponent component)
	{
		BetterSurvival.network.sendToAll(new PacketSyncFusionIOComponent(x, y, z, component, page));
	}
	
	public void syncComponent(int id, int page)
	{
		syncComponent(getComponent(id), page);
	}
	
	public void syncComponent(FusionIOComponent component, int page)
	{
		BetterSurvival.network.sendToAll(new PacketSyncFusionIOComponent(x, y, z, component, page));
	}
	
	
	public static FusionIO fromBuf(int x, int y, int z, ByteBuf buf)
	{
		int page = buf.readInt();
		FusionIO fusionIO = new FusionIO(x, y, z, page);
		int componentCount = buf.readInt();
		
		int compX = 0;
		int compY = 0;
		int compID = 0;
		boolean compActive = false;
		boolean compVisible = false;
		int compGroup = 0;
		
		for(int i = 0; i < componentCount; i++)
		{
			compX = buf.readInt();
			compY = buf.readInt();
			compID = buf.readInt();
			compActive = buf.readBoolean();
			compVisible = buf.readBoolean();
			compGroup = buf.readInt();
			FusionIOComponent comp = FusionIOComponent.fromBufString(ByteBufUtils.readUTF8String(buf));
			comp.x = compX;
			comp.y = compY;
			comp.id = compID;
			comp.active = compActive;
			comp.visible = compVisible;
			comp.group = compGroup;
			fusionIO.addComponent(comp, page);
		}
		
		return fusionIO;
	}
	
	public void toBuf(ByteBuf buf)
	{
		buf.writeInt(page);
		
		int componentSize = 0;
		
		for(Entry<FusionIOComponent, Integer> entry : components.entrySet())
		{
			if(entry.getValue() == page || entry.getValue() == -1)
			{
				componentSize++;
			}
		}

		buf.writeInt(componentSize);
		
		for(Entry<FusionIOComponent, Integer> entry : components.entrySet())
		{
			if(entry.getValue() == page || entry.getValue() == -1)
			{
				buf.writeInt(entry.getKey().x);
				buf.writeInt(entry.getKey().y);
				buf.writeInt(entry.getKey().id);
				buf.writeBoolean(entry.getKey().active);
				buf.writeBoolean(entry.getKey().visible);
				buf.writeInt(entry.getKey().group);
				ByteBufUtils.writeUTF8String(buf, entry.getKey().toBufString());
			}
		}
	}
	
	public static FusionIO fromXML(int x, int y, int z, File file)
	{
		FusionIO fusionIO = new FusionIO(x, y, z);
		
		try
		{
			Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(file);
			doc.getDocumentElement().normalize();
			
			NodeList pages = doc.getElementsByTagName("Page");
			
			for(int i = 0; i < pages.getLength(); i++)
			{
				Element element = (Element)pages.item(i);
				int currentPage = Integer.parseInt(element.getAttribute("page"));
				
				NodeList components = element.getElementsByTagName("Component");
				
				for(int j = 0; j < components.getLength(); j++)
				{
					FusionIOComponent comp = FusionIOComponent.xmlToComponent((Element)components.item(j));
					
					if(comp != null)
					{
						fusionIO.addComponent(comp, currentPage);
					}
				}
			}
		} 
		catch (SAXException e)
		{
			e.printStackTrace();
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		} 
		catch (ParserConfigurationException e)
		{
			e.printStackTrace();
		}
		
		return fusionIO;
	}
	
	public void setInputHandler(IFusionIOInputHandler handler)
	{
		this.handler = handler;
	}
	
	public FusionIOComponent getComponent(int id)
	{
		for(Entry<FusionIOComponent, Integer> entry : components.entrySet())
		{
			if(entry != null)
			{
				if(entry.getKey().id == id)
				{
					return entry.getKey();
				}
			}
		}
		
		return null;
	}
	
	public void setComponent(FusionIOComponent component, int page)
	{
		for(Entry<FusionIOComponent, Integer> entry : components.entrySet())
		{
			if(entry != null)
			{
				if(entry.getKey().id == component.id)
				{
					components.remove(entry.getKey());
					break;
				}
			}
		}
		
		components.put(component, page);
	}
	
	public FusionIOComponent[] getGroup(int id)
	{
		ArrayList<FusionIOComponent> group = new ArrayList<FusionIOComponent>();
		
		for(Entry<FusionIOComponent, Integer> entry : components.entrySet())
		{
			if(entry != null)
			{
				if(entry.getKey().group == id)
				{
					group.add(entry.getKey());
				}
			}
		}
		
		return group.toArray(new FusionIOComponent[group.size()]);
	}
	
	public void activeGroup(boolean active, int group)
	{
		for(Entry<FusionIOComponent, Integer> entry : components.entrySet())
		{
			if(entry != null)
			{
				if(entry.getKey().group == group)
				{
					entry.getKey().active = active;
				}
			}
		}
		
		sync();
	}
	
	public void visibleGroup(boolean visible, int group)
	{
		for(Entry<FusionIOComponent, Integer> entry : components.entrySet())
		{
			if(entry != null)
			{
				if(entry.getKey().group == group)
				{
					entry.getKey().visible = visible;
				}
			}
		}

		sync();
	}
}
