package lesson_16.priority;

import org.testng.Assert;
import org.testng.annotations.Test;

public class Priority1 {

    @Test(priority = 5, dependsOnMethods = "d", alwaysRun = true)
    public void c() {
        Assert.assertTrue(true);
    }

    @Test(priority = 3, dependsOnMethods = "f", alwaysRun = true)
    public void e() {
        Assert.assertTrue(true);
    }

    @Test(priority = 7, dependsOnMethods = "b", alwaysRun = true)
    public void a() {
        Assert.assertTrue(true);
    }

    @Test(priority = 1)
    public void g() {
        Assert.assertTrue(true);
    }

    @Test(priority = 6, dependsOnMethods = "c", alwaysRun = true)
    public void b() {
        Assert.assertTrue(true);
    }

    @Test(priority = 4, dependsOnMethods = "e", alwaysRun = true)
    public void d() {
        Assert.assertTrue(true);
    }

    @Test(priority = 2, dependsOnMethods = "g", alwaysRun = true)
    public void f() {
        Assert.assertTrue(true);
    }
}
