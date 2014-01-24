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
Ext.define('NX.util.Permissions', {
  singleton: true,

  READ: 1,   // 0001
  UPDATE: 2, // 0010
  DELETE: 4, // 0100
  CREATE: 8, // 1000
  ALL: 15,   // 1111
  NONE: 0,   // 0000

  permissions: {},

  /**
   * @public
   */
  setPermissions: function (permissions) {
    var me = this,
        perms = permissions;

    if (Ext.isArray(permissions)) {
      perms = {};
      Ext.each(permissions, function (entry) {
        if (entry.id && entry.value) {
          perms[entry.id] = entry.value;
        }
      });
    }

    me.permissions = Ext.apply({}, perms);
  },

  /**
   * @public
   */
  check: function (value, perm /* , perm... */) {
    var me = this,
        p = perm,
        pVal = me.permissions[value] | me.NONE,
        perms;

    if (arguments.length > 2) {
      perms = [].slice.call(arguments, 2);
      Ext.each(perms, function (entry) {
        p = p | entry;
      });
    }

    return ((p & pVal) == p);
  }

});