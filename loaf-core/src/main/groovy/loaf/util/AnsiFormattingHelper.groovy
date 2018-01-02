package loaf.util

import org.fusesource.jansi.Ansi
import org.fusesource.jansi.AnsiConsole

import static org.fusesource.jansi.Ansi.ansi

trait AnsiFormattingHelper
{
	private static synchronized boolean ansiOn = false

	String error(String text)
	{
		ensureAnsi()
		return ansi().fg(Ansi.Color.RED).bold().a(text).reset()
	}

	private static void ensureAnsi()
	{
		if( ! ansiOn)
			AnsiConsole.systemInstall()
	}

	String bullet(List<String> list)
	{
		ensureAnsi()
		ansi().with {
			list.each { item ->
				bold().fgBrightDefault().a(' * ').reset().a(item).newline()
			}
			it
		}
	}
}