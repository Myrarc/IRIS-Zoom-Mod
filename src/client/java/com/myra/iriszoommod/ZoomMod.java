package com.myra.iriszoommod;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class ZoomMod implements ClientModInitializer {

	private static final float ZOOM_FOV = 30.0F;
	private static KeyBinding zoomKey;
	private static boolean isZooming = false;
	private static int originalFov;

	@Override
	public void onInitializeClient() {
		zoomKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
				"key.zoommod.zoom",
				InputUtil.Type.KEYSYM,
				GLFW.GLFW_KEY_C,
				"category.zoommod"
		));

		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			if (zoomKey.isPressed()) {
				if (!isZooming) {
					originalFov = client.options.getFov().getValue();
					client.options.getFov().setValue((int) ZOOM_FOV);
					isZooming = true;
				}
			} else {
				if (isZooming) {
					client.options.getFov().setValue(originalFov);
					isZooming = false;
				}
			}
		});
	}
}