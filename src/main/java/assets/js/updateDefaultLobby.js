function updateDefaultLobby() {
  $.ajax({
      url : "/lobby_update",
      type : "GET",
      success: function() {location.reload();}
  });
}
updateDefaultLobby();
