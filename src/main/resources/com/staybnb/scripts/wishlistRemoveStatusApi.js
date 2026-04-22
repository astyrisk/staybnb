var callback = arguments[arguments.length - 1];
var slug = arguments[0];
var propertyId = arguments[1];

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

fetch('/api/t/' + slug + '/wishlists/' + propertyId, {
  method: 'DELETE',
  headers: headers
})
  .then(function (res) {
    callback(res.status);
  })
  .catch(function () {
    callback(null);
  });
