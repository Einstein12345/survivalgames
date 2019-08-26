package edu.moravian.schirripad.sg;

import java.util.HashSet;

import edu.moravian.edu.sg.mapping.Coordinate3D;

public class Arena {
	private String name;
	private int x1, y1, z1, x2, y2, z2;
	HashSet<String> flags = new HashSet<String>();

	public Arena(String name, Coordinate3D[] d, ArenaType type) {
		this.name = name;
		x1 = d[0].getX();
		y1 = d[0].getY();
		z1 = d[0].getZ();
		x2 = d[0].getX();
		y2 = d[0].getY();
		z2 = d[0].getZ();
	}

	public Arena(String name, Coordinate3D[] d, ArenaType type, String... flags) {
		this.name = name;
		x1 = d[0].getX();
		y1 = d[0].getY();
		z1 = d[0].getZ();
		x2 = d[0].getX();
		y2 = d[0].getY();
		z2 = d[0].getZ();
		for (String s : flags)
			this.flags.add(s);
	}

	public void addFlags(String... flags) {
		for (String s : flags)
			this.flags.add(s);
	}

	public void removeFlags(String... flags) {
		for (String s : flags) {
			if (this.flags.contains(s))
				this.flags.remove(s);
		}
	}

	public static enum ArenaType {
		TYPE_MOB, TYPE_CTF, TYPE_SG, TYPE_SPLEEF;
	}

}
