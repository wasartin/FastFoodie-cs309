/**
 * 
 */
function executeSearch(){
	valueCheck = true;
	var inputFood = nameCheck(document.forms["vForm"]["userInput"].value);

	document.getElementById("userInput").appendChild();
}

function DisplayResults(){
}

function nameCheck(name){
	var i;
	var regex = /^[a-zA-Z0-9]+$/i;
	if(name != null && name.match(regex)){
		return true;
	}
	valueCheck = false;
	return false;
}