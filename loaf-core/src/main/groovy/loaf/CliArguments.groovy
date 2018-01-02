package loaf

import com.lexicalscope.jewel.cli.Unparsed

interface CliArguments
{
	@Unparsed(name = 'commandName', description = 'The name of the command to run')
	String getCommandName()
}