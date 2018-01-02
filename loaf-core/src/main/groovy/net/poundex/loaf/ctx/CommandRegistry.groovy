package net.poundex.loaf.ctx

import loaf.Command
import org.springframework.stereotype.Service

@Service
class CommandRegistry
{
	private final Map<String, Command> commands = [:]

	void register(Command command)
	{
		commands[command.name.toLowerCase()] = command
	}

	Command findByName(String name)
	{
		return commands[name]
	}

	Collection<Command> getCommands()
	{
		return commands.values().asImmutable()
	}
}
