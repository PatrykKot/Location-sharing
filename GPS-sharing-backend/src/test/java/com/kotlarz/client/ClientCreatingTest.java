package com.kotlarz.client;

import com.kotlarz.client.domain.Client;
import com.kotlarz.client.service.ClientService;
import com.kotlarz.client.service.ClientServiceImpl;
import com.kotlarz.database.Database;
import com.kotlarz.system.web.WebSystemBase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(BlockJUnit4ClassRunner.class)
public class ClientCreatingTest extends WebSystemBase {
    public ClientService clientService;

    @Before
    public void init() {
        init("com.kotlarz.client");
        clientService = injector.getInstance(ClientService.class);
    }

    @Test
    public void createClient() {
        Client createdClient = clientService.create();
        assertNotNull(createdClient);

        String uuid = createdClient.getUuid();

        Client foundClient = clientService.getByUuid(uuid);
        assertTrue(foundClient.equals(createdClient));

        clientService.removeClient(foundClient);
        foundClient = clientService.getByUuid(uuid);

        assertNull(foundClient);
    }

    @Test
    public void theSameService() {
        assertTrue(injector.getInstance(ClientService.class) == injector.getInstance(ClientServiceImpl.class));
    }

    @Test
    public void theSameServiceDatabase() {
        assertTrue(injector.getInstance(Database.class) == injector.getInstance(Database.class));
    }

    @After
    public void after() {
        clientService.getAll().forEach(client -> clientService.removeClient(client));
        close();
    }

}
