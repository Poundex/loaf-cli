package loaf.cmd

import org.springframework.stereotype.Component
import loaf.Command
import loaf.service.JpsService

@Component
class JpsTest implements Command
{
	private final JpsService jpsService

	JpsTest(JpsService jpsService)
	{
		this.jpsService = jpsService
	}

	@Override
	String getName()
	{
		return 'jps'
	}

	@Override
	String execute()
	{
		return jpsService.list().toListString()
	}
}
