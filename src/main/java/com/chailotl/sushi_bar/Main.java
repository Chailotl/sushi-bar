package com.chailotl.sushi_bar;

import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main implements ModInitializer
{
	public static final String MOD_ID = "sushi_bar";
	public static final Logger LOGGER = LoggerFactory.getLogger("Sushi Bar");

	@Override
	public void onInitialize()
	{
		FunnyLogger.run();
	}
}