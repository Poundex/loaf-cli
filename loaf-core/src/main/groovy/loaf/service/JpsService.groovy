package loaf.service

import groovy.transform.Immutable
import groovy.transform.Memoized
import org.springframework.stereotype.Service
import sun.jvmstat.monitor.*

@Service
class JpsService
{
	@Memoized
	List<RunningVM> list()
	{
		return runningVMs
	}

	private List<RunningVM> getRunningVMs()
	{
		HostIdentifier hostIdentifier = new HostIdentifier('localhost')
		MonitoredHost monitoredHost = MonitoredHost.getMonitoredHost(hostIdentifier)
		monitoredHost.activeVms().collect { vmId ->
			try {
				VmIdentifier vmIdentifier = new VmIdentifier("//${vmId}?mode=r")
				MonitoredVm monitoredVm = monitoredHost.getMonitoredVm(vmIdentifier, 0)
				new RunningVM(
						MonitoredVmUtil.mainClass(monitoredVm, true),
						MonitoredVmUtil.mainArgs(monitoredVm),
						MonitoredVmUtil.jvmArgs(monitoredVm))
			} catch (Exception ignored) {
				null
			}
		}.findAll()
	}

	@Immutable
	static class RunningVM
	{
		String mainClass
		String mainArgs
		String jvmArgs
	}
}

// /:([0-9]+)\s.*LISTEN\s+([0-9]+)\/.*/
