var callback = arguments[arguments.length - 1];
var slug = arguments[0];

var token = null;
try {
  token = window.localStorage ? window.localStorage.getItem('staybnb_token') : null;
} catch (e) {
  token = null;
}

var headers = {};
if (token) {
  headers['Authorization'] = 'Bearer ' + token;
}

fetch('/api/t/' + slug + '/hosting/properties', {
  method: 'GET',
  headers: headers
})
  .then(function (res) {
    callback(res.status);
  })
  .catch(function (err) {
    callback(null);
  });
