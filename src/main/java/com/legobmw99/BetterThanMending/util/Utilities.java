package com.legobmw99.BetterThanMending.util;

import net.minecraft.entity.player.EntityPlayer;

public class Utilities {
	
	/*
	 * This was mostly taken from NotEnoughWands by romelo333
	 * https://github.com/romelo333/notenoughwands
	 * I edited it to reflect the newer values of xp levels 
	 * Those values were sourced from http://minecraft.gamepedia.com/Experience
	 * 
	 */

	public static int getPlayerXP(EntityPlayer player) {
		return (int) (getExperienceForLevel(player.experienceLevel) + (player.experience * player.xpBarCap()));
	}

	public static void addPlayerXP(EntityPlayer player, int amount) {

		int experience = getPlayerXP(player) + amount;
		if (experience < 0) {
			return;
		}
		player.experienceTotal = experience;
		player.experienceLevel = getLevelForExperience(experience);
		int expForLevel = getExperienceForLevel(player.experienceLevel);
		player.experience = (experience - expForLevel) / (float) player.xpBarCap();
	}

	public static int getLevelForExperience(int experience) {
		int i = 0;
		while (getExperienceForLevel(i) <= experience) {
			i++;
		}
		return i - 1;
	}

	public static int getExperienceForLevel(int level) {
		if (level == 0) {
			return 0;
		}
		if (level > 0 && level < 16) {
			return (int) (Math.pow(level, 2)+ 6 * level);
		} else if (level > 15 && level < 32) {
			return (int) (2.5* Math.pow(level, 2) - 40.5 * level + 360);
		} else {
			return (int) (4.5 * Math.pow(level, 2) - 162.5 * level + 2220);
		}
	}
}
