package utilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class PageListener implements ITestListener {
    private Logger logger = LogManager.getLogger(this.getClass());

    @Override
    public void onTestFailure(ITestResult result) {
        String message = "Test failed: " + result.getMethod().getMethodName();
        logger.error(message);
    }

    @Override
    public void onStart(ITestContext context) {
        String message = "Started new test run===";
        logger.debug(message);
    }

    @Override
    public void onFinish(ITestContext context) {
        String message = "Finished test run===";
        logger.debug(message);
    }
}
