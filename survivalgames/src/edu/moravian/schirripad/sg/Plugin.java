package edu.moravian.schirripad.sg;

import java.util.Hashtable;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.command.SendCommandEvent;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import com.google.inject.Inject;

import edu.moravian.edu.sg.mapping.Coordinate3D;
import edu.moravian.schirripad.sg.Arena.ArenaType;

@org.spongepowered.api.plugin.Plugin(id = "survivalgames", name = "Survival Games")

public class Plugin {
	@Inject
	private Logger log;

	private Hashtable<Player, Coordinate3D[]> positions = new Hashtable<Player, Coordinate3D[]>();

	@Listener
	public void serverStart(GameStartedServerEvent e) {
		// Begin tick thread to check player locations against a database of coordinates
		Timer checker = new Timer();
		TimerTask check = new TimerTask() {

			@Override
			public void run() {
				check();
			}

		};
		checker.schedule(check, 500);
	}

	@Listener
	public void command(SendCommandEvent e) {
		switch (e.getCommand()) {
		case "set":
			runSetPos(e);
			break;
		case "create":
			runCreateArena(e);
			break;
		}
	}

	private void runSetPos(SendCommandEvent e) {
		// Check is source is player
		if (e.getSource() instanceof Player) {
			Player p = (Player) e.getSource();
			// Check if player already has a coordinate array assigned to their name
			Coordinate3D[] d = positions.get(p);
			if (d == null) {
				// If not, create one
				positions.put(p, new Coordinate3D[2]);
				d = positions.get(p);
			}
			// If arg is 1, set first position
			if (e.getArguments().equals("1")) {
				Location<World> loc = p.getLocation();
				positions.get(p)[0] = new Coordinate3D(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
			}
			// If arg is 2, set second position
			if (e.getArguments().equals("2")) {
				Location<World> loc = p.getLocation();
				positions.get(p)[0] = new Coordinate3D(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
			} else {
				p.sendMessage(Text.of("Invalid Syntax!"));
			}
		}
	}

	private void runCreateArena(SendCommandEvent e) {
		// Check if source is player
		if (e.getSource() instanceof Player) {
			Player p = (Player) e.getSource();
			// Check if player has coordinate array, if not, cancel create request
			Coordinate3D[] d = positions.get(p);
			if (d == null) {
				p.sendMessage(Text.of("No positions set!"));
				return;
			}
			p.sendMessage(Text.of("Creating arena with bounds at " + d[0].getX() + "," + d[0].getY() + "," + d[0].getZ()
					+ ":" + d[1].getX() + "," + d[1].getY() + "," + d[1].getZ()));
			log.info("Creating arena with bounds at " + d[0].getX() + "," + d[0].getY() + "," + d[0].getZ() + ":"
					+ d[1].getX() + "," + d[1].getY() + "," + d[1].getZ());

			// Register the arena with the specified name, and bounds
			ArenaManager.addArena(d, e.getArguments(), ArenaType.TYPE_SG);
			p.sendMessage(Text.of("Created arena \"" + e.getArguments() + "\""));
		}
	}

	private void runListArena(SendCommandEvent e) {
		if (e.getSource() instanceof Player) {
			Player p = (Player) e.getSource();
			final Set<String> list = ArenaManager.listArenaNames();
			p.sendMessage(Text.of("Arenas Currently Registered:"));
			for (String name : list)
				p.sendMessage(Text.of(name));
			p.sendMessage(Text.of("Listed " + list.size() + " arenas"));
		}
	}

	public void check() {
		// Get all players in world, check locations against borders
		// TODO figure out how to get world general access, in order to load in series
		// of all players
	}
}
