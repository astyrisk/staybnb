var callback = arguments[arguments.length - 1];
var slug = arguments[0];

fetch('/api/t/' + slug + '/categories', {
  method: 'GET',
  headers: { 'Accept': 'application/json' }
})
  .then(function (res) { return res.json(); })
  .then(function (data) {
    var ok = true;
    if (!Array.isArray(data) || data.length === 0) {
      ok = false;
    } else {
      for (var i = 0; i < data.length; i++) {
        var c = data[i] || {};
        var hasId = c.hasOwnProperty('id');
        var hasName = c.hasOwnProperty('name');
        var hasIcon = c.hasOwnProperty('icon');
        if (!hasId || !hasName || !hasIcon) {
          ok = false;
          break;
        }
      }
    }
    callback(ok);
  })
  .catch(function () {
    callback(false);
  });

