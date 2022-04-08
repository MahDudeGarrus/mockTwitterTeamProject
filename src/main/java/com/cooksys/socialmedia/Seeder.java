package com.cooksys.socialmedia;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.cooksys.socialmedia.embeddables.CredentialsEmbeddable;
import com.cooksys.socialmedia.embeddables.ProfileEmbeddable;
import com.cooksys.socialmedia.entities.Hashtag;
import com.cooksys.socialmedia.entities.Tweet;
import com.cooksys.socialmedia.entities.User;
import com.cooksys.socialmedia.repositories.UserRepository;
import com.cooksys.socialmedia.repositories.HashtagRepository;
import com.cooksys.socialmedia.repositories.TweetRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class Seeder implements CommandLineRunner{
	
	private final UserRepository userRepository;
	private final TweetRepository tweetRepository;
	private final HashtagRepository hashtagRepository;

	@Override
	public void run(String... args) throws Exception {
		
		User jim = new User();
		CredentialsEmbeddable jimCredentials = new CredentialsEmbeddable();
		ProfileEmbeddable jimProfile = new ProfileEmbeddable();
		jimCredentials.setUsername("Jim");
		jimCredentials.setPassword("dunder");
		jim.setJoined(Timestamp.from(Instant.now()));
		jim.setDeleted(false);
		jimProfile.setFirstName("Jim");
		jimProfile.setLastName("Halpert");
		jimProfile.setEmail("jim@dundermifflin.com");
		jimProfile.setPhone("555-555-5555");
		jim.setCredentials(jimCredentials);
		jim.setProfile(jimProfile);
		
		
		User dwight = new User();
		CredentialsEmbeddable dwightCredentials = new CredentialsEmbeddable();
		ProfileEmbeddable dwightProfile = new ProfileEmbeddable();
		dwightCredentials.setUsername("Dwight");
		dwightCredentials.setPassword("mifflin");
		dwight.setJoined(Timestamp.from(Instant.now()));
		dwight.setDeleted(false);
		dwightProfile.setFirstName("Dwight");
		dwightProfile.setLastName("Schrute");
		dwightProfile.setEmail("dwight@dundermifflin.com");
		dwightProfile.setPhone("666-666-6666");
		dwight.setCredentials(dwightCredentials);
		dwight.setProfile(dwightProfile);
		
		User pam = new User();
		CredentialsEmbeddable pamCredentials = new CredentialsEmbeddable();
		ProfileEmbeddable pamProfile = new ProfileEmbeddable();
		pamCredentials.setUsername("Pam");
		pamCredentials.setPassword("dundermifflin");
		pam.setJoined(Timestamp.from(Instant.now()));
		pam.setDeleted(false);
		pamProfile.setFirstName("Pam");
		pamProfile.setLastName("Beasly");
		pamProfile.setEmail("pam@dundermifflin.com");
		pamProfile.setPhone("777-777-7777");
		pam.setCredentials(pamCredentials);
		pam.setProfile(pamProfile);
		
		//userRepository.saveAll(Arrays.asList(new User[] {jim, dwight, pam}));
	
		Timestamp time = new Timestamp(50);
		
		Tweet jimTweet = new Tweet();
		jimTweet.setAuthor(jim);
		
		//jimTweet.setPosted(Timestamp.from(Instant.now()));
		jimTweet.setPosted(time);
		System.out.println("tweet1>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + time);
		jimTweet.setDeleted(false);
		//jimTweet.setContent("Fact: Bears eat beets. Bears. Beets. Battlestar Galactica.");
		jimTweet.setContent("earliest");
		
		
		time.setTime(200000);
		Tweet jimTweet2 = new Tweet();
		jimTweet2.setAuthor(jim);
		
		//jimTweet2.setPosted(Timestamp.from(Instant.now()));
		jimTweet2.setPosted(time);
		System.out.println("tweet2>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + time);
		jimTweet2.setDeleted(false);
		//jimTweet2.setContent("Fact: Bears eat beets. Bears. Beets. Battlestar Galactica.");
		jimTweet2.setContent("middle");
		
		time.setTime(20000000);
		Tweet jimTweet3 = new Tweet();
		jimTweet3.setAuthor(jim);
		//jimTweet3.setPosted(Timestamp.from(Instant.now()));
		jimTweet3.setPosted(time);
		System.out.println("tweet3>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + time);
		jimTweet3.setDeleted(false);
		//jimTweet3.setContent("Fact: Bears eat beets. Bears. Beets. Battlestar Galactica.");
		jimTweet3.setContent("last");
		
		Tweet dwightTweet = new Tweet();
		dwightTweet.setAuthor(dwight);
		dwightTweet.setPosted(Timestamp.from(Instant.now()));
		dwightTweet.setDeleted(false);
		dwightTweet.setContent("Whenever I'm about to do something, I think, 'Would an idiot do that? ");
		
		Tweet pamTweet = new Tweet();
		pamTweet.setAuthor(pam);
		pamTweet.setPosted(Timestamp.from(Instant.now()));
		pamTweet.setDeleted(false);
		pamTweet.setContent("I SUGGESTED WE FLIP A COIN, BUT ANGELA SAID SHE DOESN'T LIKE TO GAMBLE.");
		
		//tweetRepository.saveAll(Arrays.asList(new Tweet[] {jimTweet, jimTweet2, jimTweet3, dwightTweet, pamTweet}));
	
		Hashtag working = new Hashtag();
		working.setLabel("#working");
		working.setFirstUsed(Timestamp.from(Instant.now()));
		working.setLastUsed(Timestamp.from(Instant.now()));
//		
//		List<Tweet> allTweets = new ArrayList<>();
//		allTweets.add(dwightTweet);
//		allTweets.add(jimTweet);
//		working.setTweets(allTweets);
		
//		hashtagRepository.saveAll(Arrays.asList(new Hashtag[] {working}));
		List<Hashtag> hashtags = new ArrayList<>();
		hashtags.add(working);
		jimTweet.setHashtags(hashtags);
		dwightTweet.setHashtags(hashtags);
		
		List<Tweet> jimTweets = new ArrayList<>();
		jimTweets.add(jimTweet);
		
		List<Tweet> dwightTweets = new ArrayList<>();
		dwightTweets.add(dwightTweet);
		
		jim.setTweets(jimTweets);
		dwight.setTweets(dwightTweets);

		
		List<Tweet> jimLikes = new ArrayList<>();
		jimLikes.add(dwightTweet);
		
		List<Tweet> dwightLikes = new ArrayList<>();
		dwightLikes.add(jimTweet);
		
		jim.setLikedTweets(jimLikes);
		dwight.setLikedTweets(dwightLikes);
		
		
		//List<User> jimFollowers = new ArrayList<>();
		List<User> jimFollowing = new ArrayList<>();
		//jimFollowers.add(dwight);
		jimFollowing.add(dwight);
		jimFollowing.add(pam);
		//jim.setFollowers(jimFollowers);
		jim.setFollowing(jimFollowing);
		
		//List<User> dwightFollowers = new ArrayList<>();
		List<User> dwightFollowing = new ArrayList<>();
		//dwightFollowers.add(jim);
		dwightFollowing.add(jim);
		dwightFollowing.add(pam);
		//dwight.setFollowers(dwightFollowers);
		dwight.setFollowing(dwightFollowing);
		
//		List<Tweet> jimMentions = new ArrayList<>();
//		jimMentions.add(dwightTweet);
//		jim.setMentions(jimMentions);
//		
//		List<Tweet> dwightMentions = new ArrayList<>();
//		dwightMentions.add(jimTweet);
//		dwight.setMentions(dwightMentions);

		List<User> jimMentions = new ArrayList<>();
		jimMentions.add(pam);
		jimMentions.add(dwight);
		jimTweet.setUsersMentioned(jimMentions);
		
		time.setTime(20000000);
		Tweet jimRepost = new Tweet();
		jimRepost.setAuthor(jim);
		jimRepost.setPosted(time);
		System.out.println("jimRepost>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + time);
		jimRepost.setDeleted(false);
		jimRepost.setContent(null);
		jimRepost.setRepostOf(dwightTweet);
		
		time.setTime(20000000);
		Tweet jimRepost2 = new Tweet();
		jimRepost2.setAuthor(jim);
		jimRepost2.setPosted(time);
		System.out.println("jimRepost2>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + time);
		jimRepost2.setDeleted(false);
		jimRepost2.setContent(null);
		jimRepost2.setRepostOf(pamTweet);
		
		time.setTime(20000000);
		Tweet pamRepost = new Tweet();
		pamRepost.setAuthor(pam);
		pamRepost.setPosted(time);
		System.out.println("pamRepost>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + time);
		pamRepost.setDeleted(false);
		pamRepost.setContent(null);
		pamRepost.setRepostOf(jimTweet);
		
		time.setTime(20000000);
		Tweet pamRepost2 = new Tweet();
		pamRepost2.setAuthor(pam);
		pamRepost2.setPosted(time);
		System.out.println("pamRepost>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + time);
		pamRepost2.setDeleted(false);
		pamRepost2.setContent(null);
		pamRepost2.setRepostOf(dwightTweet);
		
		userRepository.saveAll(Arrays.asList(new User[] {jim, dwight, pam}));
		tweetRepository.saveAll(Arrays.asList(new Tweet[] {dwightTweet, jimRepost, jimRepost2, pamRepost, pamRepost2, jimTweet, pamTweet}));
		hashtagRepository.saveAll(Arrays.asList(new Hashtag[] {working}));
		
		
	}

	
}
