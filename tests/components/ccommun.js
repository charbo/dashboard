function Parameter(name, provider) {
    this.name = name;
    this.provider = provider;
}

function Component(id, divId, childs, dataset, parameters, opts) {
    this.id = id;
    this.divId = divId;
    this.childs = childs;
    this.dataset = dataset;
    this.parameters = parameters;
    this.opts = opts;
}