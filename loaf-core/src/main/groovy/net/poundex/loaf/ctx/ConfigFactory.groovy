package net.poundex.loaf.ctx

import com.moandjiezana.toml.Toml

class ConfigFactory<T>
{
	private final String configFileBaseName
	private final Class<T> configClass

	ConfigFactory(String configFileBaseName, Class<T> configClass)
	{
		this.configFileBaseName = configFileBaseName
		this.configClass = configClass
	}

	T getConfig()
	{
		return new Toml().read(configFile).to(configClass)
	}

	private File getConfigFile()
	{
		File userConfig = new File(System.properties['user.home'], ".${configFileBaseName}")
		if ( ! userConfig.exists())
			userConfig.text = getClass().getResource("/${configFileBaseName}").text

		return userConfig
	}
}
