package com.chailotl.sushi_bar.mixin.lavender;

import com.chailotl.sushi_bar.lavender.SushiBook;
import com.moulberry.mixinconstraints.annotations.IfModLoaded;
import io.wispforest.lavender.book.Book;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@IfModLoaded(value = "lavender")
@Mixin(Book.class)
public class InjectBook implements SushiBook
{
	@Unique
	private boolean enableSushiFeatures = false;

	@Override
	public void enableSushiFeatures()
	{
		enableSushiFeatures = true;
	}

	@Override
	public boolean getSushiFeatures()
	{
		return enableSushiFeatures;
	}
}