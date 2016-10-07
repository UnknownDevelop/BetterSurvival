package com.bettersurvival.structure;

import java.util.ArrayList;

public class StructureStorageRadtown
{
	private static ArrayList<StructureFile> files = new ArrayList<StructureFile>();
	
	public static void addStructure(StructureFile file)
	{
		files.add(file);
	}
	
	public static StructureFile[] getStructures()
	{
		return files.toArray(new StructureFile[files.size()]);
	}
}
