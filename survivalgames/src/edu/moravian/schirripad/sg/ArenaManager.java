package edu.moravian.schirripad.sg;

import java.util.Hashtable;
import java.util.Set;

import edu.moravian.edu.sg.mapping.Coordinate3D;
import edu.moravian.schirripad.sg.Arena.ArenaType;

public class ArenaManager {
	private static Hashtable<String, Arena> arenas = new Hashtable<String, Arena>();

	public static void addArena(Coordinate3D[] d, String name, ArenaType type) {
		arenas.put(name, new Arena(name, d, type));
	}

	public static Arena removeArena(String name) {
		Arena a = arenas.get(name);
		arenas.remove(name);
		return a;
	}

	public static Arena getArena(String name) {
		return arenas.get(name);
	}

	public static Set<String> listArenaNames() {
		return arenas.keySet();
	}

}
