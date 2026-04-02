return Array.from(arguments[0].childNodes)
  .filter(function(n) { return n.nodeType === 3; })
  .map(function(n) { return n.textContent; })
  .join('')
  .trim();
