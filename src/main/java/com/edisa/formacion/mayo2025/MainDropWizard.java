package com.edisa.formacion.mayo2025;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class MainDropWizard extends Application<DropWizardConfiguration> {
    public static void main(String[] args) throws Exception {
        new MainDropWizard().run(args);

    }
    @Override
    public void initialize(Bootstrap<DropWizardConfiguration> bootstrap) {
        // Configuración adicional si es necesaria
    }

    @Override
    public void run(DropWizardConfiguration configuration, Environment environment) {
        final Recursos resource = new Recursos();
        environment.jersey().register(resource);
    }
}