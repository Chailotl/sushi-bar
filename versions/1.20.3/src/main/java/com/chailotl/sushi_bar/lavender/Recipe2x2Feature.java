package com.chailotl.sushi_bar.lavender;

import io.wispforest.lavender.md.compiler.BookCompiler;
import io.wispforest.lavender.md.features.RecipeFeature;
import io.wispforest.lavendermd.Lexer;
import io.wispforest.lavendermd.MarkdownFeature;
import io.wispforest.lavendermd.Parser;
import io.wispforest.lavendermd.compiler.MarkdownCompiler;
import io.wispforest.lavendermd.compiler.OwoUICompiler;
import io.wispforest.owo.ui.component.Components;
import io.wispforest.owo.ui.component.ItemComponent;
import io.wispforest.owo.ui.container.Containers;
import io.wispforest.owo.ui.core.*;
import io.wispforest.owo.ui.parsing.UIModelLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.recipe.CraftingRecipe;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.Registries;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class Recipe2x2Feature implements MarkdownFeature
{
	private final BookCompiler.ComponentSource bookComponentSource;
	private final Map<RecipeType<?>, RecipeFeature.RecipePreviewBuilder<?>> previewBuilders;

	public static final RecipeFeature.RecipePreviewBuilder<CraftingRecipe> CRAFTING_2X2_PREVIEW_BUILDER = new RecipeFeature.RecipePreviewBuilder<>()
	{
		@Override
		public @NotNull Component buildRecipePreview(BookCompiler.ComponentSource componentSource, RecipeEntry<CraftingRecipe> recipeEntry)
		{
			var recipeComponent = componentSource.template(UIModelLoader.get(new Identifier("sushi_bar:book_components")), ParentComponent.class, "crafting-2x2-recipe");

			populateIngredientsGrid(recipeEntry, recipeEntry.value().getIngredients(), recipeComponent.childById(ParentComponent.class, "input-grid"), 2, 2);
			recipeComponent.childById(ItemComponent.class, "output").stack(recipeEntry.value().getResult(MinecraftClient.getInstance().world.getRegistryManager()));

			return recipeComponent;
		}
	};

	public Recipe2x2Feature(BookCompiler.ComponentSource bookComponentSource, @Nullable Map<RecipeType<?>, RecipeFeature.RecipePreviewBuilder<?>> previewBuilders)
	{
		this.bookComponentSource = bookComponentSource;
		this.previewBuilders = new HashMap<>(previewBuilders != null ? previewBuilders : Map.of());
		this.previewBuilders.putIfAbsent(RecipeType.CRAFTING, CRAFTING_2X2_PREVIEW_BUILDER);
	}

	@Override
	public String name()
	{
		return "2x2_recipes";
	}

	@Override
	public boolean supportsCompiler(MarkdownCompiler<?> compiler)
	{
		return compiler instanceof OwoUICompiler;
	}

	@Override
	public void registerTokens(TokenRegistrar registrar)
	{
		registrar.registerToken((nibbler, tokens) -> {
			if (!nibbler.tryConsume("<recipe_2x2;")) { return false; }

			var recipeIdString = nibbler.consumeUntil('>');
			if (recipeIdString == null) { return false; }

			var recipeId = Identifier.tryParse(recipeIdString);
			if (recipeId == null) { return false; }

			var recipe = MinecraftClient.getInstance().world.getRecipeManager().get(recipeId);
			if (recipe.isEmpty()) { return false; }

			tokens.add(new RecipeToken(recipeIdString, (RecipeEntry<Recipe<?>>) recipe.get()));
			return true;
		}, '<');
	}

	@Override
	public void registerNodes(NodeRegistrar registrar)
	{
		registrar.registerNode(
			(parser, recipeToken, tokens) -> new RecipeNode(recipeToken.recipe),
			(token, tokens) -> token instanceof RecipeToken recipe ? recipe : null
		);
	}

	private static class RecipeToken extends Lexer.Token
	{
		public final RecipeEntry<Recipe<?>> recipe;

		public RecipeToken(String content, RecipeEntry<Recipe<?>> recipe)
		{
			super(content);
			this.recipe = recipe;
		}
	}

	private class RecipeNode extends Parser.Node
	{
		public final RecipeEntry<Recipe<?>> recipe;

		public RecipeNode(RecipeEntry<Recipe<?>> recipe)
		{
			this.recipe = recipe;
		}

		@Override
		@SuppressWarnings({"rawtypes", "unchecked"})
		protected void visitStart(MarkdownCompiler<?> compiler)
		{
			var previewBuilder = (RecipeFeature.RecipePreviewBuilder) previewBuilders.get(recipe.value().getType());

			if (previewBuilder != null)
			{
				((OwoUICompiler) compiler).visitComponent(previewBuilder.buildRecipePreview(bookComponentSource, recipe));
			}
			else
			{
				((OwoUICompiler) compiler).visitComponent(
					Containers.verticalFlow(Sizing.fill(100), Sizing.content())
						.child(Components.label(Text.literal("No preview builder registered for recipe type '" + Registries.RECIPE_TYPE.getId(recipe.value().getType()) + "'"))
						.horizontalSizing(Sizing.fill(100)))
						.padding(Insets.of(10))
						.surface(Surface.flat(0x77A00000).and(Surface.outline(0x77FF0000)))
				);
			}
		}

		@Override
		protected void visitEnd(MarkdownCompiler<?> compiler) {}
	}
}