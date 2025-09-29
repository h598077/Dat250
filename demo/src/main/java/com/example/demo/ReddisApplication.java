package com.example.demo;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import redis.clients.jedis.Jedis;

public class ReddisApplication {

    private static final String REDIS_HOST = "localhost";
    private static final int REDIS_PORT = 6379;
    private static final int CACHE_TTL_SECONDS = 300; // 5 minutes

    // Simulate DB query 
    private Map<Integer, Long> queryVotesFromDatabase(String pollId) {
        // Example: key = presentationOrder, value = voteCount
        Map<Integer, Long> votes = new HashMap<>();
        votes.put(1, 269L);
        votes.put(2, 268L);
        votes.put(3, 42L);
        return votes;
    }

    // Retrieve votes, using Redis cache
    public Map<Integer, Long> getPollVotes(String pollId) {
        String key = "poll:" + pollId + ":votes";

        try (Jedis jedis = new Jedis(REDIS_HOST, REDIS_PORT)) {
            Map<String, String> cached = jedis.hgetAll(key);

            if (!cached.isEmpty()) {
                // Cache hit → convert back to Integer keys
                return cached.entrySet().stream()
                        .collect(Collectors.toMap(
                                e -> Integer.parseInt(e.getKey().replace("option:", "")),
                                e -> Long.parseLong(e.getValue())
                        ));
            }

            // Cache miss → fetch from DB
            Map<Integer, Long> aggregatedVotes = queryVotesFromDatabase(pollId);

            // Save to Redis
            Map<String, String> toCache = aggregatedVotes.entrySet().stream()
                    .collect(Collectors.toMap(
                            e -> "option:" + e.getKey(),
                            e -> String.valueOf(e.getValue())
                    ));
            jedis.hset(key, toCache);
            jedis.expire(key, CACHE_TTL_SECONDS);

            return aggregatedVotes;
        }
    }

    // Increment vote count for an option (updates DB + cache)
    public void vote(String pollId, int optionPresentationOrder) {
        

        String key = "poll:" + pollId + ":votes";

        try (Jedis jedis = new Jedis(REDIS_HOST, REDIS_PORT)) {
            // Increment cache directly for strong consistency
            jedis.hincrBy(key, "option:" + optionPresentationOrder, 1);
            jedis.expire(key, CACHE_TTL_SECONDS); // refresh TTL
        }
    }

    public static void main(String[] args) {
    	ReddisApplication cache = new ReddisApplication();

        String pollId = "03ebcb7b-bd69-440b-924e-f5b7d664af7b";

        // Fetch votes (first time → cache miss)
        Map<Integer, Long> votes = cache.getPollVotes(pollId);
        System.out.println("Votes: " + votes);

        // Simulate a vote for option 2
        cache.vote(pollId, 2);

        // Fetch votes again (should be updated in cache)
        Map<Integer, Long> updatedVotes = cache.getPollVotes(pollId);
        System.out.println("Updated Votes: " + updatedVotes);
    }
}
