package loaf.cmd

import loaf.Command
import loaf.service.JpsService
import loaf.util.AnsiFormattingHelper
import loaf.util.TableBuilder
import org.springframework.stereotype.Component

/**
 * Created by poundex on 16/06/17.
 */
@Component
class TableTest implements Command, AnsiFormattingHelper
{
	final String name = "table"

	private final JpsService jpsService

	TableTest(JpsService jpsService)
	{
		this.jpsService = jpsService
	}

	@Override
	String execute()
	{
		new TableBuilder().
				column('mainClass', padRight: 5).
				column('mainArgs').
				column('jvmArgs').
				withRowBuilder( { rb ->
					jpsService.list().each { s ->
						rb.row(
								rb.cell(s.mainClass, { v -> error(v) }),
								rb.cell(s.mainArgs),
								rb.cell(s.jvmArgs)
						)
					}
				}).
				build()
	}
}
