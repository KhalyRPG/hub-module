package me.khaly.module.hub.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import me.clip.placeholderapi.PlaceholderAPI;
import me.khaly.core.KhalyCore;
import me.khaly.core.user.User;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;

public class AsyncPlayerChatListener implements Listener {
	
	private KhalyCore core;
	
	public AsyncPlayerChatListener(KhalyCore core) {
		this.core = core;
	}
	
	@EventHandler
	public void onChat(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();
		User user = core.getUser(player);
		if(!user.hasProfile()) {
			event.setCancelled(true);
			return;
		}
		String placeholded = PlaceholderAPI.setPlaceholders(player, "%vault_prefix% ");
		ComponentBuilder userInfo = new ComponentBuilder("§8§m               §f 「 INFO 」 §8§m                 \n");
		boolean hasClass = !user.getProfile().getRPGClass().getID().equals("human");
		userInfo.append("§6Clase: "+(hasClass ? user.getProfile().getRPGClass().getPrefix() : "§7Ninguna")+"\n");
		if(!placeholded.isEmpty()) {
			userInfo.append("§aRango: "+placeholded+"\n");
		}
		userInfo.append("§2Gremio: §7Ninguno\n");
		//userInfo.append("§bRanking: "+user.getRanking().getColor() + "" + user.getRanking().getKey());
		ComponentBuilder message = new ComponentBuilder("");
		message.append((placeholded.isEmpty() ? "§7" : placeholded)+player.getName()).event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, userInfo.create()));
		
		ComponentBuilder ignorePlayerComponent = new ComponentBuilder("§eClic aquí para ignorar a "+event.getPlayer().getName());
		String chatColor = player.hasPermission("khaly.chatcolor") ? "§f" : "§7";
		message//.event(new HoverEvent(Action.SHOW_TEXT, new ComponentBuilder("§eClic para ver las opciones sociales de "+player.getName()+".").create()))
		       .append(" §8» ")
		       .event(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, ""))
		       .append(chatColor+event.getMessage())
			   .event(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/ignore add "+event.getPlayer().getName()))
			   .event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, ignorePlayerComponent.create()));
		
		event.setCancelled(true);
		for(Player target : event.getRecipients())
			target.spigot().sendMessage(message.create());
		Bukkit.getConsoleSender().sendMessage("§7"+player.getName() + "§f: §7"+event.getMessage());
	}
	
}
