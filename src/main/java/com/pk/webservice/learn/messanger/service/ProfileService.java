package com.pk.webservice.learn.messanger.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.pk.webservice.learn.messanger.database.DatabaseClass;
import com.pk.webservice.learn.messanger.model.Profile;

public class ProfileService {
private Map<String, Profile> profiles = DatabaseClass.getProfiles();
	
	public ProfileService() {
		profiles.put("Punit", new Profile(1L,"Punit", "Punit" , "Singh"));	
		profiles.put("Suresh", new Profile(2L,"Suresh", "Suresh", "Nagaraja"));
	}

	public List<Profile> getAllProfile() {
		return new ArrayList<Profile>(profiles.values());
	}

	public Profile getProfile(String profileName) {
		return profiles.get(profileName);
	}

	public Profile addProfile(Profile profile) {
		profile.setId(profiles.size() + 1);
		profiles.put(profile.getProfileName(), profile);
		return profile;
	}

	public Profile updateProfile(Profile profile) {
		if (profile.getProfileName().isEmpty()) {
			return null;
		}
		profiles.put(profile.getProfileName(), profile);
		return profile;
	}

	public Profile removeProfile(String profileName) {
		return profiles.remove(profileName);
	}
}
