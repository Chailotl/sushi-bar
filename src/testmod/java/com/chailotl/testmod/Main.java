package com.chailotl.testmod;

import com.chailotl.sushi_bar.RegistrationHelper;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main implements ModInitializer
{
	public static final String MOD_ID = "testmod";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static final com.chailotl.testmod.TestConfig CONFIG = com.chailotl.testmod.TestConfig.createAndLoad();
	public static final RegistrationHelper register = new RegistrationHelper(MOD_ID);

	// Register items
	public static final Item ITEM_A = register.item("item_a");
	public static final Item ITEM_B = register.item("item_b", MilkBucketItem::new);
	public static final Item ITEM_C = register.item("item_c", new FabricItemSettings().maxCount(1));
	public static final Item ITEM_D = register.item("item_d", new EnderPearlItem(new FabricItemSettings().maxCount(16)));

	@Override
	public void onInitialize()
	{
		LOGGER.info("Hello world!");
	}
}
