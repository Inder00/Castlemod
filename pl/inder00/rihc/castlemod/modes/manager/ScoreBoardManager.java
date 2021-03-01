package pl.inder00.rihc.castlemod.modes.manager;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import pl.inder00.rihc.castlemod.CastleMod;
import pl.inder00.rihc.castlemod.game.Human;
import pl.inder00.rihc.castlemod.modes.Arena;

public class ScoreBoardManager {
	
	public static void checkPlayers(Arena a){
				ScoreboardManager manager = Bukkit.getScoreboardManager();
				Scoreboard board = manager.getNewScoreboard();
				Objective objective = board.registerNewObjective("dummy", "CastleMod");
				objective.setDisplaySlot(DisplaySlot.SIDEBAR);
				objective.setDisplayName(ChatColor.YELLOW+"CastleMod #"+a.getName());
				for(Player p : a.getUsers()){
					p.getScoreboard().resetScores(p);
					Human e = Human.get(p.getName());
					Score pkt = objective.getScore(ChatColor.GREEN + "Ranking:");
					pkt.setScore(e.getPkt());
					Score kills = objective.getScore(ChatColor.GREEN + "Zabojstwa:");
					kills.setScore(e.getKills());
					Score deaths = objective.getScore(ChatColor.GREEN + "Smierci:");
					deaths.setScore(e.getDeaths());
					Score monety = objective.getScore(ChatColor.GREEN + "Monety:");
					monety.setScore(e.getCoins());
					p.setScoreboard(board);
				}

	}

}
