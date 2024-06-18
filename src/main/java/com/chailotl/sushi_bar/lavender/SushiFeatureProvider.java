package com.chailotl.sushi_bar.lavender;

import io.wispforest.lavender.client.LavenderBookScreen;
import io.wispforest.lavender.md.compiler.BookCompiler;
import io.wispforest.lavendermd.MarkdownFeature;

import java.util.List;

public class SushiFeatureProvider implements LavenderBookScreen.FeatureProvider
{
	@Override
	public List<MarkdownFeature> createFeatures(BookCompiler.ComponentSource componentSource)
	{
		return List.of(
			new Recipe2x2Feature(componentSource, null),
			new Recipe1x1Feature(componentSource, null)
		);
	}
}