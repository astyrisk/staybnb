var el = arguments[0];
var overflowX = window.getComputedStyle(el).overflowX.toLowerCase();
return el.scrollWidth > el.clientWidth && (overflowX === 'auto' || overflowX === 'scroll');
