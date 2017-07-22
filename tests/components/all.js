    function Parameter(name, provider) {
        this.name = name;
        this.provider = provider;
    }
    
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
        
        this.refresh = function(resp) {
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
        this.create = function(opts) {
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
        
        this.refresh = function(resp) {
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
    
    
    function CSelect(id, divId, childs) {
        this.id = id;
        this.create = function() {
            document.getElementById(divId).innerHTML = '<select id="' + id + '">"<option> option 1</option><option> option 2</option></select>';
        }
        this.childs = childs;
        this.chart = this.create();
    }
    
    
    function CDynamicSelect(id, divId, childs, dataset) {
        var _this = this;
        this.id = id;
        this.divId = divId;
        this.create = function() {
            document.getElementById(_this.divId).innerHTML = '<select id="' + _this.id + '" class="form-control"></select>';
            var params = '{"name": "' + this.dataset + '","parameters": []}';
            $.ajax({
               type: "POST",
               url: "http://localhost:9090/datas",
               contentType: 'application/json; charset=utf-8',
               data: params,
               success: function(resp){
                   // we have the response
                   _this.refresh(resp);
                },
               error: function(e){
                 console.log('unable to retreive datas ' + e);
               }
             });
            
        }
        this.childs = childs;
        this.dataset = dataset;
        this.chart = this.create();
        this.refresh = function(resp) {
            
            var html = '<option>sélectionner</option>';
            
            for (index in resp) {
                html += '<option>';
                html += resp[index].key;
                html += '</option>';
            }
            
            console.log('create CDynamicSelect ' + _this.divId + ' ' + html);
            document.getElementById(_this.id).innerHTML = html;
        }
        
        this.selectedValue = function(event) {
            var e =  document.getElementById(this.id);
            return e.options[e.selectedIndex].value;
        }
    }
    
    
    
    