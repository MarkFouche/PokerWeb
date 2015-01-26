function updateSingleGameLobby() {
  $.ajax({
      url : "/single_lobby_update",
      type : "GET",
      data : {hostname: $(".hostNameVar").text()},
      success: function() {location.reload();}
  });
}
updateSingleGameLobby();
