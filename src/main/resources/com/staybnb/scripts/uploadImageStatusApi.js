var callback = arguments[arguments.length - 1];
var slug = arguments[0];
var fileBase64 = arguments[1];
var fileName = arguments[2];
var mimeType = arguments[3];
var attachFile = arguments[4];

var token = null;
try {
  token = window.localStorage ? window.localStorage.getItem('staybnb_token') : null;
} catch (e) {
  token = null;
}

function base64ToUint8Array(base64) {
  var binaryString = atob(base64);
  var len = binaryString.length;
  var bytes = new Uint8Array(len);
  for (var i = 0; i < len; i++) {
    bytes[i] = binaryString.charCodeAt(i);
  }
  return bytes;
}

var headers = {};
if (token) {
  headers['Authorization'] = 'Bearer ' + token;
}

var form = new FormData();
if (attachFile && fileBase64 && fileName) {
  var bytes = base64ToUint8Array(fileBase64);
  var blob = new Blob([bytes], { type: mimeType || 'application/octet-stream' });
  var file = new File([blob], fileName, { type: mimeType || 'application/octet-stream' });
  form.append('image', file);
}

fetch('/api/t/' + slug + '/upload', {
  method: 'POST',
  headers: headers,
  body: form
})
  .then(function (res) {
    callback(res.status);
  })
  .catch(function () {
    callback(null);
  });
