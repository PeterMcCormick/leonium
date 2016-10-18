package main.utils.metadata;

import main.utils.Utils;

public abstract class AbstractActorMetaData {

	private String firstName;
	private String lastName;
	private String email;
	private String addressLine1;
	private String addressLine2;
	private String city;
	private String state;
	private String zipcode;
	private int socialSecurityNumber;

	public int getSocialSecurityNumber() {
		return socialSecurityNumber;
	}

	public AbstractActorMetaData(String firstName, String lastName, String email, String addressLine1,
			String addressLine2, String city, String state, String zipcode, int socialSecurityNumber) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.addressLine1 = addressLine1;
		this.addressLine2 = addressLine2;
		this.city = city;
		this.state = state;
		this.zipcode = zipcode;
		this.socialSecurityNumber = socialSecurityNumber;
	}

	@SuppressWarnings("unused")
	private AbstractActorMetaData() {
	}

	public String getAddressLine1() {
		return addressLine1;
	}

	public String getAddressLine2() {
		return addressLine2;
	}

	public String getCity() {
		return city;
	}

	public String getEmail() {
		return email;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getState() {
		return state;
	}

	public String getZipcode() {
		return zipcode;
	}

	public static Integer createSocialSecurityNumber() {
		return Utils.randint(10000000, 999999999);
	}

	public static String createUniqueName() {
		String name = Utils.getColumnVal(System.nanoTime());
		String firstChar = Character.toString(name.charAt(0));
		return name.replaceFirst(firstChar, firstChar.toUpperCase());
	}

	public static String createAddressLine() {
		return String.format("%s %s Rd", createUniqueName(), createUniqueName());
	}

	public static String createEmailAddress(String firstName, String lastName) {
		String milliseed = Long.toString(System.currentTimeMillis());
		return String.format("%s.%s_%s@pobox.com", firstName, lastName, milliseed);
	}
}