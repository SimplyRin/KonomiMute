package net.simplyrin.konomimute.command;

import java.util.Collection;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.simplyrin.konomimute.Main;

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
public class CommandMute extends Command {

	private Main instance;

	public CommandMute(Main instance) {
		super("mute");
		this.instance = instance;
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		if (!sender.hasPermission("konomicore.command")) {
			sender.sendMessage(ChatColor.RED + "You don't have access to this command!");
			return;
		}

		if (args.length > 0) {
			Collection<ProxiedPlayer> players = this.instance.getProxy().getPlayers();
			for (ProxiedPlayer player : players) {
				if (player.getName().equalsIgnoreCase(args[0])) {
					if (this.instance.getUniqueId().contains(player.getUniqueId())) {
						this.instance.getUniqueId().remove(player.getUniqueId());
						sender.sendMessage("§cPlayer " + player.getName() + " unmuted.");
						return;
					}

					this.instance.getUniqueId().add(player.getUniqueId());
					sender.sendMessage("§cPlayer " + player.getName() + " muted.");
					return;
				}
			}

			Collection<ServerInfo> servers = this.instance.getProxy().getServers().values();
			for (ServerInfo server : servers) {
				if (server.getName().equalsIgnoreCase(args[0])) {
					if (this.instance.getServers().contains(server.getName())) {
						this.instance.getServers().remove(server.getName());
						sender.sendMessage("§cServer " + server.getName() + " unmuted.");
						return;
					}

					this.instance.getServers().add(server.getName());
					sender.sendMessage("§cServer " + server.getName() + " muted.");
					return;
				}
			}
		}

		sender.sendMessage("§cUsage: /mute <player|server>");
		return;
	}

}
