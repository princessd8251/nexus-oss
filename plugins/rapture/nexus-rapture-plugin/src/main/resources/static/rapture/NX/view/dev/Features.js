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
Ext.define('NX.view.dev.Features', {
  extend: 'Ext.grid.Panel',
  alias: 'widget.nx-dev-features',

  title: 'Features',
  store: 'FeatureMenu',

  columns: [
    { text: 'path', dataIndex: 'path', width: 200, editor: 'textfield' },
    { text: 'bookmark', dataIndex: 'bookmark', editor: 'textfield' },
    { text: 'weight', dataIndex: 'weight', editor: 'textfield' },
    { text: 'view', dataIndex: 'view', width: 250, editor: 'textfield' },
    { text: 'iconCls', dataIndex: 'iconCls', width: 250, editor: 'textfield' },
    {
      xtype: 'templatecolumn',
      text: 'icon',
      // NOTE: img must have src value for Chrome to render it w/o a border?!
      tpl: '<img src="{[ Ext.BLANK_IMAGE_URL ]}" class="{iconCls}"/>'
    }
  ],

  plugins: [
    { ptype: 'rowediting', clicksToEdit: 1 }
  ],

  viewConfig: {
    markDirty: false
  }

});