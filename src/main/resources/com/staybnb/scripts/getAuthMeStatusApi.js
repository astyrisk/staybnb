var callback = arguments[arguments.length - 1];
var slug = arguments[0];

fetch('/api/t/' + slug + '/auth/me')
  .then(function (res) {
    callback(res.status);
  })
  .catch(function (err) {
    callback(null);
  });
