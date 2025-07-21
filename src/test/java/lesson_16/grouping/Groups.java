package lesson_16.grouping;

import org.testng.Assert;
import org.testng.annotations.Test;

public class Groups {

    @Test(groups = {"first"}, priority = 1)
    public void one() {
        Assert.assertTrue(true);
    }

    @Test(groups = {"second"}, priority = 1)
    public void two() {
        Assert.assertTrue(true);
    }

    @Test(groups = {"first"}, priority = 2)
    public void three() {
        Assert.assertTrue(true);
    }

    @Test(groups = {"second"}, priority = 2)
    public void four() {
        Assert.assertTrue(true);
    }

    @Test(groups = {"first"}, priority = 3)
    public void five() {
        Assert.assertTrue(true);
    }

    @Test(groups = {"second"}, priority = 3)
    public void six() {
        Assert.assertTrue(true);
    }

    @Test(groups = {"first"}, priority = 4)
    public void seven() {
        Assert.assertTrue(true);
    }

    @Test(groups = {"second"}, priority = 4)
    public void eight() {
        Assert.assertTrue(true);
    }
}
