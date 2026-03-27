var callback = arguments[arguments.length - 1];
var slug = arguments[0];

fetch('/api/t/' + slug + '/users/me/host', { method: 'PUT' })
  .then(function (res) {
    callback(res.status);
  })
  .catch(function (err) {
    callback(null);
  });

