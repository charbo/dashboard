function generateChart(parent) {
    _instance = parent;
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
        }
    });
    return chart;
}

function generateBar(parent) {
    _instance = parent;
    console.log('create c3bar ' + parent.divId + " " + parent.id);
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
        }
    });
    return chart;
}

function generateMultiBar(parent) {
    _instance = parent;
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
                _instance.setValue(d.id);
            }
        },
                     axis: {
                             x: {
                                 type: 'category' // this needed to load string x value
                             }
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

    console.log(labels);
    console.log(js);
    return {keys: labels, jsonValues: js};
}

function parseMultiDataset(datasets) {
    var labels = new Array();
    var all = '[';
    var groups = '[';
    for (var d = 0; d < datasets.length; d++) {
        var dataset = datasets[d];
        var line = '{"x":"' + dataset.label + '", ';
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

    console.log("---MULTI---");
    console.log(groups);
    console.log(all);
    return {groups: groups, jsonValues: all};
}

function refreshMultiple(chart, datas) {
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
        chart.groups([JSON.parse(groups)]);
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

function SimpleSerieC3(id, divId, childs, dataset, parameters, opts, type) {
    Component.call(this, id, divId, childs, dataset, parameters, opts);
    this.type = type;
    _instance = this;
    this.create = function() {
        switch(this.type) {
            case 'pie':
            case 'donut':
            case 'bar':
                return generateChart(_instance);
                break;
            case 'stackedbar':
                return generateMultiBar(_instance);
                break;
        }
    }

    this.refresh = function(dataset) {
        var __ret;
        switch(this.type) {
            case 'pie':
            case 'donut':
            case 'bar':
                __ret = parseDataset(dataset);
                refreshSimple(this.chart, __ret);
                break;
            case 'stackedbar':
                __ret = parseMultiDataset(dataset);
                refreshMultiple(this.chart, __ret);
                break;
        }

    }

    this.chart = this.create();
}

SimpleSerieC3.prototype = Object.create(SimpleSerieC3.prototype);
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

function C3MultiBar(id, divId, childs, dataset, parameters, opts) {
    SimpleSerieC3.call(this, id, divId, childs, dataset, parameters, opts, 'stackedbar');
}

SimpleSerieC3.prototype.selected = null;

SimpleSerieC3.prototype.setValue = function(value) {
    this.selected = value;
}
SimpleSerieC3.prototype.selectedValue = function(event) {
    return _instance.selected;
}

C3Donut.prototype = Object.create(SimpleSerieC3.prototype);
C3Donut.prototype.constructor = C3Donut;

C3Pie.prototype = Object.create(SimpleSerieC3.prototype);
C3Pie.prototype.constructor = C3Pie;

C3Bar.prototype = Object.create(SimpleSerieC3.prototype);
C3Bar.prototype.constructor = C3Bar;

C3MultiBar.prototype = Object.create(SimpleSerieC3.prototype);
C3MultiBar.prototype.constructor = C3MultiBar;

