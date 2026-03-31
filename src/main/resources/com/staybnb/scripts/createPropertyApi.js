var callback = arguments[arguments.length - 1];
var slug = arguments[0];
var payload = arguments[1];

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

fetch('/api/t/' + slug + '/properties', {
  method: 'POST',
  headers: headers,
  body: JSON.stringify(payload || {})
})
  .then(function (res) {
    return res.text().then(function (text) {
      callback(JSON.stringify({
        status: res.status,
        statusText: res.statusText,
        body: text
      }));
    });
  })
  .catch(function () {
    callback(null);
  });
