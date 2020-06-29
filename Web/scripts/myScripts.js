// Handles user input from dropdown boxes so that the correct method is run.
function outputController(query) {

	var e = document.getElementById("format-type");
	var f = document.getElementById("format-type2");

	var format = e.options[e.selectedIndex].value;
	var format2 = f.options[f.selectedIndex].value;

	if (format == "json" && query == "show") {
		allFilmsJsonTable();
	}
	if (format == "text" && query == "show") {
		allFilmsText();
	}
	
	if (format == "xml" && query == "show") {
		allFilmsXMLTable();
	}
	if (format2 == "json" && query == "search") {
		searchFilmsJsonTable();
	}
	if (format2 == "xml" && query == "search") {
		searchFilmsXMLTable();
	}
	if (query == "search") {
		searchFilmsText();
	}

	else {
		return;
	}

}

function allFilmsXMLTable() {
	// Clears table for new entry.
	$('#table>tbody').empty();
	$.ajax({
		url : "getAllFilms?format=xml",
		dataType : "xml",

		success : function(data) {
			$(data).find("film").each(
					function() {
						var genre = [], cast = [];

						$("table").append(
								"<tr>" + "<td>" + $(this).find("title").text()
										+ "</td>" +

										"<td>" + $(this).find("id").text()
										+ "</td>" +

										"<td>" + $(this).find("review")

										.text() + "</td>" + "<td>"
										+ $(this).find("stars")

										.text() + "</td>" + "<td>"
										+ $(this).find("director")

										.text() + "</td>" + "<td>"
										+ $(this).find("year")

										.text() + "</td>" + "</tr>");
					});
		}
	});
}

function allFilmsJsonTable() {
	// Clears table for new entry.
	$('#table>tbody').empty();
	$.getJSON("getAllFilms", function(data) {
		var filmInfo = '';
		$.each(data, function(key, value) {
			filmInfo += '<tr>';
			filmInfo += '<td>' + value.title + '</td>';
			filmInfo += '<td>' + value.id + '</td>';
			filmInfo += '<td>' + value.review + '</td>';
			filmInfo += '<td>' + value.stars + '</td>';
			filmInfo += '<td>' + value.director + '</td>';
			filmInfo += '<td>' + value.year + '</td>';
			filmInfo += '<tr>';
		});
		$('#table').append(filmInfo);
	});

}

function searchFilmsXMLTable() {
	// Clears table for new entry
	$('#table>tbody').empty();

	var searchString = document.getElementById("searchString").value;

	// Stops the function printing all films if nothing is entered into the text
	// box.
	 if (searchString === "") {
	 return;
	 }
	$.ajax({
		url : "getFilmByTitle?format=xml&filmname=" + searchString,
		dataType : "xml",

		success : function(data) {
			$(data).find("film").each(
					function() {
						var genre = [], cast = [];

						$("table").append(
								"<tr>" + "<td>" + $(this).find("title").text()
										+ "</td>" +

										"<td>" + $(this).find("id").text()
										+ "</td>" +

										"<td>" + $(this).find("review")

										.text() + "</td>" + "<td>"
										+ $(this).find("stars")

										.text() + "</td>" + "<td>"
										+ $(this).find("director")

										.text() + "</td>" + "<td>"
										+ $(this).find("year")

										.text() + "</td>" + "</tr>");
					});
		}
	});
}

function searchFilmsJsonTable() {
	// Clears table for new entry.
	$('#table>tbody').empty();

	var searchString = document.getElementById("searchString").value;

	// Stops the function printing all films if nothing is entered into the text
	// box.
	 if (searchString === "") {
	 return;
	 }
	$.getJSON("getFilmByTitle?filmname=" + searchString, function(data) {
		var filmInfo = '';

		$.each(data, function(key, value) {
			filmInfo += '<tr>';
			filmInfo += '<td>' + value.title + '</td>';
			filmInfo += '<td>' + value.id + '</td>';
			filmInfo += '<td>' + value.review + '</td>';
			filmInfo += '<td>' + value.stars + '</td>';
			filmInfo += '<td>' + value.director + '</td>';
			filmInfo += '<td>' + value.year + '</td>';
			filmInfo += '<tr>';
		});
		$('#table').append(filmInfo);
	});

}

function deleteFilm() {

	var id = document.getElementById("searchString3").value;

	var request = new XMLHttpRequest();
	request.open("GET", "deleteFilm?filmid=" + id);
	request.send();

	alert("Your Film has been deleted.");

}

function updateID() {

	var currentID = document.getElementById("searchString4").value;
	var newID = document.getElementById("searchString5").value;

	var request = new XMLHttpRequest();
	request.open("GET", "updateID?currentFilmID=" + currentID + "&newFilmID="
			+ newID);
	request.send();

	alert("Your Film has been updated.");

}

function addFilm() {

	var title = document.getElementById("title").value;
	var id = document.getElementById("id").value;
	var review = document.getElementById("review").value;
	var stars = document.getElementById("stars").value;
	var director = document.getElementById("director").value;
	var year = document.getElementById("year").value;

	var request = new XMLHttpRequest();
	request.open("GET", "insertFilm?Title=" + title + "&ID=" + id + "&Review="
			+ review + "&Stars=" + stars + "&Director=" + director + "&Year="
			+ year);
	request.send();

	alert("Your Film has been added to the database.");

}

function allFilmsText() {

	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			document.getElementById("table").innerHTML = this.responseText;
		}
	};
	xhttp.open("POST", "getAllFilms?format=text", true);
	xhttp.send();
}

function searchFilmsText() {
	var searchString = document.getElementById("searchString").value;
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			document.getElementById("table").innerHTML = this.responseText;
		}
	};
	xhttp.open("POST", "getFilmByTitle?format=text&filmname=" + searchString, true);
	xhttp.send();
}
