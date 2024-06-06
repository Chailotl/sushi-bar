package com.chailotl.sushi_bar.mixin;

import blue.endless.jankson.Jankson;
import com.chailotl.sushi_bar.owo.config.SushiConfigScreen;
import com.chailotl.sushi_bar.owo.config.SushiModmenu;
import com.moulberry.mixinconstraints.annotations.IfModLoaded;
import io.wispforest.owo.config.ConfigWrapper;
import io.wispforest.owo.config.ui.ConfigScreen;
import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Consumer;

@IfModLoaded(value = "owo-lib")
@Mixin(ConfigWrapper.class)
public class InjectConfigWrapper
{
	@Inject(
		method = "<init>(Ljava/lang/Class;Ljava/util/function/Consumer;)V",
		at = @At(
			value = "INVOKE",
			target = "Lnet/fabricmc/loader/api/FabricLoader;getEnvironmentType()Lnet/fabricmc/api/EnvType;"
		)
	)
	private <C> void injectSushiModmenu(Class<C> clazz, Consumer<Jankson.Builder> janksonBuilder, CallbackInfo info)
	{
		if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT && clazz.isAnnotationPresent(SushiModmenu.class))
		{
			SushiModmenu annotation = clazz.getAnnotation(SushiModmenu.class);
			ConfigScreen.registerProvider(
				annotation.modId(),
				screen -> SushiConfigScreen.createWithCustomModel(new Identifier(annotation.uiModelId()), (ConfigWrapper<C>)(Object)this, screen)
			);
		}
	}
}