function generateChart(parent) {
    var _instance = parent;
    console.log('create c3chart ' + parent.divId + " " + parent.id);
    var html = '<div id="' + parent.id + '"/>';
    document.getElementById(parent.divId).innerHTML = html;
    var chart = c3.generate({
        bindto: '#' + parent.id,
        data: {
            type: parent.type,
            columns: [],
            onclick: function(d, element) {
                _instance.setValue(d.id);
            }
        },
        color: {
                pattern: parent.opts['colors']
            }
    });
    return chart;
}

function generateBar(parent) {
    var _instance = parent;
    console.log('create c3bar ' + parent.divId + " " + parent.id);
    var html = '<div id="' + parent.id + '"/>';
    document.getElementById(parent.divId).innerHTML = html;
    var chart = c3.generate({
        bindto: '#' + parent.id,
        data: {
            type: parent.type,
            columns: [],
            onclick: function(d, element) {
                _instance.setValue(_instance.x[d.x]);
            }
        },
                              axis: {
                                      x: {
                                          type: 'category' // this needed to load string x value
                                      }
                                  },
                                  color: {
                                           pattern: parent.opts['colors']
                                       }
    });
    return chart;
}

function generateMultiBar(parent) {
    var _instance = parent;
    console.log('create c3bar ' + parent.divId + " " + parent.id);
    var html = '<div id="' + parent.id + '"/>';
    document.getElementById(parent.divId).innerHTML = html;
    var chart = c3.generate({
        bindto: '#' + parent.id,
        data: {
            type: 'bar',
            x : 'x',
            columns: [],
            onclick: function(d, element) {
                            console.log("---CLICK---");
                            console.log(d);
                            console.log(_instance.x);
                            console.log(_instance.x[d.x]);
                _instance.setValue(_instance.x[d.x]);
            }
        },
                     axis: {
                             x: {
                                 type: 'category' // this needed to load string x value
                             }
                         },
                         color: {
                                  pattern: parent.opts['colors']
                              }
    });
    return chart;
}

function parseDataset(dataset) {
    var labels = new Array();
    var resp = dataset[0].datas;
    var finalJSONObj = {};
    for (var i = 0; i < resp.length; i++) {
        finalJSONObj[resp[i].key] = resp[i].value;
        labels[i] = resp[i].key;
    }

    var js = '[' + JSON.stringify(finalJSONObj) + ']';

    console.log('datas= ', js);
    console.log('labels= ', labels);
    return {keys: labels, jsonValues: js};
}

function parseMultiDataset(datasets) {
    var labels = new Array();
    var all = '[';
    var groups = '[';
    var x = new Array();
    for (var d = 0; d < datasets.length; d++) {
        var dataset = datasets[d];
        var line = '{"x":"' + dataset.label + '", ';
        x[d] = dataset.label;
        var datas = dataset.datas;
        for (var i = 0; i < datas.length; i++) {
            line += '"' + datas[i].key + '":"' + datas[i].value + '",';
            if (d == 0) {
                groups += '"' + datas[i].key + '",';
            }
        }
        line = line.slice(0,-1);
        line += '},';

        all += line;
    }

    all = all.slice(0,-1);
    all += ']';
    groups = groups.slice(0,-1);
    groups += ']';

    console.log('x= ', x);
    console.log('jsonValues= ', all);
    console.log('groups= ', groups);
    return {groups: groups, jsonValues: all, x: x};
}

function parseBarDataset(dataset) {
    var labels = new Array();
    var x = new Array();
        var resp = dataset[0].datas;
        var datas = '';
        for (var i = 0; i < resp.length; i++) {
            datas += '{"' + [dataset[0].label] + '": "' + resp[i].value + '", ';
            datas += '"key": "' + resp[i].key + '"},';
            x[i] = resp[i].key;
        }

        var js = '[' + datas.slice(0,-1) + ']';

        console.log('datas= ', js);
        console.log('labels= ', dataset[0].label);
        return {keys: '["' + dataset[0].label + '"]', jsonValues: js, x: x};
}

function refreshMultiple(chart, datas, component, hasToGroup) {
    console.log('refreshMultiple ', datas.x);
    var groups = datas.groups;
        var js = datas.jsonValues;
        chart.load({
            unload: true,
            json: JSON.parse(js),
            keys: {
                x: 'x',
                value:  JSON.parse(groups)
            }
        });
        if (hasToGroup) {
            chart.groups([JSON.parse(groups)]);
        }
        component.x = datas.x;
}

