/*
 * #%L
 * This file is part of eAudit4j, a library for creating pluggable
 * auditing solutions, providing an audit processor that creates
 * a unique machine ID for the machine executing the library and
 * appends it as a field to audit events.
 * %%
 * Copyright (C) 2015 - 2016 Michael Beiter <michael@beiter.org>
 * %%
 * All rights reserved.
 * .
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the copyright holder nor the names of the
 *       contributors may be used to endorse or promote products derived
 *       from this software without specific prior written permission.
 * .
 * .
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * #L%
 */
package org.beiter.michael.eaudit4j.processors.machineid.propsbuilder;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.beiter.michael.eaudit4j.processors.machineid.MachineIdProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * This class builds a set of {@link MachineIdProperties} using the settings obtained from a Map.
 * <p>
 * Use the keys from the various KEY_* fields to properly populate the Map before calling this class' methods.
 */
// CHECKSTYLE:OFF
// this is flagged in checkstyle with a missing whitespace before '}', which is a bug in checkstyle
// suppress warnings about the long variable names
@SuppressWarnings({"PMD.LongVariable"})
// CHECKSTYLE:ON
public final class MapBasedMachineIdPropsBuilder {

    /**
     * The logger object for this class
     */
    private static final Logger LOG = LoggerFactory.getLogger(MapBasedMachineIdPropsBuilder.class);

    // #################
    // # Default values
    // #################

    /**
     * @see MachineIdProperties#setMachineId(String)
     */
    public static final String DEFAULT_MACHINE_ID = null;

    /**
     * @see MachineIdProperties#setEventFieldName(String)
     */
    public static final String DEFAULT_EVENT_FIELD_NAME = "org.beiter.michael.eaudit4j.processors.machineid";

    /**
     * @see MachineIdProperties#setMachineIdFromEnv(boolean)
     */
    public static final boolean DEFAULT_MACHINE_ID_FROM_ENV = false;

    /**
     * @see MachineIdProperties#setMachineIdEnvName(String)
     */
    public static final String DEFAULT_MACHINE_ID_ENV_NAME = null;

    /**
     * @see MachineIdProperties#setMachineIdFromHostname(boolean)
     */
    public static final boolean DEFAULT_MACHINE_ID_FROM_HOSTNAME = false;


    // #####################
    // # Configuration Keys
    // #####################

    /**
     * @see MachineIdProperties#setMachineId(String)
     */
    public static final String KEY_MACHINE_ID = "audit.processor.mid.machineid";

    /**
     * @see MachineIdProperties#setEventFieldName(String)
     */
    public static final String KEY_EVENT_FIELD_NAME = "audit.processor.mid.eventFieldName";

    /**
     * @see MachineIdProperties#setMachineIdFromEnv(boolean)
     */
    public static final String KEY_MACHINE_ID_FROM_ENV = "audit.processor.mid.fromEnv";

    /**
     * @see MachineIdProperties#setMachineIdEnvName(String)
     */
    public static final String KEY_MACHINE_ID_ENV_NAME = "audit.processor.mid.envVarName";

    /**
     * @see MachineIdProperties#setMachineIdFromHostname(boolean)
     */
    public static final String KEY_MACHINE_ID_FROM_HOSTNAME = "audit.processor.mid.fromHostname";


    /**
     * A private constructor to prevent instantiation of this class
     */
    private MapBasedMachineIdPropsBuilder() {
    }

    /**
     * Creates a set of machine ID properties that use the defaults as specified in this class.
     *
     * @return A set of machine ID properties with (reasonable) defaults
     * @see MapBasedMachineIdPropsBuilder
     */
    public static MachineIdProperties buildDefault() {

        return build(new ConcurrentHashMap<String, String>());
    }

