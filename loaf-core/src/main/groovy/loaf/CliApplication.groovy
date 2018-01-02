package loaf

import com.lexicalscope.jewel.cli.CliFactory
import net.poundex.loaf.ctx.ApplicationConfiguration
import net.poundex.loaf.ctx.ConfigFactory
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.context.annotation.AnnotationConfigApplicationContext

abstract class CliApplication
{
	protected CliApplication(Class<?> configurationClass, String[] args)
	{
		CliArguments cliArguments = CliFactory.parseArguments(CliArguments, args)

		ConfigurableApplicationContext applicationContext =
				new AnnotationConfigApplicationContext()

		applicationContext.beanFactory.registerSingleton('cliArguments', cliArguments)
		applicationContext.beanFactory.registerSingleton('appConfig',
				new ConfigFactory(configFileBaseName, configurationClass).config
		)

		applicationContext.register(ApplicationConfiguration)
		applicationContext.scan('net.poundex.loaf.ctx', 'loaf.cmd', 'loaf.service', *basePackages)

		applicationContext.refresh()
	}

	protected List<String> getBasePackages()
	{
		return []
	}

	protected String getConfigFileBaseName()
	{
		return "${this.class.simpleName}.toml"
	}
}
