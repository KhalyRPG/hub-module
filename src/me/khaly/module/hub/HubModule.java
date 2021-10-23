package me.khaly.module.hub;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;

import me.khaly.core.module.Module;

public class HubModule extends Module {

	private List<Listener> listeners;
	
	public HubModule() {
		super("Hub", "hub", 0.1F);
		this.setAuthor("Khaly");
		this.setPersistent(true);
		
		this.listeners = new ArrayList<>();
	}

	@Override
	public void load() {
		
	}

	@Override
	public void unload() {
		for(int i = 0; i < listeners.size(); i++) {
			Listener listener = listeners.remove(i);
			HandlerList.unregisterAll(listener);
		}
	}
	
	private void registerListeners(Listener...listeners) {
		PluginManager manager = Bukkit.getPluginManager();
		for(Listener listener : listeners) {
			manager.registerEvents(listener, getProvider());
			HubModule.this.listeners.add(listener);
		}
	}
}
