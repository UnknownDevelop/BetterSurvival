package com.bettersurvival.util;

import java.util.UUID;

public class UUIDUtil
{
	public static boolean isUUIDEqual(UUID idOne, UUID idTwo)
	{
		return idOne.toString().equals(idTwo.toString());
	}
}
