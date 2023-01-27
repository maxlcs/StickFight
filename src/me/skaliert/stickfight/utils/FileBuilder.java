package me.skaliert.stickfight.utils;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

public class FileBuilder {

	private File file;
	private YamlConfiguration config;

	public FileBuilder(String filePath, String fileName) {
		this.file = new File(filePath, fileName);
		this.config = YamlConfiguration.loadConfiguration(this.file);
	}

	public FileBuilder setValue(String valuePath, Object value) {
		config.set(valuePath, value);
		return this;
	}

	public int getInt(String valuePath) {
		return config.getInt(valuePath);
	}

	public String getString(String valuePath) {
		return config.getString(valuePath);
	}

	public boolean getBoolean(String valuePath) {
		return config.getBoolean(valuePath);
	}

	public long getLong(String valuePath) {
		return config.getLong(valuePath);
	}

	public double getDouble(String valuePath) {
		return config.getDouble(valuePath);
	}

	public List<String> getStringList(String valuePath) {
		return config.getStringList(valuePath);
	}

	public Set<String> getKeys(boolean deep) {
		return config.getKeys(deep);
	}

	public ConfigurationSection getConfigSection(String section) {
		return config.getConfigurationSection(section);
	}

	public boolean exists() {
		return this.file.exists();
	}

	public FileBuilder save() {
		try {
			config.save(this.file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return this;
	}
}