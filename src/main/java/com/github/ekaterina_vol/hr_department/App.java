package com.github.ekaterina_vol.hr_department;

import com.github.ekaterina_vol.hr_department.app.cli.CliApp;
import com.github.ekaterina_vol.hr_department.domain.repositories.database.DBStorage;
import com.github.ekaterina_vol.hr_department.domain.repositories.inmemory.InMemoryStorage;
import com.github.ekaterina_vol.hr_department.infrastructure.services.ServiceProvider;

public class App {
    public static void main(String[] args) {
        DBStorage storage = DBStorage.getInstance();
        ServiceProvider serviceProvider = new ServiceProvider(storage);
        CliApp cliApp = new CliApp(serviceProvider);

        cliApp.run();
    }
}
