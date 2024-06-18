package com.chailotl.sushi_bar.mixin.lavender;

import com.chailotl.sushi_bar.lavender.SushiBook;
import com.google.gson.JsonObject;
import com.llamalad7.mixinextras.sugar.Local;
import com.moulberry.mixinconstraints.annotations.IfModLoaded;
import io.wispforest.lavender.book.Book;
import io.wispforest.lavender.book.BookLoader;
import net.minecraft.resource.Resource;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@IfModLoaded(value = "lavender")
@Mixin(BookLoader.class)
public class InjectBookLoader
{
	@Inject(
		method = "lambda$reload$1(Lnet/minecraft/util/Identifier;Lnet/minecraft/resource/Resource;)V",
		at = @At(
			value = "TAIL"
		)
	)
	private static void readSushiFeatures(Identifier identifier, Resource resource, CallbackInfo ci, @Local(ordinal = 0) JsonObject bookObject, @Local(ordinal = 0) Book book)
	{
		if (JsonHelper.getBoolean(bookObject, "sushi_features", false))
		{
			((SushiBook)(Object) book).enableSushiFeatures();
		}
	}
}