package com.phonepe.core;

import com.google.inject.Stage;
import com.phonepe.data.provider.rosey.bundle.RoseyConfigProviderBundle;
import io.appform.opentracing.TracingManager;
import io.appform.opentracing.TracingOptions;
import io.dropwizard.Application;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.ResourceConfigurationSourceProvider;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.forms.MultiPartBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import ru.vyarus.dropwizard.guice.GuiceBundle;

/**
 * @author ankush.nakaskar
 */
public class OpenTracingApp extends Application<BasicConfiguration> {



    public static void main(final String[] args) throws Exception {
        new OpenTracingApp().run("server", "application.yml");
    }

    @Override
    public void run(final BasicConfiguration basicConfiguration, final Environment environment) {
        TracingOptions tracingOptions = new TracingOptions.TracingOptionsBuilder()
                .parameterCaptureEnabled(true)
                .build();
        TracingManager.initialize(tracingOptions);
    }

    @Override
    public void initialize(final Bootstrap<BasicConfiguration> bootstrap) {

        bootstrap.setConfigurationSourceProvider(
                new SubstitutingSourceProvider(
                        bootstrap.getConfigurationSourceProvider(),
                        new EnvironmentVariableSubstitutor()));

        bootstrap.addBundle(new MultiPartBundle());
        bootstrap.setConfigurationSourceProvider(new ResourceConfigurationSourceProvider());

        RoseyConfigProviderBundle<BasicConfiguration> configProviderBundle = appConfigProviderBundle();
        bootstrap.addBundle(configProviderBundle);


        GuiceBundle guiceBundle = guiceBundle(bootstrap,configProviderBundle);
        bootstrap.addBundle(guiceBundle);

        super.initialize(bootstrap);
    }

    RoseyConfigProviderBundle<BasicConfiguration> appConfigProviderBundle() {

        return new RoseyConfigProviderBundle<BasicConfiguration>() {
            @Override
            public String getRoseyConfigPath(final BasicConfiguration userServiceConfiguration) {
                return "/rosey/config.yml";
            }

            @Override
            public String getRoseyTeamId(final BasicConfiguration userServiceConfiguration) {
                return "StratosApplication.ROSEY_TEAM_NAME";
            }

            @Override
            public String getRoseyConfigName(final BasicConfiguration userServiceConfiguration) {
                return "APP_NAME";
            }
        };
    }




    GuiceBundle guiceBundle(Bootstrap<BasicConfiguration> bootstrap, RoseyConfigProviderBundle<BasicConfiguration> configProviderBundle) {

        return GuiceBundle.builder()
                .enableAutoConfig(getClass().getPackage().getName())
                .modules(
                        new ConfigModule()
                )
                .build(Stage.PRODUCTION);
    }



}
