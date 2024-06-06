package com.chailotl.testmod;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main implements ModInitializer
{
	public static final String MOD_ID = "sushi_bar_testmod";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static final com.chailotl.testmod.TestConfig CONFIG = com.chailotl.testmod.TestConfig.createAndLoad();

	@Override
	public void onInitialize()
	{
		LOGGER.info("Hello world!");
	}
}
