package com.myra.iriszoommod;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class ZoomMod implements ClientModInitializer {

	private static final float DEFAULT_FOV = 70.0F;
	private static final float ZOOM_FOV = 30.0F;
	private static KeyBinding zoomKey;
	private static boolean isZooming = false;

	@Override
	public void onInitializeClient() {
		// Register the zoom key binding
		zoomKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
				"key.zoommod.zoom", // The translation key of the keybinding's name
				InputUtil.Type.KEYSYM, // The type of the keybinding, KEYSYM for keyboard, MOUSE for mouse.
				GLFW.GLFW_KEY_C, // The keycode of the key
				"category.zoommod" // The translation key of the keybinding's category.
		));

		// Register a client tick event to handle the zoom functionality
		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			if (zoomKey.isPressed()) {
				if (!isZooming) {
					client.options.fov = ZOOM_FOV; // Set FOV to zoom value
					isZooming = true;
				}
			} else {
				if (isZooming) {
					client.options.fov = DEFAULT_FOV; // Reset FOV to default
					isZooming = false;
				}
			}
		});
	}
}