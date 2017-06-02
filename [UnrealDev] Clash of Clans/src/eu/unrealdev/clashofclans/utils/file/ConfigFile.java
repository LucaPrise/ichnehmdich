package eu.unrealdev.clashofclans.utils.file;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import eu.unrealdev.clashofclans.COCMain;

public class ConfigFile{
	
	
private File file;
private FileConfiguration config;
public ConfigFile(String name){
file = new File(COCMain.getInstance().getDataFolder(), File.separator + name +".yml");
config = YamlConfiguration.loadConfiguration(file);
}
public FileConfiguration getConfig(){
return config;
}
public void saveConfig(){
try{
config.save(file);
}catch(Exception ex){
ex.printStackTrace();
}
}
}
