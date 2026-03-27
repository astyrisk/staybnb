var callback = arguments[arguments.length - 1];
var slug = arguments[0];
var userId = arguments[1];

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

fetch('/api/t/' + slug + '/users/' + userId, { headers: headers })
  .then(function (res) {
    return res.text().then(function (text) {
      callback(text);
    });
  })
  .catch(function (err) {
    callback(null);
  });
