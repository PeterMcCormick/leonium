package main.utils.metadata;

import main.testdomains.samples.addressgenerator.trials.AddressGeneratorDemo0;
import main.utils.Utils;
import main.utils.browserutils.BrowserHandler;

public class ActorFactory {
	public static ActorMetaData getDefaultActor() {
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

	public static ActorMetaData getLiveActor(BrowserHandler web) {
		AddressGeneratorDemo0 agd = new AddressGeneratorDemo0(web);
		agd.run();
		
		int socialSecurityNumber = AbstractActorMetaData.createSocialSecurityNumber();
		String firstName = AbstractActorMetaData.createUniqueName();
		String lastName = AbstractActorMetaData.createUniqueName();
		String email = AbstractActorMetaData.createEmailAddress(firstName, lastName);
		String[] address = Utils.randomItem(agd.getAddresses()).split(",");
		String addressLine1 = address[0];
		String addressLine2 = "";
		String city = address[1];
		String state = address[2];
		String zipcode = address[3];

		return new ActorMetaData(firstName, lastName, email, addressLine1, addressLine2, city, state, zipcode,
				socialSecurityNumber);
	}
}
