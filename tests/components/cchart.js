function CPie(id, divId, childs, dataset, parameters, opts) {
    this.id = id;
    this.divId = divId;
    this.childs = childs;
    this.dataset = dataset;
    this.parameters = parameters;
    this.opts = opts;
    this.create = function() {
        console.log('create pie ' + this.divId);
        var html = '<canvas id="' + this.id + '"/>';
        document.getElementById(this.divId).innerHTML = html;
        var config = {
            type: 'pie',
            data : {
                datasets: [{
                    data: [],
                    backgroundColor: this.opts['colors']
                }],
                labels: [

                ]
            },
            options: {
                responsive: true
            }
        };
        var ctx = document.getElementById(this.id).getContext("2d");
        return new Chart(ctx, config);
    };

    this.refresh = function(dataset) {
        var resp = dataset[0].datas;
        var values = new Array();
        var labels = new Array();
        for (index in resp) {
            values[values.length] = resp[index].value;
            labels[labels.length] = resp[index].key;
        }
        this.chart.data.datasets[0].data = values;
        this.chart.data.labels = labels;
        this.chart.update();

    }

    this.chart = this.create();

    this.selectedValue = function(event) {
        var value;
        var activePoints = this.chart.getElementsAtEvent(event);
        var firstPoint = activePoints[0];
        if (firstPoint != undefined) {
            var label = this.chart.data.labels[firstPoint._index];
            value = this.chart.data.labels[firstPoint._index];
            console.log('click pie!' + value);
        }
        return value;
    }
}


function CBar(id, divId, childs, dataset, parameters, opts) {
    this.id = id;
    this.divId = divId;
    this.childs = childs;
    this.dataset = dataset;
    this.parameters = parameters;
    this.opts = opts;
    this.create = function() {
        console.log('create bar ' + this.divId + " " + this.id);
        var html = '<canvas id="' + this.id + '"/>';
        document.getElementById(this.divId).innerHTML = html;
        var config = {
            type: 'bar',
            data : {
                datasets: [{
                    data: [],
                    backgroundColor: this.opts['colors']
                }],
                labels: [

                ]
            },
        };
        var ctx = document.getElementById(this.id).getContext("2d");
        return new Chart(ctx, config);
    };

    this.refresh = function(dataset) {
        var resp = dataset[0].datas;
        var values = new Array();
        var labels = new Array();
        for (index in resp) {
            values[values.length] = resp[index].value;
            labels[labels.length] = resp[index].key;
        }
        this.chart.data.datasets[0].data = values;
        this.chart.data.labels = labels;
        this.chart.update();

    }

    this.chart = this.create();
    this.chart.options.responsive = true;
    this.chart.options.scales.yAxes[0].ticks.beginAtZero = true;

    this.selectedValue = function(event) {
        var value;
        var activePoints = this.chart.getElementsAtEvent(event);
        var firstPoint = activePoints[0];
        if (firstPoint != undefined) {
            var label = this.chart.data.labels[firstPoint._index];
            value = this.chart.data.labels[firstPoint._index];
            console.log('click pie!' + value);
        }
        return value;
    }
}

