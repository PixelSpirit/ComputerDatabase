var introduced = true;
var discontinued = true;
var name = false;
var dateOrder = true;

var nameInput = $("#computerName");
var introducedInput = $("#introduced");
var discontinuedInput = $("#discontinued");


/* Entries Validation */

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
		introducedBeforeDiscontinued();
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
		introducedBeforeDiscontinued();
	}
	else {
		discontinuedInput.parent().removeClass("has-success");
		discontinuedInput.parent().addClass("has-error");
		discontinued = false;
	}
});


/* Submit */

$("form").on("submit", function(e){
	if (!introduced || !discontinued || !name || !dateOrder) {
		e.preventDefault();
	}
});

/* Util functions */

function introducedBeforeDiscontinued(){
	if(!(introducedInput.val().length === 0 || discontinuedInput.val().length === 0)){
		if(Date.parse(introducedInput.val()) < Date.parse(discontinuedInput.val())){
			dateOrder = true;
			introducedInput.parent().removeClass("has-error");
			introducedInput.parent().addClass("has-success");
			discontinuedInput.parent().removeClass("has-error");
			discontinuedInput.parent().addClass("has-success");
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
	return (!date || /^[0-9]{4}-(0[1-9]|1[012])-([0-2][0-9]|3[0-1])$/.test(date));
}
