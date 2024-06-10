package com.chailotl.testmod;

import com.chailotl.sushi_bar.owo.config.SushiModmenu;
import io.wispforest.owo.config.annotation.Config;
import io.wispforest.owo.config.annotation.Nest;
import net.minecraft.util.Identifier;

import java.util.Arrays;
import java.util.List;

@SushiModmenu(modId = Main.MOD_ID)
@Config(name = Main.MOD_ID, wrapperName = "TestConfig")
public class ConfigModel
{
	public String string = "hello world";

	public List<Identifier> ids = Arrays.asList(
		new Identifier("minecraft:lush_caves"),
		new Identifier("minecraft:dripstone_caves"),
		new Identifier("minecraft:deep_dark")
	);

	@Nest
	public Struct struct = new Struct();

	public static class Struct
	{
		public List<Identifier> ids = Arrays.asList(
			new Identifier("minecraft:lush_caves"),
			new Identifier("minecraft:dripstone_caves"),
			new Identifier("minecraft:deep_dark")
		);
	}
}
