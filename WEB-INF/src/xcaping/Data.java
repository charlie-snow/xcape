package xcaping;

public class Data {

	private String country_name = "Country";
	private String region_name = "Region";
	private String area_name = "Area";
	private String resort_name = "Resort";
	private String property_name = "Property";
	
	public String name = "";
	public String father_name = "";
	
	private String country_jsp_name = "country";
	private String region_jsp_name = "region";
	private String area_jsp_name = "area";
	private String resort_jsp_name = "resort";
	private String property_jsp_name = "property";

	public String jsp_name = "";
	
	/*private String country_table_name = "CountryNameTBL";
	private String region_table_name = "RegionNameTBL";
	private String area_table_name = "AreaNameTBL";
	private String resort_table_name = "ResortNameTBL";
	private String property_table_name = "Property";*/
	
	public String table_name = "";
	
	// it won't be necessary to repeat all url if on the property url were "propertyid"(standard) instead of "propid" 
	private String country_brochure = "http://www.xcapewithus.com/site/brochers/country.asp?CountryID=";
	private String region_brochure = "http://www.xcapewithus.com/site/brochers/region.asp?RegionID=";
	private String area_brochure = "http://www.xcapewithus.com/site/brochers/area.asp?AreaID=";
	private String resort_brochure = "http://www.xcapewithus.com/site/brochers/resort.asp?ResortID=";
	private String property_brochure = "http://www.xcapewithus.com/site/brochers/property.asp?propid=";
	
	public String brochure = "";
	// -------------
	
	public String headings_table_name = "HeadingTBL";
	public String photos_table_name = "PhotoSelectTBL";
	public String descriptions_table_name = "DescriptionTBL";
	public String field_name = "ID";
	
	public void getNames(String item_type) {
		int item = Integer.parseInt(item_type);
		switch (item) {
			case 1: 
					this.name = this.country_name;
					this.jsp_name = this.country_jsp_name;
					this.table_name = this.country_name;
					break;
			case 2: 
					this.name = this.region_name;
					this.father_name = this.country_name;
					this.jsp_name = this.region_jsp_name;
					this.table_name = this.region_name;
					break;
			case 3: 
					this.name = this.area_name;
					this.father_name = this.region_name;
					this.jsp_name = this.area_jsp_name;
					this.table_name = this.area_name;
					break;
			case 4: 
					this.name = this.resort_name;
					this.father_name = this.area_name;
					this.jsp_name = this.resort_jsp_name;
					this.table_name = this.resort_name;
					break;
			case 5: 
					this.name = this.property_name;
					this.father_name = this.resort_name;
					this.jsp_name = this.property_jsp_name;
					this.table_name = this.property_name;
					break;
		}
	}
	// it won't be necessary to repeat all url if on the property url were "propertyid"(standard) instead of "propid"
	public void getBrochures(String item_type) {
		int item = Integer.parseInt(item_type);
		switch (item) {
			case 1: 
					this.brochure = this.country_brochure;
					break;
			case 2: 
					this.brochure = this.region_brochure;
					break;
			case 3: 
					this.brochure = this.area_brochure;
					break;
			case 4: 
					this.brochure = this.resort_brochure;
					break;
			case 5: 
					this.brochure = this.property_brochure;
					break;
			}
	}
	// ---------------------------	
}
