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
    this.refresh = function(dataset) {
        var resp = dataset[0].datas;
        var html = '<option>sélectionner</option>';

        for (index in resp) {
            html += '<option>';
            html += resp[index].key;
            html += '</option>';
        }

        document.getElementById(_this.id).innerHTML = html;
    }

    this.selectedValue = function(event) {
        var e =  document.getElementById(this.id);
        return e.options[e.selectedIndex].value;
    }
}

