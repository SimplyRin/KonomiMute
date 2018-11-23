package net.simplyrin.konomimute;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import lombok.Getter;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.event.EventHandler;
import net.simplyrin.konomimute.command.CommandMute;

/**
 * Created by SimplyRin on 2018/11/23.
 *
 * Copyright (c) 2018 SimplyRin
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
public class Main extends Plugin implements Listener {

	@Getter
	private List<UUID> uniqueId;
	@Getter
	private List<String> servers;

	@Override
	public void onEnable() {
		this.uniqueId = new ArrayList<>();
		this.servers = new ArrayList<>();

		this.getProxy().getPluginManager().registerCommand(this, new CommandMute(this));
		this.getProxy().getPluginManager().registerListener(this, this);
	}

	@EventHandler
	public void onChat(ChatEvent event) {
		if (event.isCommand()) {
			return;
		}

		ProxiedPlayer player = (ProxiedPlayer) event.getSender();

		if (player.hasPermission("konomimute.bypass")) {
			return;
		}

		if (this.uniqueId.contains(player.getUniqueId())) {
			event.setCancelled(true);
			player.sendMessage("§cYou have muted / あなたはミュートされています");
			return;
		}

		if (this.servers.contains(player.getServer().getInfo().getName())) {
			event.setCancelled(true);
			player.sendMessage("§cChat on this server is muted / このサーバーでのチャットはミュートされています");
			return;
		}

	}

}
