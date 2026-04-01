var callback = arguments[arguments.length - 1];
var slug = arguments[0];
var propertyId = arguments[1];
var payload = arguments[2];

var token = null;
try {
  token = window.localStorage ? window.localStorage.getItem('staybnb_token') : null;
} catch (e) {
  token = null;
}

var headers = {
  'Content-Type': 'application/json'
};
if (token) {
  headers['Authorization'] = 'Bearer ' + token;
}

fetch('/api/t/' + slug + '/properties/' + propertyId, {
  method: 'PUT',
  headers: headers,
  body: JSON.stringify(payload || {})
})
  .then(function (res) {
    return res.text().then(function (text) {
      callback(text);
    });
  })
  .catch(function () {
    callback(null);
  });
