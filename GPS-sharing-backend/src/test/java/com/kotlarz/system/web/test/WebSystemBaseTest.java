package com.kotlarz.system.web.test;

import com.kotlarz.system.web.WebSystemBase;
import com.kotlarz.system.web.test.client.TestClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

@RunWith(BlockJUnit4ClassRunner.class)
public class WebSystemBaseTest extends WebSystemBase {
    private TestClient client;

    @Before
    public void init() {
        init("com.kotlarz.system.web.test");
        client = createClient(TestClient.class);
    }

    @After
    public void close() {
        super.close();
    }

    @Test
    public void testWebSystem() throws IOException {
        assertEquals(client.getTestData().execute().body(), "\"TEST_GET\"");
        assertEquals(client.postTestData().execute().body(), "\"TEST_POST\"");
        assertEquals(client.putTestData().execute().body(), "\"TEST_PUT\"");
        assertEquals(client.deleteTestData().execute().body(), "\"TEST_DELETE\"");
    }
}
