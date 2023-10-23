<%

// ---------------------------------------------- general
boolean users = false;
boolean lpp = false;
boolean suppliers = false;
boolean galas = false;
boolean galas_edit = false;
boolean history = false;

// ---------------------------------------------- item
boolean av_pr = false;
boolean total_stop_sale = false;
boolean activate_photos = false;
boolean photos = false;
boolean photos_edit = false;
boolean descriptions = false;
boolean descriptions_edit = false;
boolean edit_item = false;
boolean edit_item_edit = false;

// ---------------------------------------------- property
boolean contracts_property = false;
boolean rooming_property = false;
boolean move_property = false;
boolean feature_property = false;
boolean activate_property = false;

// ---------------------------------------------- contract
boolean generate = false;
boolean generate_sql_view = false;
boolean activate_contract = false;
boolean delete_contract = false;
boolean contract_edit = false;
boolean offers = false;

// ---------------------------------------------- period
boolean period_edit = false;


if (session.getAttribute("type").equals("administrator")) {
	users = true;
	lpp = true;
	suppliers = true;
	galas = false;
	galas_edit = false;
	history = true;
	
	av_pr = true;
	total_stop_sale = true;
	activate_photos = true;
	photos = true;
	photos_edit = true;
	descriptions = true;
	descriptions_edit = true;
	edit_item = true;
	
	contracts_property = true;
	rooming_property = true;
	move_property = true;
	feature_property = true;
	activate_property = true;
	
	generate = true;
	generate_sql_view = true;
	activate_contract = true;
	delete_contract = true;
	contract_edit = true;
	offers = true;
	
	period_edit = true;
}

if (session.getAttribute("type").equals("properties_manager")) {
	users = false;
	lpp = false;
	suppliers = false;
	galas = false;
	galas_edit = false;
	history = false;
	
	av_pr = false;
	total_stop_sale = false;
	activate_photos = true;
	photos = true;
	photos_edit = true;
	descriptions = true;
	descriptions_edit = true;
	edit_item = true;
	
	contracts_property = true;
	rooming_property = false;
	move_property = true;
	feature_property = false;
	activate_property = true;
	
	generate = false;
	generate_sql_view = false;
	activate_contract = false;
	delete_contract = false;
	contract_edit = false;
	offers = false;
	
	period_edit = false;
}

if (session.getAttribute("type").equals("contracts_manager")) {
	users = false;
	lpp = true;
	suppliers = true;
	galas = false;
	galas_edit = false;
	history = false;
	
	av_pr = true;
	total_stop_sale = true;
	activate_photos = false;
	photos = true;
	photos_edit = true;
	descriptions = true;
	descriptions_edit = true;
	edit_item = true;
	
	contracts_property = true;
	rooming_property = true;
	move_property = true;
	feature_property = false;
	activate_property = true;
	
	generate = true;
	generate_sql_view = false;
	activate_contract = true;
	delete_contract = true;
	contract_edit = true;
	offers = true;
	
	period_edit = true;
}

if (session.getAttribute("type").equals("contracts_operator")) {
	users = false;
	lpp = true;
	suppliers = false;
	galas = false;
	galas_edit = false;
	history = false;
	
	av_pr = false;
	total_stop_sale = true;
	activate_photos = false;
	photos = true;
	photos_edit = false;
	descriptions = true;
	descriptions_edit = true;
	edit_item = true;
	
	contracts_property = true;
	rooming_property = true;
	move_property = true;
	feature_property = false;
	activate_property = false;
	
	generate = false;
	generate_sql_view = false;
	activate_contract = false;
	delete_contract = false;
	contract_edit = true;
	offers = true;
	
	period_edit = true;
}

if (session.getAttribute("type").equals("manager")) {
	users = false;
	lpp = false;
	suppliers = true;
	galas = false;
	galas_edit = false;
	history = false;
	
	av_pr = true;
	total_stop_sale = false;
	activate_photos = false;
	photos = true;
	photos_edit = false;
	descriptions = true;
	descriptions_edit = false;
	edit_item = true;
	
	contracts_property = true;
	rooming_property = true;
	move_property = true;
	feature_property = true;
	activate_property = false;
	
	generate = false;
	generate_sql_view = false;
	activate_contract = false;
	delete_contract = false;
	contract_edit = false;
	offers = false;
	
	period_edit = false;
}

if (session.getAttribute("type").equals("sales")) {
	users = false;
	lpp = false;
	suppliers = false;
	galas = false;
	galas_edit = false;
	history = false;
	
	av_pr = true;
	total_stop_sale = false;
	activate_photos = false;
	photos = false;
	photos_edit = false;
	descriptions = false;
	descriptions_edit = false;
	edit_item = false;
	
	contracts_property = false;
	rooming_property = false;
	move_property = false;
	feature_property = false;
	activate_property = false;
	
	generate = false;
	generate_sql_view = false;
	activate_contract = false;
	delete_contract = false;
	contract_edit = false;
	offers = false;
	
	period_edit = false;
}

session.setAttribute("users", String.valueOf(users));
session.setAttribute("lpp", String.valueOf(lpp));
session.setAttribute("suppliers", String.valueOf(suppliers));
session.setAttribute("galas", String.valueOf(galas));
session.setAttribute("galas_edit", String.valueOf(galas_edit));
session.setAttribute("history", String.valueOf(history));

session.setAttribute("av_pr", String.valueOf(av_pr));
session.setAttribute("total_stop_sale", String.valueOf(total_stop_sale));
session.setAttribute("activate_photos", String.valueOf(activate_photos));
session.setAttribute("photos", String.valueOf(photos));
session.setAttribute("photos_edit", String.valueOf(photos_edit));
session.setAttribute("descriptions", String.valueOf(descriptions));
session.setAttribute("descriptions_edit", String.valueOf(descriptions_edit));
session.setAttribute("edit_item", String.valueOf(edit_item));

session.setAttribute("contracts_property", String.valueOf(contracts_property));
session.setAttribute("rooming_property", String.valueOf(rooming_property));
session.setAttribute("move_property", String.valueOf(move_property));
session.setAttribute("feature_property", String.valueOf(feature_property));
session.setAttribute("activate_property", String.valueOf(activate_property));

session.setAttribute("generate", String.valueOf(generate));
session.setAttribute("generate_sql_view", String.valueOf(generate_sql_view));
session.setAttribute("activate_contract", String.valueOf(activate_contract));
session.setAttribute("delete_contract", String.valueOf(delete_contract));
session.setAttribute("contract_edit", String.valueOf(contract_edit));
session.setAttribute("offers", String.valueOf(offers));

session.setAttribute("period_edit", String.valueOf(period_edit));

// example:	session.getAttribute("delete_contract").equals("true")
%>