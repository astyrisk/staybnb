var callback = arguments[arguments.length - 1];
var slug = arguments[0];

fetch('/api/t/' + slug + '/amenities', {
  method: 'GET',
  headers: { 'Accept': 'application/json' }
})
  .then(function (res) { return res.json(); })
  .then(function (data) {
    var list = Array.isArray(data) ? data : (data && Array.isArray(data.amenities) ? data.amenities : null);
    if (!list || list.length === 0) {
      callback(false);
      return;
    }
    for (var i = 0; i < list.length; i++) {
      var a = list[i] || {};
      if (!a.hasOwnProperty('id') || !a.hasOwnProperty('name') || !a.hasOwnProperty('icon')) {
        callback(false);
        return;
      }
    }
    callback(true);
  })
  .catch(function () {
    callback(false);
  });
