package net.labymod.core.test.integration.suites;

import net.labymod.api.addon.entrypoint.EntrypointProgress;
import net.labymod.api.service.annotation.AutoService;
import net.labymod.api.test.IntegrationTest;
import net.labymod.api.test.IntegrationTestSuite;
import net.labymod.api.test.TestAssertion;
import net.labymod.api.test.TestContext;
import net.labymod.api.test.TestPhase;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/test/integration/suites/EntrypointProgressTests.class */
@AutoService(IntegrationTestSuite.class)
public class EntrypointProgressTests implements IntegrationTestSuite {
    private static final String[] STEPS = {"Downloading OptiFine", "Verifying signatures", "Installing components", "Configuring shaders"};

    @IntegrationTest(phase = TestPhase.AFTER_INIT, timeout = 20000, description = "Launch progress window with dual progress bars")
    public void testProgressWindow(TestContext context) throws Exception {
        context.log("Opening progress window...");
        EntrypointProgress progress = EntrypointProgress.open("Test: Entrypoint Progress");
        try {
            TestAssertion.assertTrue(progress.isAlive(), "Progress process should be alive");
            for (int step = 0; step < STEPS.length; step++) {
                String stepName = STEPS[step];
                progress.overallStatus(String.format("Step %d of %d", Integer.valueOf(step + 1), Integer.valueOf(STEPS.length)));
                progress.overallProgress(step / STEPS.length);
                progress.status(stepName + "...");
                progress.indeterminate();
                Thread.sleep(500L);
                for (int i = 0; i <= 10; i++) {
                    float value = i / 10.0f;
                    progress.status(String.format("%s... %.0f%%", stepName, Float.valueOf(value * 100.0f)));
                    progress.progress(value);
                    Thread.sleep(150L);
                }
            }
            progress.overallStatus("Complete");
            progress.overallProgress(1.0f);
            progress.status("Done!");
            progress.progress(1.0f);
            Thread.sleep(1000L);
            context.log("Closing progress window...");
            if (progress != null) {
                progress.close();
            }
            context.log("Progress window test completed.");
        } catch (Throwable th) {
            if (progress != null) {
                try {
                    progress.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }
}
