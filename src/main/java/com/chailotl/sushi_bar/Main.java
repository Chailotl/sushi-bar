package com.chailotl.sushi_bar;

import net.fabricmc.api.ModInitializer;

import net.minecraft.MinecraftVersion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main implements ModInitializer
{
	public static final String MOD_ID = "sushi_bar";
	public static final Logger LOGGER = LoggerFactory.getLogger("Sushi Bar");
	public static boolean preVersion;

	@Override
	public void onInitialize()
	{
		FunnyLogger.run();

		String version = MinecraftVersion.CURRENT.getName();
		preVersion = version.equals("1.20") || version.equals("1.20.1");
	}
}