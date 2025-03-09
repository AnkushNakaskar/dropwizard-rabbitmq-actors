package com.phonepe.core;

import io.dropwizard.Configuration;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author ankush.nakaskar
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BasicConfiguration extends Configuration {

    private  int defaultSize;

}
