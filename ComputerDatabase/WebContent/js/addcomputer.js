var introduced = true;
var discontinued = true;
var name = false;
var dateOrder = true;

var nameInput = $("#computerName");
var introducedInput = $("#introduced");
var discontinuedInput = $("#discontinued");

nameInput.on('keyup', function() {
	if(nameInput.val().length === 0){
		nameInput.parent().removeClass("has-success");
		nameInput.parent().addClass("has-error");
		name = false;
	}
	else {
		nameInput.parent().removeClass("has-error");
		nameInput.parent().addClass("has-success");
		name = true;
	}
});


introducedInput.on('keyup', function() {
	if(isValideDate(introducedInput.val())){
		introducedInput.parent().removeClass("has-error");
		introducedInput.parent().addClass("has-success");
		introduced = true;
	}
	else {
		introducedInput.parent().removeClass("has-success");
		introducedInput.parent().addClass("has-error");
		introduced = false;
	}
});

discontinuedInput.on('keyup', function() {
	if(isValideDate(discontinuedInput.val())){
		discontinuedInput.parent().removeClass("has-error");
		discontinuedInput.parent().addClass("has-success");
		discontinued = true;
	}
	else {
		discontinuedInput.parent().removeClass("has-success");
		discontinuedInput.parent().addClass("has-error");
		discontinued = false;
	}
});

$("form").on("submit", function(e){
	if (!introduced || !discontinued || !name || !date) {
		e.preventDefault();
	}
});

function showError(){
	var s = "";
	if(!name){
		s += "Name can't be empty.\n";
	}
	if(!introduced){
		s += "Introducing date format is invalid.\n";
	}
	if(!discontinued){
		s += "Discontinuing date format is invalid.\n";
	}
	if(!dateOrder){
		s += "Introducing date must be befor discontinuing date."
	}
	alert(s);
}

function introducedBeforeDiscontinued(){
	if(!(introducedInput.val().length === 0 || discontinuedInput.val().length === 0)){
		if(Date.parse(introducedInput.val()) < Date.parse(discontinuedInput.val())){
			dateOrder = true;
		}
		else {
			introducedInput.parent().removeClass("has-success");
			introducedInput.parent().addClass("has-error");
			discontinuedInput.parent().removeClass("has-success");
			discontinuedInput.parent().addClass("has-error");
			dateOrder = false;
		}
	}
}

function isValideDate(date){
	var timestamp=Date.parse(date)

	if (isNaN(timestamp)==false || date.length === 0){
	    return true;
	}
	return false;
}