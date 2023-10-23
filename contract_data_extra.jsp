<%
	String item_id = contract.property;
	xcaping.Item property = new xcaping.Item("5");
	property.get(contract.property);
	xcaping.Supplier supplier = new xcaping.Supplier();
	supplier.get(contract.supplier);
	String arrival_days = "";
	xcaping.Markup markup = new xcaping.Markup();
	markup.get(contract.markup);
	xcaping.Currency currency = new xcaping.Currency();
	currency.get(contract.currency);
%>