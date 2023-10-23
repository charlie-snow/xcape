<%
	String contract_id = request.getParameter("contract_id");
	String period_id = request.getParameter("period_id");
	String goto_next = request.getParameter("next");
	xcaping.Period period = new xcaping.Period();
	xcaping.Contract contract = new xcaping.Contract();
	contract.get(contract_id);
	
	String follow = "";

	// data gathering from the form -----------------

	period.active = request.getParameter("active");
	period.edit_date = request.getParameter("edit_date");
	period.contract = contract_id;
	period.from_date = request.getParameter("from_date");
	period.to_date = request.getParameter("to_date");
	period.minimum_stay = request.getParameter("minimum_stay");

	if (request.getParameter("active_monday") != null) {
		period.active_monday = request.getParameter("active_monday");
	} else {
		period.active_monday = "0";
	}
	if (request.getParameter("active_tuesday") != null) {
		period.active_tuesday = request.getParameter("active_tuesday");
	} else {
		period.active_tuesday = "0";
	}
	if (request.getParameter("active_wednesday") != null) {
		period.active_wednesday = request.getParameter("active_wednesday");
	} else {
		period.active_wednesday = "0";
	}
	if (request.getParameter("active_thursday") != null) {
		period.active_thursday = request.getParameter("active_thursday");
	} else {
		period.active_thursday = "0";
	}
	if (request.getParameter("active_friday") != null) {
		period.active_friday = request.getParameter("active_friday");
	} else {
		period.active_friday = "0";
	}
	if (request.getParameter("active_saturday") != null) {
		period.active_saturday = request.getParameter("active_saturday");
	} else {
		period.active_saturday = "0";
	}
	if (request.getParameter("active_sunday") != null) {
		period.active_sunday = request.getParameter("active_sunday");
	} else {
		period.active_sunday = "0";
	}
	
	if (request.getParameter("arrival_monday") != null) {
		period.arrival_monday = request.getParameter("arrival_monday");
	} else {
		period.arrival_monday = "0";
	}
	if (request.getParameter("arrival_tuesday") != null) {
		period.arrival_tuesday = request.getParameter("arrival_tuesday");
	} else {
		period.arrival_tuesday = "0";
	}
	if (request.getParameter("arrival_wednesday") != null) {
		period.arrival_wednesday = request.getParameter("arrival_wednesday");
	} else {
		period.arrival_wednesday = "0";
	}
	if (request.getParameter("arrival_thursday") != null) {
		period.arrival_thursday = request.getParameter("arrival_thursday");
	} else {
		period.arrival_thursday = "0";
	}
	if (request.getParameter("arrival_friday") != null) {
		period.arrival_friday = request.getParameter("arrival_friday");
	} else {
		period.arrival_friday = "0";
	}
	if (request.getParameter("arrival_saturday") != null) {
		period.arrival_saturday = request.getParameter("arrival_saturday");
	} else {
		period.arrival_saturday = "0";
	}
	if (request.getParameter("arrival_sunday") != null) {
		period.arrival_sunday = request.getParameter("arrival_sunday");
	} else {
		period.arrival_sunday = "0";
	}
	
	period.short_stay_suplement = request.getParameter("short_stay_suplement");
	if (request.getParameter("short_stay_percentage") != null) {
		period.short_stay_percentage = request.getParameter("short_stay_percentage");
	} else {
		period.short_stay_percentage = "0";
	}
	period.edited_by = request.getParameter("edited_by");
	period.air_conditioned = request.getParameter("air_conditioned");
	
	period.agent_group = request.getParameter("agent_group");

	// fillin of the data in the object prices --------------------

	java.util.Vector data_board_bases = new java.util.Vector();
	xcaping.Lists list_board_bases = new xcaping.Lists();
	java.util.Enumeration enum_data_board_bases;
	
	data_board_bases = list_board_bases.getBoardBasesContract(contract_id);
	xcaping.BoardBasis board_basis = new xcaping.BoardBasis();
	xcaping.BoardBasisPeriod board_basisPeriod = new xcaping.BoardBasisPeriod();
	
	java.util.Vector data_discounts = new java.util.Vector();
	xcaping.Lists list_discounts = new xcaping.Lists();
	java.util.Enumeration enum_data_discounts;
	
	data_discounts = list_discounts.getDiscountsContract(contract_id);
	xcaping.Discount discount = new xcaping.Discount();
	xcaping.DiscountPeriod discountPeriod = new xcaping.DiscountPeriod();
	
	
	xcaping.Lists list_rooms = new xcaping.Lists();
	java.util.Vector data_rooms = new java.util.Vector();
	data_rooms = list_rooms.getRoomsContract(contract_id, true);
	
	xcaping.Room room = new xcaping.Room();
	xcaping.RoomPeriod roomPeriod = new xcaping.RoomPeriod();
	
	xcaping.Prices prices = new xcaping.Prices();

	for (enum_data_board_bases = data_board_bases.elements(); enum_data_board_bases.hasMoreElements();) {
		board_basis = (xcaping.BoardBasis)enum_data_board_bases.nextElement();
		board_basisPeriod = new xcaping.BoardBasisPeriod();
		board_basisPeriod.board_basis_id = board_basis.id;
		board_basisPeriod.price = request.getParameter("board_basis_price"+board_basis.id);
		prices.board_bases.add(board_basisPeriod);
	} 

	for (enum_data_discounts = data_discounts.elements(); enum_data_discounts.hasMoreElements();) {
		discount = (xcaping.Discount)enum_data_discounts.nextElement();
		discountPeriod = new xcaping.DiscountPeriod();
		discountPeriod.discount_id = discount.id;
		discountPeriod.amount = request.getParameter("discount_amount"+discount.id);
		if (request.getParameter("discount_percentage"+discount.id) != null) {
			discountPeriod.percentage = request.getParameter("discount_percentage"+discount.id);
		} else {
			discountPeriod.percentage = "0";
		}
		prices.discounts.add(discountPeriod);
	} 

	java.util.Enumeration e;
	for (e = data_rooms.elements() ; e.hasMoreElements() ;) {
		room = (xcaping.Room)e.nextElement();
		
		roomPeriod = new xcaping.RoomPeriod();
		roomPeriod.room_id = room.id;
		roomPeriod.price = request.getParameter("room_price"+room.id);
		if (request.getParameter("room_percentage"+room.id) != null) {
			roomPeriod.percentage = request.getParameter("room_percentage"+room.id);
		} else {
			roomPeriod.percentage = "0";
		}
		roomPeriod.allotment = request.getParameter("room_allotment"+room.id);
		if (request.getParameter("room_unit"+room.id) != null) {
			roomPeriod.unit = request.getParameter("room_unit"+room.id);
		} else {
			roomPeriod.unit = "0";
		}
		roomPeriod.release = request.getParameter("room_release"+room.id);
		prices.rooms.add(roomPeriod);
	}

	period.offer = request.getParameter("offer");

	// if the period data is incorrect or any price is empty or 0 the period is not entered
	period.id = period_id;
	period.getURLs(contract_id);
	boolean offer = period.offer.equals("1");
	if (period.modify(prices, request.getParameter("last_complementary").equals("true"))) {
		String next_id = period.getIdNextPeriod(period.id, contract_id);
		if (next_id.equals("0") || !goto_next.equals("1")) {
			follow = period.returnURL;
			String comp_id = period.id;
			period.id = period.complementary_id;
			period.getURLs(contract_id);
			if (period.hasComplementary) {
				follow = period.editURL+"&first_complementary=0&complementary_id="+comp_id;
			}
		} else {
			follow = period.next_periodURL+"&period_id="+next_id;
		}
	} else {
		follow = period.errorURL+period.error;
	}

    response.sendRedirect(follow);
%>