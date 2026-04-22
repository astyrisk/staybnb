var callback = arguments[arguments.length - 1];
var slug = arguments[0];

var token = null;
try {
  token = window.localStorage ? window.localStorage.getItem('staybnb_token') : null;
} catch (e) {}

var headers = { 'Content-Type': 'application/json' };
if (token) headers['Authorization'] = 'Bearer ' + token;

fetch('/api/t/' + slug + '/wishlists', {
  method: 'GET',
  headers: headers
})
  .then(function(res) { return res.json(); })
  .then(function(data) {
    var items = Array.isArray(data) ? data
              : Array.isArray(data.properties) ? data.properties
              : Array.isArray(data.content)    ? data.content
              : Array.isArray(data.items)      ? data.items
              : Array.isArray(data.data)       ? data.data
              : [];

    var ids = [];
    for (var i = 0; i < items.length; i++) {
      var item = items[i];
      var id = null;
      if (typeof item === 'number' || typeof item === 'string') {
        id = item;
      } else if (item.propertyId != null) {
        id = item.propertyId;
      } else if (item.property && item.property.id != null) {
        id = item.property.id;
      } else if (item.id != null) {
        id = item.id;
      }
      if (id != null) ids.push(String(id));
    }
    callback(ids);
  })
  .catch(function() {
    callback([]);
  });
