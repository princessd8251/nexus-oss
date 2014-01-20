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
package org.sonatype.nexus.analytics.internal;

import static com.google.common.base.Preconditions.checkNotNull;
import io.kazuki.v0.store.journal.JournalStore;
import io.kazuki.v0.store.lifecycle.Lifecycle;
import io.kazuki.v0.store.schema.TypeValidation;

import java.util.Iterator;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.analytics.EventData;
import org.sonatype.nexus.analytics.EventStore;
import org.sonatype.sisu.goodies.lifecycle.LifecycleSupport;

/**
 * Default {@link EventStore} implementation.
 *
 * @since 2.8
 */
@Named
@Singleton
public class EventStoreImpl
  extends LifecycleSupport
  implements EventStore
{
  private final JournalStore store; 
  private final Lifecycle lifecycle;

  @Inject
  public EventStoreImpl(JournalStore store, @Named("nexusanalytics") Lifecycle lifecycle) {
    this.store = store;
    this.lifecycle = lifecycle;
  }
  
  @Override
  protected void doStart() throws Exception {
    lifecycle.init();
    lifecycle.start();
  }

  @Override
  protected void doStop() throws Exception {
    lifecycle.shutdown();
    lifecycle.stop();
  }

  @Override
  public void add(final EventData data) throws Exception {
    checkNotNull(data);
    store.append("event_data", EventData.class, data, TypeValidation.STRICT);
  }

  @Override
  public void clear() throws Exception {
    store.clear(false, false);
    log.debug("Cleared");
  }

  @Override
  public long approximateSize() throws Exception {
    return store.approximateSize("event_data");
  }

  @Override
  public Iterator<EventData> iterator(final long index) throws Exception {
    return store.getIteratorAbsolute("event_data", EventData.class, index, -1L);
  }
}
