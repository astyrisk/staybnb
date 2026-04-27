var nodes = Array.from(arguments[0].childNodes)
  .filter(function(n) { return n.nodeType === 3 && n.textContent.trim() !== ''; });
return nodes[arguments[1]] ? nodes[arguments[1]].textContent.trim() : '';
