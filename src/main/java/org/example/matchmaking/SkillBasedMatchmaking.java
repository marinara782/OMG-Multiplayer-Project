package org.example.matchmaking;

public class SkillBasedMatchmaking {

    // Basic structure:
    //if players skill level is on par with another players skill level
    // they should play together
    // otherwise, continue searching and prevent search from picking up that user again

    //Extremities:
    //If a player has not been matched after x amount of time, the skill level threshold
    //should be increased so as to not make the player wait too long.
    //There needs to be a certain amount of time that a player has to wait. In extreme cases,
    //a player may need to be told that a match could not be found at this time.

    //High-level Structure
    //1. Iterate through all players looking for a match
    //2. Identify their respective skill levels
    //3. Match players with similar skill levels
    //4. If match found, remove those players from the pool of players looking for match
    //5. Ensure two players that are incompatible do not get matched repeatedly, and handle
    //other extremities as well.
}
