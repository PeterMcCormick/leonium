package main.utils.metadata;

public class ActorFactory {
	public static AbstractActorMetaData getDefaultActor() {
		String milliseed = Long.toString(System.currentTimeMillis());
		String nanoseed = Long.toString(System.nanoTime());

		String firstName = AbstractActorMetaData.createUniqueName();
		String lastName = AbstractActorMetaData.createUniqueName();
		String email = AbstractActorMetaData.createEmailAddress(firstName, lastName);
		String addressLine1 = AbstractActorMetaData.createAddressLine();
		String addressLine2 = "";
		String city = "New Castle";
		String state = "DE";
		String zipcode = "19720";
		int socialSecurityNumber = AbstractActorMetaData.createSocialSecurityNumber();
		return new ActorMetaData(firstName, lastName, email, addressLine1, addressLine2, city, state, zipcode,
				socialSecurityNumber);
	}
}
