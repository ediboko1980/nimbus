/*
 * Copyright 1999-2008 University of Chicago
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy
 * of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package org.globus.workspace.client_core;

import org.apache.axis.message.addressing.EndpointReferenceType;
import org.globus.workspace.common.print.Print;
import org.globus.workspace.client_core.utils.WSUtils;
import org.nimbustools.messaging.gt4_0.generated.status.WorkspaceStatusPortType;
import org.nimbustools.messaging.gt4_0.common.CommonUtil;

import javax.xml.rpc.Stub;

public abstract class WSAction_Status extends WSAction {


    // -------------------------------------------------------------------------
    // CONSTRUCTORS
    // -------------------------------------------------------------------------

    /**
     * New WorkspaceStatusPortType instance will be created from given
     * parameters during the validation phase.
     *
     * @param epr       endpoint to create portType with, may not be null
     * @param stubConf  to configure portType with, may not be null
     * @param debug     for debug messages; if null, a new instance of
     *                  disabled print impl will be created
     */
    public WSAction_Status(EndpointReferenceType epr,
                           StubConfigurator stubConf,
                           Print debug) {
        super(epr, stubConf, debug);
    }

    /**
     * Re-use pre-created WorkspaceStatusPortType instance.
     *
     * @param statusPortType to use for action, may not be null
     * @param debug    for debug messages; if null, a new instance of disabled
     *                 print impl will be created
     */
    public WSAction_Status(WorkspaceStatusPortType statusPortType,
                           Print debug) {
        super(statusPortType, debug);
    }

    
    // -------------------------------------------------------------------------
    // VALIDATE
    // -------------------------------------------------------------------------

    public void validateAll() throws ParameterProblem {

        if (this.portType != null) {

            // future programmer error, should not be possible
            if (!(this.portType instanceof WorkspaceStatusPortType)) {
                throw new ParameterProblem(
                    "portType is not WorkspaceStatusPortType");
            }

            return; // *** EARLY RETURN ***
        }

        try {
            final WorkspaceStatusPortType port =
                    WSUtils.initStatusPortType(this.epr);
            this.stubConf.setOptions((Stub)port);
            this.portType = port;
        } catch (Throwable t) {
            final String err = "Problem setting up: " +
                    CommonUtil.genericExceptionMessageWrapper(t);
            throw new ParameterProblem(err, t);
        }
    }
}