    /**
     * Initialize a set of machine ID properties based on key / values in a <code>HashMap</code>.
     *
     * @param properties A <code>HashMap</code> with configuration properties, using the keys as specified in this class
     * @return A {@link MachineIdProperties} object with default values, plus the provided parameters
     * @throws NullPointerException When {@code properties} is {@code null}
     */
    // CHECKSTYLE:OFF
    // this is flagged in checkstyle with a missing whitespace before '}', which is a bug in checkstyle
    // suppress warnings about this method being too long (not much point in splitting up this one!)
    // suppress warnings about this method being too complex (can't extract a generic subroutine to reduce exec paths)
    @SuppressWarnings({"PMD.ExcessiveMethodLength", "PMD.NPathComplexity", "PMD.CyclomaticComplexity", "PMD.StdCyclomaticComplexity", "PMD.ModifiedCyclomaticComplexity"})
    // CHECKSTYLE:ON
    public static MachineIdProperties build(final Map<String, String> properties) {

        Validate.notNull(properties, "The validated object 'value' is null");

        final MachineIdProperties machineIdProperties = new MachineIdProperties();
        String tmp = properties.get(KEY_MACHINE_ID);
        if (StringUtils.isNotEmpty(tmp)) {
            machineIdProperties.setMachineId(tmp);
            logValue(KEY_MACHINE_ID, tmp);
        } else {
            machineIdProperties.setMachineId(DEFAULT_MACHINE_ID);
            logDefault(KEY_MACHINE_ID, DEFAULT_MACHINE_ID);
        }

        tmp = properties.get(KEY_EVENT_FIELD_NAME);
        if (StringUtils.isNotEmpty(tmp)) {
            machineIdProperties.setEventFieldName(tmp);
            logValue(KEY_EVENT_FIELD_NAME, tmp);
        } else {
            machineIdProperties.setEventFieldName(DEFAULT_EVENT_FIELD_NAME);
            logDefault(KEY_EVENT_FIELD_NAME, DEFAULT_EVENT_FIELD_NAME);
        }

        tmp = properties.get(KEY_MACHINE_ID_FROM_ENV);
        if (StringUtils.isNotEmpty(tmp)) {
            machineIdProperties.setMachineIdFromEnv(Boolean.parseBoolean(tmp));
            logValue(KEY_MACHINE_ID_FROM_ENV, tmp);
        } else {
            machineIdProperties.setMachineIdFromEnv(DEFAULT_MACHINE_ID_FROM_ENV);
            logDefault(KEY_MACHINE_ID_FROM_ENV, String.valueOf(DEFAULT_MACHINE_ID_FROM_ENV));
        }

        tmp = properties.get(KEY_MACHINE_ID_ENV_NAME);
        if (StringUtils.isNotEmpty(tmp)) {
            machineIdProperties.setMachineIdEnvName(tmp);
            logValue(KEY_MACHINE_ID_ENV_NAME, tmp);
        } else {
            machineIdProperties.setMachineIdEnvName(DEFAULT_MACHINE_ID_ENV_NAME);
            logDefault(KEY_MACHINE_ID_ENV_NAME, DEFAULT_MACHINE_ID_ENV_NAME);
        }

        tmp = properties.get(KEY_MACHINE_ID_FROM_HOSTNAME);
        if (StringUtils.isNotEmpty(tmp)) {
            machineIdProperties.setMachineIdFromHostname(Boolean.parseBoolean(tmp));
            logValue(KEY_MACHINE_ID_FROM_HOSTNAME, tmp);
        } else {
            machineIdProperties.setMachineIdFromHostname(DEFAULT_MACHINE_ID_FROM_HOSTNAME);
            logDefault(KEY_MACHINE_ID_FROM_HOSTNAME, String.valueOf(DEFAULT_MACHINE_ID_FROM_HOSTNAME));
        }

        // set the additional properties, preserving the originally provided properties
        // create a defensive copy of the map and all its properties
        // the code looks a little complicated that "putAll()", but it catches situations where a Map is provided that
        // supports null values (e.g. a HashMap) vs Map implementations that do not (e.g. ConcurrentHashMap).
        final Map<String, String> tempMap = new ConcurrentHashMap<>();
        for (final Map.Entry<String, String> entry : properties.entrySet()) {
            final String key = entry.getKey();
            final String value = entry.getValue();

            if (value != null) {
                tempMap.put(key, value);
            }
        }
        machineIdProperties.setAdditionalProperties(tempMap);

        return machineIdProperties;
    }

    /**
     * Create a log entry when a value has been successfully configured.
     *
     * @param key   The configuration key
     * @param value The value that is being used
     */
    private static void logValue(final String key, final String value) {

        // Fortify will report a violation here because of disclosure of potentially confidential information.
        // However, the configuration keys are not confidential, which makes this a non-issue / false positive.
        if (LOG.isInfoEnabled()) {
            final StringBuilder msg = new StringBuilder("Key found in configuration ('")
                    .append(key)
                    .append("'), using configured value (not disclosed here for security reasons)");
            LOG.info(msg.toString());
        }

        // Fortify will report a violation here because of disclosure of potentially confidential information.
        // The configuration VALUES are confidential. DO NOT activate DEBUG logging in production.
        if (LOG.isDebugEnabled()) {
            final StringBuilder msg = new StringBuilder("Key found in configuration ('")
                    .append(key)
                    .append("'), using configured value ('");
            if (value == null) {
                msg.append("null')");
            } else {
                msg.append(value).append("')");
            }
            LOG.debug(msg.toString());
        }
    }

    /**
     * Create a log entry when a default value is being used in case the propsbuilder key has not been provided in the
     * configuration.
     *
     * @param key          The configuration key
     * @param defaultValue The default value that is being used
     */
    private static void logDefault(final String key, final String defaultValue) {

        // Fortify will report a violation here because of disclosure of potentially confidential information.
        // However, neither the configuration keys nor the default propsbuilder values are confidential, which makes
        // this a non-issue / false positive.
        if (LOG.isInfoEnabled()) {
            final StringBuilder msg = new StringBuilder("Key is not configured ('")
                    .append(key)
                    .append("'), using default value ('");
            if (defaultValue == null) {
                msg.append("null')");
            } else {
                msg.append(defaultValue).append("')");
            }
            LOG.info(msg.toString());
        }
    }

    /**
     * Create a log entry when a default value is being used in case that an invalid configuration value has been
     * provided in the configuration for the propsbuilder key.
     *
     * @param key             The configuration key
     * @param invalidValue    The invalid value that cannot be used
     * @param validationError The validation error that caused the invalid value to be refused
     * @param defaultValue    The default value that is being used
     */
    /* This method is not needed anymore, but keep it around for debugging in case we will need it again
    // suppress warnings about not using an object for the four strings in this PRIVATE method
    @SuppressWarnings("PMD.UseObjectForClearerAPI")
    private static void logDefault(final String key,
                                   final String invalidValue,
                                   final String validationError,
                                   final String defaultValue) {

        if (LOG.isWarnEnabled()) {
            final StringBuilder msg = new StringBuilder("Invalid value ('")
                    .append(invalidValue)
                    .append("', ")
                    .append(validationError)
                    .append(") for key '")
                    .append(key)
                    .append("', using default instead ('");
            if (defaultValue == null) {
                msg.append("null')");
            } else {
                msg.append(defaultValue).append("')");
            }
            LOG.warn(msg.toString());
        }
    }
    */
}
