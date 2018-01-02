package loaf.cmd

import net.poundex.loaf.ctx.CommandRegistry
import org.springframework.stereotype.Component
import loaf.Command
import loaf.util.AnsiFormattingHelper

@Component
class ListCommands implements Command, AnsiFormattingHelper
{
	final String name = 'list'

	private final CommandRegistry commandRegistry

	ListCommands(CommandRegistry commandRegistry)
	{
		this.commandRegistry = commandRegistry
	}

	String execute()
	{
		return bullet(commandRegistry.commands*.name)
	}
}
