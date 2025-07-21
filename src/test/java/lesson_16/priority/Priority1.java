package lesson_16.priority;

import org.testng.Assert;
import org.testng.annotations.Test;

public class Priority1 {
    @Test
    public void c() { Assert.assertTrue(true); }

    @Test
    public void e() { Assert.assertTrue(true); }

    @Test
    public void a() { Assert.assertTrue(true); }

    @Test
    public void g() { Assert.assertTrue(true); }

    @Test
    public void b() { Assert.assertTrue(true); }

    @Test
    public void d() { Assert.assertTrue(true); }

    @Test
    public void f() { Assert.assertTrue(true); }
}
