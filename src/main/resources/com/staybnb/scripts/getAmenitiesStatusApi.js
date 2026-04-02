var callback = arguments[arguments.length - 1];
var slug = arguments[0];

fetch('/api/t/' + slug + '/amenities', { method: 'GET' })
  .then(function (res) { callback(res.status); })
  .catch(function () { callback(null); });
