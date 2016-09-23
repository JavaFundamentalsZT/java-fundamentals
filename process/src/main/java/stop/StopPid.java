package stop;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.zeroturnaround.process.PidProcess;
import org.zeroturnaround.process.ProcessUtil;
import org.zeroturnaround.process.Processes;

public class StopPid {

  public static void main(String[] args) throws Exception {
    int pid = Integer.parseInt(FileUtils.readFileToString(new File("pidfile")));
    PidProcess p = Processes.newPidProcess(pid);
    ProcessUtil.destroyGracefullyOrForcefullyAndWait(p,
        30, TimeUnit.SECONDS, // graceful timeout
        10, TimeUnit.SECONDS); // forceful timeout
  }

}
