function updateDefaultLobby() {
  $.ajax({
      url : "/lobby_update",
      type : "GET",
  });
}
updateDefaultLobby();
