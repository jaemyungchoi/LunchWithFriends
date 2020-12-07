window.onscroll = function() {myFunction()};

var header = document.getElementById("myHeader");
var sticky = header.offsetTop;

function myFunction() {
  if (window.pageYOffset >= 1) {
    header.classList.add("sticky");
  } else {
    header.classList.remove("sticky");
  }
}
function initMap() {
  gMap = new google.maps.Map(document.getElementById('map'));

  navigator.geolocation.getCurrentPosition(function(position) {
    // Center on user's current location if geolocation prompt allowed
    var initialLocation = new google.maps.LatLng(position.coords.latitude, position.coords.longitude);
    gMap.setCenter(initialLocation);
    gMap.setZoom(13);
  }, function(positionError) {
    // User denied geolocation prompt - default to Chicago
    gMap.setCenter(new google.maps.LatLng(39.8097343, -98.5556199));
    gMap.setZoom(5);
  });
} 