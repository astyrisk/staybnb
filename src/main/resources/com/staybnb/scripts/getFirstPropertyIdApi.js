var callback = arguments[arguments.length - 1];
var slug = arguments[0];

fetch('/api/t/' + slug + '/properties?limit=1', {
  method: 'GET',
  headers: { 'accept': '*/*' }
})
  .then(function(res) { return res.json(); })
  .then(function(data) {
    var items = Array.isArray(data) ? data
              : Array.isArray(data.properties) ? data.properties
              : Array.isArray(data.content)    ? data.content
              : Array.isArray(data.data)       ? data.data
              : [];
    callback(items.length > 0 ? String(items[0].id) : null);
  })
  .catch(function() {
    callback(null);
  });
