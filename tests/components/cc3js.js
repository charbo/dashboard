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

function parseDataset(dataset) {
    var labels = new Array();
    var resp = dataset.datas;
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

function SimpleSerieC3(id, divId, childs, dataset, parameters, opts, type) {
    Component.call(this, id, divId, childs, dataset, parameters, opts);
    this.type = type;
    _instance = this;
    this.create = function() {
        switch(this.type) {
            case 'pie':
            case 'donut':
                return generateChart(_instance);
                break;
            case 'bar':
                return generateBar(_instance);
                break;
        }
    }

    this.refresh = function(dataset) {
        var __ret;
        switch(this.type) {
            case 'pie':
            case 'donut':
                __ret = parseDataset(dataset);
                break;
            case 'bar':
                __ret = null;
                break;
        }
        var labels = __ret.keys;
        var js = __ret.jsonValues;

        this.chart.load({
            unload: true,
            json: JSON.parse(js),
            keys: {
                value:  labels
            }
        });
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

