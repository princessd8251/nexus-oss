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
Ext.define('NX.view.dev.Permissions', {
  extend: 'Ext.grid.Panel',
  requires: [
    'NX.util.Permissions'
  ],
  alias: 'widget.nx-dev-permissions',

  title: 'Permissions',
  store: 'Permission',
  emptyText: 'No permissions',

  viewConfig: {
    deferEmptyText: false,
    markDirty: false
  },

  columns: [
    { text: 'permission', dataIndex: 'id', flex: 1, editor: { xtype: 'textfield', allowBlank: false } }
  ],

  plugins: [
    { pluginId: 'editor', ptype: 'rowediting', clicksToEdit: 1, errorSummary: false }
  ],

  tbar: [
    { xtype: 'button', text: 'Add', action: 'add' },
    { xtype: 'button', text: 'Delete', action: 'delete', disabled: true }
  ],

  /**
   * @protected
   */
  initComponent: function () {
    var me = this;

    // Add columns for each major permission
    Ext.each(['CREATE', 'READ', 'UPDATE', 'DELETE'], function (perm) {
      me.columns.push(
          { text: perm, xtype: 'templatecolumn', width: 80, align: 'center', dataIndex: perm.toLowerCase(), editor: 'checkbox',
            tpl: new Ext.XTemplate(
                '<img src="{[ Ext.BLANK_IMAGE_URL ]}" class="',
                '<tpl if="' + perm.toLowerCase() +
                    '">nx-icon-permission-granted-x16<tpl else>nx-icon-permission-denied-x16</tpl>',
                '"/>'
            )
          }
      );
    });

    me.callParent();
  }
});