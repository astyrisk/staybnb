var callback = arguments[arguments.length - 1];
var slug = arguments[0];
var payloadJson = arguments[1];

var token = null;
try {
  token = window.localStorage ? window.localStorage.getItem('staybnb_token') : null;
} catch (e) {
  token = null;
}

if (!token) {
  callback(null);
} else {
  fetch('/api/t/' + slug + '/users/me/host', {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json',
      'Authorization': 'Bearer ' + token
    },
    body: payloadJson
  })
    .then(function (res) {
      return res.text().then(function (text) {
        callback(text);
      });
    })
    .catch(function (err) {
      callback(null);
    });
}

