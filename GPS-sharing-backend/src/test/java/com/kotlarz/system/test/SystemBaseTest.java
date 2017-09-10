package com.kotlarz.system.test;

import com.kotlarz.system.SystemBase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import static org.junit.Assert.assertTrue;

@RunWith(BlockJUnit4ClassRunner.class)
public class SystemBaseTest extends SystemBase {
    @Before
    public void init() {
        init("com.kotlarz");
    }

    @After
    public void destroy() {
        close();
    }

    @Test
    public void checkServices() {
        assertTrue(!injector.getAllBindings().values().isEmpty());
    }

}
