// ==UserScript==
// @namespace     http://javascript.about.com
// @author        Stephen Chapman
// @name          Exit Block Blocker
// @description   Blocks onbeforeunload
// @include       *
// ==/UserScript==

/*var th = document.getElementsByTagName('body')[0];
var s = document.createElement('script');
s.setAttribute('type','text/javascript');
s.innerHTML = "window.onbeforeunload = function() {}";
th.appendChild(s);*/

$(window).bind('beforeunload', function(){});

/*$(document).ready(function() {
    window.onbeforeunload = null;
});*/

/*window.onbeforeunload = null;*/
