function bodyParameter(api, schemas, parameters) {
	const schema = api.requestBody.content['application/json'].schema;
	if (schema.$ref) {
		let ref = schema.$ref;
		let entity = ref.split('/')[3];
		let properties = schemas[entity].properties;
		let required = schemas[entity].required;
		if (!required) {
			required = [];
		}
		for (let entry of Object.entries(properties)) {
			parameters.push({
				isBodyParameter: true,
				camelCaseName: entry[0],
				name: entry[0],
				paramType: entry[1].type,
				required: required.indexOf(entry[0]) !== -1
			});
		}
	} else if (schema.type === 'array') {
		parameters.push({ $body: true });
	} else {
		if (schema.properties) {
			for (let entry of Object.entries(schema.properties)) {
				parameters.push({
					isFormParameter: true,
					camelCaseName: entry[0],
					name: entry[0]
				});
			}
		}
	}
}

function otherParameters(api, parameters) {
	api.parameters.forEach((entry) => {
		parameters.push({
			...entry,
			isPathParameter: entry.in === 'path',
			isBodyParameter: entry.in === 'body',
			$body: entry.schema.type === 'array',
			isQueryParameter: entry.in === 'query',
			isQueryArrayParameter: entry.schema.type === 'array',
			isHeaderParameter: entry.in === 'header',
			isFormParameter: entry.in === 'formData',
			camelCaseName: entry.name
		});
	});
}

function parameters(api, schemas) {
	const parameters = [];
	if (api.requestBody) {
		bodyParameter(api, schemas, parameters);
	}
	if (api.parameters) {
		otherParameters(api, parameters);
	}
	return parameters;
}

module.exports = function (opts) {
	let swagger = opts.swagger;
	let data = {
		description: swagger.info.description,
		domain:
      swagger.schemes && swagger.schemes.length > 0 && swagger.host && swagger.basePath
      	? swagger.schemes[0] + '://' + swagger.host + swagger.basePath.replace(/\/+$/g, '')
      	: '',
		methods: [],
		definitions: []
	};
	const schemas = swagger.components.schemas;
	try {
		for (let pathEntry of Object.entries(swagger.paths)) {
			const path = pathEntry[0];
			for (let apiEntry of Object.entries(pathEntry[1])) {
				const methodType = apiEntry[0];
				const api = apiEntry[1];
				const headers = [];
				let method = {
					path,
					methodName: api.operationId,
					method: methodType,
					summary: api.description || api.summary,
					tags: api.tags,
					parameters: parameters(api, schemas),
					headers
				};
				data.methods.push(method);
			}
		}
		console.log('finish');
		return data;
	} catch (e) {
		console.error(e);
	}
};
