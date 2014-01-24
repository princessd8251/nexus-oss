/*
 * Sonatype Nexus (TM) Open Source Version
 * Copyright (c) 2007-2013 Sonatype, Inc.
 * All rights reserved. Includes the third-party code listed at http://links.sonatype.com/products/nexus/oss/attributions.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License Version 1.0,
 * which accompanies this distribution and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Sonatype Nexus (TM) Professional Version is available from Sonatype, Inc. "Sonatype" and "Sonatype Nexus" are trademarks
 * of Sonatype, Inc. Apache Maven is a trademark of the Apache Software Foundation. M2eclipse is a trademark of the
 * Eclipse Foundation. All other trademarks are the property of their respective owners.
 */

package org.sonatype.nexus.extdirect.internal;

import java.io.File;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.configuration.application.ApplicationConfiguration;
import org.sonatype.nexus.plugin.support.FileWebResource;
import org.sonatype.nexus.web.WebResource;

/**
 * Ext.Direct Prod API {@link WebResource}.
 *
 * @since 2.8
 */
@Named
@Singleton
public class ExtDirectProdApiWebResource
    extends FileWebResource
{

  @Inject
  public ExtDirectProdApiWebResource(final ApplicationConfiguration applicationConfiguration) {
    super(
        new File(applicationConfiguration.getTemporaryDirectory(), "djn/Nexus-min.js"),
        "/static/rapture/extdirect-prod.js",
        JAVASCRIPT,
        true
    );
  }

}