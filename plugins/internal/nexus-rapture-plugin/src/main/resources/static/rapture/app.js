Ext.Loader.setConfig({
  enabled: true,
  paths: {
    NX: 'app'
  }
});

// TODO: Sort out how to dynamically get the list of plugin ids
Ext.ns('NX.app');
NX.app.pluginIds = [
    'nexus-rapturetest-plugin'
];

Ext.application('NX.app.Application');