function refreshSimple(chart, datas) {
    var labels = datas.keys;
            var js = datas.jsonValues;

            chart.load({
                unload: true,
                json: JSON.parse(js),
                keys: {
                    value:  labels
                }
            });
}

function refreshSimpleBar(chart, datas, component) {
    var labels = datas.keys;
    console.log('labels= ', labels);
            var js = datas.jsonValues;

            chart.load({
                unload: true,
                json: JSON.parse(js),
                keys: {
                    x: 'key',
                    value:  JSON.parse(labels)
                }
            });
     component.x = datas.x;
}

function SimpleSerieC3(id, divId, childs, dataset, parameters, opts, type) {
    Component.call(this, id, divId, childs, dataset, parameters, opts);
    this.type = type;
    var _instance = this;
    this.create = function() {
        switch(this.type) {
            case 'pie':
            case 'donut':
                return generateChart(_instance);
                break;
            case 'line':
            case 'bar':
                return generateBar(_instance);
                break;
            case 'stackedbar':
            case 'multibar':
                return generateMultiBar(_instance);
                break;
        }
    }

    this.refresh = function(dataset) {
        var __ret;
        switch(this.type) {
            case 'pie':
            case 'donut':
                __ret = parseDataset(dataset);
                refreshSimple(this.chart, __ret);
                break;
            case 'line':
            case 'bar':
                __ret = parseBarDataset(dataset);
               refreshSimpleBar(this.chart, __ret, _instance);
                break;
            case 'stackedbar':
                __ret = parseMultiDataset(dataset);
                refreshMultiple(this.chart, __ret, _instance, true);
                break;
            case 'multibar':
                __ret = parseMultiDataset(dataset);
                refreshMultiple(this.chart, __ret, _instance, false);
                break;
        }

    }

    this.chart = this.create();
}

SimpleSerieC3.prototype = Object.create(Component.prototype);
SimpleSerieC3.prototype.constructor = SimpleSerieC3;

function C3Donut(id, divId, childs, dataset, parameters, opts) {
    SimpleSerieC3.call(this, id, divId, childs, dataset, parameters, opts, 'donut');
}

function C3Pie(id, divId, childs, dataset, parameters, opts) {
    SimpleSerieC3.call(this, id, divId, childs, dataset, parameters, opts, 'pie');
}

function C3Bar(id, divId, childs, dataset, parameters, opts) {
    SimpleSerieC3.call(this, id, divId, childs, dataset, parameters, opts, 'bar');
}

function C3Line(id, divId, childs, dataset, parameters, opts) {
    SimpleSerieC3.call(this, id, divId, childs, dataset, parameters, opts, 'line');
}

function C3StackedBar(id, divId, childs, dataset, parameters, opts) {
    SimpleSerieC3.call(this, id, divId, childs, dataset, parameters, opts, 'stackedbar');
}

function C3MultiBar(id, divId, childs, dataset, parameters, opts) {
    SimpleSerieC3.call(this, id, divId, childs, dataset, parameters, opts, 'multibar');
}

SimpleSerieC3.prototype.selected = null;
SimpleSerieC3.prototype.x = null;

SimpleSerieC3.prototype.setValue = function(value) {
    this.selected = value;
}
SimpleSerieC3.prototype.selectedValue = function(event) {
    return this.selected;
}

C3Donut.prototype = Object.create(SimpleSerieC3.prototype);
C3Donut.prototype.constructor = C3Donut;

C3Pie.prototype = Object.create(SimpleSerieC3.prototype);
C3Pie.prototype.constructor = C3Pie;

C3Bar.prototype = Object.create(SimpleSerieC3.prototype);
C3Bar.prototype.constructor = C3Bar;

C3Line.prototype = Object.create(SimpleSerieC3.prototype);
C3Line.prototype.constructor = C3Line;

C3StackedBar.prototype = Object.create(SimpleSerieC3.prototype);
C3StackedBar.prototype.constructor = C3StackedBar;

C3MultiBar.prototype = Object.create(SimpleSerieC3.prototype);
C3MultiBar.prototype.constructor = C3MultiBar;

