package pl.inder00.rihc.castlemod.game.files.lodader;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;

import pl.inder00.rihc.castlemod.game.files.FileManager;
import pl.inder00.rihc.castlemod.modes.Arena;


public class ArenaLoader {
	
	public static void loadArenas(){
		for(File f : FileManager.getArenasFolder().listFiles()){
			if(!f.getName().toLowerCase().equalsIgnoreCase("null")){
				YamlConfiguration yml = YamlConfiguration.loadConfiguration(f);
				String arena = yml.getString("arena.name");
				String creator = yml.getString("arena.creator");
				String world = yml.getString("arena.world");
				int x1 = yml.getInt("arena.castle.x1");
				int z1 = yml.getInt("arena.castle.z1");
				int x2 = yml.getInt("arena.castle.x2");
				int z2 = yml.getInt("arena.castle.z2");
				
				int spawn_1x = yml.getInt("arena.castle.spawn.x1");
				int spawn_1z = yml.getInt("arena.castle.spawn.z1");
				int spawn_1y = yml.getInt("arena.castle.spawn.y1");
				String spawn_1world = yml.getString("arena.castle.spawn.world1");
				
				int spawn_2x = yml.getInt("arena.castle.spawn.x2");
				int spawn_2z = yml.getInt("arena.castle.spawn.z2");
				int spawn_2y = yml.getInt("arena.castle.spawn.y2");
				String spawn_2world = yml.getString("arena.castle.spawn.world2");
				
				int map_2x = yml.getInt("arena.map.x2");
				int map_2z = yml.getInt("arena.map.z2");
				int map_2y = yml.getInt("arena.map.y2");
				String map_2world = yml.getString("arena.map.world2");
				
				int map_1x = yml.getInt("arena.map.x1");
				int map_1z = yml.getInt("arena.map.z1");
				int map_1y = yml.getInt("arena.map.y1");
				String map_1world = yml.getString("arena.map.world1");
				
				int attack_1x = yml.getInt("arena.attack.x1");
				int attack_1z = yml.getInt("arena.attack.z1");
				int attack_1y = yml.getInt("arena.attack.y1");
				
				int attack_2x = yml.getInt("arena.attack.x2");
				int attack_2z = yml.getInt("arena.attack.z2");
				int attack_2y = yml.getInt("arena.attack.y2");
				
				if(spawn_1world != null && spawn_2world != null && spawn_1world.equalsIgnoreCase(spawn_2world)){
					new Arena(arena, creator, world, x1, z1, x2, z2, spawn_1x, spawn_1z, spawn_1y, spawn_1world, spawn_2x, spawn_2z, spawn_2y, spawn_2world, map_1x, map_1y, map_1z, map_1world, map_2x, map_2y, map_2z, map_2world,
							attack_1x, attack_1y, attack_1z, attack_2x, attack_2y, attack_2z);
				}
			}

		}
	}
	
	public static void saveArenas(){
		for(Arena a : Arena.arenas){
				File f = new File(FileManager.getArenasFolder(), a.getName() + ".yml");
				if(!f.exists()){
					try {
						f.createNewFile();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				YamlConfiguration yml = YamlConfiguration.loadConfiguration(f);
				yml.set("arena.name", a.getName());
				yml.set("arena.creator", a.getCreator());
				yml.set("arena.world", a.getWorld());
				yml.set("arena.castle.x1", a.getX1());
				yml.set("arena.castle.z1", a.getZ1());
				yml.set("arena.castle.x2", a.getX2());
				yml.set("arena.castle.z2", a.getZ2());
				yml.set("arena.castle.spawn.x1", a.getSpawn_1_x());
				yml.set("arena.castle.spawn.y1", a.getSpawn_1_y());
				yml.set("arena.castle.spawn.z1", a.getSpawn_1_z());
				yml.set("arena.castle.spawn.world1", a.getSpawn_1_world());
				yml.set("arena.castle.spawn.x2", a.getSpawn_2_x());
				yml.set("arena.castle.spawn.y2", a.getSpawn_2_y());
				yml.set("arena.castle.spawn.z2", a.getSpawn_2_z());
				yml.set("arena.castle.spawn.world2", a.getSpawn_2_world());
				yml.set("arena.map.x1", a.getMap_1_x());
				yml.set("arena.map.y1", a.getMap_1_y());
				yml.set("arena.map.z1", a.getMap_1_z());
				yml.set("arena.map.world1", a.getMap_1_world());
				yml.set("arena.map.x2", a.getMap_2_x());
				yml.set("arena.map.y2", a.getMap_2_y());
				yml.set("arena.map.z2", a.getMap_2_z());
				yml.set("arena.map.world2", a.getMap_2_world());
				
				yml.set("arena.attack.x1", a.getAttack_1_x());
				yml.set("arena.attack.y1", a.getAttack_1_y());
				yml.set("arena.attack.z1", a.getAttack_1_z());
				
				yml.set("arena.attack.x2", a.getAttack_2_x());
				yml.set("arena.attack.y2", a.getAttack_2_y());
				yml.set("arena.attack.z2", a.getAttack_2_z());
				try {
					yml.save(f);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
	}

}
