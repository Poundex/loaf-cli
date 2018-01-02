package net.poundex.loaf.ctx

import groovy.util.logging.Slf4j
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service
import loaf.CliArguments
import loaf.Command
import loaf.util.AnsiFormattingHelper

@Slf4j
@Service
class ExecutionManager implements AnsiFormattingHelper
{
	private final CliArguments cliArguments
	private final CommandRegistry commandRegistry

	ExecutionManager(CliArguments cliArguments, CommandRegistry commandRegistry)
	{
		this.cliArguments = cliArguments
		this.commandRegistry = commandRegistry
	}

	@EventListener(ContextRefreshedEvent)
	void execute(ContextRefreshedEvent event)
	{
		Command command = commandRegistry.findByName(cliArguments.commandName)
		if( ! command)
			println error("Could not find command ${cliArguments.commandName}")
		else
			println command.execute()
	}
}
